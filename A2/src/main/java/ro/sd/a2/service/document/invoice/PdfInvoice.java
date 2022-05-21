package ro.sd.a2.service.document.invoice;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ro.sd.a2.dto.OrderInvoiceDTO;
import ro.sd.a2.service.document.DocumentStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfInvoice implements DocumentStrategy<OrderInvoiceDTO> {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public File generate(OrderInvoiceDTO orderInvoice) throws IOException {

        Context context = new Context();
        context.setVariable("order", orderInvoice);

        String htmlBody = templateEngine.process("pdf-resources/pdf_order", context);

        File file = File.createTempFile("invoice", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlBody);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        finally {
            outputStream.close();
            file.deleteOnExit();
        }

        return file;
    }

}
