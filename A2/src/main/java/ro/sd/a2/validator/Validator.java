package ro.sd.a2.validator;

import org.apache.commons.lang3.ObjectUtils;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;

public class Validator {

    public static void isNotEmpty(Object obj) {

        if(ObjectUtils.isEmpty(obj))
            throw new InvalidDataException(WrongMessage.EMPTY_FIELD);

    }

}
