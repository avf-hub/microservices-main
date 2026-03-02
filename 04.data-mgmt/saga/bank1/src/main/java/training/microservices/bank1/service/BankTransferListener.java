package training.microservices.bank1.service;

import training.microservices.orchestrator.domain.MoneyTransferRequest;
import training.microservices.orchestrator.domain.MoneyTransferResult;
import training.microservices.orchestrator.domain.MoneyTransferType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BankTransferListener {

	@Value("${bank.name}")
	String bankName;

	private final TransferService transferService;

	public BankTransferListener(TransferService transferService) {
		this.transferService = transferService;
	}

	@Bean
	public Function<MoneyTransferRequest, MoneyTransferResult> process() {
		return moneyTransferRequest -> {
			String bankName = moneyTransferRequest.getBankName();
			if (!this.bankName.equals(bankName)) return null;

			MoneyTransferType transferType = moneyTransferRequest.getMoneyTransferType();
			try {
				if (transferType == MoneyTransferType.TRANSFER) {
					transferService.transfer(
						moneyTransferRequest.getAccountId(),
						moneyTransferRequest.getAmount());
				} else if (transferType == MoneyTransferType.TRANSFER_ROLLBACK) {
					transferService.rollback(
						moneyTransferRequest.getAccountId(),
						moneyTransferRequest.getAmount());
				}
				return MoneyTransferResult.builder()
					.moneyTransferRequest(moneyTransferRequest)
					.result(true)
					.build();
			} catch (Exception e) {
				return MoneyTransferResult.builder()
					.moneyTransferRequest(moneyTransferRequest)
					.result(false)
					.failureReason(e.getMessage())
					.build();
			}
		};
	}

}
