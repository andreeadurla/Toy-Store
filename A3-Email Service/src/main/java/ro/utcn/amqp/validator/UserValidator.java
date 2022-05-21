package ro.utcn.amqp.validator;

import ro.utcn.amqp.exception.InvalidDataException;
import ro.utcn.amqp.message.WrongMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static void validateName(String name) {
        String regex = "^[a-zA-Z ]+([ -][a-zA-Z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_NAME);
    }

    public static void validateEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_EMAIL);
    }


    public static void validatePhone(String phone) {
        String regex = "^07\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_PHONE_NUMBER);
    }

}