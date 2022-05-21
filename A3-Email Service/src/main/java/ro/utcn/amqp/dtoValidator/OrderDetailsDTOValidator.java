package ro.utcn.amqp.dtoValidator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import ro.utcn.amqp.dto.OrderDetailsDTO;
import ro.utcn.amqp.exception.InvalidDataException;
import ro.utcn.amqp.message.WrongMessage;

public class OrderDetailsDTOValidator {

    public static void validate(OrderDetailsDTO orderDetails) {

        if(StringUtils.isEmpty(orderDetails.getId()))
            throw new InvalidDataException(WrongMessage.EMPTY_ID);

        UserDTOValidator.validate(orderDetails.getUser());

        if(CollectionUtils.isEmpty(orderDetails.getItems()))
            throw new InvalidDataException(WrongMessage.INVALID_ORDER_ITEM_LIST);

        if(ObjectUtils.isEmpty(orderDetails.getDeliveryMethod()))
            throw new InvalidDataException(WrongMessage.INVALID_DELIVERY_METHOD);

        if(ObjectUtils.isEmpty(orderDetails.getPaymentMethod()))
            throw new InvalidDataException(WrongMessage.INVALID_PAYMENT_METHOD);

        DeliveryAddressDTOValidator.validate(orderDetails.getDeliveryAddress());
    }

}
