package security.issue;

import dto.IssueDto;
import model.Issue;
import model.Project;
import security.exceptions.InvalidAssignedMemberException;
import security.exceptions.NotAllowedException;
import service.issue.IssueService;
import service.issue.IssueServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import java.util.Date;
import java.util.Set;

public class EditIssueSecurityImpl extends IssueSecurityImpl implements EditIssueSecurity {

    private IssueService issueService = new IssueServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    public Issue secureEditIssue(IssueDto issueDto) {
        Issue issue = issueService.getIssue(Integer.valueOf(issueDto.getId()));
        Project project = projectService.getProject(issue.getProjectId());
        if (isAllowedToEditIssue(issueDto.getRequestPerformer(), project)) {
            editAssigned(issueDto, issue, project);
            editStatus(issueDto, issue);
            editSolvingDate(issue);
            editTitle(issueDto, issue);
            editDescription(issueDto, issue);
            editPriority(issueDto, issue);
            return issue;
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editAssigned(IssueDto issueDto, Issue issue, Project project) {
        String assigned = issueDto.getAssigned();
        if (assigned != null && ! assigned.isEmpty()) {
            Set<String> allowed = projectMemberService.getMembersToAssign(project, issueDto.getRequestPerformer());
            if (! allowed.contains(assigned)) {
                throw new InvalidAssignedMemberException();
            }
        }
        issue.setAssigned(issueDto.getAssigned());
    }

    private void editPriority(IssueDto issueDto, Issue issue) {
        String priority = issueDto.getPriority();
        if (priority != null && ! priority.isEmpty()) {
            issue.setPriority(issueDto.getPriority());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editStatus(IssueDto issueDto, Issue issue) {
        String status = issueDto.getStatus();
        if (status != null && ! status.isEmpty()) {
            issue.setStatus(status);
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editSolvingDate(Issue issue) {
        if (issue.getStatus().equals("resolved") && issue.getSolvingDate() == null) {
            issue.setSolvingDate(new Date());
        }
        if (! issue.getStatus().equals("resolved")) {
            issue.setSolvingDate(null);
        }
    }

    private void editTitle(IssueDto issueDto, Issue issue) {
        String title = issueDto.getTitle();
        if (title != null && ! title.isEmpty()) {
            issue.setTitle(title);
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editDescription(IssueDto issueDto, Issue issue) {
        String description = issueDto.getDescription();
        if (description != null && ! description.isEmpty()) {
            issue.setDescription(description);
        }
        else {
            throw new NotAllowedException();
        }
    }

}
