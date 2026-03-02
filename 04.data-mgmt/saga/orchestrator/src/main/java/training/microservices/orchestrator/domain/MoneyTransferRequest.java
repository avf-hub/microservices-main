package training.microservices.orchestrator.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MoneyTransferRequest {
	@Id
	@GeneratedValue
	private Long id;

	private MoneyTransferType moneyTransferType;

	private UUID moneyTransferUUID;

	private String bankName;
	private Long accountId;
	private Double amount;

	private boolean completed;
	private boolean cancelled;
}
