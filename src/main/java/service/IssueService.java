package service;

import model.Issue;

import java.util.List;

public interface IssueService {
    public void addIssue(Issue issue);
    public void editIssue(int issue_id, Issue issue);
    public void removeIssue(int issue_id);
    public List<Issue> getIssues(int projectId, int page);
    public Issue getIssue(int issue_id);
}
