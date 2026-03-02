package training.microservices.cqrs.write.service;

import training.microservices.cqrs.write.commands.DepositMoneyCommand;
import training.microservices.cqrs.write.commands.WithdrawMoneyCommand;

public interface BankAccountCommandHandler {
	void handle(WithdrawMoneyCommand command);

	void handle(DepositMoneyCommand command);

	void dumpWriteDatabase();
}
