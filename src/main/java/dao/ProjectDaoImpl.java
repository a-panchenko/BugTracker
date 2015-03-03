package dao;

import dao.resultparser.ProjectResultParser;
import dao.resultparser.ResultParser;
import model.Project;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl extends AbstractDao<Project> implements ProjectDao {

    private final Logger LOGGER = Logger.getLogger(ProjectDaoImpl.class);
    private final ResultParser<Project> projectResultParser = new ProjectResultParser();

    @Override
    public Project getProject(int projectId) {
        return selectById(projectId, Utils.SELECT_PROJECT_BY_PROJECT_ID, projectResultParser);
    }

    @Override
    public List<Project> getAllProjects() {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(Utils.SELECT_ALL_PROJECTS);
             ResultSet result = statement.executeQuery()) {
            return projectResultParser.extractAll(result);
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Project>();
        }
    }

    @Override
    public void addProject(Project project) {
        String sql = "INSERT INTO PROJECT (project_title, project_description, start_date) VALUES (?, ?, ?)";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getTitle());
            statement.setString(2, project.getDescription());
            statement.setDate(3, Utils.utilDateToSql(project.getStartDate()));
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    @Override
    public void removeProject(int projectId) {
        String sql = "DELETE FROM PROJECT WHERE project_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    @Override
    public void updateProject(int projectId, Project project) {
        String sql = "UPDATE PROJECT SET project_title = ?, project_description = ?, start_date = ?, end_date = ? WHERE project_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getTitle());
            statement.setString(2, project.getDescription());
            statement.setDate(3, Utils.utilDateToSql(project.getStartDate()));
            statement.setDate(4, Utils.utilDateToSql(project.getEndDate()));
            statement.setInt(5, projectId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }
}