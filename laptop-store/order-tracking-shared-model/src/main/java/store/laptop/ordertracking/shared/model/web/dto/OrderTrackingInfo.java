

package store.laptop.ordertracking.shared.model.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.laptop.ordering.shared.model.web.dto.OrderStatus;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;
import store.laptop.shipping.shared.model.web.dto.ShippingStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTrackingInfo {
	OrderStatus orderStatus;
	PaymentStatus paymentStatus;
	ShippingStatus shippingStatus;
}
