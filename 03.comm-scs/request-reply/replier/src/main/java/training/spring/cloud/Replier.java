package training.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@SpringBootApplication
public class Replier {
    @Bean
    public Function<Message<String>, Message<String>> reply() {
        return request -> {
            // Process event
            String payload = request.getPayload();
            String uppercasedPayload = payload.toUpperCase();

            // Get the Topic to replyTo and correlation ID
            String cid = request.getHeaders()
                    .getOrDefault(KafkaHeaders.CORRELATION_ID, "").toString();
            System.out.println("Processing request with cid of: " + cid);

            return MessageBuilder.withPayload(uppercasedPayload)
                .setHeader(KafkaHeaders.CORRELATION_ID, cid)
                .build();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Replier.class, args);
    }
}
