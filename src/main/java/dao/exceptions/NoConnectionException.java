package dao.exceptions;

public class NoConnectionException extends Exception {

    public NoConnectionException() {
    }

    public NoConnectionException(String arg0) {
        super(arg0);
    }

    public NoConnectionException(Throwable arg0) {
        super(arg0);
    }

    public NoConnectionException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
