package ro.utcn.amqp.dtoValidator;

import org.apache.commons.lang3.ObjectUtils;
import ro.utcn.amqp.dto.UserDTO;
import ro.utcn.amqp.exception.InvalidDataException;
import ro.utcn.amqp.message.WrongMessage;
import ro.utcn.amqp.validator.UserValidator;

public class UserDTOValidator {

    public static void validate(UserDTO userDTO) {

        if(ObjectUtils.isEmpty(userDTO.getId()))
            throw new InvalidDataException(WrongMessage.EMPTY_ID);

        UserValidator.validateName(userDTO.getName());
        UserValidator.validateEmail(userDTO.getEmail());
    }

}
