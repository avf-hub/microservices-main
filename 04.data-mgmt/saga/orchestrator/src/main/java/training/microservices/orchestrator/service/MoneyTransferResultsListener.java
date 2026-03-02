package training.microservices.orchestrator.service;

import training.microservices.orchestrator.domain.MoneyTransferResult;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
public class MoneyTransferResultsListener {
	private final MoneyTransferResultsService moneyTransferService;

	public MoneyTransferResultsListener(MoneyTransferResultsService moneyTransferService) {
		this.moneyTransferService = moneyTransferService;
	}

	@Bean
	public Consumer<MoneyTransferResult> process() {
		return moneyTransferResult -> {
			Boolean success = moneyTransferResult.getResult();
			if (success) {
				Long id = moneyTransferResult.getMoneyTransferRequest().getId();
				moneyTransferService.completeMoneyTransfer(id);
			} else {
				UUID moneyTransferUUID = moneyTransferResult
					.getMoneyTransferRequest()
					.getMoneyTransferUUID();
				moneyTransferService.rollbackMoneyTransfer(moneyTransferUUID);
			}
		};
	}

}
