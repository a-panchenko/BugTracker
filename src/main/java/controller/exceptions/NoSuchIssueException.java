package controller.exceptions;

public class NoSuchIssueException extends RuntimeException {

    public NoSuchIssueException(){
    }

    public NoSuchIssueException(String message) {
        super(message);
    }

    public NoSuchIssueException(Throwable exception) {
        super(exception);
    }

    public NoSuchIssueException(String message, Throwable exception) {
        super(message, exception);
    }
}
