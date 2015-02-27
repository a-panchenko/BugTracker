package dao;

import dao.exceptions.NoConnectionException;
import dao.exceptions.QueryExecException;
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
    public Issue getIssue(int issueId) throws NoConnectionException {
        String sql = "SELECT * FROM ISSUE WHERE issue_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = Utils.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, issueId);
            result = statement.executeQuery();
            if (result.isBeforeFirst()) {
                result.next();
                int id = result.getInt("issue_id");
                int projectId = result.getInt("project_id");
                String title = result.getString("issue_title");
                String description = result.getString("description");
                String priority = result.getString("priority");
                String status = result.getString("status");
                Date creationDate = result.getDate("creation_date");
                Date solvingDate = result.getDate("solving_date");
                Issue issue = new Issue(id, projectId, title, description, priority,
                        status, creationDate, solvingDate);
                return issue;
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
    public List<Issue> getIssues(int projectId) throws NoConnectionException {
        String sql = "SELECT * FROM ISSUE WHERE project_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = Utils.getDataSource().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            result = statement.executeQuery();
            List<Issue> issues = new ArrayList<Issue>();
            while (result.next()) {
                int id = result.getInt("issue_id");
                int projId = result.getInt("project_id");
                String title = result.getString("issue_title");
                String description = result.getString("description");
                String priority = result.getString("priority");
                String status = result.getString("status");
                Date creationDate = result.getDate("creation_date");
                Date solvingDate = result.getDate("solving_date");
                Issue issue = new Issue(id, projId, title, description, priority,
                        status, creationDate, solvingDate);
                issues.add(issue);
            }
            return issues;
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
    public void addIssue(Issue issue) throws NoConnectionException {

    }

    @Override
    public void removeIssue(int issueId) throws NoConnectionException {

    }

    @Override
    public void updateIssue(int issueId, Issue issue) throws NoConnectionException {

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
