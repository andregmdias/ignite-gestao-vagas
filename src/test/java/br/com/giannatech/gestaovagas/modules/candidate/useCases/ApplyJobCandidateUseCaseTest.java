package br.com.giannatech.gestaovagas.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.giannatech.gestaovagas.exceptions.JobNotFoundException;
import br.com.giannatech.gestaovagas.exceptions.UserNotFoundException;
import br.com.giannatech.gestaovagas.modules.candidate.entities.ApplyJobEntity;
import br.com.giannatech.gestaovagas.modules.candidate.entities.CandidateEntity;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.CandidateRepository;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import br.com.giannatech.gestaovagas.modules.company.entities.JobEntity;
import br.com.giannatech.gestaovagas.modules.company.repositories.JobRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  void should_not_be_able_to_apply_job_with_candidate_not_found() {
    var idCandidate = UUID.randomUUID();
    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.empty());

    try {
      applyJobCandidateUseCase.execute(idCandidate, Mockito.any());
    } catch (Exception e) {
      assertInstanceOf(UserNotFoundException.class, e);
      assertEquals("User not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("Should not be able to apply job with job not found")
  void should_not_be_able_to_apply_job_with_job_not_found() {
    var idCandidate = UUID.randomUUID();
    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(Mockito.mock(CandidateEntity.class)));

    var idJob = UUID.randomUUID();
    when(jobRepository.findById(idJob)).thenReturn(Optional.empty());

    try {
      applyJobCandidateUseCase.execute(idCandidate, idJob);
    } catch (Exception e) {
      assertInstanceOf(JobNotFoundException.class, e);
      assertEquals("Job not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("Should be able to apply job with success")
  void should_be_able_to_apply_job_with_success() {
    var idCandidate = UUID.randomUUID();
    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(Mockito.mock(CandidateEntity.class)));

    var idJob = UUID.randomUUID();
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(Mockito.mock(JobEntity.class)));

    var applyJobEntityParams = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();

    var applyJobId = UUID.randomUUID();
    var applyJobEntity = ApplyJobEntity.builder().id(applyJobId).candidateId(idCandidate).jobId(idJob).build();

    when(applyJobRepository.save(applyJobEntityParams)).thenReturn(applyJobEntity);

    var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
    assertEquals(idCandidate, applyJobEntity.getCandidateId());
    assertEquals(idJob, applyJobEntity.getJobId());
    assertInstanceOf(ApplyJobEntity.class, result);
  }
}