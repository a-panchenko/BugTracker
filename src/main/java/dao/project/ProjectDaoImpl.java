package dao.project;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.ResultParser;
import dao.Utils;
import dao.exceptions.QueryExecutionException;
import model.Project;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class ProjectDaoImpl extends AbstractDao<Project, Integer> implements ProjectDao {

    private static final String SELECT_PROJECT_BY_PROJECT_ID = "SELECT * FROM PROJECT WHERE project_id = ?";
    private static final String SELECT_PROJECTS = "SELECT * FROM (SELECT /*+ FIRST_ROWS(20) */ a.*, ROWNUM rnum FROM " +
            "(SELECT * FROM PROJECT ORDER BY project_id) a WHERE ROWNUM <= ?) WHERE rnum >= ?";
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT";
    private static final String INSERT_INTO_PROJECT = "INSERT INTO " +
            "PROJECT (project_title, project_description, start_date, project_leed) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PROJECT = "UPDATE PROJECT " +
            "SET project_title = ?, project_description = ?, start_date = ?, project_leed = ?, end_date = ? WHERE project_id = ?";
    private static final String DELETE_PROJECT_BY_PROJECT_ID = "DELETE FROM PROJECT WHERE project_id = ?";

    private static final Logger LOGGER = Logger.getLogger(ProjectDaoImpl.class);
    
    private final ResultParser<Project> resultParser = new ProjectResultParser();

    private Connection connection;

    public ProjectDaoImpl(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    public Project getProject(int projectId) {
        return select(projectId, SELECT_PROJECT_BY_PROJECT_ID, resultParser);
    }

    @Override
    public List<Project> getProjects() {
        return select(SELECT_ALL_PROJECTS, resultParser);
    }

    @Override
    public List<Project> getProjects(int page) {
        return select(
                SELECT_PROJECTS,
                resultParser,
                page * Utils.ROWS_PER_PAGE,
                (page - 1) * Utils.ROWS_PER_PAGE + 1);
    }

    @Override
    public void addProject(Project project) {
        insert(project, INSERT_INTO_PROJECT, new PlaceholderCompleter<Project>() {
            @Override
            public void complete(PreparedStatement statement, Project project) throws SQLException {
                completeAdd(statement, project);
            }
        });
    }

    @Override
    public void removeProject(int projectId) {
        delete(projectId, DELETE_PROJECT_BY_PROJECT_ID);
    }

    @Override
    public void updateProject(Project project) {
        update(project, UPDATE_PROJECT, new PlaceholderCompleter<Project>() {
            @Override
            public void complete(PreparedStatement statement, Project project) throws SQLException {
                completeAdd(statement, project);
                if (project.getEndDate() == null) {
                    statement.setNull(5, Types.NUMERIC);
                }
                else {
                    statement.setLong(5, project.getEndDate().getTime());
                }
                statement.setInt(6, project.getId());
            }
        });
    }

    private void completeAdd(PreparedStatement statement, Project project) throws SQLException {
        statement.setString(1, project.getTitle());
        statement.setString(2, project.getDescription());
        statement.setLong(3, project.getStartDate().getTime());
        statement.setString(4, project.getProjectLeed());
    }
}