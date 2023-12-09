package br.com.giannatech.gestaovagas.modules.candidate.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.giannatech.gestaovagas.exceptions.UserNotFoundException;
import br.com.giannatech.gestaovagas.modules.candidate.dto.ProfileCandidateReponseDTO;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.CandidateRepository;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

	@Autowired
	private CandidateRepository repository;

	public ProfileCandidateReponseDTO execute(UUID id) {
		var candidate = this.repository
				.findById(id)
				.orElseThrow(UserNotFoundException::new);

		return ProfileCandidateReponseDTO
				.builder()
				.description(candidate.getDescription())
				.username(candidate.getUsername())
				.email(candidate.getEmail())
				.id(candidate.getId())
				.name(candidate.getName())
				.build();
	}
}
