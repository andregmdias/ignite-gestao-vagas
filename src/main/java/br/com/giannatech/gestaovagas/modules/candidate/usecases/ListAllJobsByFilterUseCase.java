package br.com.giannatech.gestaovagas.modules.candidate.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.giannatech.gestaovagas.modules.company.entities.JobEntity;
import br.com.giannatech.gestaovagas.modules.company.repositories.JobRepository;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

	@Autowired
	private JobRepository jobRepository;

	public List<JobEntity> execute(String filter) {
		return this.jobRepository.findAByDescriptionContainingIgnoreCase(filter);
	}
}