package dao.project;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.Utils;
import dao.ResultParser;
import model.Project;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl extends AbstractDao<Project> implements ProjectDao {

    private static final String SELECT_PROJECT_BY_PROJECT_ID = "SELECT * FROM PROJECT WHERE project_id = ?";
    private static final String SELECT_PROJECTS = "SELECT * FROM (SELECT /*+ FIRST_ROWS(20) */ a.*, ROWNUM rnum FROM " +
            "(SELECT * FROM PROJECT ORDER BY project_id) a WHERE ROWNUM <= ?) WHERE rnum >= ?";
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT";
    private static final String INSERT_INTO_PROJECT = "INSERT INTO " +
            "PROJECT (project_title, project_description, start_date, project_leed) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PROJECT = "UPDATE PROJECT " +
            "SET project_title = ?, project_description = ?, start_date = ?, project_leed = ?, end_date = ? WHERE project_id = ?";
    private static final String DELETE_PROJECT_BY_PROJECT_ID = "DELETE FROM PROJECT WHERE project_id = ?";

    private final Logger LOGGER = Logger.getLogger(ProjectDaoImpl.class);
    private final ResultParser<Project> projectResultParser = new ProjectResultParser();
    private final PlaceholderCompleter<Project> placeholderCompleter = new PlaceholderCompleter<Project>() {
        @Override
        public void completeAdd(PreparedStatement statement, Project project) throws SQLException {
            statement.setString(1, project.getTitle());
            statement.setString(2, project.getDescription());
            statement.setDate(3, Utils.utilDateToSql(project.getStartDate()));
            statement.setString(4, project.getProjectLeed());
        }
        @Override
        public void completeUpdate(PreparedStatement statement, int id, Project project) throws SQLException {
            completeAdd(statement, project);
            if (project.getEndDate() == null) {
                statement.setNull(5, Types.DATE);
            }
            else {
                statement.setDate(5, Utils.utilDateToSql(project.getEndDate()));
            }
            statement.setInt(6, id);
        }
    };

    @Override
    public Project getProject(int projectId) {
        return selectById(projectId, SELECT_PROJECT_BY_PROJECT_ID, projectResultParser);
    }

    @Override
    public List<Project> getProjects() {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PROJECTS)) {
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
             PreparedStatement statement = connection.prepareStatement(SELECT_PROJECTS)) {
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
        insert(project, INSERT_INTO_PROJECT, placeholderCompleter);
    }

    @Override
    public void removeProject(int projectId) {
        deleteById(projectId, DELETE_PROJECT_BY_PROJECT_ID);
    }

    @Override
    public void updateProject(int projectId, Project project) {
        update(projectId, project, UPDATE_PROJECT, placeholderCompleter);
    }
}