package br.com.juniorcintra.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.juniorcintra.gestao_vagas.exceptions.UserFoundException;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity create(CompanyEntity companyEntity) {
    this.companyRepository
        .findByEmailOrUsername(companyEntity.getEmail(), companyEntity.getUsername())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    return this.companyRepository.save(companyEntity);
  }
}
