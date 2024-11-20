package school.cesar.monitora_pne_backend;

import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = MonitoraPneBackendApplication.class)
public class CucumberSpringConfiguration {
}
