package training.microservices.cqrs.read.service.impl;

import training.microservices.cqrs.events.MoneyDepositedEvent;
import training.microservices.cqrs.events.MoneyWithdrawnEvent;
import training.microservices.cqrs.read.model.CustomerSavings;
import training.microservices.cqrs.read.model.CustomerSavingsDto;
import training.microservices.cqrs.read.service.SavingsQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@KafkaListener(
	topics = "bank-events-topic"
)
public class SavingsQueryServiceImpl implements SavingsQueryService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	// this is READ-DB
	private final ConcurrentHashMap<String, CustomerSavings> accounts =
		new ConcurrentHashMap<>();

	@KafkaHandler
	public void handleMoneyWithdrawnEvent(MoneyWithdrawnEvent event) {
		logger.info("got withdraw event: {}", event);
		CustomerSavings savings = resolveSavings(event.customerId());
		savings.modify(-event.amount());

		logger.info("current customer savings: {}", savings);
	}

	@KafkaHandler
	public void handleMoneyDepositedEvent(MoneyDepositedEvent event) {
		logger.info("got deposit event: {}", event);
		CustomerSavings savings = resolveSavings(event.customerId());
		savings.modify(event.amount());

		//logger.info("current customer savings: {}", savings);
	}

	private CustomerSavings resolveSavings(String customerId) {
		return accounts.computeIfAbsent(customerId, CustomerSavings::new);
	}

	@Override public CustomerSavingsDto findSavings(String customerId) {
		CustomerSavings savings = resolveSavings(customerId);
		return new CustomerSavingsDto(savings.getCustomerId(), savings.getSavings());
	}

	@Override public List<String> getCustomerIds() {
		return accounts.keySet().stream().toList();
	}

	@Override
	public void dumpReadDatabase() {
		logger.info("#read DB contents:");
		accounts.values()
			.stream()
			.forEach(customerSavingsDto ->
				logger.info("\t %20s -> %s".formatted(customerSavingsDto.getCustomerId(), customerSavingsDto))
			);
		logger.info("##");
	}

}
