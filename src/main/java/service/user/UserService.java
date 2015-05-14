package service.user;

import model.GroupMember;
import model.User;

public interface UserService {

    User getUser(String name);

    Void addUser(User user, GroupMember groupMember);

    Void updateUser(User user, GroupMember groupMember);

    Void removeUser(String name);
}
