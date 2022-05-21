package ro.sd.a2.dtoValidator;

import org.apache.commons.lang3.StringUtils;
import ro.sd.a2.dto.ProductDTO;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.validator.URLValidator;
import ro.sd.a2.validator.Validator;

public class ProductDTOValidator {

    public static void validate(ProductDTO productDTO) {

        Validator.isNotEmpty(productDTO.getName());

        if(productDTO.getPrice() <= 0)
            throw new InvalidDataException(WrongMessage.INVALID_PRICE);

        if(productDTO.getQuantity() <= 0)
            throw new InvalidDataException(WrongMessage.INVALID_QUANTITY);

        if(StringUtils.isNotEmpty(productDTO.getImageURL()))
            URLValidator.validateURL(productDTO.getImageURL());

        Validator.isNotEmpty(productDTO.getCategory_id());

    }

}
