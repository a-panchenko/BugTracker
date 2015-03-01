package dao;

import model.Issue;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IssueDaoImpl implements IssueDao {

    private final Logger LOGGER = Logger.getLogger(IssueDaoImpl.class);

    @Override
    public Issue getIssue(int issueId) {
        String sql = "SELECT * FROM ISSUE WHERE issue_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, issueId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.isBeforeFirst()) {
                    result.next();
                    int projectId = result.getInt(2);
                    String title = result.getString(3);
                    String description = result.getString(4);
                    String priority = result.getString(5);
                    String status = result.getString(6);
                    Date creationDate = result.getDate(7);
                    Date solvingDate = result.getDate(8);
                    return new Issue(issueId, projectId, title, description, priority,
                            status, creationDate, solvingDate);
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
    public List<Issue> getIssues(int projectId) {
        String sql = "SELECT * FROM ISSUE WHERE project_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            try (ResultSet result = statement.executeQuery()) {
                List<Issue> issues = new ArrayList<Issue>();
                while (result.next()) {
                    int id = result.getInt(1);
                    String title = result.getString(3);
                    String description = result.getString(4);
                    String priority = result.getString(5);
                    String status = result.getString(6);
                    Date creationDate = result.getDate(7);
                    Date solvingDate = result.getDate(8);
                    Issue issue = new Issue(id, projectId, title, description, priority,
                            status, creationDate, solvingDate);
                    issues.add(issue);
                }
                return issues;
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
