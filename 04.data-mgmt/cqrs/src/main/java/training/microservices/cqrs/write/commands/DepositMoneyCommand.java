package training.microservices.cqrs.write.commands;

import training.microservices.cqrs.events.MoneyDepositedEvent;

import java.time.LocalDateTime;

public record DepositMoneyCommand(String accountId, String customerId, double amount) {
	public MoneyDepositedEvent toEvent() {
		return new MoneyDepositedEvent(accountId, customerId, LocalDateTime.now(), amount);
	}
}
