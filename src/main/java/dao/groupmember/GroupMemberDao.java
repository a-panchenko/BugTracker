package dao.groupmember;

import model.GroupMember;

import java.util.List;

public interface GroupMemberDao {

    List<GroupMember> getAllMembers();

    List<GroupMember> getMembersByGroup(String group);

    List<GroupMember> getMembersByProjectId(int projectId);

    List<GroupMember> getMembersWithNameLike(String name, String group);

    void addMember(GroupMember groupMember);

    void updateMember(GroupMember groupMember);

    GroupMember getMemberByName(String username);
}
