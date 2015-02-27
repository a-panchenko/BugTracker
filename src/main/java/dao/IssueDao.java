package dao;

import model.Issue;

import java.util.List;

public interface IssueDao {

    public Issue getIssue(int issueId);

    public List<Issue> getIssues(int projectId);

    public void addIssue(Issue issue);

    public void removeIssue(int issueId);

    public void updateIssue(int issueId, Issue issue);
}
