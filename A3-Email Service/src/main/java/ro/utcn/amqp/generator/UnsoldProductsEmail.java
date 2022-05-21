package ro.utcn.amqp.generator;

import ro.utcn.amqp.dto.NotificationUnsoldProductsDTO;
import ro.utcn.amqp.entity.Email;

import java.util.HashMap;
import java.util.Map;

public class UnsoldProductsEmail implements EmailGenerator<NotificationUnsoldProductsDTO> {

    private final String from = "toysstore@toysstore.com";

    @Override
    public Email generate(NotificationUnsoldProductsDTO notification) {

        Map<String, Object> properties  = new HashMap<String, Object>();
        properties.put("notification", notification);

        Email email = Email.builder()
                .from(from)
                .to(notification.getAdminEmail())
                .htmlTemplate(new Email.HtmlTemplate("unsoldProductsEmail", properties))
                .subject("Produse nevandute")
                .build();

        return email;
    }

}
