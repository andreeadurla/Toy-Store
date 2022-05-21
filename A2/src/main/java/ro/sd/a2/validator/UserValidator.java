package ro.sd.a2.validator;

import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends Validator{

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

    public static void validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,30}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_PASSWORD);
    }

    public static void validatePhone(String phone) {
        String regex = "^07\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);

        if(matcher.matches() == false)
            throw new InvalidDataException(WrongMessage.INVALID_PHONE_NUMBER);
    }

    public static void validateConfirmPassword(String password, String confirmPassword) {
        if(password.equals(confirmPassword) == false)
            throw new InvalidDataException(WrongMessage.WRONG_PASSWORD);
    }

}