package br.com.giannatech.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.giannatech.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.giannatech.gestao_vagas.modules.company.dto.CreateJobRequestDTO;
import br.com.giannatech.gestao_vagas.modules.company.entities.JobEntity;
import br.com.giannatech.gestao_vagas.modules.company.useCases.CreateJobUseCase;
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

@RestController
@RequestMapping("/companies")
@Tag(name = "Jobs", description = "Job API")
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/jobs")
  @PreAuthorize("hasRole('company')")
  @Operation(summary = "Creates a Job", description = "Creates a Job with de given params")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = JobEntity.class), mediaType = "application/json")
      })
  })
  @SecurityRequirement(name = "jwt_token")
  public ResponseEntity<Object> create(@RequestBody @Valid CreateJobRequestDTO createJobRequestDTO,
      HttpServletRequest request) {
    var companyId = request.getAttribute("company_id").toString();

    var jobEntity = JobEntity.builder()
        .description(createJobRequestDTO.getDescription())
        .level(createJobRequestDTO.getLevel())
        .benefits(createJobRequestDTO.getBenefits())
        .companyId(UUID.fromString(companyId))
        .build();

    var createdJob = this.createJobUseCase.execute(jobEntity);
    return ResponseEntity.ok(createdJob);
  }
}
