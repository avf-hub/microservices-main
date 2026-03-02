package training.microservices.cqrs.read.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerSavings {
	private final String customerId;
	private double savings = 0;

	public void modify(double amount) {
		savings += amount;
	}
}
