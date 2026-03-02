package training.microservices.cqrs.read.service;

import training.microservices.cqrs.read.model.CustomerSavingsDto;

import java.util.List;

public interface SavingsQueryService {
	CustomerSavingsDto findSavings(String customerId);

	List<String> getCustomerIds();

	void dumpReadDatabase();
}
