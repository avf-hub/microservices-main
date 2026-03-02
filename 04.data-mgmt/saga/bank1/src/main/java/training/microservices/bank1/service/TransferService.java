package training.microservices.bank1.service;

import training.microservices.bank1.domain.BankAccount;
import training.microservices.bank1.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
	private final BankAccountRepository accountRepository;

	public TransferService(BankAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Transactional
	public void transfer(Long accountNumber, Double amount) {
		BankAccount bankAccount =
			accountRepository.findById(accountNumber).get();
		bankAccount.setBalance(bankAccount.getBalance() + amount); // -100
	}

	@Transactional
	public void rollback(Long accountNumber, Double amount) {
		BankAccount bankAccount =
			accountRepository.findById(accountNumber).get();
		bankAccount.setBalance(bankAccount.getBalance() - amount); // -(-100)
	}
}
