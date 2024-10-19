package school.cesar.monitora_pne_backend.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class EstrategiaSteps {

    private String url = "http://localhost:8080/api/estrategias";
    private ResponseEntity<String> response;

    @Autowired
    private RestTemplate restTemplate;

    private String novaEstrategiaJson;

    @Given("que eu tenha uma nova estratégia válida")
    public void prepararNovaEstrategia() {
        // Definindo a estratégia no formato JSON
        novaEstrategiaJson = """
        {
            "nome": "Nova Estratégia",
            "descricao": "Descrição da nova estratégia",
            "objetivo": "Aumentar a eficiência escolar"
        }
        """;
    }

    @When("eu envio a solicitação para criar a estratégia")
    public void enviarSolicitacaoCriacao() throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(novaEstrategiaJson, headers);
        Thread.sleep(5000); // Aguarde 5 segundos
        response = restTemplate.postForEntity(url, request, String.class);
    }

    @Then("a estratégia é criada com sucesso")
    public void validarCriacao() {
        Assertions.assertEquals(201, response.getStatusCodeValue(), "Verificando se o status é 201 Created");
    }
}
