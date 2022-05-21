package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.controller.AuthenticationController;
import ro.sd.a2.dto.*;
import ro.sd.a2.utils.ApplicationUtils;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class StockCheckService {

    private static final Logger logger = Logger.getLogger(StockCheckService.class.getName());

    @Autowired
    private RabbitMQSender rabbitMQOrderSender;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * Send a notification email to the admin with products that have not been sold
     * for at least 5 minutes. Each notification contains a subject, the date on which
     * the notification is sent, the list of unsold products and admin's email.
     * The admin who will receive the email is "george@shop.com"
     */
    public void notifyAdminAboutUnsoldProducts() {
        Duration duration = Duration.ofMinutes(5);
        List<UnsoldProductDTO> unsoldProductDTOs = productService.unsoldForAtLeast(duration);

        NotificationUnsoldProductsDTO notification = NotificationUnsoldProductsDTO.builder()
                .subject("Produsele nevandute de cel putin 5 minute")
                .date(new Date())
                .unsoldProducts(unsoldProductDTOs)
                .adminEmail("george@shop.com")
                .build();


        logger.log(Level.INFO, "Notification about unsold products is sent to email service");

        rabbitMQOrderSender.send(notification);
    }

    /**
     * Send a notification email to the admin with products that have been sold on that day.
     * Each notification contains a subject, the date on which
     * the notification is sent, the list of sold products and admin's email.
     * The admin who will receive the email is "george@shop.com"
     */
    public void notifyAdminAboutSoldProducts() {

        String currentDay = ApplicationUtils.getCurrentDay();

        PeriodDTO period = PeriodDTO.builder().startDate(currentDay).endDate(currentDay).build();
        SoldProductsDTO soldProductsDTO = orderItemService.findAllSoldProducts(period);

        NotificationSoldProductsDTO notification = NotificationSoldProductsDTO.builder()
                .subject("Produsele vandute in ziua " + currentDay)
                .date(new Date())
                .soldProducts(soldProductsDTO.getProducts())
                .adminEmail("george@shop.com")
                .build();

        logger.log(Level.INFO, "Notification about sold products is sent to email service");

        rabbitMQOrderSender.send(notification);
    }
}
