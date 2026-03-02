package training.microservices.orchestrator.controllers;

import training.microservices.orchestrator.service.MoneyTransferResultsService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("transfer")
public class MoneyTransferController {
	private final MoneyTransferResultsService moneyTransferService;

	public MoneyTransferController(MoneyTransferResultsService moneyTransferService) {
		this.moneyTransferService = moneyTransferService;
	}

	@PostMapping
	public String moneyTransfer(@RequestBody MoneyTransferUserRequest request) {
		UUID uuid = moneyTransferService.transfer(
			request.getBankFrom(),
			request.getAccountIdFrom(),
			request.getBankTo(),
			request.getAccountIdTo(),
			request.getAmount()
		);
		return uuid.toString();
	}

	@GetMapping
	public String getTransferStatus(String uuid) {
		return moneyTransferService.getTransferStatus(UUID.fromString(uuid));
	}
}
