package service;

import model.Issue;

import java.util.List;

public interface IssueService {

    public void addIssue(Issue issue);

    public void editIssue(int issueId, Issue issue);

    public void removeIssue(int issueId);

    public List<Issue> getIssues(int projectId, int page);

    public Issue getIssue(int issueId);
}
