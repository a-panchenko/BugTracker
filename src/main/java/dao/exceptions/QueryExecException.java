package dao.exceptions;

public class QueryExecException extends RuntimeException {

    public QueryExecException() {
    }

    public QueryExecException(String arg0) {
        super(arg0);
    }

    public QueryExecException(Throwable arg0) {
        super(arg0);
    }

    public QueryExecException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
