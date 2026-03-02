

package store.laptop.accounting.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * An {@link Invoice} object represents an invoice with
 * line of items for each part of the order.
 *
 * @author Alexander Yakunin
 */
@Entity
@Table(name = "invoices")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Invoice implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long orderId;
	private Long customerId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_id")
	@OrderColumn(name = "idx")
	@Singular
	private List<InvoiceItem> items = new LinkedList<>();

}
