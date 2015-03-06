package dao;

import dao.resultparser.IssueResultParser;
import dao.resultparser.ResultParser;
import model.Issue;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IssueDaoImpl extends AbstractDao<Issue> implements IssueDao {

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
        }
        @Override
        public void completeUpdate(PreparedStatement statement, int id, Issue issue) throws SQLException {
            completeAdd(statement, issue);
            statement.setDate(7, Utils.utilDateToSql(issue.getSolvingDate()));
            statement.setInt(8, id);
        }
    };

    @Override
    public Issue getIssue(int issueId) {
        return selectById(issueId, Utils.SELECT_ISSUE_BY_ISSUE_ID, issueResultParser);
    }

    @Override
    public List<Issue> getIssues(int projectId, int page) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(Utils.SELECT_ISSUES)) {
            statement.setInt(1, projectId);
            statement.setInt(2, page * Utils.ROWS_PER_PAGE);
            statement.setInt(3, (page - 1) * Utils.ROWS_PER_PAGE + 1);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return issueResultParser.extractAll(result);
                }
                else {
                    return new ArrayList<Issue>();
                }
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Issue>();
        }
    }

    @Override
    public void addIssue(Issue issue) {
        insert(issue, Utils.INSERT_INTO_ISSUE, placeholderCompleter);
    }

    @Override
    public void removeIssue(int issueId) {
        deleteById(issueId, Utils.DELETE_ISSUE_BY_ISSUE_ID);
    }

    @Override
    public void updateIssue(int issueId, Issue issue) {
        update(issueId, issue, Utils.UPDATE_ISSUE, placeholderCompleter);
    }
}
