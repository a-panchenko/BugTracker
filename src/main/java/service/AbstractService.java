package service;

import dao.exceptions.QueryExecutionException;
import service.exceptions.TransactionFailException;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractService {

    public <T> T service(TransactionScript<T> transactionScript) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            QueryExecutionException savedException = null;
            T result = null;
            try {
                result = transactionScript.transact(connection);
                connection.commit();
            }
            catch (QueryExecutionException e) {
                savedException = e;
                connection.rollback();
            }
            finally {
                connection.setAutoCommit(true);
                if (savedException != null) {
                    throw savedException;
                }
            }
            return result;
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }
}
