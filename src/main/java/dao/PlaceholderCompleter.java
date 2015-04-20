package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PlaceholderCompleter<T> {

    void complete(PreparedStatement statement, T entity) throws SQLException;
}
