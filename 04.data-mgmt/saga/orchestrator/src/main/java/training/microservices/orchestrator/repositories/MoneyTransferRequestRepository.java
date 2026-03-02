package training.microservices.orchestrator.repositories;

import training.microservices.orchestrator.domain.MoneyTransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface MoneyTransferRequestRepository
	extends JpaRepository<MoneyTransferRequest, Long> {

	Collection<MoneyTransferRequest> findAllByMoneyTransferUUID(UUID uuid);
}
