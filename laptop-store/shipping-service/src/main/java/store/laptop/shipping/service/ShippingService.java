package store.laptop.shipping.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import store.laptop.commons.util.DelayedId;
import store.laptop.shipping.AppConfig;
import store.laptop.shipping.domain.model.Shipping;
import store.laptop.shipping.domain.model.ShippingRepository;
import store.laptop.shipping.exception.ShippingValidationException;
import store.laptop.shipping.shared.model.web.dto.ShippingStatus;

import java.util.Optional;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

@Service
public class ShippingService {
	private final ShippingRepository shippingRepository;
	private final AppConfig appConfig;
	private final DelayQueue<DelayedId> shippingQueue = new DelayQueue<>();

	public ShippingService(ShippingRepository shippingRepository,
						   AppConfig appConfig) {
		this.shippingRepository = shippingRepository;
		this.appConfig = appConfig;
	}

	@Transactional
	public Long startShipping(Long orderId) {
		// looking for existing shipping for this order
		Optional<Shipping> shippingOptional = shippingRepository.findByOrderId(orderId);
		Shipping shipping;
		if (shippingOptional.isPresent()) {
			shipping = shippingOptional.get();

			if (ShippingStatus.IN_DELIVERY == shipping.getShippingStatus()) {
				throw new ShippingValidationException(
					"Order is already in process of delivery",
					shipping.getId()
				);
			}

			if (ShippingStatus.DELIVERED == shipping.getShippingStatus()) {
				throw new ShippingValidationException(
					"Order is already delivered",
					shipping.getId()
				);
			}

			shipping.setShippingStatus(ShippingStatus.IN_DELIVERY);
		} else {
			shipping = Shipping.builder()
				.orderId(orderId)
				.shippingStatus(ShippingStatus.IN_DELIVERY)
				.build();
		}
		shippingRepository.save(shipping);
		shippingQueue.put(new DelayedId(shipping.getId(), appConfig.getShippingDelay()));
		return shipping.getId();
	}

	@Transactional
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
	public void processShippings() {
		// deliver all the requests in the queue
		DelayedId delayedId = shippingQueue.poll();
		while (delayedId != null) {
			Shipping shipping = shippingRepository.findById(delayedId.getId()).orElseThrow();
			shipping.setShippingStatus(ShippingStatus.DELIVERED);
			delayedId = shippingQueue.poll();
		}
	}

	public boolean clearShipping(Long orderId) {
		Optional<Shipping> shippingOptional = shippingRepository.findByOrderId(orderId);
		Shipping shipping;
		if (shippingOptional.isPresent()) {
			shipping = shippingOptional.get();
			if (ShippingStatus.IN_DELIVERY == shipping.getShippingStatus()) {
				shippingRepository.delete(shipping);
				return true;
			} else if (ShippingStatus.DELIVERED == shipping.getShippingStatus()) {
				throw new ShippingValidationException(
					"Order is already delivered",
					shipping.getId()
				);
			} else if (ShippingStatus.UNKNOWN == shipping.getShippingStatus()) {
				throw new ShippingValidationException(
					"Order is not in process of delivery",
					shipping.getId()
				);
			}
		} else {
			throw new ShippingValidationException(
					"Shipping for Order is not found",
					orderId
			);
		}
		return false;
	}

	private Shipping noShipping() {
		return Shipping.builder()
			.shippingStatus(ShippingStatus.UNKNOWN)
			.build();
	}

	public ShippingStatus getStatusByOrderId(Long orderId) {
		return shippingRepository
			.findByOrderId(orderId)
			.orElse(noShipping())
			.getShippingStatus();
	}

}
