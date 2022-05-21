package ro.sd.a2.dtoValidator;

import ro.sd.a2.dto.LoginDTO;
import ro.sd.a2.validator.UserValidator;

public class LoginDTOValidator {

    public static void validate(LoginDTO loginDTO) {

        UserValidator.validateEmail(loginDTO.getEmail());
        UserValidator.validatePassword(loginDTO.getPassword());
    }

}
