package dao.groupmember;

import dao.Utils;
import model.GroupMember;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberDaoImpl implements GroupMemberDao {

    private final Logger LOGGER = Logger.getLogger(GroupMemberDaoImpl.class);

    @Override
    public List<GroupMember> getMembersByGroup(String group) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(Utils.SELECT_MEMBERS_BY_GROUP)) {
            statement.setString(1, group);
            try (ResultSet result = statement.executeQuery()) {
                List<GroupMember> list = new ArrayList<GroupMember>();
                while (result.next()) {
                    GroupMember groupMember = new GroupMember();
                    groupMember.setGroup(result.getString(1));
                    groupMember.setName(result.getString(2));
                    list.add(groupMember);
                }
                return list;
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<GroupMember>();
        }
    }
}
