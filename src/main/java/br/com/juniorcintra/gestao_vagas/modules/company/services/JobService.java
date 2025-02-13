package br.com.juniorcintra.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.JobEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class JobService {

  @Autowired
  private JobRepository jobRepository;

  public JobEntity create(JobEntity jobEntity) {
    return this.jobRepository.save(jobEntity);
  }
}
