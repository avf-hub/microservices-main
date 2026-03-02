package training.microservices.cqrs.write.service.impl;

import training.microservices.cqrs.write.commands.DepositMoneyCommand;
import training.microservices.cqrs.write.commands.WithdrawMoneyCommand;
import training.microservices.cqrs.write.model.BankAccount;
import training.microservices.cqrs.write.service.BankAccountCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class BankAccountCommandHandlerImpl implements BankAccountCommandHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private final KafkaTemplate<String, Object> kafkaTemplate;

	// this is WRITE-DB
	private final ConcurrentHashMap<String, BankAccount> accounts = new ConcurrentHashMap<>();

	@Autowired
	public BankAccountCommandHandlerImpl(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override public void handle(WithdrawMoneyCommand command) {
		BankAccount account =
			resolveBankAccount(command.accountId(), command.customerId());
		account.withdraw(command.amount());
		publishEvent(command.toEvent());
	}

	@Override public void handle(DepositMoneyCommand command) {
		BankAccount account = resolveBankAccount(command.accountId(), command.customerId());
		account.deposit(command.amount());
		publishEvent(command.toEvent());
	}

	private void publishEvent(Object event) {
		logger.info("publishing event: {}", event);
		kafkaTemplate.send("bank-events-topic", event);
	}

	private BankAccount resolveBankAccount(String accountId, String customerId) {
		return accounts.computeIfAbsent(
			accountId,
			id -> new BankAccount(id, customerId)
		);
	}


	@Override public void dumpWriteDatabase() {
		logger.info("$write DB contents:");
		accounts.values()
			.stream()
			.forEach(account ->
				logger.info("\t %20s -> %s".formatted(account.getAccountId(), account))
			);
		logger.info("$$");

	}
}
