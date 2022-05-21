package ro.sd.a2.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Value("${queue.rabbitmq.queue.order}")
    private String queueOrder;

    @Value("${queue.rabbitmq.queue.unsold.products}")
    private String queueUnsoldProducts;

    @Value("${queue.rabbitmq.queue.sold.products}")
    private String queueSoldProducts;

    @Value("${queue.rabbitmq.exchange}")
    private String exchange;

    @Value("${queue.rabbitmq.routingkey.order}")
    private String routingKeyOrder;

    @Value("${queue.rabbitmq.routingkey.unsold.products}")
    private String routingKeyUnsoldProducts;

    @Value("${queue.rabbitmq.routingkey.sold.products}")
    private String routingKeySoldProducts;

    @Bean
    Queue queueOrder() {
        return new Queue(queueOrder, false);
    }

    @Bean
    Queue queueUnsoldProducts() {
        return new Queue(queueUnsoldProducts, false);
    }

    @Bean
    Queue queueSoldProducts() {
        return new Queue(queueSoldProducts, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding bindingOrder(DirectExchange exchange) {
        return BindingBuilder.bind(queueOrder()).to(exchange).with(routingKeyOrder);
    }

    @Bean
    Binding bindingUnsoldProducts(DirectExchange exchange) {
        return BindingBuilder.bind(queueUnsoldProducts()).to(exchange).with(routingKeyUnsoldProducts);
    }

    @Bean
    Binding bindingSoldProducts(DirectExchange exchange) {
        return BindingBuilder.bind(queueSoldProducts()).to(exchange).with(routingKeySoldProducts);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
