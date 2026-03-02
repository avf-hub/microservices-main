package training.spring.cloud;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {
    private final StreamBridge streamBridge;

    public PublisherController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @GetMapping("/publish")
    private void publish(@RequestParam(required = false) String word) {
        System.out.println(streamBridge.send("wordStreamErrors", word));
    }

}
