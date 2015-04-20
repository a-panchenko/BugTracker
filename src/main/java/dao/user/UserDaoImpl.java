package dao.user;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.ResultParser;
import dao.Utils;
import model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends AbstractDao<User, String> implements UserDao {

    private static final String SELECT_USER = "SELECT * FROM USERS WHERE u_name = ?";
    private static final String INSERT_INTO_USER = "INSERT INTO USERS (u_name, u_password) VALUES (?, ?)";
    private static final String UPDATE_USER = "UPDATE USERS SET u_password = ? WHERE u_name = ?";

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private final ResultParser resultParser = new UserResultParser();

    @Override
    public User getUser(String name) {
        return select(name, SELECT_USER, resultParser);
    }

    @Override
    public void addUser(User user) {
        insert(user, INSERT_INTO_USER, new PlaceholderCompleter<User>() {
            @Override
            public void complete(PreparedStatement statement, User user) throws SQLException {
                statement.setString(1, user.getName());
                statement.setString(2, user.getPassword());
            }
        });
    }

    @Override
    public void updateUser(User user) {
        update(user, UPDATE_USER, new PlaceholderCompleter<User>() {
            @Override
            public void complete(PreparedStatement statement, User user) throws SQLException {
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getName());
            }
        });
    }
}
