package br.com.giannatech.gestaovagas.modules.candidate.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.giannatech.gestaovagas.exceptions.JobNotFoundException;
import br.com.giannatech.gestaovagas.exceptions.UserNotFoundException;
import br.com.giannatech.gestaovagas.modules.candidate.entities.ApplyJobEntity;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.CandidateRepository;
import br.com.giannatech.gestaovagas.modules.company.repositories.JobRepository;

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
		validateCandidate(candidateId);
		validateJob(jobId);

		var applyJobEntity = ApplyJobEntity.builder().jobId(jobId).candidateId(candidateId).build();

		return applyJobRepository.save(applyJobEntity);
	}

	private void validateCandidate(UUID candidateId) {
		candidateRepository
				.findById(candidateId)
				.orElseThrow(UserNotFoundException::new);
	}

	private void validateJob(UUID jobId) {
		jobRepository
				.findById(jobId)
				.orElseThrow(JobNotFoundException::new);
	}
}
