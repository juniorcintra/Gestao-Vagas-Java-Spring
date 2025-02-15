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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;

  @PostMapping()
  @Operation(summary = "Cadastro do candidato",
      description = "Essa função é responsável cadastrar o candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Informações do candidato cadastrado",
          content = {
              @Content(schema = @Schema(implementation = CandidateEntity.class))
          }), @ApiResponse(responseCode = "400", description = "Candidato ja existe!")
  })
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
  @Operation(summary = "Perfil do candidato",
      description = "Essa função é responsável por buscar as informações do perfil do candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Informações do candidato", content = {
          @Content(schema = @Schema(implementation = CandidateEntity.class))
      }), @ApiResponse(responseCode = "400", description = "User not found!")
  })
  @SecurityRequirement(name = "bearerAuth")
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
