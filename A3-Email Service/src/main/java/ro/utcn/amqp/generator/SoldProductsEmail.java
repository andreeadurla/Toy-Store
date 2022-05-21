package ro.utcn.amqp.generator;

import ro.utcn.amqp.dto.NotificationSoldProductsDTO;
import ro.utcn.amqp.entity.Email;

import java.util.HashMap;
import java.util.Map;

public class SoldProductsEmail implements EmailGenerator<NotificationSoldProductsDTO> {

    private final String from = "toysstore@toysstore.com";

    @Override
    public Email generate(NotificationSoldProductsDTO notification) {

        Map<String, Object> properties  = new HashMap<String, Object>();
        properties.put("notification", notification);

        Email email = Email.builder()
                .from(from)
                .to(notification.getAdminEmail())
                .htmlTemplate(new Email.HtmlTemplate("soldProductsEmail", properties))
                .subject("Produse vandute")
                .build();

        return email;
    }

}