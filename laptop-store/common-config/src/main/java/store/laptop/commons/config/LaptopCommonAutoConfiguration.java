

package store.laptop.commons.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(LaptopCommonProperties.class)
@ConditionalOnProperty(prefix = "laptop-common-config", name = "enabled", matchIfMissing = true)
public class LaptopCommonAutoConfiguration {
	private final Logger logger = LoggerFactory.getLogger(LaptopCommonAutoConfiguration.class);

	@Value("${spring.application.name:default-application}")
	private String applicationName;

	@Value("${server.port:8080}")
	private Integer defaultPort;

	/**
	 * Customize port number for the Tomcat from the common configuration.
	 * See {@literal src/main/resources/application-default.yml} inside the {@literal laptop-common-config} dependency.
	 */
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatServerPortCustomizer(
		LaptopCommonProperties config) {
		Integer port;
		if (config.getServices().containsKey(applicationName)) {
			port = config.getServices().get(applicationName).port();
			logger.info("Tomcat server port will be overridden on {}", port);
		} else {
			port = defaultPort;
			logger.warn("Custom port for Tomcat server for service '{}' was NOT found. " +
				"Will be use current or default '{}'", applicationName, port);
		}
		return factory -> factory.setPort(port);
	}

	@Bean
	public Map<String, LaptopCommonProperties.ServiceProperties> serviceCommonConfig(LaptopCommonProperties config) {
		return config.getServices();
	}

	@PostConstruct
	public void showWarnLogMessage() {
		logger.warn("Common configuration is enabled! This feature should NOT BE USE in production. " +
			"To disable it use non default profile or set the 'laptop-common-config.enabled' property to 'false'");
	}

}
