package service;

import model.GroupMember;

import java.util.List;

public interface GroupMemberService {

    List<GroupMember> getAllMembers();

    List<GroupMember> getMembersByGroup(String group);

    List<GroupMember> getMembersByProjectId(int projectId);

    List<GroupMember> getAvailableMembers();

    List<GroupMember> getProjectManagers();

    void addMember(GroupMember groupMember);

    void updateMember(GroupMember groupMember);

    GroupMember getMemberByName(String username);

    boolean isUserInGroup(String username, String group);
}
