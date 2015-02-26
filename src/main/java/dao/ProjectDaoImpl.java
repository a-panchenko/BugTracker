package dao;

import dao.exceptions.NoConnectionException;
import dao.exceptions.QueryExecException;
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
    public Project getProject(int projectId) throws NoConnectionException {
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
                Project project = new Project(id, title, start);
                return project;
            }
            else {
                return null;
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            throw new QueryExecException(se);
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
            releaseResource(result);
        }
    }

    @Override
    public List<Project> getAllProjects() throws NoConnectionException {
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
                Project project = new Project(id, title, start);
                projects.add(project);
            }
            return projects;
        }
        catch (SQLException se) {
            LOGGER.error(se);
            throw new QueryExecException(se);
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
            releaseResource(result);
        }
    }

    @Override
    public void addProject(Project project) throws NoConnectionException {
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
            throw new QueryExecException(se);
        }
        finally {
            releaseResource(connection);
            releaseResource(statement);
        }
    }

    @Override
    public void removeProject(int projectId) {

    }

    @Override
    public void updateProject(int projectId, Project project) {

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
