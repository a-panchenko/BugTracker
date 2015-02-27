package dao;

import dao.exceptions.NoConnectionException;
import model.Issue;

import java.util.List;

public interface IssueDao {

    public Issue getIssue(int issueId) throws NoConnectionException;

    public List<Issue> getIssues(int projectId) throws NoConnectionException;

    public void addIssue(Issue issue) throws NoConnectionException;

    public void removeIssue(int issueId) throws NoConnectionException;

    public void updateIssue(int issueId, Issue issue) throws NoConnectionException;
}
