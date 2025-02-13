package br.com.juniorcintra.gestao_vagas.modules.candidate;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

  Optional<CandidateEntity> findByUsername(String username);

  Optional<CandidateEntity> findByEmail(String email);

  Optional<CandidateEntity> findByEmailOrUsername(String email, String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByEmailOrUsername(String email, String username);


}
