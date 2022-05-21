package ro.utcn.amqp.dtoValidator;

import ro.utcn.amqp.dto.NotificationSoldProductsDTO;
import ro.utcn.amqp.validator.UserValidator;

public class NotificationSoldProductsDTOValidator {

    public static void validate(NotificationSoldProductsDTO notification) {

        UserValidator.validateEmail(notification.getAdminEmail());

    }

}
