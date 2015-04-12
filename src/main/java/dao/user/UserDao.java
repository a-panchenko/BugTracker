package dao.user;

import model.User;

public interface UserDao {

    User getUser(String name);

    void addUser(User user);

    void updateUser(User user);
}
