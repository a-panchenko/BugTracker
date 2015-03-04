package dao;

import dao.resultparser.ResultParser;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractDao<T> {

    private final Logger LOGGER = Logger.getLogger(AbstractDao.class);

    T selectById(int id, String sql, ResultParser<T> resultParser) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return resultParser.extractSingle(result);
                }
                else {
                    return null;
                }
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return null;
        }
    }

    void insert(T entity, String sql, PlaceholderCompleter<T> placeholderCompleter) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            placeholderCompleter.completeAdd(statement, entity);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    void update(int id, T entity, String sql, PlaceholderCompleter<T> placeholderCompleter) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            placeholderCompleter.completeUpdate(statement, id, entity);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    void deleteById(int id, String sql) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }
}
