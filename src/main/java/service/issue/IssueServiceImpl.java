package service.issue;

import dao.exceptions.QueryExecutionException;
import dao.issue.IssueDaoImpl;
import model.Issue;
import service.DataSourceProvider;
import service.exceptions.TransactionFailException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class IssueServiceImpl implements IssueService {

    public void addIssue(Issue issue) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new IssueDaoImpl(connection).addIssue(issue);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public void editIssue(Issue issue) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new IssueDaoImpl(connection).updateIssue(issue);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public void removeIssue(int issueId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new IssueDaoImpl(connection).removeIssue(issueId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public List<Issue> getIssues(int projectId, int page) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new IssueDaoImpl(connection).getIssues(projectId, page);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public List<Issue> getIssues(int projectId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new IssueDaoImpl(connection).getIssues(projectId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public Issue getIssue(int issueId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new IssueDaoImpl(connection).getIssue(issueId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }
}
