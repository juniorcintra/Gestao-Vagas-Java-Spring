package br.com.juniorcintra.gestao_vagas.modules.company.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.juniorcintra.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.JobEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {

  @Autowired
  private CompanyService companyService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {

    try {
      var result = this.companyService.create(companyEntity);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  @PostMapping("/job")
  @PreAuthorize("hasRole('COMPANY')")
  @Tag(name = "Vagas", description = "Criação de vaga")
  @Operation(summary = "Criação de vaga", description = "Criação de vaga")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Criação de vaga", content = {
          @Content(schema = @Schema(implementation = JobEntity.class))
      })
  })
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO jobDTO,
      HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    try {
      var jobEntity = JobEntity.builder().title(jobDTO.getTitle())
          .companyId(UUID.fromString(companyId.toString())).description(jobDTO.getDescription())
          .benefits(jobDTO.getBenefits()).level(jobDTO.getLevel()).build();

      var result = this.companyService.createJob(jobEntity);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Tag(name = "Vagas", description = "Listagem de vagas")
  @Operation(summary = "Listagem de vagas", description = "Listagem de vagas")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Listagem de vagas", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  @SecurityRequirement(name = "bearerAuth")
  public List<JobEntity> listAllJobsByFilter(@RequestParam String description) {
    return this.companyService.listAllJobs(description);
  }
}
