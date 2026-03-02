package store.laptop.accounting.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.laptop.accounting.domain.model.Invoice;
import store.laptop.invoicing.shared.model.web.dto.InvoiceData;
import store.laptop.ordering.shared.model.web.dto.OrderData;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
	Invoice toInvoice(InvoiceData invoiceData);

	Invoice toInvoice(OrderData invoiceData);

	@Mapping(source = "id", target = "invoiceId")
	InvoiceData toInvoiceData(Invoice invoice);
}
