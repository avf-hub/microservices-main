package training.microservices.cqrs.write.commands;

import training.microservices.cqrs.events.MoneyWithdrawnEvent;

import java.time.LocalDateTime;

public record WithdrawMoneyCommand(String accountId, String customerId, double amount) {
	public MoneyWithdrawnEvent toEvent() {
		return new MoneyWithdrawnEvent(accountId, customerId, LocalDateTime.now(), amount);
	}
}
