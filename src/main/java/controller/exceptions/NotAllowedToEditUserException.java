package controller.exceptions;

public class NotAllowedToEditUserException extends RuntimeException {

    public NotAllowedToEditUserException(){
    }

    public NotAllowedToEditUserException(String message) {
        super(message);
    }

    public NotAllowedToEditUserException(Throwable exception) {
        super(exception);
    }

    public NotAllowedToEditUserException(String message, Throwable exception) {
        super(message, exception);
    }
}
