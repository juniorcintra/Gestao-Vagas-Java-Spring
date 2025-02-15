package br.com.juniorcintra.gestao_vagas.modules.candidate.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.juniorcintra.gestao_vagas.modules.candidate.entity.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
  Optional<CandidateEntity> findByEmailOrUsername(String email, String username);

  Optional<CandidateEntity> findByUsername(String username);
}
