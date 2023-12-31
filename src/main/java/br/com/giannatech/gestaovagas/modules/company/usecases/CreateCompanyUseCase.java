package br.com.giannatech.gestaovagas.modules.company.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.giannatech.gestaovagas.exceptions.UserFoundException;
import br.com.giannatech.gestaovagas.modules.company.entities.CompanyEntity;
import br.com.giannatech.gestaovagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CompanyEntity execute(CompanyEntity companyEntity) {
		this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
				.ifPresent(company -> {
					throw new UserFoundException();
				});

		var encodedPassword = passwordEncoder.encode(companyEntity.getPassword());
		companyEntity.setPassword(encodedPassword);

		return this.companyRepository.save(companyEntity);
	}

}
