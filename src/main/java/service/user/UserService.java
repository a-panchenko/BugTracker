package service.user;

import model.GroupMember;
import model.User;

public interface UserService {

    User getUser(String name);

    void addUser(User user, GroupMember groupMember);

    void updateUser(User user, GroupMember groupMember);

    void removeUser(String name);
}
