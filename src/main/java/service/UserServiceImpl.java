package service;

import dao.groupmember.GroupMemberDaoImpl;
import dao.user.UserDaoImpl;
import model.GroupMember;
import model.User;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String name) {
        return new UserDaoImpl().getUser(name);
    }

    @Override
    public void addUser(User user, GroupMember groupMember) {
        new UserDaoImpl().addUser(user);
        new GroupMemberDaoImpl().addMember(groupMember);
    }

    @Override
    public void updateUser(User user, GroupMember groupMember) {
        new UserDaoImpl().updateUser(user);
        new GroupMemberDaoImpl().updateMember(groupMember);
    }

    @Override
    public void removeUser(String name) {
        new UserDaoImpl().removeUser(name);
    }
}
