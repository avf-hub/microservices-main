package training.microservices.orchestrator.service;

import training.microservices.orchestrator.domain.MoneyTransferRequest;
import training.microservices.orchestrator.domain.MoneyTransferType;
import training.microservices.orchestrator.repositories.MoneyTransferRequestRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MoneyTransferResultsService {

	private final StreamBridge streamBridge;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	private final MoneyTransferRequestRepository moneyTransferRequestRepository;

	public MoneyTransferResultsService(StreamBridge streamBridge, MoneyTransferRequestRepository moneyTransferRequestRepository) {
		this.streamBridge = streamBridge;
		this.moneyTransferRequestRepository = moneyTransferRequestRepository;
	}

	public String getTransferStatus(UUID moneyTransferUUID) {
		Collection<MoneyTransferRequest> moneyTransferRequests =
			moneyTransferRequestRepository
				.findAllByMoneyTransferUUID(moneyTransferUUID);
		boolean allCompleted = true;
		for (MoneyTransferRequest moneyTransferRequest : moneyTransferRequests) {
			if (moneyTransferRequest.isCancelled()) {
				return "cancelled";
			}
			if (!moneyTransferRequest.isCompleted()) allCompleted = false;
		}
		if (allCompleted) {
			return "completed";
		} else {
			return "in process";
		}
	}

	public UUID transfer(String bankFrom,
	                     Long transferFromId,
	                     String bankTo,
	                     Long transferToId,
	                     Double amount) {
		UUID moneyTransferUUID = UUID.randomUUID();

		MoneyTransferRequest moneyTransferRequestFrom =
			MoneyTransferRequest.builder()
				.moneyTransferUUID(moneyTransferUUID)
				.bankName(bankFrom)
				.accountId(transferFromId)
				.amount(-amount)
				.moneyTransferType(MoneyTransferType.TRANSFER)
				.build();
		moneyTransferRequestFrom = moneyTransferRequestRepository.save(moneyTransferRequestFrom);
		streamBridge.send("transferRequests", moneyTransferRequestFrom);
		MoneyTransferRequest moneyTransferRequestTo =
			MoneyTransferRequest.builder()
				.moneyTransferUUID(moneyTransferUUID)
				.bankName(bankTo)
				.accountId(transferToId)
				.amount(+amount)
				.moneyTransferType(MoneyTransferType.TRANSFER)
				.build();
		moneyTransferRequestTo = moneyTransferRequestRepository.save(moneyTransferRequestTo);
		streamBridge.send("transferRequests", moneyTransferRequestTo);

		// define timeout check for the money transfer operation to complete
		executor.schedule(() -> {
			checkMoneyTransferCompleted(moneyTransferUUID);
		}, 10, TimeUnit.SECONDS);

		return moneyTransferUUID;
	}

	private void checkMoneyTransferCompleted(UUID moneyTransferUUID) {
		Collection<MoneyTransferRequest> moneyTransferRequests =
			moneyTransferRequestRepository
				.findAllByMoneyTransferUUID(moneyTransferUUID);
		boolean uncompleted = false;
		for (MoneyTransferRequest moneyTransferRequest : moneyTransferRequests) {
			if (!moneyTransferRequest.isCancelled() &&
				!moneyTransferRequest.isCompleted()) {
				uncompleted = true;
				break;
			}
		}
		if (uncompleted) {
			rollbackMoneyTransfer(moneyTransferUUID);
		}
	}

	@Transactional
	public void completeMoneyTransfer(Long id) {
		MoneyTransferRequest moneyTransferRequest =
			moneyTransferRequestRepository.findById(id).get();
		moneyTransferRequest.setCompleted(true);
	}

	/**
	 * Finds all operations related to this transfer and creates requests
	 * to rollback all these operations
	 *
	 * @param moneyTransferUUID UUID of money transfer operation
	 */
	@Transactional
	public void rollbackMoneyTransfer(UUID moneyTransferUUID) {
		Collection<MoneyTransferRequest> moneyTransferRequests =
			moneyTransferRequestRepository
				.findAllByMoneyTransferUUID(moneyTransferUUID);
		for (MoneyTransferRequest moneyTransferRequest : moneyTransferRequests) {
			if (!moneyTransferRequest.isCancelled()) {
				MoneyTransferRequest rollbackRequest = MoneyTransferRequest.builder()
					.moneyTransferType(MoneyTransferType.TRANSFER_ROLLBACK)
					.moneyTransferUUID(moneyTransferUUID)
					.bankName(moneyTransferRequest.getBankName())
					.accountId(moneyTransferRequest.getAccountId())
					.amount(moneyTransferRequest.getAmount())
					.build();
				streamBridge.send("transferRequests", rollbackRequest);
				moneyTransferRequest.setCancelled(true);
				moneyTransferRequest.setCompleted(false);
				moneyTransferRequestRepository.save(moneyTransferRequest);
			}
		}
	}

/***
 * MoneyTransfer BANK1->BANK2
 * moneyTransferRequest to BANK1: completed
 * moneyTransferRequest to BANK2: not completed, not canceled
 */

}
