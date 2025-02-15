package br.com.juniorcintra.gestao_vagas.modules.candidate.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(description = "Nome do candidato", required = true, example = "João da silva")
  private String name;

  @NotBlank()
  @Pattern(regexp = "\\S+", message = "O campo (username) não deve conter espaço.")
  @Schema(description = "Login do candidato", required = true, example = "nome.sobrenome")
  private String username;

  @Email(message = "O campo (email) deve conter um e-mail válido.")
  @Schema(description = "E-mail do candidato", required = true, example = "joao@silva.com")
  private String email;

  @Length(min = 10, max = 100)
  private String password;

  @Schema(description = "E-mail do candidato", required = true,
      example = "FullStack Developer | MBA | ReactJS | Java | PHP | Next.js | TypeScript | Node.js | TailwindCSS | React Native | SCRUM/KANBAN | APIs RESTful")
  private String description;
  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @CreationTimestamp
  private LocalDateTime updatedAt;

}
