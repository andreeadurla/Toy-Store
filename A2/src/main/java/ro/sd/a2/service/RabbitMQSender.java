package ro.sd.a2.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.NotificationSoldProductsDTO;
import ro.sd.a2.dto.NotificationUnsoldProductsDTO;
import ro.sd.a2.dto.OrderDetailsForEmailDTO;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${queue.rabbitmq.exchange}")
    private String exchange;

    @Value("${queue.rabbitmq.routingkey.order}")
    private String routingKeyOrder;

    @Value("${queue.rabbitmq.routingkey.unsold.products}")
    private String routingKeyUnsoldProducts;

    @Value("${queue.rabbitmq.routingkey.sold.products}")
    private String routingKeySoldProducts;

    public void send(OrderDetailsForEmailDTO payload) {
        amqpTemplate.convertAndSend(exchange, routingKeyOrder, payload);
    }

    public void send(NotificationUnsoldProductsDTO payload) {
        amqpTemplate.convertAndSend(exchange, routingKeyUnsoldProducts, payload);
    }

    public void send(NotificationSoldProductsDTO payload) {
        amqpTemplate.convertAndSend(exchange, routingKeySoldProducts, payload);
    }
}
