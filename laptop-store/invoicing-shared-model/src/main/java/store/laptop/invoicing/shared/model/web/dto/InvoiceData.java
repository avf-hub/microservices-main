

package store.laptop.invoicing.shared.model.web.dto;

import lombok.Data;
import lombok.Singular;
import store.laptop.ordering.shared.model.web.dto.OrderItemInfo;

import java.util.Set;

@Data
public class InvoiceData {
	Long invoiceId;
	Long orderId;
	Long customerId;

	@Singular
	Set<OrderItemInfo> items;
}
