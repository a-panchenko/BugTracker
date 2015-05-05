package security.groupmember;

import dto.GroupMemberDto;
import model.GroupMember;
import security.exceptions.NotAllowedException;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;

public class EditGroupMemberSecurityImpl implements EditGroupMemberSecurity {

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public GroupMember secureEditGroupMember(GroupMemberDto groupMemberDto, String username) {
        GroupMember groupMember = groupMemberService.getMemberByName(groupMemberDto.getUsername());
        if (groupMemberDto.getGroup() != null) {
            editGroup(groupMemberDto, groupMember, username);
        }
        return groupMember;
    }

    private void editGroup(GroupMemberDto groupMemberDto, GroupMember groupMember, String username) {
        if (groupMemberService.isUserInGroup(username, "administrators")) {
            groupMember.setGroup(groupMemberDto.getGroup());
        }
        else {
            throw new NotAllowedException();
        }
    }
}
