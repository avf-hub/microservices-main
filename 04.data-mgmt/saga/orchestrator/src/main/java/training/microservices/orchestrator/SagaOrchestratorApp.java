package training.microservices.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SagaOrchestratorApp {
	public static void main(String[] args) {
		SpringApplication.run(SagaOrchestratorApp.class, args);
	}
}
