package controller.issueController.exceptions;

public class NoSuchProjectException extends RuntimeException {

    public NoSuchProjectException(){
    }

    public NoSuchProjectException(String message) {
        super(message);
    }

    public NoSuchProjectException(Throwable exception) {
        super(exception);
    }

    public NoSuchProjectException(String message, Throwable exception) {
        super(message, exception);
    }
}
