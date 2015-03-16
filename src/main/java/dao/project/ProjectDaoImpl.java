package dao.project;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.Utils;
import dao.ResultParser;
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
    private final PlaceholderCompleter<Project> placeholderCompleter = new PlaceholderCompleter<Project>() {
        @Override
        public void completeAdd(PreparedStatement statement, Project project) throws SQLException {
            statement.setString(1, project.getTitle());
            statement.setString(2, project.getDescription());
            statement.setDate(3, Utils.utilDateToSql(project.getStartDate()));
        }
        @Override
        public void completeUpdate(PreparedStatement statement, int id, Project project) throws SQLException {
            completeAdd(statement, project);
            statement.setDate(4, Utils.utilDateToSql(project.getEndDate()));
            statement.setInt(5, id);
        }
    };

    @Override
    public Project getProject(int projectId) {
        return selectById(projectId, Utils.SELECT_PROJECT_BY_PROJECT_ID, projectResultParser);
    }

    @Override
    public List<Project> getProjects() {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(Utils.SELECT_ALL_PROJECTS)) {
            try (ResultSet result = statement.executeQuery()) {
                return projectResultParser.extractAll(result);
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Project>();
        }
    }

    @Override
    public List<Project> getProjects(int page) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(Utils.SELECT_PROJECTS)) {
            statement.setInt(1, page * Utils.ROWS_PER_PAGE);
            statement.setInt(2, (page - 1) * Utils.ROWS_PER_PAGE + 1);
            try (ResultSet result = statement.executeQuery()) {
                return projectResultParser.extractAll(result);
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Project>();
        }
    }

    @Override
    public void addProject(Project project) {
        insert(project, Utils.INSERT_INTO_PROJECT, placeholderCompleter);
    }

    @Override
    public void removeProject(int projectId) {
        deleteById(projectId, Utils.DELETE_PROJECT_BY_PROJECT_ID);
    }

    @Override
    public void updateProject(int projectId, Project project) {
        update(projectId, project, Utils.UPDATE_PROJECT, placeholderCompleter);
    }
}