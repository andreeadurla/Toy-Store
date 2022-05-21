package ro.utcn.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.amqp.dto.NotificationSoldProductsDTO;
import ro.utcn.amqp.dto.NotificationUnsoldProductsDTO;
import ro.utcn.amqp.dto.OrderDetailsDTO;
import ro.utcn.amqp.dtoValidator.NotificationSoldProductsDTOValidator;
import ro.utcn.amqp.dtoValidator.NotificationUnsoldProductsDTOValidator;
import ro.utcn.amqp.dtoValidator.OrderDetailsDTOValidator;
import ro.utcn.amqp.entity.Email;
import ro.utcn.amqp.generator.PlaceOrderEmail;
import ro.utcn.amqp.generator.SoldProductsEmail;
import ro.utcn.amqp.generator.UnsoldProductsEmail;
import ro.utcn.amqp.service.EmailSenderService;

import javax.mail.MessagingException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class QueueListener {

    private static final Logger logger = Logger.getLogger(QueueListener.class.getName());

    @Autowired
    EmailSenderService senderService;

    @RabbitListener(queues = "${queue.rabbitmq.queue.order}")
    public void listen(OrderDetailsDTO payload) {

        PlaceOrderEmail placeOrderEmail = new PlaceOrderEmail();
        Email email = placeOrderEmail.generate(payload);

        try {
            OrderDetailsDTOValidator.validate(payload);
            senderService.sendEmail(email);
        } catch (MessagingException e) {
            logger.log(Level.INFO, "Cannot send email (order email); An error occurred: " +
                    e.getMessage());
        }
    }

    @RabbitListener(queues = "${queue.rabbitmq.queue.unsold.products}")
    public void listen(NotificationUnsoldProductsDTO payload) {

        UnsoldProductsEmail unsoldProductsEmail = new UnsoldProductsEmail();
        Email email = unsoldProductsEmail.generate(payload);

        try {
            NotificationUnsoldProductsDTOValidator.validate(payload);
            senderService.sendEmail(email);
        } catch (MessagingException e) {
            logger.log(Level.INFO, "Cannot send email (unsold products email); An error occurred: " +
                    e.getMessage());
        }
    }

    @RabbitListener(queues = "${queue.rabbitmq.queue.sold.products}")
    public void listen(NotificationSoldProductsDTO payload) {

        SoldProductsEmail soldProductsEmail = new SoldProductsEmail();
        Email email = soldProductsEmail.generate(payload);

        try {
            NotificationSoldProductsDTOValidator.validate(payload);
            senderService.sendEmail(email);
        } catch (MessagingException e) {
            logger.log(Level.INFO, "Cannot send email (sold products email); An error occurred: " +
                    e.getMessage());
        }
    }

}
