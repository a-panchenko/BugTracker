package dao;

import model.Project;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {

    private final Logger LOGGER = Logger.getLogger(ProjectDaoImpl.class);

    @Override
    public Project getProject(int projectId) {
        String sql = "SELECT * FROM PROJECT WHERE project_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.isBeforeFirst()) {
                    result.next();
                    String title = result.getString(2);
                    Date start = result.getDate(3);
                    return new Project(projectId, title, start, null);
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
    public List<Project> getAllProjects() {
        String sql = "SELECT * FROM PROJECT";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet result = statement.executeQuery()) {
                List<Project> projects = new ArrayList<Project>();
                while (result.next()) {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    Date start = result.getDate(3);
                    Project project = new Project(id, title, start, null);
                    projects.add(project);
                }
                return projects;
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Project>();
        }
    }

    @Override
    public void addProject(Project project) {
        String sql = "INSERT INTO PROJECT (project_title, start_date) VALUES (?, ?)";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getTitle());
            statement.setDate(2, Utils.utilDateToSql(project.getStartDate()));
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
        String sql = "UPDATE PROJECT SET project_title = ?, start_date = ?, end_date = ? WHERE project_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getTitle());
            statement.setDate(2, Utils.utilDateToSql(project.getStartDate()));
            statement.setDate(3, Utils.utilDateToSql(project.getEndDate()));
            statement.setInt(4, projectId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }
}