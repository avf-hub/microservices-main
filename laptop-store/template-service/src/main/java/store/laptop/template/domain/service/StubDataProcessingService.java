

package store.laptop.template.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.laptop.template.domain.exception.BusinessException;
import store.laptop.template.domain.model.StubData;
import store.laptop.template.domain.model.StubDataRepository;

import java.util.NoSuchElementException;

@Service
public class StubDataProcessingService {
	private final StubDataRepository repository;

	@Autowired
	public StubDataProcessingService(StubDataRepository repository) {
		this.repository = repository;
	}

	public StubData getStabDataById(Long stubId) {
		try {
			return repository.findById(stubId).orElseThrow();
		} catch (NoSuchElementException ex) {
			throw new BusinessException("Explanation message", stubId, ex);
		}
	}
}
