package store.laptop.accounting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.laptop.accounting.service.InvoiceService;
import store.laptop.invoicing.shared.model.web.dto.InvoiceData;

@RestController
@RequestMapping("invoices")
public class InvoiceController {

	private final InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping("byOrder/{orderId}")
	public InvoiceData getInvoiceData(@PathVariable Long orderId) {
		return invoiceService.getInvoiceStatus(orderId);
	}
}
