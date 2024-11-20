package school.cesar.monitora_pne_backend.steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class EstrategiaSteps {

    private final String BASE_URL = "http://localhost:8080/api/estrategias";
    private final RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;

    @When("eu envio uma solicitação GET para \"/api/estrategias\"")
    public void listarEstrategias() {
        response = restTemplate.getForEntity(BASE_URL, String.class);
    }

    @When("eu envio uma solicitação POST para \"/api/estrategias\"")
    public void criarEstrategia() {
        String novaEstrategia = """
            {
                "nome": "Nova Estratégia",
                "descricao": "Descrição da nova estratégia",
                "objetivo": "Aumentar a eficiência escolar"
            }
        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(novaEstrategia, headers);
        response = restTemplate.postForEntity(BASE_URL, request, String.class);
    }

    @When("eu envio uma solicitação DELETE para \"/api/estrategias/{id}\"")
    public void excluirEstrategia(@PathVariable("id") int id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }

    @Then("eu recebo uma lista de estratégias cadastradas")
    public void validarListaEstrategias() {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("a estratégia é criada com sucesso com status {int}")
    public void validarCriacaoEstrategia(int status) {
        Assertions.assertEquals(status, response.getStatusCodeValue());
    }
}
