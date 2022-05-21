package ro.sd.a2.dtoValidator;

import ro.sd.a2.dto.CreditCardDTO;
import ro.sd.a2.validator.CreditCardValidator;

public class CreditCardDTOValidator {

    public static void validate(CreditCardDTO creditCardDTO) {

        CreditCardValidator.isNotEmpty(creditCardDTO.getIdClient());
        CreditCardValidator.validateNoCard(creditCardDTO.getNoCard());
        CreditCardValidator.validateCVV(creditCardDTO.getCvv());
        CreditCardValidator.validateExpirationDate(creditCardDTO.getExpirationDate());
        CreditCardValidator.validateName(creditCardDTO.getName());
    }

}
