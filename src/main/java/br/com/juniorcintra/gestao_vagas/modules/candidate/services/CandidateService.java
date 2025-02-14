package br.com.juniorcintra.gestao_vagas.modules.candidate.services;

import java.util.UUID;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.juniorcintra.gestao_vagas.exceptions.UserFoundException;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CandidateService {

  @Autowired
  private CandidateRepository candidateRepository;

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
    var candidate = this.candidateRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    return candidate;
  }
}
