package br.com.giannatech.gestaovagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.giannatech.gestaovagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.AuthCandidateUseCase;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/candidates")
public class AuthCandidateController {

	@Autowired
	private AuthCandidateUseCase authCandidateUseCase;

	@PostMapping("/auth")
	public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO dto) {
		try {
			var token = this.authCandidateUseCase.execute(dto);
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
}
