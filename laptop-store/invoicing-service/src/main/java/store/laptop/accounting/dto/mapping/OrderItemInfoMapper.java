package store.laptop.accounting.dto.mapping;

import org.mapstruct.Mapper;
import store.laptop.accounting.domain.model.InvoiceItem;
import store.laptop.ordering.shared.model.web.dto.OrderItemInfo;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface OrderItemInfoMapper {
	Collection<InvoiceItem> toInvoiceItems(Collection<OrderItemInfo> items);

	Collection<OrderItemInfo> toOrderItems(Collection<InvoiceItem> items);
}
