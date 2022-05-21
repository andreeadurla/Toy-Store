package ro.utcn.amqp.generator;

import ro.utcn.amqp.dto.OrderDetailsDTO;
import ro.utcn.amqp.entity.Email;

import java.util.HashMap;
import java.util.Map;

public class PlaceOrderEmail implements EmailGenerator<OrderDetailsDTO> {

    private final String from = "toysstore@toysstore.com";

    @Override
    public Email generate(OrderDetailsDTO orderDetails) {

        Map<String, Object> properties  = new HashMap<String, Object>();
        properties.put("order", orderDetails);

        Email email = Email.builder()
                .from(from)
                .to(orderDetails.getUser().getEmail())
                .htmlTemplate(new Email.HtmlTemplate("placeOrderEmail", properties))
                .subject("Confirmare inregistrare comanda")
                .build();

        return email;
    }

}
