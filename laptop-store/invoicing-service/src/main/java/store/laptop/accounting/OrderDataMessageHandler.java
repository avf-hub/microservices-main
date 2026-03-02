package store.laptop.accounting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.laptop.accounting.domain.model.Invoice;
import store.laptop.accounting.domain.model.InvoiceRepository;
import store.laptop.accounting.dto.mapping.InvoiceMapper;
import store.laptop.invoicing.shared.model.web.dto.InvoiceData;
import store.laptop.ordering.shared.model.web.dto.OrderData;

import java.util.function.Consumer;

@Configuration
public class OrderDataMessageHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final InvoiceRepository invoiceRepository;
	private final InvoiceMapper invoiceMapper;

	public OrderDataMessageHandler(
		InvoiceRepository invoiceRepository,
		InvoiceMapper invoiceMapper
	) {
		this.invoiceRepository = invoiceRepository;
		this.invoiceMapper = invoiceMapper;
	}

	@Bean
	public Consumer<OrderData> processOrder2Invoice() {
		return orderData -> {
			// Same invoice request can be delivered multiple times
			// We have to check if it was already added
			// this is `message consumer idempotency`
			if (invoiceRepository.findByOrderId(orderData.getOrderId()).isPresent()) {
				logger.info("The invoice for {} already created", orderData);
			} else {
				Invoice invoice = invoiceMapper.toInvoice(orderData);
				Invoice savedInvoice = invoiceRepository.save(invoice);

				InvoiceData invoiceData = invoiceMapper.toInvoiceData(savedInvoice);
				logger.info("invoice created: {}", invoiceData);
			}
		};
	}
}
