package ro.sd.a2.dtoValidator;

import org.apache.commons.lang3.StringUtils;
import ro.sd.a2.dto.ClientDTO;
import ro.sd.a2.validator.UserValidator;

public class ClientDTOValidator {

    public static void validate(ClientDTO clientDTO) {

        UserValidator.isNotEmpty(clientDTO.getId());
        UserValidator.validateName(clientDTO.getFirstName());
        UserValidator.validateName(clientDTO.getLastName());
        UserValidator.validateEmail(clientDTO.getEmail());

        if(StringUtils.isNotEmpty(clientDTO.getPhone()))
            UserValidator.validatePhone(clientDTO.getPhone());

    }

}
