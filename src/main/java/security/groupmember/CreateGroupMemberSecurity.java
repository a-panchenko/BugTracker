package security.groupmember;

import dto.GroupMemberDto;
import model.GroupMember;

public interface CreateGroupMemberSecurity {

    GroupMember secureCreateGroupMember(GroupMemberDto groupMemberDto, String username);

}