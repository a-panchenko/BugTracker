package dao.groupmember;

import dao.ResultParser;
import model.GroupMember;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMemberResultParser extends ResultParser<GroupMember> {

    @Override
    public GroupMember extractSingle(ResultSet result) throws SQLException {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(result.getString(1));
        groupMember.setName(result.getString(2));
        return groupMember;
    }
}
