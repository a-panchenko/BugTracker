package service;

import java.sql.Connection;

public interface TransactionScript<T> {

    T transact(Connection connection);

}
