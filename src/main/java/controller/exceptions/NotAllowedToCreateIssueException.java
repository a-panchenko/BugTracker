package controller.exceptions;

public class NotAllowedToCreateIssueException extends RuntimeException {

    public NotAllowedToCreateIssueException(){
    }

    public NotAllowedToCreateIssueException(String message) {
        super(message);
    }

    public NotAllowedToCreateIssueException(Throwable exception) {
        super(exception);
    }

    public NotAllowedToCreateIssueException(String message, Throwable exception) {
        super(message, exception);
    }
}
