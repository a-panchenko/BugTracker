package dao.user;

import dao.ResultParser;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultParser extends ResultParser<User> {

    @Override
    public User extractSingle(ResultSet result) throws SQLException {
        User user = new User();
        user.setName(result.getString(1));
        user.setPassword(result.getString(2));
        return user;
    }
}
