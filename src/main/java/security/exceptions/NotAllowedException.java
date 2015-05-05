package security.exceptions;

public class NotAllowedException extends RuntimeException {

    public NotAllowedException(){
    }

    public NotAllowedException(String message) {
        super(message);
    }

    public NotAllowedException(Throwable exception) {
        super(exception);
    }

    public NotAllowedException(String message, Throwable exception) {
        super(message, exception);
    }
}
