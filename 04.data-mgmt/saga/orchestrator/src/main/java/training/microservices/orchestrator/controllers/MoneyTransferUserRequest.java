package training.microservices.orchestrator.controllers;

import lombok.Data;

@Data
public class MoneyTransferUserRequest {
	private String bankFrom;
	private Long accountIdFrom;
	private String bankTo;
	private Long accountIdTo;
	private Double amount;
}
