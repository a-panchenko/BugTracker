package security.groupmember;

import dto.GroupMemberDto;
import model.GroupMember;
import security.exceptions.NotAllowedException;

public class CreateGroupMemberSecurityImpl implements CreateGroupMemberSecurity {

    public GroupMember secureCreateGroupMember(GroupMemberDto groupMemberDto, String username) {
        GroupMember groupMember = new GroupMember();
        setName(groupMemberDto, groupMember);
        setGroup(groupMemberDto, groupMember);
        return groupMember;
    }

    private void setName(GroupMemberDto groupMemberDto, GroupMember groupMember) {
        if (groupMemberDto.getUsername() != null) {
            groupMember.setName(groupMemberDto.getUsername());
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
