package dao.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(){
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable exception) {
        super(exception);
    }

    public NotFoundException(String message, Throwable exception) {
        super(message, exception);
    }
}
