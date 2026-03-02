package store.laptop.shipping.domain.model;

import jakarta.persistence.*;
import lombok.*;
import store.laptop.shipping.shared.model.web.dto.ShippingStatus;

@Entity
@Table(name = "shippings")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Shipping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long orderId;

	@Enumerated(EnumType.STRING)
	private ShippingStatus shippingStatus;
}
