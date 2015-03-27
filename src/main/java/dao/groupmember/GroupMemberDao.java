package dao.groupmember;

import model.GroupMember;

import java.util.List;

public interface GroupMemberDao {

    List<GroupMember> getMembersByGroup(String group);
}
