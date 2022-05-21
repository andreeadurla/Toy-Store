package ro.sd.a2.dtoValidator;

import ro.sd.a2.dto.ShoppingCartItemDTO;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.validator.Validator;

public class ShoppingCartItemDTOValidator {

    public static void validate(ShoppingCartItemDTO shoppingCartItemDTO) {

        if(shoppingCartItemDTO.getQuantity() <= 0)
            throw new InvalidDataException(WrongMessage.INVALID_QUANTITY);

        Validator.isNotEmpty(shoppingCartItemDTO.getClient_id());
        Validator.isNotEmpty(shoppingCartItemDTO.getProduct_id());
    }


}
