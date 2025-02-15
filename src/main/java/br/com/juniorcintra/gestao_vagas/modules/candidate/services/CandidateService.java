package br.com.juniorcintra.gestao_vagas.modules.candidate.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.juniorcintra.gestao_vagas.exceptions.JobNotFoundException;
import br.com.juniorcintra.gestao_vagas.exceptions.UserFoundException;
import br.com.juniorcintra.gestao_vagas.exceptions.UserNotFoundException;
import br.com.juniorcintra.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.juniorcintra.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.juniorcintra.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.juniorcintra.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.juniorcintra.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CandidateService {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ApplyJobRepository applyJobRepository;

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

  public CandidateEntity getProfile(UUID id) {
    var candidate = this.candidateRepository.findById(id).orElseThrow(() -> {
      throw new UserNotFoundException();
    });

    return candidate;
  }

  public ApplyJobEntity applyToJob(UUID candidateId, UUID jobId) {
    this.candidateRepository.findById(candidateId).orElseThrow(() -> {
      throw new UserNotFoundException();
    });

    this.jobRepository.findById(jobId).orElseThrow(() -> {
      throw new JobNotFoundException();
    });

    var applyJob = ApplyJobEntity.builder().candidateId(candidateId).jobId(jobId).build();

    applyJob = applyJobRepository.save(applyJob);
    return applyJob;
  }
}
