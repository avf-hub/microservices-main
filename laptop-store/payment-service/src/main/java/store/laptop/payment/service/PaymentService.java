package store.laptop.payment.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import store.laptop.commons.util.DelayedId;
import store.laptop.ordering.shared.model.web.dto.OrderData;
import store.laptop.payment.AppConfig;
import store.laptop.payment.clients.OrderServiceClient;
import store.laptop.payment.domain.model.Payment;
import store.laptop.payment.domain.model.PaymentRepository;
import store.laptop.payment.exception.PaymentProcessingException;
import store.laptop.payment.exception.PaymentValidationException;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final AppConfig appConfig;
	private final DelayQueue<DelayedId> paymentsQueue = new DelayQueue<>();

	private final OrderServiceClient orderServiceClient;

	public PaymentService(PaymentRepository paymentRepository,
	                      AppConfig appConfig,
	                      OrderServiceClient orderServiceClient) {
		this.paymentRepository = paymentRepository;
		this.appConfig = appConfig;
		this.orderServiceClient = orderServiceClient;
	}

	@Transactional
	public Long startPayment(Long orderId) {
		// looking for existing payment for this order
		Optional<Payment> paymentOptional = paymentRepository.findByOrderId(orderId);
		Payment payment;
		if (paymentOptional.isPresent()) {
			payment = paymentOptional.get();

			if (PaymentStatus.IN_PROCESS == payment.getPaymentStatus()) {
				throw new PaymentValidationException(
					"Payment is already in process",
					payment.getId()
				);
			}

			if (PaymentStatus.PAID == payment.getPaymentStatus()) {
				throw new PaymentValidationException(
					"Payment has already been made",
					payment.getId()
				);
			}

			payment.setPaymentStatus(PaymentStatus.IN_PROCESS);
		} else {
			OrderData orderData = orderServiceClient.getOrderData(orderId);
			payment = Payment.builder()
				.orderId(orderId)
				.paymentCost(orderData.getCost())
				.customerId(orderData.getCustomerId())
				.paymentStatus(PaymentStatus.IN_PROCESS)
				.build();
		}
		paymentRepository.save(payment);
		paymentsQueue.put(new DelayedId(payment.getId(), appConfig.getPaymentDelay()));
		return payment.getId();
	}

	@Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
	@Transactional
	public void processPayments() {
		DelayedId delayedId = paymentsQueue.poll();
		if (delayedId != null) {
			Payment payment = paymentRepository.findById(delayedId.getId()).orElseThrow();
			payment.setPaymentStatus(PaymentStatus.PAID);
			paymentRepository.save(payment);
		}
	}

	public PaymentStatus getStatusByOrderId(Long orderId) {
		return paymentRepository
			.findByOrderId(orderId)
			.orElse(noPayment())
			.getPaymentStatus();
	}

	private Payment noPayment() {
		return Payment.builder()
			.paymentStatus(PaymentStatus.UNPAID)
			.build();
	}

	public PaymentStatus getPaymentStatus(Long paymentId) {
		try {
			Payment payment = paymentRepository
				.findById(paymentId)
				.orElse(noPayment());
			return payment.getPaymentStatus();
		} catch (NoSuchElementException ex) {
			throw new PaymentProcessingException("Payment was not found",
				paymentId, ex);
		}
	}
}
