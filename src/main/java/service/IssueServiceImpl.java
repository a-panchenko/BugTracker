package service;

import dao.issue.IssueDao;
import dao.issue.IssueDaoImpl;
import model.Issue;

import java.util.List;

public class IssueServiceImpl implements IssueService {

    public void addIssue(Issue issue) {
        IssueDao issueDao = new IssueDaoImpl();
        issueDao.addIssue(issue);
    }

    public void editIssue(Issue issue) {
        IssueDao issueDao = new IssueDaoImpl();
        issueDao.updateIssue(issue);
    }

    public void removeIssue(int issueId) {
        IssueDao issueDao = new IssueDaoImpl();
        issueDao.removeIssue(issueId);
    }

    public List<Issue> getIssues(int projectId, int page) {
        IssueDao issueDao = new IssueDaoImpl();
        return issueDao.getIssues(projectId, page);
    }

    public Issue getIssue(int issueId) {
        IssueDao issueDao = new IssueDaoImpl();
        return issueDao.getIssue(issueId);
    }
}
