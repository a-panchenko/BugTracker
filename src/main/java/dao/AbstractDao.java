package dao;

import dao.exceptions.NotFoundException;
import dao.exceptions.QueryExecutionExeption;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbstractDao<T, PK> {

    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class);

    protected T select(PK primaryKey, String sql, ResultParser<T> resultParser) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, primaryKey);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return resultParser.extractSingle(result);
                }
                else {
                    throw new NotFoundException();
                }
            }
        }
        catch (SQLException se) {
            LOGGER.error(sql, se);
            throw new QueryExecutionExeption(se);
        }
    }

    protected List<T> select(String sql, ResultParser<T> resultParser, Object ... params) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            try (ResultSet result = statement.executeQuery()) {
                return resultParser.extractAll(result);
            }
        }
        catch (SQLException se) {
            LOGGER.error(sql, se);
            throw new QueryExecutionExeption(se);
        }
    }

    protected void insert(T entity, String sql, PlaceholderCompleter<T> placeholderCompleter) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            placeholderCompleter.complete(statement, entity);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(sql, se);
            throw new QueryExecutionExeption(se);
        }
    }

    protected void update(T entity, String sql, PlaceholderCompleter<T> placeholderCompleter) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            placeholderCompleter.complete(statement, entity);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(sql, se);
            throw new QueryExecutionExeption(se);
        }
    }

    protected void delete(PK primaryKey, String sql) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, primaryKey);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(sql, se);
            throw new QueryExecutionExeption(se);
        }
    }
}
