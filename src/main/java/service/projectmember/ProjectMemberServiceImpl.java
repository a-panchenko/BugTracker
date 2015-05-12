package service.projectmember;

import dao.exceptions.QueryExecutionException;
import dao.projectmember.ProjectMemberDaoImpl;
import model.Project;
import model.ProjectMember;
import service.DataSourceProvider;
import service.exceptions.TransactionFailException;
import service.groupmember.GroupMemberServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Set<String> getMembersToAssign(Project project, String username) {
        Set<String> membersToAssign = new HashSet<>();
        for (ProjectMember projectMember : getMembers(project.getId(), "debugers")) {
            membersToAssign.add(projectMember.getName());
        }
        if (username.equals(project.getProjectLeed())) {
            membersToAssign.add(project.getProjectLeed());
        }
        if (new GroupMemberServiceImpl().isUserInGroup(username, "administrators")) {
            membersToAssign.add(project.getProjectLeed());
            membersToAssign.add(username);
        }
        return membersToAssign;
    }
}
