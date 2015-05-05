package service.groupmember;

import dao.exceptions.QueryExecutionException;
import dao.groupmember.GroupMemberDaoImpl;
import model.GroupMember;
import service.DataSourceProvider;
import service.exceptions.TransactionFailException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GroupMemberServiceImpl implements GroupMemberService {

    @Override
    public List<GroupMember> getAllMembers() {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new GroupMemberDaoImpl(connection).getAllMembers();
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public List<GroupMember> getMembersByGroup(String group) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new GroupMemberDaoImpl(connection).getMembersByGroup(group);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public List<GroupMember> getMembersByProjectId(int projectId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new GroupMemberDaoImpl(connection).getMembersByProjectId(projectId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public List<GroupMember> getMembersWithNameLike(String name, String group) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new GroupMemberDaoImpl(connection).getMembersWithNameLike(name, group);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void addMember(GroupMember groupMember) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new GroupMemberDaoImpl(connection).addMember(groupMember);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void updateMember(GroupMember groupMember) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new GroupMemberDaoImpl(connection).updateMember(groupMember);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public GroupMember getMemberByName(String username) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new GroupMemberDaoImpl(connection).getMemberByName(username);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public boolean isUserInGroup(String username, String group) {
        return getMemberByName(username).getGroup().equals(group);
    }

    @Override
    public List<GroupMember> getAvailableMembers() {
        List<GroupMember> availableMembers = getMembersByGroup("debugers");
        availableMembers.addAll(getMembersByGroup("testers"));
        return availableMembers;
    }

    @Override
    public List<GroupMember> getProjectManagers() {
        List<GroupMember> projectManagers = getMembersByGroup("administrators");
        projectManagers.addAll(getMembersByGroup("project-managers"));
        return projectManagers;
    }
}
