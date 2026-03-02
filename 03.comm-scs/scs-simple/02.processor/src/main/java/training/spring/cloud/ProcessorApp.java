package training.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class ProcessorApp {
	@Bean
	Function<String, String> toUpperCase() {
		return String::toUpperCase;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApp.class, args);
	}
}
