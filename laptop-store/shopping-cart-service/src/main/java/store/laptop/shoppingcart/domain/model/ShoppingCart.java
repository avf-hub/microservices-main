

package store.laptop.shoppingcart.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	@CollectionTable(
		name = "shopping_cart_items",
		joinColumns = @JoinColumn(name = "cart_id")
	)
	@Singular
	private List<ShoppingCartItem> items = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		ShoppingCart cart = (ShoppingCart) o;
		return id != null && Objects.equals(id, cart.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
