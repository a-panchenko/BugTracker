package service.user;

import dao.groupmember.GroupMemberDaoImpl;
import dao.user.UserDaoImpl;
import model.GroupMember;
import model.User;
import service.AbstractService;
import service.TransactionScript;

import java.sql.Connection;

public class UserServiceImpl extends AbstractService implements UserService {

    @Override
    public User getUser(final String name) {
        return service(new TransactionScript<User>() {
            @Override
            public User transact(Connection connection) {
                return new UserDaoImpl(connection).getUser(name);
            }
        });
    }

    @Override
    public Void addUser(final User user, final GroupMember groupMember) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new UserDaoImpl(connection).addUser(user);
                new GroupMemberDaoImpl(connection).addMember(groupMember);
                return null;
            }
        });
    }

    @Override
    public Void updateUser(final User user, final GroupMember groupMember) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new UserDaoImpl(connection).updateUser(user);
                new GroupMemberDaoImpl(connection).updateMember(groupMember);
                return null;
            }
        });
    }

    @Override
    public Void removeUser(final String name) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new UserDaoImpl(connection).removeUser(name);
                return null;
            }
        });
    }
}
