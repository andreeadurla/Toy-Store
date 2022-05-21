package ro.sd.a2.dtoValidator;

import org.apache.commons.lang3.StringUtils;
import ro.sd.a2.dto.PlaceOrderDTO;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.validator.Validator;

public class PlaceOrderDTOValidator {

    public static void validate(PlaceOrderDTO orderDTO) {

        Validator.isNotEmpty(orderDTO.getId_client());

        if(StringUtils.isEmpty(orderDTO.getId_delivery_address()))
            throw new InvalidDataException(WrongMessage.INVALID_DELIVERY_ADDRESS);

        Validator.isNotEmpty(orderDTO.getPaymentMethod());
        Validator.isNotEmpty(orderDTO.getDeliveryMethod());

        if(orderDTO.getPrice() <= 0)
            throw new InvalidDataException(WrongMessage.INVALID_PRICE);

    }

}
