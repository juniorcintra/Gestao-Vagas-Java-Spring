package br.com.juniorcintra.gestao_vagas.modules.company.controllers;

import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import br.com.juniorcintra.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.juniorcintra.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
  }

  @Test
  public void should_be_able_to_create_job() throws Exception {
    var createJob = CreateJobDTO.builder().benefits("Todos").description("Teste").level("Pleno")
        .title("Desenvolvedor Java").build();

    mvc.perform(MockMvcRequestBuilders.post("/company/job").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(createJob))
        .header("Authorization", TestUtils.generateToken(
            UUID.fromString("b1b3fcf8-befc-4173-86ce-49509c0d2f85"), "JAVAGAS_@123#")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
