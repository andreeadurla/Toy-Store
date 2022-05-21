package ro.sd.a2.service.document.soldProductReport;

import ro.sd.a2.dto.SoldProductsDTO;
import ro.sd.a2.service.document.DocumentStrategy;

import java.io.File;
import java.io.IOException;

public class ReportService {

    private DocumentStrategy<SoldProductsDTO> reportStrategy;

    public ReportService(DocumentStrategy<SoldProductsDTO> reportStrategy) {
        this.reportStrategy = reportStrategy;
    }

    public File generateReport(SoldProductsDTO t) throws IOException {
        return reportStrategy.generate(t);
    }

}
