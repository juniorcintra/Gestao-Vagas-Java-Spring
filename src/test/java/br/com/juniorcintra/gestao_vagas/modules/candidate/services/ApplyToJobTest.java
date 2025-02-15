package br.com.juniorcintra.gestao_vagas.modules.candidate.services;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.juniorcintra.gestao_vagas.exceptions.UserNotFoundException;
import br.com.juniorcintra.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.juniorcintra.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyToJobTest {

  @InjectMocks
  private CandidateService candidateService;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private CandidateRepository candidateRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  public void should_not_be_able_to_apply_job_with_candidate_not_found() {

    try {
      candidateService.applyToJob(null, null);

    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }

  }


}
