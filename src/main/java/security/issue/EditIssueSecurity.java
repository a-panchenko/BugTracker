package security.issue;

import dto.IssueDto;
import model.Issue;
import model.Project;

public interface EditIssueSecurity {

    Issue secureEditIssue(IssueDto issueDto);

}
