package store.laptop.payment.domain.model;

import jakarta.persistence.*;
import lombok.*;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "payments")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Payment {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private Long customerId;
	private Long orderId;
	private BigDecimal paymentCost;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
}
