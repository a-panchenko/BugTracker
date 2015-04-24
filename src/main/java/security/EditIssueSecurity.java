package security;

import controller.exceptions.NoSuchIssueException;
import controller.exceptions.NotAllowedToEditIssueException;
import dto.IssueDto;
import model.Issue;
import model.Project;
import service.*;

import java.util.Date;
import java.util.List;

public class EditIssueSecurity {

    private IssueService issueService = new IssueServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    public Issue checkEditIssue(IssueDto issueDto, String username) {
        Issue issue = getPresentIssue(issueDto);
        Project project = projectService.getProject(issue.getProjectId());
        editCreator(issueDto, issue, project, username);
        editAssigned(issueDto, issue, project, username);
        editPriority(issueDto, issue, project, username);
        editStatus(issueDto, issue, project, username);
        editSolvingDate(issue);
        editTitle(issueDto, issue, project, username);
        editDescription(issueDto, issue, project, username);
        editProjectId(issueDto, issue, username);
        return issue;
    }

    public void checkIssue(Issue issue) {
        if (issue == null) {
            throw new NoSuchIssueException();
        }
    }

    private Issue getPresentIssue(IssueDto issueDto) {
        int id = Integer.valueOf(issueDto.getId());
        Issue issue = issueService.getIssue(id);
        checkIssue(issue);
        return issue;
    }

    private void editCreator(IssueDto issueDto, Issue issue, Project project, String username) {
        String creator = issueDto.getCreator();
        if (creator != null) {
            if (groupMemberService.isUserInGroup(username, "administrators") || project.getProjectLeed().equals(username)) {
                issue.setCreator(creator);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editAssigned(IssueDto issueDto, Issue issue, Project project, String username) {
        String assigned = issueDto.getAssigned();
        if (assigned != null) {
            if (groupMemberService.isUserInGroup(username, "administrators") || project.getProjectLeed().equals(username)) {
                issue.setAssigned(assigned);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editPriority(IssueDto issueDto, Issue issue, Project project, String username) {
        String priority = issueDto.getPriority();
        if (priority != null) {
            if (groupMemberService.isUserInGroup(username, "administrators")
                    || project.getProjectLeed().equals(username)
                    || issue.getCreator().equals(username)) {
                issue.setPriority(priority);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editStatus(IssueDto issueDto, Issue issue, Project project, String username) {
        String newStatus = issueDto.getStatus();
        String oldStatus = issue.getStatus();
        if (groupMemberService.isUserInGroup(username, "administrators") || username.equals(project.getProjectLeed())) {
            issue.setStatus(newStatus);
        }
        else {
            if (! newStatus.equals(oldStatus)) {
                switch (oldStatus) {
                    case "open":
                        caseOldStatusOpen(username, issue, project, newStatus);
                        break;
                    case "in progress":
                        caseOldStatusInProgress(username, issue, project, newStatus);
                        break;
                    case "resolved":
                        caseOldStatusResolved(username, issue, project, newStatus);
                        break;
                    case "testing":
                        caseOldStatusTesting(username, issue, project, newStatus);
                        break;
                    default:
                        throw new NotAllowedToEditIssueException();
                }
            }
        }
    }

    private void caseOldStatusOpen(String username, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "in progress":
                if (issue.getAssigned() != null) {
                    if (username.equals(issue.getAssigned())) {
                        issue.setStatus("in progress");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getMembersToAssign(project, username);
                    if (allowed.contains(username)) {
                        issue.setStatus("in progress");
                        issue.setAssigned(username);
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                break;
            case "close":
                caseNewStatusClose(username, issue, project);
                break;
            default:
                throw new NotAllowedToEditIssueException();
        }
    }

    private void caseOldStatusInProgress(String username, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "resolved":
                if (issue.getAssigned() != null) {
                    if (username.equals(issue.getAssigned())) {
                        issue.setStatus("resolved");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getMembersToAssign(project, username);
                    if (allowed.contains(username)) {
                        issue.setStatus("resolved");
                        issue.setAssigned(username);
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                break;
            case "close":
                caseNewStatusClose(username, issue, project);
                break;
            default:
                throw new NotAllowedToEditIssueException();
        }
    }

    private void caseOldStatusResolved(String username, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "testing":
                if (issue.getCreator() != null) {
                    if (username.equals(issue.getCreator())) {
                        issue.setStatus("testing");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getPossibleCreators(project, username);
                    if (allowed.contains(username)) {
                        issue.setStatus("testing");
                        issue.setCreator(username);
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                break;
            case "close":
                caseNewStatusClose(username, issue, project);
                break;
            default:
                throw new NotAllowedToEditIssueException();
        }
    }

    private void caseOldStatusTesting(String username, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "close":
                if (issue.getCreator() != null) {
                    if (username.equals(issue.getCreator())) {
                        issue.setStatus("close");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getPossibleCreators(project, username);
                    if (allowed.contains(username)) {
                        issue.setStatus("close");
                        issue.setCreator(username);
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                break;
            default:
                throw new NotAllowedToEditIssueException();
        }
    }

    private void caseNewStatusClose(String username, Issue issue, Project project) {
        if (groupMemberService.isUserInGroup(username, "administrators") || username.equals(project.getProjectLeed())) {
            issue.setStatus("close");
        }
        else {
            throw new NotAllowedToEditIssueException();
        }
    }

    private void editSolvingDate(Issue issue) {
        if (issue.getStatus().equals("close") && issue.getSolvingDate() == null) {
            issue.setSolvingDate(new Date());
        }
        if (! issue.getStatus().equals("close")) {
            issue.setSolvingDate(null);
        }
    }

    private void editTitle(IssueDto issueDto, Issue issue, Project project, String username) {
        String title = issueDto.getTitle();
        if (title != null) {
            if (groupMemberService.isUserInGroup(username, "administrators")
                    || project.getProjectLeed().equals(username)
                    || issue.getCreator().equals(username)) {
                issue.setTitle(title);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editDescription(IssueDto issueDto, Issue issue, Project project, String username) {
        String description = issueDto.getDescription();
        if (description != null) {
            if (groupMemberService.isUserInGroup(username, "administrators")
                    || project.getProjectLeed().equals(username)
                    || issue.getCreator().equals(username)) {
                issue.setDescription(description);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editProjectId(IssueDto issueDto, Issue issue, String username) {
        String projectId = issueDto.getProjectId();
        if (projectId != null) {
            if (groupMemberService.isUserInGroup(username, "administrators")) {
                int projectIdValue = Integer.valueOf(projectId);
                issue.setProjectId(projectIdValue);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }
}
