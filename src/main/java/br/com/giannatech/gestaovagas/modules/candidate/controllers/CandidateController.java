package br.com.giannatech.gestaovagas.modules.candidate.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import br.com.giannatech.gestaovagas.exceptions.UserFoundException;
import br.com.giannatech.gestaovagas.modules.candidate.dto.ProfileCandidateReponseDTO;
import br.com.giannatech.gestaovagas.modules.candidate.entities.ApplyJobEntity;
import br.com.giannatech.gestaovagas.modules.candidate.entities.CandidateEntity;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.CreateCandidateUseCase;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.ListAllJobsByFilterUseCase;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.ProfileCandidateUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/candidates")
@Tag(name = "Candidate", description = "Cadidate API")
public class CandidateController {

	@Autowired
	private CreateCandidateUseCase createCandidateUseCase;

	@Autowired
	private ProfileCandidateUseCase profileCandidateUseCase;

	@Autowired
	private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

	@Autowired
	private ApplyJobCandidateUseCase applyJobCandidateUseCase;

	@PostMapping
	@Operation(summary = "Creates a new candidate profile", description = "Creates a new candidate profile with the given params")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = CandidateEntity.class), mediaType = "application/json")
			}),
			@ApiResponse(responseCode = "404", description = "Usuário já cadastrado")
	})
	public ResponseEntity<Object> create(@RequestBody @Valid CandidateEntity candidateEntity) {
		try {
			var createdCandidate = this.createCandidateUseCase.execute(candidateEntity);
			return ResponseEntity.ok(createdCandidate);
		} catch (UserFoundException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
	}

	@GetMapping("/profile")
	@PreAuthorize("hasRole('CANDIDATE')")
	@Operation(summary = "Profile", description = "Get the profile of candidate data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = ProfileCandidateReponseDTO.class), mediaType = "application/json")
			}),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@SecurityRequirement(name = "jwt_token")
	public ResponseEntity<Object> get(HttpServletRequest request) {
		var id = request.getAttribute("candidate_id").toString();
		try {
			var profile = this.profileCandidateUseCase.execute(UUID.fromString(id));

			return ResponseEntity.ok(profile);
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/jobs")
	@PreAuthorize("hasRole('CANDIDATE')")
	@Operation(summary = "List all jobs", description = "List all jobs by filter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = CandidateEntity.class)), mediaType = "application/json")
			})
	})
	@SecurityRequirement(name = "jwt_token")
	public ResponseEntity<Object> findByFilter(@RequestParam String filter) {
		return ResponseEntity.ok(this.listAllJobsByFilterUseCase.execute(filter));
	}

	@PostMapping("/jobs/apply")
	@PreAuthorize("hasRole('CANDIDATE')")
	@Operation(summary = "Jobs Apply", description = "Candidate applies to a job offer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ApplyJobEntity.class)), mediaType = "application/json")
			})
	})
	@SecurityRequirement(name = "jwt_token")
	public ResponseEntity<Object> createApplyJobEntity(HttpServletRequest request, @RequestBody UUID jobId) {
		var id = request.getAttribute("candidate_id").toString();

		var candidateUUID = UUID.fromString(id);

		try {
			var applyJobCandidate = this.applyJobCandidateUseCase.execute(candidateUUID, jobId);
			return ResponseEntity.ok(applyJobCandidate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
