package store.laptop.accounting.service;

import org.springframework.stereotype.Service;
import store.laptop.accounting.domain.model.Invoice;
import store.laptop.accounting.domain.model.InvoiceRepository;
import store.laptop.accounting.dto.mapping.InvoiceMapper;
import store.laptop.invoicing.shared.model.web.dto.InvoiceData;

import java.util.Optional;

@Service
public class InvoiceService {
	private final InvoiceRepository invoiceRepository;
	private final InvoiceMapper invoiceMapper;

	public InvoiceService(InvoiceRepository invoiceRepository,
	                      InvoiceMapper invoiceMapper) {
		this.invoiceRepository = invoiceRepository;
		this.invoiceMapper = invoiceMapper;
	}

	public InvoiceData getInvoiceStatus(Long orderId) {
		Optional<Invoice> invoice = invoiceRepository.findByOrderId(orderId);
		if (invoice.isEmpty()) {
			InvoiceData invoiceData = new InvoiceData();
			invoiceData.setOrderId(orderId);
			return invoiceData;
		}
		return invoiceMapper.toInvoiceData(invoice.get());
	}

}
