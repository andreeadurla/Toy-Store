package ro.utcn.amqp.dtoValidator;

import ro.utcn.amqp.dto.NotificationUnsoldProductsDTO;
import ro.utcn.amqp.validator.UserValidator;

public class NotificationUnsoldProductsDTOValidator {

    public static void validate(NotificationUnsoldProductsDTO notification) {

        UserValidator.validateEmail(notification.getAdminEmail());

    }
}
