package training.microservices.orchestrator.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoneyTransferResult {
	private MoneyTransferRequest moneyTransferRequest;
	private Boolean result;
	private String failureReason;
}
