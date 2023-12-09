package br.com.giannatech.gestaovagas.modules.candidate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.giannatech.gestaovagas.modules.candidate.entities.CandidateEntity;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

	Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);

	Optional<CandidateEntity> findByUsername(String username);
}
