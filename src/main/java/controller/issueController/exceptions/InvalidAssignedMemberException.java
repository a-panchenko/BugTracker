package controller.issueController.exceptions;

public class InvalidAssignedMemberException extends RuntimeException {

    public InvalidAssignedMemberException(){
    }

    public InvalidAssignedMemberException(String message) {
        super(message);
    }

    public InvalidAssignedMemberException(Throwable exception) {
        super(exception);
    }

    public InvalidAssignedMemberException(String message, Throwable exception) {
        super(message, exception);
    }
}
