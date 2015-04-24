package controller.exceptions;

public class NotAllowedToEditProjectException extends RuntimeException {

    public NotAllowedToEditProjectException(){
    }

    public NotAllowedToEditProjectException(String message) {
        super(message);
    }

    public NotAllowedToEditProjectException(Throwable exception) {
        super(exception);
    }

    public NotAllowedToEditProjectException(String message, Throwable exception) {
        super(message, exception);
    }
}
