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
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = Utils.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            result = statement.executeQuery();
            if (result.isBeforeFirst()) {
                result.next();
                int id = result.getInt("project_id");
                String title = result.getString("project_title");
                Date start = result.getDate("start_date");
                Project project = new Project(id, title, start, null);
                return project;
            }
            else {
                return null;
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return null;
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
            releaseResource(result);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        String sql = "SELECT * FROM PROJECT";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = Utils.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();
            List<Project> projects = new ArrayList<Project>();
            while (result.next()) {
                int id = result.getInt("project_id");
                String title = result.getString("project_title");
                Date start = result.getDate("start_date");
                Project project = new Project(id, title, start, null);
                projects.add(project);
            }
            return projects;
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Project>();
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
            releaseResource(result);
        }
    }

    @Override
    public void addProject(Project project) {
        String sql = "INSERT INTO PROJECT (project_title, start_date) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Utils.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            String title = project.getTitle();
            java.util.Date date = project.getStartDate();
            java.sql.Date start = new java.sql.Date(date.getTime());
            statement.setString(1, title);
            statement.setDate(2, start);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
        }
    }

    @Override
    public void removeProject(int projectId) {
        String sql = "DELETE FROM PROJECT WHERE project_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Utils.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
        }
    }

    @Override
    public void updateProject(int projectId, Project project) {
        String sql = "UPDATE PROJECT SET project_title = ?, start_date = ?, end_date = ? WHERE project_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Utils.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            String title = project.getTitle();
            java.util.Date startUtilDate = project.getStartDate();
            java.sql.Date startSqlDate = null;
            if (startUtilDate != null) {
                startSqlDate = new java.sql.Date(startUtilDate.getTime());
            }
            java.util.Date endUtilDate = project.getEndDate();
            java.sql.Date endSqlDate = null;
            if (endUtilDate != null) {
                endSqlDate = new java.sql.Date(endUtilDate.getTime());
            }
            statement.setString(1, title);
            statement.setDate(2, startSqlDate);
            statement.setDate(3, endSqlDate);
            statement.setInt(4, projectId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
        }
    }

    private void releaseResource(Object resourse) {
        try {
            if (resourse != null) {
                if (resourse instanceof Connection) {
                    ((Connection) resourse).close();
                }
                if (resourse instanceof PreparedStatement) {
                    ((PreparedStatement) resourse).close();
                }
                if (resourse instanceof ResultSet) {
                    ((ResultSet) resourse).close();
                }
            }
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
    }
}