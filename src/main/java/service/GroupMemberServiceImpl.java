package service;

import dao.groupmember.GroupMemberDaoImpl;
import model.GroupMember;

import java.util.List;

public class GroupMemberServiceImpl implements GroupMemberService {

    public List<GroupMember> getMembersByGroup(String group) {
        return new GroupMemberDaoImpl().getMembersByGroup(group);
    }
}
