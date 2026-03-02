package store.laptop.payment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.laptop.payment.service.PaymentService;
import store.laptop.payment.shared.model.web.api.PaymentAPI;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;

@RestController
@RequestMapping("payments")
public class PaymentController implements PaymentAPI {
	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@Override
	public Long makePayment(Long orderId) {
		return paymentService.startPayment(orderId);
	}

	@Override
	public PaymentStatus getStatus(Long paymentId) {
		return paymentService.getPaymentStatus(paymentId);
	}

	@Override
	public PaymentStatus getStatusByOrder(Long orderId) {
		return paymentService.getStatusByOrderId(orderId);
	}
}
