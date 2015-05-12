package security.issue;

import dto.IssueDto;
import model.Issue;

public interface CreateIssueSecurity {

    Issue secureCreateIssue(IssueDto issueDto);
}
