package ro.utcn.amqp.dtoValidator;

import org.apache.commons.lang3.ObjectUtils;
import ro.utcn.amqp.dto.DeliveryAddressDTO;
import ro.utcn.amqp.exception.InvalidDataException;
import ro.utcn.amqp.message.WrongMessage;
import ro.utcn.amqp.validator.UserValidator;

public class DeliveryAddressDTOValidator {

    public static void validate(DeliveryAddressDTO deliveryAddressDTO) {

        UserValidator.validateName(deliveryAddressDTO.getFirstName());
        UserValidator.validateName(deliveryAddressDTO.getLastName());

        if(ObjectUtils.isEmpty(deliveryAddressDTO.getCounty()))
            throw new InvalidDataException(WrongMessage.INVALID_COUNTY);

        if(ObjectUtils.isEmpty(deliveryAddressDTO.getCity()))
            throw new InvalidDataException(WrongMessage.INVALID_CITY);

        if(ObjectUtils.isEmpty(deliveryAddressDTO.getAddress()))
            throw new InvalidDataException(WrongMessage.INVALID_ADDRESS);

        UserValidator.validatePhone(deliveryAddressDTO.getPhone());
    }

}
