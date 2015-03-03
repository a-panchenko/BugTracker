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

    @Override
    public Issue getIssue(int issueId) {
        return selectById(issueId, Utils.SELECT_PROJECT_BY_ISSUE_ID, issueResultParser);
    }

    @Override
    public List<Issue> getIssues(int projectId) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(Utils.SELECT_ISSUES_BY_PROJECT_ID)) {
            statement.setInt(1, projectId);
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
        String sql = "INSERT INTO ISSUE (project_id, issue_title, description, " +
                "priority, status, creation_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, issue.getProjectId());
            statement.setString(2, issue.getTitle());
            statement.setString(3, issue.getDescription());
            statement.setString(4, issue.getPriority());
            statement.setString(5, issue.getStatus());
            statement.setDate(6, Utils.utilDateToSql(issue.getCreationDate()));
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    @Override
    public void removeIssue(int issueId) {
        String sql = "DELETE FROM ISSUE WHERE issue_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, issueId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    @Override
    public void updateIssue(int issueId, Issue issue) {
        String sql = "UPDATE ISSUE SET project_id = ?, issue_title = ?, description = ? priority = ? " +
                "status = ? creation_date = ? solving_date = ? WHERE issue_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, issue.getProjectId());
            statement.setString(2, issue.getTitle());
            statement.setString(3, issue.getDescription());
            statement.setString(4, issue.getPriority());
            statement.setString(5, issue.getStatus());
            statement.setDate(6, Utils.utilDateToSql(issue.getCreationDate()));
            statement.setDate(7, Utils.utilDateToSql(issue.getSolvingDate()));
            statement.setInt(8, issueId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }
}
