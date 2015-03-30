package dao.projectmember;

import dao.Utils;
import model.ProjectMember;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectMemberDaoImpl implements ProjectMemberDao {

    private static final String SELECT_PROJECTMEMBERS_BY_PROJECT_ID = "SELECT * FROM PROJECTMEMBERS WHERE project_id = ?";
    private static final String SELECT_PROJECTMEMBERS_BY_PROJECT_ID_AND_GROUP = "SELECT * FROM PROJECTMEMBERS WHERE project_id = ? " +
            "AND name in (SELECT g_member FROM GROUPMEMBERS WHERE g_name = ?)";
    private static final String DELETE_PROJECTMEMBERS_BY_PROJECT_ID = "DELETE FROM PROJECTMEMBERS WHERE project_id = ?";
    private static final String INSERT_INTO_PROJECTMEMBERS = "INSERT INTO PROJECTMEMBERS (project_id, name) VALUES (?, ?)";

    private final Logger LOGGER = Logger.getLogger(ProjectMemberDaoImpl.class);

    @Override
    public List<ProjectMember> getMembers(int projectId) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PROJECTMEMBERS_BY_PROJECT_ID)) {
            statement.setInt(1, projectId);
            try (ResultSet result = statement.executeQuery()) {
                List<ProjectMember> list = new ArrayList<ProjectMember>();
                while (result.next()) {
                    ProjectMember projectMember = new ProjectMember();
                    projectMember.setProjectId(result.getInt(1));
                    projectMember.setName(result.getString(2));
                    list.add(projectMember);
                }
                return list;
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<ProjectMember>();
        }
    }
    @Override
    public List<ProjectMember> getMembers(int projectId, String group) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PROJECTMEMBERS_BY_PROJECT_ID_AND_GROUP)) {
            statement.setInt(1, projectId);
            statement.setString(2, group);
            try (ResultSet result = statement.executeQuery()) {
                List<ProjectMember> list = new ArrayList<ProjectMember>();
                while (result.next()) {
                    ProjectMember projectMember = new ProjectMember();
                    projectMember.setProjectId(result.getInt(1));
                    projectMember.setName(result.getString(2));
                    list.add(projectMember);
                }
                return list;
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<ProjectMember>();
        }
    }

    @Override
    public void addMember(ProjectMember projectMember) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_INTO_PROJECTMEMBERS)) {
            statement.setInt(1, projectMember.getProjectId());
            statement.setString(2, projectMember.getName());
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    @Override
    public void removeMembers(int projectId) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PROJECTMEMBERS_BY_PROJECT_ID)) {
            statement.setInt(1, projectId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }
}
