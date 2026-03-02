

package store.laptop.ordertracking.shared.model.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.laptop.ordering.shared.model.web.dto.OrderStatus;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTrackingInfo {
	OrderStatus orderStatus;
	PaymentStatus paymentStatus;

	public String getShippingStatus() {
		return "UNKNOWN";
	}
}
