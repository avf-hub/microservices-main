package training.grpcdemo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
	public record Address(String host, int port) {

	}

	private Address bookServiceAddress;
}
