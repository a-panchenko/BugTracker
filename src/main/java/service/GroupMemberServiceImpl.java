package service;

import dao.groupmember.GroupMemberDaoImpl;
import model.GroupMember;

import java.util.List;

public class GroupMemberServiceImpl implements GroupMemberService {

    @Override
    public List<GroupMember> getAllMembers() {
        return new GroupMemberDaoImpl().getAllMembers();
    }

    @Override
    public List<GroupMember> getMembersByGroup(String group) {
        return new GroupMemberDaoImpl().getMembersByGroup(group);
    }

    @Override
    public List<GroupMember> getMembersByProjectId(int projectId) {
        return new GroupMemberDaoImpl().getMembersByProjectId(projectId);
    }

    @Override
    public List<GroupMember> getMembersWithNameLike(String name, String group) {
        return new GroupMemberDaoImpl().getMembersWithNameLike(name, group);
    }

    @Override
    public void addMember(GroupMember groupMember) {
        new GroupMemberDaoImpl().addMember(groupMember);
    }

    @Override
    public void updateMember(GroupMember groupMember) {
        new GroupMemberDaoImpl().updateMember(groupMember);
    }

    @Override
    public GroupMember getMemberByName(String username) {
        return new GroupMemberDaoImpl().getMemberByName(username);
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
