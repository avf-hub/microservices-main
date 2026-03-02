

package store.laptop.shoppingcart.controller.dto.mapping;

import org.mapstruct.Mapper;
import store.laptop.shoppingcart.domain.model.ShoppingCart;
import store.laptop.shoppingcart.shared.model.web.dto.CartContent;

@Mapper(componentModel = "spring")
public interface CartContentMapper {
	CartContent map(ShoppingCart shoppingCart);
}
