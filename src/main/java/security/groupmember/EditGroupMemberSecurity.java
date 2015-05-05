package security.groupmember;

import dto.GroupMemberDto;
import model.GroupMember;

public interface EditGroupMemberSecurity {

    GroupMember secureEditGroupMember(GroupMemberDto groupMemberDto, String username);
}
