package service;

import model.GroupMember;

import java.util.List;

public interface GroupMemberService {

    List<GroupMember> getMembersByGroup(String group);
}
