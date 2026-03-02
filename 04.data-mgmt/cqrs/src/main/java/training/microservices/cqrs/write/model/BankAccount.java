package training.microservices.cqrs.write.model;

import lombok.Data;

@Data
public class BankAccount {
	private String customerId;
	private String accountId;
	private double balance;

	public BankAccount(String customerId, String accountId) {
		this.customerId = customerId;
		this.accountId = accountId;
		this.balance = 0.0;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		balance -= amount;
	}
}
