package br.com.giannatech.gestaovagas.modules.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.giannatech.gestaovagas.modules.company.entities.JobEntity;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

	List<JobEntity> findAByDescriptionContainingIgnoreCase(String filter);
}
