package br.com.giannatech.gestaovagas.modules.candidate.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.giannatech.gestaovagas.modules.candidate.usecases.ListAllJobsByFilterUseCase;
import br.com.giannatech.gestaovagas.modules.company.entities.JobEntity;
import br.com.giannatech.gestaovagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
class ListAllJobsByFilterUseCaseTest {

  @InjectMocks
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Mock
  private JobRepository jobRepository;

  @Test
  @DisplayName("Should be able to list all jobs by filter")
  void should_be_able_to_list_all_jobs_by_filter() {
    var jobEntity = JobEntity.builder()
        .description("description")
        .level("level")
        .benefits("benefits")
        .companyId(UUID.randomUUID())
        .build();

    when(jobRepository.findAByDescriptionContainingIgnoreCase("descr")).thenReturn(Arrays.asList(jobEntity));

    var fetchedJobs = listAllJobsByFilterUseCase.execute("descr");
    assertEquals(1, fetchedJobs.size());
    assertEquals(jobEntity.getDescription(), fetchedJobs.get(0).getDescription());
    assertEquals(jobEntity.getLevel(), fetchedJobs.get(0).getLevel());
    assertEquals(jobEntity.getBenefits(), fetchedJobs.get(0).getBenefits());
    assertEquals(jobEntity.getCompanyId(), fetchedJobs.get(0).getCompanyId());
  }

}
