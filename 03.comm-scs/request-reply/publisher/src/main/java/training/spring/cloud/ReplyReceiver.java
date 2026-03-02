package training.spring.cloud;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
@RestController
public class ReplyReceiver {

    private final StreamBridge streamBridge;

    public ReplyReceiver(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    private final Map<String, String> messagesMap = new HashMap<>();

    @GetMapping("/publish-reply")
    public void publishReply(@RequestParam(required = false) String word) {

        // Create Correlation ID & Topic we want replier to return response on
        String id = UUID.randomUUID().toString();
        messagesMap.put(id, word);

        Message<?> requestMsg = MessageBuilder.withPayload(word)
                .setHeader(KafkaHeaders.CORRELATION_ID, id)
                .build();
        System.out.println("Sending request with id: " + id);

        streamBridge.send("wordStreamReply", requestMsg);
    }

    @Bean
    public Consumer<Message<String>> responseReceiver() {
        return msg -> {
            // Get Correlation ID
            String correlationId = (String) msg.getHeaders()
                    .get(KafkaHeaders.CORRELATION_ID);
            System.out.println("Received response for correlation id: " +
                    correlationId);
            System.out.println("For request: "+
                    messagesMap.get(correlationId));
            System.out.println("We have received response: "+
                    msg.getPayload());
        };
    }

}
