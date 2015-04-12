package dao.groupmember;

import model.GroupMember;

import java.util.List;

public interface GroupMemberDao {

    List<GroupMember> getAllMembers();

    List<GroupMember> getMembersByGroup(String group);

    List<GroupMember> getMembersByProjectId(int projectId);

    void addMember(GroupMember groupMember);

    void updateMember(GroupMember groupMember);

    GroupMember getMemberByName(String username);
}
