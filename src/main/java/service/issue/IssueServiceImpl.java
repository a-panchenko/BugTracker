package service.issue;

import dao.issue.IssueDaoImpl;
import model.Issue;
import service.AbstractService;
import service.TransactionScript;

import java.sql.Connection;
import java.util.List;

public class IssueServiceImpl extends AbstractService implements IssueService {

    public Void addIssue(final Issue issue) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new IssueDaoImpl(connection).addIssue(issue);
                return null;
            }
        });
    }

    public Void editIssue(final Issue issue) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new IssueDaoImpl(connection).updateIssue(issue);
                return null;
            }
        });
    }

    public Void removeIssue(final int issueId) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new IssueDaoImpl(connection).removeIssue(issueId);
                return null;
            }
        });
    }

    public List<Issue> getIssues(final int projectId, final int page) {
        return service(new TransactionScript<List<Issue>>() {
            @Override
            public List<Issue> transact(Connection connection) {
                return new IssueDaoImpl(connection).getIssues(projectId, page);
            }
        });
    }

    public List<Issue> getIssues(final int projectId) {
        return service(new TransactionScript<List<Issue>>() {
            @Override
            public List<Issue> transact(Connection connection) {
                return new IssueDaoImpl(connection).getIssues(projectId);
            }
        });
    }

    public Issue getIssue(final int issueId) {
        return service(new TransactionScript<Issue>() {
            @Override
            public Issue transact(Connection connection) {
                return new IssueDaoImpl(connection).getIssue(issueId);
            }
        });
    }
}
