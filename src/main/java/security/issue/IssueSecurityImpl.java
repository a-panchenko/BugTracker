package security.issue;

import model.Project;
import model.ProjectMember;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import java.util.HashSet;
import java.util.Set;

public class IssueSecurityImpl implements IssueSecurity {

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public boolean isAllowedToEditIssue(String username, Project project) {
        Set<String> allowed = new HashSet<>();
        for (ProjectMember projectMember : projectMemberService.getMembers(project.getId())) {
            allowed.add(projectMember.getName());
        }
        if (username.equals(project.getProjectLeed())
                || groupMemberService.isUserInGroup(username, "administrators")) {
            allowed.add(username);
        }
        return allowed.contains(username);
    }
}
