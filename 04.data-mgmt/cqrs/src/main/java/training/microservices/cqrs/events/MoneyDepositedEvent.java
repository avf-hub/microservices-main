package training.microservices.cqrs.events;

import java.time.LocalDateTime;

public record MoneyDepositedEvent(String accountId, String customerId, LocalDateTime dateTime, double amount) {
}
