package training.jva083.mini;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RestController
public class MiniApp {
	private int instanceId;
	private Logger logger = LoggerFactory.getLogger(getClass());

	MiniApp() {
		this.instanceId = new Random().nextInt(1000);
		logger.info("Started instance with ID #{}", instanceId);
	}

	@GetMapping("/")
	public String hello() {
		return "Hello from MiniApp with ID: #%s".formatted(instanceId);
	}

	@GetMapping("/kill")
	public void kill() {
		System.exit(-1);
	}

	public static void main(String[] args) {
		SpringApplication.run(MiniApp.class, args);
	}
}
