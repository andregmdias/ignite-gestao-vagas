package br.com.giannatech.gestaovagas.modules.company.controllers;

import br.com.giannatech.gestaovagas.modules.company.dto.CreateJobRequestDTO;
import br.com.giannatech.gestaovagas.modules.company.entities.CompanyEntity;
import br.com.giannatech.gestaovagas.modules.company.repositories.CompanyRepository;
import br.com.giannatech.gestaovagas.utils.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private CompanyRepository companyRepository;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Test
	public void should_be_able_to_create_a_new_job() throws Exception {
		var company = CompanyEntity.builder()
				.cnpj("12345678912")
				.username("COMPANY_USERNAME")
				.email("company@email.com")
				.password("123456789")
				.website("https://companywebsite.com")
				.description("COMPANY_DESCRIPTION")
				.build();

		companyRepository.saveAndFlush(company);

		var createJobDTO = CreateJobRequestDTO.builder()
				.description("New job offer")
				.level("MID LEVEL")
				.benefits("ALL EXISTENT").build();

		mvc.perform(
				post("/companies/jobs")
						.header("Authorization", TestUtils.createCompanyJWT(company.getId(), "JAVAGAS_@123#"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.objectToJson(createJobDTO)))
				.andExpect(status().isOk());
	}

	@Test
	public void should_not_be_able_to_create_a_new_job_if_the_company_doesnt_exists() throws Exception {
		var companyId = UUID.randomUUID();

		var createJobDTO = CreateJobRequestDTO.builder()
				.description("New job offer")
				.level("MID LEVEL")
				.benefits("ALL EXISTENT").build();

		mvc.perform(
				post("/companies/jobs")
						.header("Authorization", TestUtils.createCompanyJWT(companyId, "JAVAGAS_@123#"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.objectToJson(createJobDTO)))
				.andExpect(status().isUnprocessableEntity());
	}
}