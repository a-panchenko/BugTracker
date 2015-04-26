package service;

import dao.user.UserDaoImpl;
import model.User;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String name) {
        return new UserDaoImpl().getUser(name);
    }

    @Override
    public void addUser(User user) {
        new UserDaoImpl().addUser(user);
    }

    @Override
    public void updateUser(User user) {
        new UserDaoImpl().updateUser(user);
    }

    @Override
    public void removeUser(String name) {
        new UserDaoImpl().removeUser(name);
    }
}
