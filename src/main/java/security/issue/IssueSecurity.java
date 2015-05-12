package security.issue;

import model.Project;

public interface IssueSecurity {

    boolean isAllowedToEditIssue(String username, Project project);
}
