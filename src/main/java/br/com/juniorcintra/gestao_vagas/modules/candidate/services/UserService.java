package br.com.juniorcintra.gestao_vagas.modules.candidate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.juniorcintra.gestao_vagas.exceptions.UserFoundException;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class UserService {

  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity create(CandidateEntity candidateEntity) {
    this.candidateRepository
        .findByEmailOrUsername(candidateEntity.getEmail(), candidateEntity.getUsername())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    return this.candidateRepository.save(candidateEntity);
  }
}
