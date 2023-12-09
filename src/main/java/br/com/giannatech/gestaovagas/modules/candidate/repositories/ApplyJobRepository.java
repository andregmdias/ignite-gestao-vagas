package br.com.giannatech.gestaovagas.modules.candidate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.giannatech.gestaovagas.modules.candidate.entities.ApplyJobEntity;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
