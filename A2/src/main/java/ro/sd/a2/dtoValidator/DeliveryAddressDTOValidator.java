package ro.sd.a2.dtoValidator;

import ro.sd.a2.dto.DeliveryAddressDTO;
import ro.sd.a2.validator.UserValidator;

public class DeliveryAddressDTOValidator {

    public static void validate(DeliveryAddressDTO deliveryAddressDTO) {

        UserValidator.validateName(deliveryAddressDTO.getFirstName());
        UserValidator.validateName(deliveryAddressDTO.getLastName());
        UserValidator.isNotEmpty(deliveryAddressDTO.getCounty());
        UserValidator.isNotEmpty(deliveryAddressDTO.getCity());
        UserValidator.isNotEmpty(deliveryAddressDTO.getAddress());
        UserValidator.validatePhone(deliveryAddressDTO.getPhone());
        UserValidator.isNotEmpty(deliveryAddressDTO.getId_client());

    }

}
