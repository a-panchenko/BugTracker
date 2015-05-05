package dao.groupmember;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.ResultParser;
import model.GroupMember;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class GroupMemberDaoImpl extends AbstractDao<GroupMember, String> implements GroupMemberDao {

    private static final String SELECT_GROUPMEMBER_BY_NAME = "SELECT * FROM GROUPMEMBERS WHERE g_member = ?";
    private static final String SELECT_ALL_GROUPMEMBERS = "SELECT * FROM GROUPMEMBERS";
    private static final String SELECT_GROUPMEMBERS_BY_GROUP = "SELECT * FROM GROUPMEMBERS WHERE g_name = ?";
    private static final String SELECT_GROUPMEMBERS_BY_PROJECT_ID = "SELECT * FROM GROUPMEMBERS WHERE g_member IN (SELECT name FROM PROJECTMEMBERS WHERE project_id = ?)";
    private static final String SELECT_GROUPMEMBER_WHERE_NAME_LIKE = "SELECT * FROM GROUPMEMBERS WHERE g_member LIKE ? AND g_name = ?";
    private static final String INSERT_INTO_GROUPMEMBERS = "INSERT INTO GROUPMEMBERS (g_name, g_member) VALUES (?, ?)";
    private static final String UPDATE_GROUPMEMBER = "UPDATE GROUPMEMBERS SET g_name = ? WHERE g_member = ?";

    private static final Logger LOGGER = Logger.getLogger(GroupMemberDaoImpl.class);

    private final ResultParser<GroupMember> resultParser = new GroupMemberResultParser();

    public GroupMemberDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<GroupMember> getAllMembers() {
        return select(SELECT_ALL_GROUPMEMBERS, resultParser);
    }

    @Override
    public List<GroupMember> getMembersByGroup(String group) {
        return select(SELECT_GROUPMEMBERS_BY_GROUP, resultParser, group);
    }

    @Override
    public List<GroupMember> getMembersByProjectId(int projectId) {
        return select(SELECT_GROUPMEMBERS_BY_PROJECT_ID, resultParser, projectId);
    }

    @Override
    public GroupMember getMemberByName(String username) {
        return select(username, SELECT_GROUPMEMBER_BY_NAME, resultParser);
    }

    @Override
    public List<GroupMember> getMembersWithNameLike(String name, String group) {
        return select(SELECT_GROUPMEMBER_WHERE_NAME_LIKE, resultParser, name + "%", group);
    }

    @Override
    public void addMember(GroupMember groupMember) {
        insert(groupMember, INSERT_INTO_GROUPMEMBERS, new PlaceholderCompleter<GroupMember>() {
            @Override
            public void complete(PreparedStatement statement, GroupMember groupMember) throws SQLException {
                statement.setString(1, groupMember.getGroup());
                statement.setString(2, groupMember.getName());
            }
        });
    }

    @Override
    public void updateMember(GroupMember groupMember) {
        update(groupMember, UPDATE_GROUPMEMBER, new PlaceholderCompleter<GroupMember>() {
            @Override
            public void complete(PreparedStatement statement, GroupMember groupMember) throws SQLException {
                statement.setString(1, groupMember.getGroup());
                statement.setString(2, groupMember.getName());
            }
        });
    }
}
