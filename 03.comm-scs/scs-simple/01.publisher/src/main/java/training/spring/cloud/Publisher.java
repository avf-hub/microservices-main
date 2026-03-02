package training.spring.cloud;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Publisher {
	private final StreamBridge streamBridge;

	public Publisher(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	@GetMapping("/publish")
	private void publish(@RequestParam String word) {
		streamBridge.send("wordStream", word);
	}
}
