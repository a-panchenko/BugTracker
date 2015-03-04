package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PlaceholderCompleter<T> {

    void completeAdd(PreparedStatement statement, T entity) throws SQLException;

    void completeUpdate(PreparedStatement statement, int id,  T entity) throws SQLException;
}
