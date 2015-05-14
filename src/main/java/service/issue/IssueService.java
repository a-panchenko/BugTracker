package service.issue;

import model.Issue;

import java.util.List;

public interface IssueService {

    Void addIssue(Issue issue);

    Void editIssue(Issue issue);

    Void removeIssue(int issueId);

    List<Issue> getIssues(int projectId, int page);

    List<Issue> getIssues(int projectId);

    Issue getIssue(int issueId);
}
