package training.bookservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "app")
public class AppConfig {
	private boolean emulateBusinessExceptions = false;
	private boolean emulateTimeouts = false;
}
