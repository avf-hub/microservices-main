package training.microservices.bank1.controllers;

import training.microservices.bank1.domain.BankAccount;
import training.microservices.bank1.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class BankAccountController {
	private final BankAccountRepository accountRepository;

	public BankAccountController(BankAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@PostMapping
	public Long createAccount(Double balance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(balance);
		accountRepository.save(bankAccount);
		return bankAccount.getId();
	}

	@GetMapping("/{id}")
	public BankAccount getAccount(@PathVariable("id") Long id) {
		return accountRepository.findById(id).get();
	}
}
