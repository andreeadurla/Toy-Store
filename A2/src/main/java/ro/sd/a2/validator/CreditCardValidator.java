package ro.sd.a2.validator;

import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.utils.ApplicationUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardValidator extends Validator {

    public static void validateName(String name) {
        String regex = "^[a-zA-Z ]+([ -][a-zA-Z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_NAME);
    }

    public static void validateNoCard(String noCard) {
        String regex = "^\\d{16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(noCard);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_NOCARD);
    }

    public static void validateCVV(String cvv) {
        String regex = "^\\d{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cvv);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_CVV);
    }

    public static void validateExpirationDate(String expirationDate) {

        Date date = ApplicationUtils.convertFromStringYMToDate(expirationDate);

        if(date.before(new Date()))
            throw new InvalidDataException(WrongMessage.INVALID_EXPIRATION_DATE);
    }

}
