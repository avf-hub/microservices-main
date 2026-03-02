

package store.laptop.commons.config;

import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Map;

@Getter
@ConfigurationProperties(prefix = "laptop-common-config")
@ConditionalOnClass(LaptopCommonAutoConfiguration.class)
public class LaptopCommonProperties {
	private final Map<String, ServiceProperties> services;

	@ConstructorBinding
	public LaptopCommonProperties(Map<String, ServiceProperties> services) {
		this.services = services;
	}

	public record ServiceProperties(String remoteHost, Integer port) {
		public String getUrl() {
			return "http://" + remoteHost() + ":" + port();
		}
	}
}
