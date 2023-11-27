package br.com.giannatech.gestao_vagas.modules.company.repositories;

import br.com.giannatech.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.giannatech.gestao_vagas.modules.company.entities.JobEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JobRepositoryTest {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void should_be_able_to_save_a_new_job(){

		var company = CompanyEntity.builder()
				.cnpj("12345678912")
				.username("COMPANY_USERNAME")
				.email("company@email.com")
				.password("123456789")
				.website("https://companywebsite.com")
				.description("COMPANY_DESCRIPTION")
				.build();

		var dbCompany = entityManager.persistAndFlush(company);

		var jobParams = JobEntity.builder()
				.description("JOB_DESCRIPTION")
				.level("JOB_LEVEL")
				.benefits("JOB_BENEFITS")
				.companyId(dbCompany.getId())
				.createdAt(LocalDateTime.now())
				.build();

		var createdJob = jobRepository.save(jobParams);

		assertAll(
				() -> Assertions.assertNotNull(createdJob.getId()),
				() -> Assertions.assertNotNull(createdJob.getCreatedAt()),
				() -> assertEquals(jobParams.getLevel(), createdJob.getLevel()),
				() -> assertEquals(jobParams.getDescription(), createdJob.getDescription()),
				() -> assertEquals(jobParams.getBenefits(), createdJob.getBenefits())
		);
	}

	@Test
	public void should_not_be_able_to_save_a_new_job_when_some_required_param_is_missing(){

		var company = CompanyEntity.builder()
				.cnpj("12345678912")
				.username("COMPANY_USERNAME")
				.email("company@email.com")
				.password("123456789")
				.website("https://companywebsite.com")
				.description("COMPANY_DESCRIPTION")
				.build();

		var dbCompany = entityManager.persistAndFlush(company);

		var jobParams = JobEntity.builder()
				.description(null)
				.level("JOB_LEVEL")
				.benefits("JOB_BENEFITS")
				.companyId(dbCompany.getId())
				.createdAt(LocalDateTime.now())
				.build();

		var exception = assertThrows(
				Exception.class,
				() -> jobRepository.save(jobParams)
		);


		System.out.println(exception.getMessage());
	}
}