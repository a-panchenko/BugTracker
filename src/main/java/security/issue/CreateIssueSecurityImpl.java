package security.issue;

import security.exceptions.InvalidAssignedMemberException;
import dto.IssueDto;
import model.Issue;
import model.Project;
import model.ProjectMember;
import security.exceptions.NotAllowedException;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateIssueSecurityImpl implements CreateIssueSecurity {

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public Issue secureCreateIssue(IssueDto issueDto, String username) {
        Issue issue = new Issue();
        setProjectId(issueDto, issue, username);
        setTitle(issueDto, issue);
        setDescription(issueDto, issue);
        setPriority(issueDto, issue);
        issue.setStatus("open");
        issue.setCreationDate(new Date());
        setCreator(issueDto, issue, username);
        setAssigned(issueDto, issue, username);
        return issue;
    }

    private void checkAllowedToCreateIssue(Project project, String username) {
        List<String> allowed = getAllowed(project);
        if (! allowed.contains(username) && ! groupMemberService.isUserInGroup(username, "administrators")) {
            throw new NotAllowedException();
        }
    }

    private List<String> getAllowed(Project project) {
        List<String> allowed = new ArrayList<>();
        for (ProjectMember projectMember : projectMemberService.getMembers(project.getId())) {
            allowed.add(projectMember.getName());
        }
        allowed.add(project.getProjectLeed());
        return allowed;
    }

    private void setProjectId(IssueDto issueDto, Issue issue, String username) {
        int projectId = Integer.valueOf(issueDto.getProjectId());
        Project project = projectService.getProject(projectId);
        checkAllowedToCreateIssue(project, username);
        issue.setProjectId(projectId);
    }

    private void setTitle(IssueDto issueDto, Issue issue) {
        if (issueDto.getTitle() != null) {
            issue.setTitle(issueDto.getTitle());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setDescription(IssueDto issueDto, Issue issue) {
        if (issueDto.getDescription() != null) {
            issue.setDescription(issueDto.getDescription());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setPriority(IssueDto issueDto, Issue issue) {
        if (issueDto.getPriority() != null) {
            issue.setPriority(issueDto.getPriority());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setCreator(IssueDto issueDto, Issue issue, String username) {
        String creator = issueDto.getCreator();
        if (! creator.isEmpty()) {
            Project project = projectService.getProject(issue.getProjectId());
            List<String> allowed = projectMemberService.getPossibleCreators(project, username);
            if (allowed.contains(creator)) {
                issue.setCreator(creator);
            }
            else {
                throw new InvalidAssignedMemberException();
            }
        }
    }

    private void setAssigned(IssueDto issueDto, Issue issue, String username) {
        String assigned = issueDto.getAssigned();
        if (! assigned.isEmpty()) {
            Project project = projectService.getProject(issue.getProjectId());
            List<String> allowed = projectMemberService.getMembersToAssign(project, username);
            if (allowed.contains(assigned)) {
                issue.setAssigned(assigned);
            }
            else {
                throw new InvalidAssignedMemberException();
            }
        }
    }
}
