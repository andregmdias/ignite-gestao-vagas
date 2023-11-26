package br.com.giannatech.gestao_vagas.modules.candidate.useCases;

import br.com.giannatech.gestao_vagas.exceptions.UserFoundException;
import br.com.giannatech.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.giannatech.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CandidateEntity execute(CandidateEntity candidateEntity) {
		this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
				.ifPresent(candidate -> {
					throw new UserFoundException();
				});

		var encodedPassword = passwordEncoder.encode(candidateEntity.getPassword());
		candidateEntity.setPassword(encodedPassword);

		return this.candidateRepository.save(candidateEntity);
	}
}
