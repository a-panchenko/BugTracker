package service.exceptions;

public class TransactionFailException extends RuntimeException {

    public TransactionFailException(){
    }

    public TransactionFailException(String message) {
        super(message);
    }

    public TransactionFailException(Throwable exception) {
        super(exception);
    }

    public TransactionFailException(String message, Throwable exception) {
        super(message, exception);
    }
}
