package ro.sd.a2.service.document.soldProductReport;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ro.sd.a2.dto.SoldProductsDTO;
import ro.sd.a2.service.document.DocumentStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfReport implements DocumentStrategy<SoldProductsDTO>  {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public File generate(SoldProductsDTO soldProducts) throws IOException {
        Context context = new Context();
        context.setVariable("soldProducts", soldProducts);

        String htmlBody = templateEngine.process("pdf-resources/pdf_sold_products", context);

        File file = File.createTempFile("report", ".pdf");
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
