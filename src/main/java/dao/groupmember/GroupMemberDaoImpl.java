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

    private static final String SELECT_GROUPMEMBER_BY_NAME = "SELECT * FROM GROUPMEMBERS WHERE g_member = ?";
    private static final String SELECT_ALL_GROUPMEMBERS = "SELECT * FROM GROUPMEMBERS";
    private static final String SELECT_GROUPMEMBERS_BY_GROUP = "SELECT * FROM GROUPMEMBERS WHERE g_name = ?";
    private static final String SELECT_GROUPMEMBERS_BY_PROJECT_ID = "SELECT * FROM GROUPMEMBERS WHERE g_member IN (SELECT name FROM PROJECTMEMBERS WHERE project_id = ?)";
    private static final String INSERT_INTO_GROUPMEMBERS = "INSERT INTO GROUPMEMBERS (g_name, g_member) VALUES (?, ?)";
    private static final String UPDATE_GROUPMEMBER = "UPDATE GROUPMEMBERS SET g_name = ? WHERE g_member = ?";


    private static final Logger LOGGER = Logger.getLogger(GroupMemberDaoImpl.class);

    @Override
    public List<GroupMember> getAllMembers() {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GROUPMEMBERS)) {
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

    @Override
    public List<GroupMember> getMembersByGroup(String group) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GROUPMEMBERS_BY_GROUP)) {
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

    @Override
    public List<GroupMember> getMembersByProjectId(int projectId) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GROUPMEMBERS_BY_PROJECT_ID)) {
            statement.setInt(1, projectId);
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

    @Override
    public void addMember(GroupMember groupMember) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_INTO_GROUPMEMBERS)) {
            statement.setString(1, groupMember.getGroup());
            statement.setString(2, groupMember.getName());
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
        }
    }

    @Override
    public GroupMember getMemberByName(String username) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GROUPMEMBER_BY_NAME)) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    GroupMember groupMember = new GroupMember();
                    groupMember.setGroup(result.getString(1));
                    groupMember.setName(result.getString(2));
                    return groupMember;
                }
                else {
                    return null;
                }
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return null;
        }
    }

    @Override
    public void updateMember(GroupMember groupMember) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_GROUPMEMBER)) {
            statement.setString(1, groupMember.getGroup());
            statement.setString(2, groupMember.getName());
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
        }
    }
}
