package security.exceptions;

public class EditPasswordException extends RuntimeException {

    public EditPasswordException(){
    }

    public EditPasswordException(String message) {
        super(message);
    }

    public EditPasswordException(Throwable exception) {
        super(exception);
    }

    public EditPasswordException(String message, Throwable exception) {
        super(message, exception);
    }
}
