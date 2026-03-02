

package store.laptop.ordering.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Table(name = "order_items")
@Data
public class OrderItem {

	private Long productId;
	private Long qty;
	private BigDecimal cost;
}
