package training.mousestore.reviews;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
	@Getter @Setter
	private int mouseCount = 20;
	@Getter @Setter
	private int simulatedErrorPossibilityPercent = 0;
	@Getter @Setter
	private int simulatedLagPossibilityPercent = 0;

	private Random random = new Random();

	public void simulateRandomProblem() {
		if (randomTrue(simulatedLagPossibilityPercent)) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		if (randomTrue(simulatedErrorPossibilityPercent))
			throw new RuntimeException("random simulated business exception");
	}

	public boolean randomTrue(int percentage) {
		return random.nextInt(100) > 100 - percentage;
	}
}
