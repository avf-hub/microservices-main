

package store.laptop.payment.shared.model.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentErrorResponse {

	private Long paymentId;
	private String message;
}
