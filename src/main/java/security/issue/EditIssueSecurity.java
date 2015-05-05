package security.issue;

import dto.IssueDto;
import model.Issue;

public interface EditIssueSecurity {

    Issue secureEditIssue(IssueDto issueDto, String username);
}
