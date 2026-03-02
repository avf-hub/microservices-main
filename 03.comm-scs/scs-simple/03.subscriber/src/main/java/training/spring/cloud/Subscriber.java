package training.spring.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@RestController
public class Subscriber {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Map<String, AtomicInteger> wordMap = new ConcurrentHashMap<>();

	@GetMapping
	public Map<String, AtomicInteger> getWords() {
		return wordMap;
	}

	@Bean
	public Consumer<String> process() {
		return word -> {
			logger.info("got word: {}", word);
			wordMap
				.computeIfAbsent(
					word,
					w -> new AtomicInteger(0)
				).incrementAndGet();
		};
	}
}
