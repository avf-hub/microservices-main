package training.microservices.cqrs;

import training.microservices.cqrs.read.service.impl.SavingsQueryServiceImpl;
import training.microservices.cqrs.write.commands.DepositMoneyCommand;
import training.microservices.cqrs.write.commands.WithdrawMoneyCommand;
import training.microservices.cqrs.write.service.BankAccountCommandHandler;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleRunner implements ApplicationRunner {
	private final BankAccountCommandHandler commandHandler;
	private final SavingsQueryServiceImpl queryService;
	private final Faker faker;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ExampleRunner(
		BankAccountCommandHandler commandHandler,
		SavingsQueryServiceImpl queryService,
		Faker faker
	) {
		this.commandHandler = commandHandler;
		this.queryService = queryService;
		this.faker = faker;
	}

	@Override public void run(ApplicationArguments args) throws Exception {
		String customer1 = faker.funnyName().name();
		String customer2 = faker.funnyName().name();

		commandHandler.handle(new DepositMoneyCommand(customer1 + "-acc001", customer1, 100.0));
		commandHandler.handle(new DepositMoneyCommand(customer1 + "-acc002", customer1, 150.0));

		commandHandler.handle(new DepositMoneyCommand(customer2 + "-acc101", customer2, 300.0));
		commandHandler.handle(new WithdrawMoneyCommand(customer2 + "-acc101", customer2, 33.0));
		commandHandler.handle(new DepositMoneyCommand(customer2 + "-acc102", customer2, 1500.0));

		commandHandler.handle(new WithdrawMoneyCommand(customer1 + "-acc002", customer1, 15.0));

		commandHandler.dumpWriteDatabase();
		queryService.dumpReadDatabase();

		logger.info("let's wait a bit and check how the state of read DB changes");
		Thread.sleep(10000);

		queryService.dumpReadDatabase();
	}
}
