package training.microservices.cqrs.events;

import java.time.LocalDateTime;

public record MoneyWithdrawnEvent(String accountId, String customerId, LocalDateTime dateTime, double amount) {
}
