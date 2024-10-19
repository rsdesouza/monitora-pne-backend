package school.cesar.monitora_pne_backend.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EstrategiaSteps {

    @Autowired
    private MockMvc mockMvc;

    private String novaEstrategiaJson;
    private MvcResult response;

    @Given("que eu tenha uma nova estratégia válida")
    public void prepararNovaEstrategia() {
        novaEstrategiaJson = """
        {
            "nome": "Nova Estratégia",
            "descricao": "Descrição da nova estratégia",
            "objetivo": "Aumentar a eficiência escolar"
        }
        """;
    }

    @When("eu envio a solicitação para criar a estratégia")
    public void enviarSolicitacaoCriacao() throws Exception {
        response = mockMvc.perform(post("/api/estrategias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novaEstrategiaJson))
                .andExpect(status().isCreated()) // Verifica se o status é 201
                .andReturn();
    }

    @Then("a estratégia é criada com sucesso")
    public void validarCriacao() {
        int statusCode = response.getResponse().getStatus();
        Assertions.assertEquals(201, statusCode, "Verificando se o status é 201 Created");
    }
}
