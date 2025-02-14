package br.com.juniorcintra.gestao_vagas.modules.candidate.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.juniorcintra.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import br.com.juniorcintra.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateService {

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateDTO authCandidateDTO)
      throws AuthenticationException {

    var user =
        this.candidateRepository.findByUsername(authCandidateDTO.username()).orElseThrow(() -> {
          throw new UsernameNotFoundException("User not found!");
        });

    var passwordMatches =
        this.passwordEncoder.matches(authCandidateDTO.password(), user.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException("Senha incorreta!");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofHours(2));
    var token = JWT.create().withIssuer("javagas").withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("CANDIDATE")).withSubject(user.getId().toString())
        .sign(algorithm);

    var authCandidateResponse = AuthCandidateResponseDTO.builder().access_token(token)
        .expires_in(expiresIn.toEpochMilli()).build();

    return authCandidateResponse;
  }

}
