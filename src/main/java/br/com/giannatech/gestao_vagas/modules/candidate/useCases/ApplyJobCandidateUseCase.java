package br.com.giannatech.gestao_vagas.modules.candidate.useCases;

import br.com.giannatech.gestao_vagas.exceptions.JobNotFoundException;
import br.com.giannatech.gestao_vagas.exceptions.UserNotFoundException;
import br.com.giannatech.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.giannatech.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.giannatech.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.giannatech.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ApplyJobRepository applyJobRepository;

	public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
		candidateRepository
				.findById(candidateId)
				.orElseThrow(() -> new UserNotFoundException());

		jobRepository
				.findById(jobId)
				.orElseThrow(() -> new JobNotFoundException());

		var applyJobEntity = ApplyJobEntity.builder().jobId(jobId).candidateId(candidateId).build();

		return applyJobRepository.save(applyJobEntity);
	}
}
