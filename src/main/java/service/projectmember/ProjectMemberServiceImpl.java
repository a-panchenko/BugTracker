package service.projectmember;

import dao.exceptions.QueryExecutionException;
import dao.projectmember.ProjectMemberDaoImpl;
import model.GroupMember;
import model.Project;
import model.ProjectMember;
import service.DataSourceProvider;
import service.exceptions.TransactionFailException;
import service.groupmember.GroupMemberServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Override
    public List<ProjectMember> getMembers(int projectId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new ProjectMemberDaoImpl(connection).getMembers(projectId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public List<ProjectMember> getMembers(int projectId, String group) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new ProjectMemberDaoImpl(connection).getMembers(projectId, group);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void addMember(ProjectMember projectMember) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ProjectMemberDaoImpl(connection).addMember(projectMember);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void removeMember(ProjectMember projectMember) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ProjectMemberDaoImpl(connection).removeMember(projectMember);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public List<String> getMembersToAssign(Project project, String username) {
        List<String> membersToAssign = new ArrayList<>();
        for (ProjectMember projectMember : getMembers(project.getId(), "debugers")) {
            membersToAssign.add(projectMember.getName());
        }
        GroupMember groupMember = new GroupMemberServiceImpl().getMemberByName(username);
        if (project.getProjectLeed().equals(username) || groupMember.getGroup().equals("administrators")) {
            membersToAssign.add(project.getProjectLeed());
        }
        return membersToAssign;
    }

    @Override
    public List<String> getPossibleCreators(Project project, String username) {
        List<String> possibleCreators = new ArrayList<>();
        for (ProjectMember projectMember : getMembers(project.getId(), "testers")) {
            possibleCreators.add(projectMember.getName());
        }
        GroupMember groupMember = new GroupMemberServiceImpl().getMemberByName(username);
        if (project.getProjectLeed().equals(username) || groupMember.getGroup().equals("administrators")) {
            possibleCreators.add(project.getProjectLeed());
        }
        return possibleCreators;
    }
}
