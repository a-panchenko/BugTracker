package security.groupmember;

import dto.GroupMemberDto;
import model.GroupMember;
import security.Security;
import security.exceptions.NotAllowedException;

public class CreateGroupMemberSecurityImpl implements Security<GroupMember, GroupMemberDto> {

    public GroupMember secure(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = new GroupMember();
        setName(groupMemberDto, groupMember);
        setGroup(groupMemberDto, groupMember);
        return groupMember;
    }

    private void setName(GroupMemberDto groupMemberDto, GroupMember groupMember) {
        if (groupMemberDto.getName() != null) {
            groupMember.setName(groupMemberDto.getName());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setGroup(GroupMemberDto groupMemberDto, GroupMember groupMember) {
        if (groupMemberDto.getGroup() != null) {
            groupMember.setGroup(groupMemberDto.getGroup());
        }
        else {
            throw new NotAllowedException();
        }
    }
}
