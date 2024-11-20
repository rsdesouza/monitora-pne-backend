package school.cesar.monitora_pne_backend.steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class PlanoAcaoSteps {

    private final String BASE_URL = "http://localhost:8080/api/planos-acao";
    private final RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;

    @When("eu envio uma solicitação GET para \"/api/planos-acao\"")
    public void listarPlanosAcao() {
        response = restTemplate.getForEntity(BASE_URL, String.class);
    }

    @When("eu envio uma solicitação POST para \"/api/planos-acao\"")
    public void criarPlanoAcao() {
        String novoPlano = """
            {
                "nome": "Plano de Ação",
                "status": "EM_ANDAMENTO"
            }
        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(novoPlano, headers);
        response = restTemplate.postForEntity(BASE_URL, request, String.class);
    }

    @When("eu envio uma solicitação DELETE para \"/api/planos-acao/{int}\"")
    public void excluirPlanoAcao(int id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }

    @Then("eu recebo uma lista de planos de ação cadastrados")
    public void validarListaPlanosAcao() {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("o plano de ação é criado com sucesso com status {int}")
    public void validarCriacaoPlanoAcao(int status) {
        Assertions.assertEquals(status, response.getStatusCodeValue());
    }
}
