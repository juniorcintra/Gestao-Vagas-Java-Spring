package br.com.juniorcintra.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {

  @Value("${security.token.secret}")
  private String secretKey;

  @Value("${security.token.secret.candidate}")
  private String secretKeyCandidate;

  public String validateToken(String token) {
    token = token.replace("Bearer ", "");

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    try {
      var subject = JWT.require(algorithm).build().verify(token).getSubject();
      return subject;
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return "";
    }
  }


  public DecodedJWT validateTokenCandidate(String token) {
    token = token.replace("Bearer ", "");

    Algorithm algorithm = Algorithm.HMAC256(secretKeyCandidate);

    try {
      var decodedToken = JWT.require(algorithm).build().verify(token);

      return decodedToken;
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return null;
    }


  }
}
