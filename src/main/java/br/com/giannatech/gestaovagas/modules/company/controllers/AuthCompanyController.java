package br.com.giannatech.gestaovagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.giannatech.gestaovagas.modules.company.dto.AuthCompanyRequestDTO;
import br.com.giannatech.gestaovagas.modules.company.usecases.AuthCompanyUseCase;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/companies")
public class AuthCompanyController {

	@Autowired
	private AuthCompanyUseCase authCompanyUseCase;

	@PostMapping("/auth")
	public ResponseEntity<Object> create(@RequestBody AuthCompanyRequestDTO dto) {
		try {
			var auth = this.authCompanyUseCase.execute(dto);
			return ResponseEntity.ok().body(auth);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário e/ou senha inválidos");
		}
	}
}
