package ro.sd.a2.service.document.invoice;

import org.springframework.stereotype.Service;
import ro.sd.a2.dto.OrderInvoiceDTO;
import ro.sd.a2.dto.OrderItemDTO;
import ro.sd.a2.service.document.DocumentStrategy;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class TxtInvoice implements DocumentStrategy<OrderInvoiceDTO> {

    @Override
    public File generate(OrderInvoiceDTO orderInvoiceDTO) throws IOException {

        File file = File.createTempFile("invoice", ".txt");
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("Toys Store\n\n");
        fileWriter.write("Factura\n");
        fileWriter.write("Comanda #" + orderInvoiceDTO.getId() + "\n\n");

        fileWriter.write("Furnizor: Toys Store SRL\n");
        fileWriter.write("C.I.F: RO 987456\n");
        fileWriter.write("Sediul: Strada Libertatii, Craiova\n");
        fileWriter.write("Tel/Fax: +40 32 555 52 14/67\n\n");

        fileWriter.write("Cumparator: " + orderInvoiceDTO.getCustomer() + "\n");
        fileWriter.write("Adresa: " + orderInvoiceDTO.getAddress() + "\n\n");

        List<OrderItemDTO> orderItemDTOs = orderInvoiceDTO.getItems();

        for(OrderItemDTO item: orderItemDTOs) {
            StringBuilder sb = new StringBuilder();
            DecimalFormat df = new DecimalFormat("#.##");

            sb.append(item.getName() + ", ");
            sb.append(item.getQuantity() + " Buc, ");
            sb.append(df.format(item.getUnit_price()) + " Lei, ");
            sb.append(df.format(item.getPrice()) + " Lei");
            sb.append("\n");

            fileWriter.write(sb.toString());
        }

        fileWriter.write("\nTotal: " + orderInvoiceDTO.getPrice() + " Lei\n");

        fileWriter.close();
        file.deleteOnExit();

        return file;
    }

}
