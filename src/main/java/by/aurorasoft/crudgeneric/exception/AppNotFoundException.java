package by.aurorasoft.crudgeneric.exception;

public class AppNotFoundException extends RuntimeException {

    public AppNotFoundException(String message) {
        super(message);
    }

    public AppNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
