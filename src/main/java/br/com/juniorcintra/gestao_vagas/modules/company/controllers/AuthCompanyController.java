package br.com.juniorcintra.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.juniorcintra.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.juniorcintra.gestao_vagas.modules.company.services.AuthCompanyService;

@RestController
@RequestMapping("/auth-company")
public class AuthCompanyController {

  @Autowired
  private AuthCompanyService authCompanyService;

  @PostMapping()
  public ResponseEntity<Object> auth(@RequestBody AuthCompanyDTO authCompanyDTO) {
    try {
      var result = this.authCompanyService.execute(authCompanyDTO);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
