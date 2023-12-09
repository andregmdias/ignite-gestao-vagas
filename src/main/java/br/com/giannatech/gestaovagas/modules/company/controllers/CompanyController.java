package br.com.giannatech.gestaovagas.modules.company.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.giannatech.gestaovagas.modules.company.entities.CompanyEntity;
import br.com.giannatech.gestaovagas.modules.company.usecases.CreateCompanyUseCase;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CreateCompanyUseCase createCompanyUseCase;

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody @Valid CompanyEntity companyEntity) {
		try {
			var createdCompany = this.createCompanyUseCase.execute(companyEntity);
			return new ResponseEntity<>(createdCompany, org.springframework.http.HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
	}
}