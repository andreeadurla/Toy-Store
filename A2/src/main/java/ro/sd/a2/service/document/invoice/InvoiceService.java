package ro.sd.a2.service.document.invoice;

import ro.sd.a2.service.document.DocumentStrategy;

import java.io.File;
import java.io.IOException;

public class InvoiceService<OrderInvoiceDTO> {

    private DocumentStrategy<OrderInvoiceDTO> invoiceStrategy;

    public InvoiceService(DocumentStrategy<OrderInvoiceDTO> invoiceStrategy) {
        this.invoiceStrategy = invoiceStrategy;
    }

    public File generateInvoice(OrderInvoiceDTO t) throws IOException {
        return invoiceStrategy.generate(t);
    }
}
