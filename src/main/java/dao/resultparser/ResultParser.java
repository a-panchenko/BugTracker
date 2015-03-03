package dao.resultparser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ResultParser<T> {

    public abstract T extractSingle(ResultSet result) throws SQLException;

    public List<T> extractAll(ResultSet result) throws SQLException {
        List<T> list = new ArrayList<T>();
        while (result.next()) {
            list.add(extractSingle(result));
        }
        return list;
    }
}
