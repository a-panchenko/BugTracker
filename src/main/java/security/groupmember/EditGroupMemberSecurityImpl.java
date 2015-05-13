package security.groupmember;

import dto.GroupMemberDto;
import model.GroupMember;
import security.Security;
import security.exceptions.NotAllowedException;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;

public class EditGroupMemberSecurityImpl implements Security<GroupMember, GroupMemberDto> {

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public GroupMember secure(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = groupMemberService.getMemberByName(groupMemberDto.getName());
        if (groupMemberDto.getGroup() != null) {
            editGroup(groupMemberDto, groupMember);
        }
        return groupMember;
    }

    private void editGroup(GroupMemberDto groupMemberDto, GroupMember groupMember) {
        if (groupMemberService.isUserInGroup(groupMemberDto.getRequestPerformer(), "administrators")) {
            groupMember.setGroup(groupMemberDto.getGroup());
        }
        else {
            throw new NotAllowedException();
        }
    }
}
