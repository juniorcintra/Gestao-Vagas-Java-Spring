package br.com.juniorcintra.gestao_vagas.modules.company.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.juniorcintra.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.JobEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.services.JobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private JobService jobService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO jobDTO,
      HttpServletRequest request) {

    try {
      var companyId = request.getAttribute("company_id");

      var jobEntity = JobEntity.builder().title(jobDTO.getTitle())
          .companyId(UUID.fromString(companyId.toString())).description(jobDTO.getDescription())
          .benefits(jobDTO.getBenefits()).level(jobDTO.getLevel()).build();


      var result = this.jobService.create(jobEntity);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

}
