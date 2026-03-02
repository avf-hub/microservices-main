package training.microservices.bank2.service;

import training.microservices.bank2.domain.BankAccount;
import training.microservices.bank2.repositories.BankAccountRepository;
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
		bankAccount.setBalance(bankAccount.getBalance() + amount);
	}

	@Transactional
	public void rollback(Long accountNumber, Double amount) {
		BankAccount bankAccount =
			accountRepository.findById(accountNumber).get();
		bankAccount.setBalance(bankAccount.getBalance() - amount);
	}
}
