package br.com.juniorcintra.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {

  @Schema(description = "Descrição da vaga", required = true, example = "Desenvolvedor Java")
  private String description;

  @Schema(description = "Benefícios da vaga", required = true, example = "VR,VA,Plano de saude")
  private String benefits;

  @Schema(description = "Level da vaga", required = true, example = "Junior,Pleno,Senior")
  private String level;

  @Schema(description = "Título da vaga", required = true, example = "Desenvolvedor Java")
  private String title;

}
