package service;

import model.User;

public interface UserService {

    User getUser(String name);

    void addUser(User user);

    void updateUser(User user);
}
