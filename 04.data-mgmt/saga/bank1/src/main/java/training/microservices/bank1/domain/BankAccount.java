package training.microservices.bank1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BankAccount {
	@Id
	@GeneratedValue
	private Long id;

	private Double balance;
}
