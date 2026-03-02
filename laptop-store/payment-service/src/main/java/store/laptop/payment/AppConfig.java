package store.laptop.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "app")
@Getter @Setter
public class AppConfig {
	private Duration paymentDelay = Duration.ofSeconds(30);
}
