package dao.user;

import dao.Utils;
import model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static final String SELECT_USER = "SELECT * FROM USERS WHERE u_name = ?";
    private static final String INSERT_INTO_USER = "INSERT INTO USERS (u_name, u_password) VALUES (?, ?)";
    private static final String UPDATE_USER = "UPDATE USERS SET u_password = ? WHERE u_name = ?";

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User getUser(String name) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER)) {
            statement.setString(1, name);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    User user = new User();
                    user.setName(result.getString(1));
                    user.setPassword(result.getString(2));
                    return user;
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

    @Override
    public void addUser(User user) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_INTO_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
        }
    }

    @Override
    public void updateUser(User user) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
        }
    }
}
