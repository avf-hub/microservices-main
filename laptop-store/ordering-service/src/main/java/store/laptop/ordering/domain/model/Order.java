

package store.laptop.ordering.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer_orders")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Order {

	// TODO: add itemReservationRecordInfo list field

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long customerId;

	@ElementCollection
	@CollectionTable(
		name = "order_items",
		joinColumns = @JoinColumn(name = "order_id")
	)
	@Singular
	private Set<OrderItem> items = new HashSet<>();

	@Enumerated(EnumType.STRING)
	private OrderState orderState;

	public BigDecimal getTotalCost() {
		return getItems().stream()
			.map(OrderItem::getCost)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
