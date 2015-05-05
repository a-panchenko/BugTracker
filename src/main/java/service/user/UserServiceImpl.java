package service.user;

import dao.exceptions.QueryExecutionException;
import dao.groupmember.GroupMemberDaoImpl;
import dao.user.UserDaoImpl;
import model.GroupMember;
import model.User;
import service.DataSourceProvider;
import service.exceptions.TransactionFailException;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String name) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new UserDaoImpl(connection).getUser(name);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void addUser(User user, GroupMember groupMember) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            QueryExecutionException savedException = null;
            try {
                new UserDaoImpl(connection).addUser(user);
                new GroupMemberDaoImpl(connection).addMember(groupMember);
                connection.commit();
            }
            catch (QueryExecutionException e) {
                savedException = e;
                connection.rollback();
            }
            finally {
                connection.setAutoCommit(true);
                if (savedException != null) {
                    throw savedException;
                }
            }
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void updateUser(User user, GroupMember groupMember) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            QueryExecutionException savedException = null;
            try {
                new UserDaoImpl(connection).updateUser(user);
                new GroupMemberDaoImpl(connection).updateMember(groupMember);
                connection.commit();
            }
            catch (QueryExecutionException e) {
                savedException = e;
                connection.rollback();
            }
            finally {
                connection.setAutoCommit(true);
                if (savedException != null) {
                    throw savedException;
                }
            }
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void removeUser(String name) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new UserDaoImpl(connection).removeUser(name);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }
}
