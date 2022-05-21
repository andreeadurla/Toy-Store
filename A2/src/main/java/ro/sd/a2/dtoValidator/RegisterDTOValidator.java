package ro.sd.a2.dtoValidator;

import org.apache.commons.lang3.StringUtils;
import ro.sd.a2.dto.RegisterDTO;
import ro.sd.a2.validator.UserValidator;

public class RegisterDTOValidator {

    public static void validate(RegisterDTO registerDTO) {

        UserValidator.validateName(registerDTO.getFirstName());
        UserValidator.validateName(registerDTO.getLastName());
        UserValidator.validateEmail(registerDTO.getEmail());

        if(StringUtils.isNotEmpty(registerDTO.getPhone()))
            UserValidator.validatePhone(registerDTO.getPhone());

        UserValidator.validatePassword(registerDTO.getPassword());
        UserValidator.validateConfirmPassword(registerDTO.getPassword(), registerDTO.getConfirmPassword());
    }

}
