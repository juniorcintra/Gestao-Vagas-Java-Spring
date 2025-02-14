package br.com.juniorcintra.gestao_vagas.modules.company.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.juniorcintra.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.JobEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.services.CompanyService;
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
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO jobDTO,
      HttpServletRequest request) {

    try {
      var companyId = request.getAttribute("company_id");

      var jobEntity = JobEntity.builder().title(jobDTO.getTitle())
          .companyId(UUID.fromString(companyId.toString())).description(jobDTO.getDescription())
          .benefits(jobDTO.getBenefits()).level(jobDTO.getLevel()).build();


      var result = this.companyService.createJob(jobEntity);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
}
