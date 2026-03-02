package training.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@SpringBootApplication
@RestController
public class Subscriber {

    @Bean
    public Consumer<Message<String>> process() {
        return message -> {
            System.out.println("Received message: " + message.getPayload());
            saveToDatabase(message.getPayload());
        };
    }

    private void saveToDatabase(String payload) {
        throw new RuntimeException("Database is down");
//        System.out.println("Saving to database: " + payload);
    }

    public static void main(String[] args) {
        SpringApplication.run(Subscriber.class, args);
    }
}
