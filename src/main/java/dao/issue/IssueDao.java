package dao.issue;

import model.Issue;

import java.util.List;

public interface IssueDao {

    Issue getIssue(int issueId);

    List<Issue> getIssues(int projectId, int page);

    List<Issue> getIssues(int projectId);

    void addIssue(Issue issue);

    void removeIssue(int issueId);

    void updateIssue(Issue issue);
}
