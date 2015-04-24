package controller.exceptions;

public class NotAllowedToCreateProjectException extends RuntimeException {

    public NotAllowedToCreateProjectException(){
    }

    public NotAllowedToCreateProjectException(String message) {
        super(message);
    }

    public NotAllowedToCreateProjectException(Throwable exception) {
        super(exception);
    }

    public NotAllowedToCreateProjectException(String message, Throwable exception) {
        super(message, exception);
    }
}
