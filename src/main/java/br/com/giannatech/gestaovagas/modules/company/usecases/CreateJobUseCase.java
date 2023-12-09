package br.com.giannatech.gestaovagas.modules.company.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.giannatech.gestaovagas.exceptions.CompanyNotFoundException;
import br.com.giannatech.gestaovagas.modules.company.entities.JobEntity;
import br.com.giannatech.gestaovagas.modules.company.repositories.CompanyRepository;
import br.com.giannatech.gestaovagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private CompanyRepository companyRepository;

	public JobEntity execute(JobEntity jobEntity) {
		companyRepository
				.findById(jobEntity.getCompanyId())
				.orElseThrow(CompanyNotFoundException::new);

		return this.jobRepository.save(jobEntity);
	}
}
