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

import java.util.*;

public class CreateIssueSecurityImpl extends IssueSecurityImpl implements CreateIssueSecurity {

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();

    public Issue secureCreateIssue(IssueDto issueDto) {
        Issue issue = new Issue();
        setProjectId(issueDto, issue);
        setCreator(issueDto, issue);
        setTitle(issueDto, issue);
        setDescription(issueDto, issue);
        setPriority(issueDto, issue);
        issue.setStatus("open");
        issue.setCreationDate(new Date());
        setAssigned(issueDto, issue);
        return issue;
    }

    private void setProjectId(IssueDto issueDto, Issue issue) {
        int projectId = Integer.valueOf(issueDto.getProjectId());
        issue.setProjectId(projectId);
    }

    private void setTitle(IssueDto issueDto, Issue issue) {
        if (issueDto.getTitle() != null && ! issueDto.getTitle().isEmpty()) {
            issue.setTitle(issueDto.getTitle());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setDescription(IssueDto issueDto, Issue issue) {
        if (issueDto.getDescription() != null && ! issueDto.getDescription().isEmpty()) {
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

    private void setCreator(IssueDto issueDto, Issue issue) {
        String creator = issueDto.getRequestPerformer();
        if (creator != null && ! creator.isEmpty()) {
            Project project = projectService.getProject(Integer.valueOf(issueDto.getProjectId()));
            if (isAllowedToEditIssue(issueDto.getRequestPerformer(), project)) {
                issue.setCreator(creator);
            }
            else {
                throw new NotAllowedException();
            }
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setAssigned(IssueDto issueDto, Issue issue) {
        String assigned = issueDto.getAssigned();
        if (assigned != null && ! assigned.isEmpty()) {
            checkAssigned(issueDto);
            issue.setAssigned(assigned);
        }
    }

    private void checkAssigned(IssueDto issueDto) {
        Project project = projectService.getProject(Integer.valueOf(issueDto.getProjectId()));
        Set<String> allowed = projectMemberService.getMembersToAssign(project, issueDto.getRequestPerformer());
        if (! allowed.contains(issueDto.getAssigned())) {
            throw new InvalidAssignedMemberException();
        }
    }
}
