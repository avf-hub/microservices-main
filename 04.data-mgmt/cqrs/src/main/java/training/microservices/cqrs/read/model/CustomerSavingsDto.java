package training.microservices.cqrs.read.model;

import lombok.Data;

@Data
public class CustomerSavingsDto {
	private final String customerId;
	private final double currentSavings;
}
