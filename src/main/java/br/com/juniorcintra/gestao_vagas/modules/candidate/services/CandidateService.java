package br.com.juniorcintra.gestao_vagas.modules.candidate.services;

import java.util.UUID;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.juniorcintra.gestao_vagas.exceptions.JobNotFoundException;
import br.com.juniorcintra.gestao_vagas.exceptions.UserFoundException;
import br.com.juniorcintra.gestao_vagas.exceptions.UserNotFoundException;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.juniorcintra.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CandidateService {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CandidateEntity create(CandidateEntity candidateEntity) {
    this.candidateRepository
        .findByEmailOrUsername(candidateEntity.getEmail(), candidateEntity.getUsername())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    var password = this.passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);

    return this.candidateRepository.save(candidateEntity);
  }

  public CandidateEntity getProfile(UUID id) throws AuthenticationException {
    var candidate =
        this.candidateRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

    return candidate;
  }

  public void applyToJob(UUID candidateId, UUID jobId) {
    var candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> {
      throw new UserNotFoundException();
    });

    var job = this.jobRepository.findById(jobId).orElseThrow(() -> {
      throw new JobNotFoundException();
    });



  }
}
