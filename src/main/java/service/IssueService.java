package service;

import model.Issue;

import java.util.List;

public interface IssueService {
    public void addIssue(Issue issue);
    public boolean editIssue(int issue_id, Issue issue);
    public boolean removeIssue(int issue_id);
    public List<Issue> getAll();
    public Issue getIssue(int issue_id);
}
