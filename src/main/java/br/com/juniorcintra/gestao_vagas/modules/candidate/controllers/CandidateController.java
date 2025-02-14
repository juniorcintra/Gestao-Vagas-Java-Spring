package br.com.juniorcintra.gestao_vagas.modules.candidate.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.juniorcintra.gestao_vagas.modules.candidate.services.CandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {

    try {
      var result = this.candidateService.create(candidateEntity);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  @GetMapping()
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {

    var candidateId = request.getAttribute("candidate_id");
    try {
      var profile = this.candidateService.getProfile(UUID.fromString(candidateId.toString()));

      return ResponseEntity.ok().body(profile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
