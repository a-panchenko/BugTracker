package service.groupmember;

import dao.groupmember.GroupMemberDaoImpl;
import model.GroupMember;
import service.AbstractService;
import service.TransactionScript;

import java.sql.Connection;
import java.util.List;

public class GroupMemberServiceImpl extends AbstractService implements GroupMemberService {

    @Override
    public List<GroupMember> getAllMembers() {
        return service(new TransactionScript<List<GroupMember>>() {
            @Override
            public List<GroupMember> transact(Connection connection) {
                return new GroupMemberDaoImpl(connection).getAllMembers();
            }
        });
    }

    @Override
    public List<GroupMember> getMembersByGroup(final String group) {
        return service(new TransactionScript<List<GroupMember>>() {
            @Override
            public List<GroupMember> transact(Connection connection) {
                return new GroupMemberDaoImpl(connection).getMembersByGroup(group);
            }
        });
    }

    @Override
    public List<GroupMember> getMembersByProjectId(final int projectId) {
        return service(new TransactionScript<List<GroupMember>>() {
            @Override
            public List<GroupMember> transact(Connection connection) {
                return new GroupMemberDaoImpl(connection).getMembersByProjectId(projectId);
            }
        });
    }

    @Override
    public List<GroupMember> getMembersWithNameLike(final String name, final String group) {
        return service(new TransactionScript<List<GroupMember>>() {
            @Override
            public List<GroupMember> transact(Connection connection) {
                return new GroupMemberDaoImpl(connection).getMembersWithNameLike(name, group);
            }
        });
    }

    @Override
    public Void addMember(final GroupMember groupMember) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new GroupMemberDaoImpl(connection).addMember(groupMember);
                return null;
            }
        });
    }

    @Override
    public Void updateMember(final GroupMember groupMember) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new GroupMemberDaoImpl(connection).updateMember(groupMember);
                return null;
            }
        });
    }

    @Override
    public GroupMember getMemberByName(final String username) {
        return service(new TransactionScript<GroupMember>() {
            @Override
            public GroupMember transact(Connection connection) {
                return new GroupMemberDaoImpl(connection).getMemberByName(username);
            }
        });
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
