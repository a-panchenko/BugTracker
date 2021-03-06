package service.groupmember;

import model.GroupMember;

import java.util.List;

public interface GroupMemberService {

    List<GroupMember> getAllMembers();

    List<GroupMember> getMembersByGroup(String group);

    List<GroupMember> getMembersByProjectId(int projectId);

    List<GroupMember> getAvailableMembers();

    List<GroupMember> getProjectManagers();

    List<GroupMember> getMembersWithNameLike(String name, String group);

    Void addMember(GroupMember groupMember);

    Void updateMember(GroupMember groupMember);

    GroupMember getMemberByName(String username);

    boolean isUserInGroup(String username, String group);
}
