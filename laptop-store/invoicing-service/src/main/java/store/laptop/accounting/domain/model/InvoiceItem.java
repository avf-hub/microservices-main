

package store.laptop.accounting.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_items")
@Getter @Setter @ToString
public class InvoiceItem {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long productId;
	private Long qty;
	private BigDecimal cost;
}
