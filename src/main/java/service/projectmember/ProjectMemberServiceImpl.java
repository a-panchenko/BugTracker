package service.projectmember;

import dao.projectmember.ProjectMemberDaoImpl;
import model.Project;
import model.ProjectMember;
import service.AbstractService;
import service.TransactionScript;
import service.groupmember.GroupMemberServiceImpl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectMemberServiceImpl extends AbstractService implements ProjectMemberService {

    @Override
    public List<ProjectMember> getMembers(final int projectId) {
        return service(new TransactionScript<List<ProjectMember>>() {
            @Override
            public List<ProjectMember> transact(Connection connection) {
                return new ProjectMemberDaoImpl(connection).getMembers(projectId);
            }
        });
    }

    @Override
    public List<ProjectMember> getMembers(final int projectId, final String group) {
        return service(new TransactionScript<List<ProjectMember>>() {
            @Override
            public List<ProjectMember> transact(Connection connection) {
                return new ProjectMemberDaoImpl(connection).getMembers(projectId, group);
            }
        });
    }

    @Override
    public Void addMember(final ProjectMember projectMember) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ProjectMemberDaoImpl(connection).addMember(projectMember);
                return null;
            }
        });
    }

    @Override
    public Void removeMember(final ProjectMember projectMember) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ProjectMemberDaoImpl(connection).removeMember(projectMember);
                return null;
            }
        });
    }

    @Override
    public Set<String> getMembersToAssign(Project project, String username) {
        Set<String> membersToAssign = new HashSet<>();
        for (ProjectMember projectMember : getMembers(project.getId(), "debugers")) {
            membersToAssign.add(projectMember.getName());
        }
        if (username.equals(project.getProjectLead())) {
            membersToAssign.add(project.getProjectLead());
        }
        if (new GroupMemberServiceImpl().isUserInGroup(username, "administrators")) {
            membersToAssign.add(project.getProjectLead());
            membersToAssign.add(username);
        }
        return membersToAssign;
    }
}
