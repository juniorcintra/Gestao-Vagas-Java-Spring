package br.com.juniorcintra.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.juniorcintra.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.juniorcintra.gestao_vagas.modules.company.services.CompanyService;
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
}
