package br.com.giannatech.gestao_vagas.modules.company.useCases;

import br.com.giannatech.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.giannatech.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.giannatech.gestao_vagas.modules.company.entities.JobEntity;
import br.com.giannatech.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.giannatech.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateJobUseCaseTest {

    @InjectMocks
    private CreateJobUseCase createJobUseCase;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CompanyRepository companyRepository;


    @Test
    public void should_be_able_to_create_a_job() {
        var company = CompanyEntity.builder().id(UUID.randomUUID())
            .cnpj("12345678912")
            .username("COMPANY_USERNAME")
            .email("company@email.com")
            .password("123456789")
            .website("https://companywebsite.com")
            .description("COMPANY_DESCRIPTION")
            .build();

        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        var jobParams = JobEntity.builder()
                .description("JOB_DESCRIPTION")
                .level("JOB_LEVEL")
                .benefits("JOB_BENEFITS")
                .companyId(company.getId())
                .build();

        var job = JobEntity.builder()
                .id(UUID.randomUUID())
                .description("JOB_DESCRIPTION")
                .level("JOB_LEVEL")
                .benefits("JOB_BENEFITS")
                .companyId(company.getId())
                .createdAt(LocalDateTime.now())
                .build();

        when(jobRepository.save(jobParams)).thenReturn(job);

        var result = createJobUseCase.execute(jobParams);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
        assertInstanceOf(JobEntity.class, result);
    }


    @Test
    public void shoub_not_be_able_to_create_a_job_if_the_company_doesnt_exists() {
        var companyId = UUID.randomUUID();

        var jobParams = JobEntity.builder()
                .description("JOB_DESCRIPTION")
                .level("JOB_LEVEL")
                .benefits("JOB_BENEFITS")
                .companyId(companyId)
                .build();

        when(companyRepository.findById(companyId))
                .thenReturn(Optional.empty());

        try {
            createJobUseCase.execute(jobParams);
        }catch (Exception e) {
            assertInstanceOf(CompanyNotFoundException.class, e);
            assertEquals("Company not found", e.getMessage());
        }
    }
}
