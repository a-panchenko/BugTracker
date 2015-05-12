package service.issue;

import model.Issue;

import java.util.List;

public interface IssueService {

    void addIssue(Issue issue);

    void editIssue(Issue issue);

    void removeIssue(int issueId);

    List<Issue> getIssues(int projectId, int page);

    List<Issue> getIssues(int projectId);

    Issue getIssue(int issueId);
}
