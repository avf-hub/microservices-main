

package store.laptop.commons.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBootConfiguration
@Import(LaptopCommonAutoConfiguration.class)
class LaptopCommonPropertiesTest {
	@Autowired LaptopCommonProperties commonProperties;

	@Test void shouldInjectServicesPropertiesAsMap() {
		assertNotNull(commonProperties.getServices());
		assertFalse(commonProperties.getServices().isEmpty());
		assertEquals("localhost", commonProperties.getServices().get("catalog-service").remoteHost());
		assertEquals(8081, commonProperties.getServices().get("catalog-service").port());
	}
}
