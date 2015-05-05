package dao.exceptions;

public class QueryExecutionException extends RuntimeException {

    public QueryExecutionException(){
    }

    public QueryExecutionException(String message) {
        super(message);
    }

    public QueryExecutionException(Throwable exception) {
        super(exception);
    }

    public QueryExecutionException(String message, Throwable exception) {
        super(message, exception);
    }
}
