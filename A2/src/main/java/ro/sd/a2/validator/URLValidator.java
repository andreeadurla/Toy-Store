package ro.sd.a2.validator;

import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class URLValidator extends Validator {

    public static void validateURL(String url) {

        try {
            new URL(url).toURI();
        }
        catch (URISyntaxException | MalformedURLException exception) {
            throw new InvalidDataException(WrongMessage.INVALID_URL);
        }
    }

}
