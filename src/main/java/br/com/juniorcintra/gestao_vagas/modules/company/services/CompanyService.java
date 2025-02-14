package br.com.juniorcintra.gestao_vagas.modules.company.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.juniorcintra.gestao_vagas.exceptions.UserFoundException;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.JobEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.juniorcintra.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JobRepository jobRepository;

  public CompanyEntity create(CompanyEntity companyEntity) {
    this.companyRepository
        .findByEmailOrUsername(companyEntity.getEmail(), companyEntity.getUsername())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    var password = this.passwordEncoder.encode(companyEntity.getPassword());
    companyEntity.setPassword(password);

    return this.companyRepository.save(companyEntity);
  }

  public JobEntity createJob(JobEntity jobEntity) {
    return this.jobRepository.save(jobEntity);
  }

  public List<JobEntity> listAllJobs(String description) {
    return this.jobRepository.findByDescriptionContainingIgnoreCase(description);
  }
}
