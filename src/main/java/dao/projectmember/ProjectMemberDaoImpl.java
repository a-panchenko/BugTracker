package dao.projectmember;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.ResultParser;
import dao.exceptions.QueryExecutionException;
import model.ProjectMember;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProjectMemberDaoImpl extends AbstractDao<ProjectMember, String> implements ProjectMemberDao {

    private static final String SELECT_PROJECTMEMBERS_BY_PROJECT_ID = "SELECT * FROM PROJECTMEMBERS WHERE project_id = ?";
    private static final String SELECT_PROJECTMEMBERS_BY_PROJECT_ID_AND_GROUP = "SELECT * FROM PROJECTMEMBERS WHERE project_id = ? " +
            "AND name in (SELECT g_member FROM GROUPMEMBERS WHERE g_name = ?)";
    private static final String DELETE_PROJECTMEMBER = "DELETE FROM PROJECTMEMBERS WHERE project_id = ? AND name = ?";
    private static final String INSERT_INTO_PROJECTMEMBERS = "INSERT INTO PROJECTMEMBERS (project_id, name) VALUES (?, ?)";

    private static final Logger LOGGER = Logger.getLogger(ProjectMemberDaoImpl.class);

    private final ResultParser<ProjectMember> resultParser = new ProjectMemberResultParser();

    private Connection connection;

    public ProjectMemberDaoImpl(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    public List<ProjectMember> getMembers(int projectId) {
        return select(SELECT_PROJECTMEMBERS_BY_PROJECT_ID, resultParser, projectId);
    }

    @Override
    public List<ProjectMember> getMembers(int projectId, String group) {
        return select(SELECT_PROJECTMEMBERS_BY_PROJECT_ID_AND_GROUP, resultParser, projectId, group);
    }

    @Override
    public void addMember(ProjectMember projectMember) {
        insert(projectMember, INSERT_INTO_PROJECTMEMBERS, new PlaceholderCompleter<ProjectMember>() {
            @Override
            public void complete(PreparedStatement statement, ProjectMember projectMember) throws SQLException {
                completeAdd(statement, projectMember);
            }
        });
    }

    @Override
    public void removeMember(ProjectMember projectMember) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PROJECTMEMBER)) {
            completeAdd(statement, projectMember);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            throw new QueryExecutionException(se);
        }
    }

    private void completeAdd(PreparedStatement statement, ProjectMember projectMember) throws SQLException {
        statement.setInt(1, projectMember.getProjectId());
        statement.setString(2, projectMember.getName());
    }
}
