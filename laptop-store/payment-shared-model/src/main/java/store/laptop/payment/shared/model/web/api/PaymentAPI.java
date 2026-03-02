package store.laptop.payment.shared.model.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;

public interface PaymentAPI {

	@PostMapping("byOrder/{orderId}")
	Long makePayment(@PathVariable("orderId") Long orderId);

	@GetMapping("{paymentId}/status")
	PaymentStatus getStatus(@PathVariable("paymentId") Long paymentId);

	@RequestMapping("byOrder/{orderId}/status")
	PaymentStatus getStatusByOrder(@PathVariable("orderId") Long orderId);

}
