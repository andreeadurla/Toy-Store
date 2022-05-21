package ro.sd.a2.service.document.soldProductReport;

import org.springframework.stereotype.Service;
import ro.sd.a2.dto.SoldProductsDTO;
import ro.sd.a2.service.document.DocumentStrategy;
import ro.sd.a2.utils.ApplicationUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

@Service
public class TxtReport implements DocumentStrategy<SoldProductsDTO> {

    @Override
    public File generate(SoldProductsDTO soldProducts) throws IOException {

        File file = File.createTempFile("report", ".txt");
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("Toys Store\n\n");
        fileWriter.write("Raport produse vandute\n");

        StringBuilder sb = new StringBuilder();

        sb.append("Perioada: ");
        sb.append(ApplicationUtils.printPrettyDate(soldProducts.getStartDate()));
        sb.append(" - ");
        sb.append(ApplicationUtils.printPrettyDate(soldProducts.getEndDate()));
        sb.append("\n\n");
        fileWriter.write(sb.toString());

        sb.setLength(0);

        Map<String, Integer> products = soldProducts.getProducts();
        for (Map.Entry<String, Integer> product : products.entrySet()) {

            sb.append(product.getKey() + " -> ");
            sb.append(product.getValue() + " Buc");
            sb.append("\n");

            fileWriter.write(sb.toString());

            sb.setLength(0);
        }

        fileWriter.close();
        file.deleteOnExit();

        return file;
    }
}
