package ro.utcn.amqp.exception;

public class InvalidDataException extends RuntimeException{

    public InvalidDataException(String message) {
        super(message);
    }
}

