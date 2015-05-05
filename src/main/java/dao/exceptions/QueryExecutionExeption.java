package dao.exceptions;

public class QueryExecutionExeption extends RuntimeException {

    public QueryExecutionExeption(){
    }

    public QueryExecutionExeption(String message) {
        super(message);
    }

    public QueryExecutionExeption(Throwable exception) {
        super(exception);
    }

    public QueryExecutionExeption(String message, Throwable exception) {
        super(message, exception);
    }
}
