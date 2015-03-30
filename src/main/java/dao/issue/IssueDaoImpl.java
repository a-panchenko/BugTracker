package dao.issue;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.Utils;
import dao.ResultParser;
import model.Issue;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueDaoImpl extends AbstractDao<Issue> implements IssueDao {

    private static final String SELECT_ISSUE_BY_ISSUE_ID = "SELECT * FROM ISSUE WHERE issue_id = ?";
    private static final String SELECT_ISSUES = "SELECT * FROM (SELECT /*+ FIRST_ROWS(20) */ a.*, ROWNUM rnum FROM " +
            "(SELECT * FROM ISSUE WHERE project_id = ? ORDER BY issue_id) a WHERE ROWNUM <= ?) WHERE rnum >= ?";
    private static final String INSERT_INTO_ISSUE = "INSERT INTO " +
            "ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator, assigned) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ISSUE = "UPDATE ISSUE SET project_id = ?, issue_title = ?, " +
            "issue_description = ?, priority = ?, status = ?, creation_date = ?, creator = ?, assigned = ?, solving_date = ? WHERE issue_id = ?";
    private static final String DELETE_ISSUE_BY_ISSUE_ID = "DELETE FROM ISSUE WHERE issue_id = ?";

    private final Logger LOGGER = Logger.getLogger(IssueDaoImpl.class);
    private final ResultParser<Issue> issueResultParser = new IssueResultParser();
    private final PlaceholderCompleter<Issue> placeholderCompleter = new PlaceholderCompleter<Issue>() {
        @Override
        public void completeAdd(PreparedStatement statement, Issue issue) throws SQLException {
            statement.setInt(1, issue.getProjectId());
            statement.setString(2, issue.getTitle());
            statement.setString(3, issue.getDescription());
            statement.setString(4, issue.getPriority());
            statement.setString(5, issue.getStatus());
            statement.setDate(6, Utils.utilDateToSql(issue.getCreationDate()));
            statement.setString(7, issue.getCreator());
            statement.setString(8, issue.getAssigned());
        }
        @Override
        public void completeUpdate(PreparedStatement statement, int id, Issue issue) throws SQLException {
            completeAdd(statement, issue);
            if (issue.getSolvingDate() == null) {
                statement.setNull(9, Types.DATE);
            }
            else {
                statement.setDate(9, Utils.utilDateToSql(issue.getSolvingDate()));
            }
            statement.setInt(10, id);
        }
    };

    @Override
    public Issue getIssue(int issueId) {
        return selectById(issueId, SELECT_ISSUE_BY_ISSUE_ID, issueResultParser);
    }

    @Override
    public List<Issue> getIssues(int projectId, int page) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ISSUES)) {
            statement.setInt(1, projectId);
            statement.setInt(2, page * Utils.ROWS_PER_PAGE);
            statement.setInt(3, (page - 1) * Utils.ROWS_PER_PAGE + 1);
            try (ResultSet result = statement.executeQuery()) {
                return issueResultParser.extractAll(result);
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Issue>();
        }
    }

    @Override
    public void addIssue(Issue issue) {
        insert(issue, INSERT_INTO_ISSUE, placeholderCompleter);
    }

    @Override
    public void removeIssue(int issueId) {
        deleteById(issueId, DELETE_ISSUE_BY_ISSUE_ID);
    }

    @Override
    public void updateIssue(int issueId, Issue issue) {
        update(issueId, issue, UPDATE_ISSUE, placeholderCompleter);
    }
}
