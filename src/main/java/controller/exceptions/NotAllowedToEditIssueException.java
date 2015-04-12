package controller.exceptions;

public class NotAllowedToEditIssueException extends RuntimeException {

    public NotAllowedToEditIssueException(){
    }

    public NotAllowedToEditIssueException(String message) {
        super(message);
    }

    public NotAllowedToEditIssueException(Throwable exception) {
        super(exception);
    }

    public NotAllowedToEditIssueException(String message, Throwable exception) {
        super(message, exception);
    }
}
