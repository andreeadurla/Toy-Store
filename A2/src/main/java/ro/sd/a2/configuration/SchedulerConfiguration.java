package ro.sd.a2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ro.sd.a2.service.StockCheckService;


@Configuration
@EnableScheduling
public class SchedulerConfiguration {

    @Autowired
    private StockCheckService stockCheckService;

    /**
     * Every 5 minutes a notification is sent to the admin about the products
     * that have not been sold lately.
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduleCheckUnsoldProductsTask() {
        stockCheckService.notifyAdminAboutUnsoldProducts();
    }

    /**
     * Every day a notification is sent to the admin about the products
     * that have been sold in that day.
     */
    @Scheduled(cron = "0 02 15 * * ?")
    public void scheduleCheckSoldProductsTask() {
       stockCheckService.notifyAdminAboutSoldProducts();
    }
}
