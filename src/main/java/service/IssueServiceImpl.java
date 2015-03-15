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

    public void editIssue(int issue_id, Issue issue) {
        IssueDao issueDao = new IssueDaoImpl();
        issueDao.updateIssue(issue_id, issue);
    }

    public void removeIssue(int issue_id) {
        IssueDao issueDao = new IssueDaoImpl();
        issueDao.removeIssue(issue_id);
    }

    public List<Issue> getIssues(int projectId, int page) {
        IssueDao issueDao = new IssueDaoImpl();
        return issueDao.getIssues(projectId, page);
    }

    public Issue getIssue(int issue_id) {
        IssueDao issueDao = new IssueDaoImpl();
        return issueDao.getIssue(issue_id);
    }
}
