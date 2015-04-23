package controller.issueController;

import controller.exceptions.NoSuchIssueException;
import controller.exceptions.NotAllowedToEditIssueException;
import model.Issue;
import model.Project;
import org.apache.log4j.Logger;
import service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EditIssueController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditIssueController.class);

    private IssueService issueService = new IssueServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Issue issue = issueService.getIssue(id);
            if (issue != null) {
                request.setAttribute("issue", issue);
                if (request.isUserInRole("administrator")) {
                    List<Project> projects = projectService.getAllProjects();
                    request.setAttribute("projects", projects);
                }
                Project project = projectService.getProject(issue.getProjectId());
                if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
                    List<String> projectMembersToAssign = projectMemberService.getMembersToAssign(project, request.getRemoteUser());
                    request.setAttribute("projectMembersToAssign", projectMembersToAssign);
                    List<String> possibleCreators = projectMemberService.getPossibleCreators(project, request.getRemoteUser());
                    request.setAttribute("possibleCreators", possibleCreators);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("editissue.jsp");
                dispatcher.forward(request, response);
            }
            else {
                throw new NoSuchIssueException();
            }
        }
        catch (NoSuchIssueException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Issue issue = issueService.getIssue(id);
            if (issue != null) {
                Project project = projectService.getProject(issue.getProjectId());
                editCreator(request, issue, project);
                editAssigned(request, issue, project);
                editPriority(request, issue, project);
                editStatus(request, issue, project);
                editSolvingDate(issue);
                editTitle(request, issue, project);
                editDescription(request, issue, project);
                editProjectId(request, issue);
                issueService.editIssue(issue);
                response.sendRedirect("/BugTracker/issue?id=" + id);
            }
            else {
                throw new NoSuchIssueException();
            }
        }
        catch (NoSuchIssueException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (NotAllowedToEditIssueException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void editCreator(HttpServletRequest request, Issue issue, Project project) {
        String creator = request.getParameter("creator");
        if (creator != null) {
            if (request.isUserInRole("administrator") || project.getProjectLeed().equals(request.getRemoteUser())) {
                issue.setCreator(creator);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editAssigned(HttpServletRequest request, Issue issue, Project project) {
        String assigned = request.getParameter("assigned");
        if (assigned != null) {
            if (request.isUserInRole("administrator") || project.getProjectLeed().equals(request.getRemoteUser())) {
                issue.setAssigned(assigned);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editProjectId(HttpServletRequest request, Issue issue) {
        String projectId = request.getParameter("project");
        if (projectId != null) {
            if (request.isUserInRole("administrator")) {
                int projectIdValue = Integer.valueOf(projectId);
                issue.setProjectId(projectIdValue);
            }
            else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editTitle(HttpServletRequest request, Issue issue, Project project) {
        String title = request.getParameter("title");
        if (title != null) {
            if (request.isUserInRole("administrator")
                    || project.getProjectLeed().equals(request.getRemoteUser())
                    || issue.getCreator().equals(request.getRemoteUser())) {
                issue.setTitle(title);
            } else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editDescription(HttpServletRequest request, Issue issue, Project project) {
        String description = request.getParameter("description");
        if (description != null) {
            if (request.isUserInRole("administrator")
                    || project.getProjectLeed().equals(request.getRemoteUser())
                    || issue.getCreator().equals(request.getRemoteUser())) {
                issue.setDescription(description);
            } else {
                throw new NotAllowedToEditIssueException();
            }
        }
    }

    private void editPriority(HttpServletRequest request, Issue issue, Project project) {
        String priority = request.getParameter("priority");
        if (priority != null) {
            if (request.isUserInRole("administrator")
                    || project.getProjectLeed().equals(request.getRemoteUser())
                    || issue.getCreator().equals(request.getRemoteUser())) {
                issue.setPriority(priority);
            } else {
                throw new NotAllowedToEditIssueException();
            }
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

    private void editStatus(HttpServletRequest request, Issue issue, Project project) {
        String newStatus = request.getParameter("status");
        String oldStatus = issue.getStatus();
        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
            issue.setStatus(newStatus);
        }
        else {
            if (! newStatus.equals(oldStatus)) {
                switch (oldStatus) {
                    case "open":
                        caseOldStatusOpen(request, issue, project, newStatus);
                        break;
                    case "in progress":
                        caseOldStatusInProgress(request, issue, project, newStatus);
                        break;
                    case "resolved":
                        caseOldStatusResolved(request, issue, project, newStatus);
                        break;
                    case "testing":
                        caseOldStatusTesting(request, issue, project, newStatus);
                        break;
                    default:
                        throw new NotAllowedToEditIssueException();
                }
            }
        }
    }

    private void caseOldStatusOpen(HttpServletRequest request, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "in progress":
                if (issue.getAssigned() != null) {
                    if (request.getRemoteUser().equals(issue.getAssigned())) {
                        issue.setStatus("in progress");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getMembersToAssign(project, request.getRemoteUser());
                    if (allowed.contains(request.getRemoteUser())) {
                        issue.setStatus("in progress");
                        issue.setAssigned(request.getRemoteUser());
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                break;
            case "close":
                caseNewStatusClose(request, issue, project);
                break;
            default:
                throw new NotAllowedToEditIssueException();
        }
    }

    private void caseOldStatusInProgress(HttpServletRequest request, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "resolved":
                if (issue.getAssigned() != null) {
                    if (request.getRemoteUser().equals(issue.getAssigned())) {
                        issue.setStatus("resolved");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getMembersToAssign(project, request.getRemoteUser());
                    if (allowed.contains(request.getRemoteUser())) {
                        issue.setStatus("resolved");
                        issue.setAssigned(request.getRemoteUser());
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                break;
            case "close":
                caseNewStatusClose(request, issue, project);
                break;
            default:
                throw new NotAllowedToEditIssueException();
        }
    }

    private void caseOldStatusResolved(HttpServletRequest request, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "testing":
                if (issue.getCreator() != null) {
                    if (request.getRemoteUser().equals(issue.getCreator())) {
                        issue.setStatus("testing");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getPossibleCreators(project, request.getRemoteUser());
                    if (allowed.contains(request.getRemoteUser())) {
                        issue.setStatus("testing");
                        issue.setCreator(request.getRemoteUser());
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                break;
            case "close":
                caseNewStatusClose(request, issue, project);
                break;
            default:
                throw new NotAllowedToEditIssueException();
        }
    }

    private void caseOldStatusTesting(HttpServletRequest request, Issue issue, Project project, String newStatus) {
        switch (newStatus) {
            case "close":
                if (issue.getCreator() != null) {
                    if (request.getRemoteUser().equals(issue.getCreator())) {
                        issue.setStatus("close");
                    }
                    else {
                        throw new NotAllowedToEditIssueException();
                    }
                }
                else {
                    List<String> allowed = projectMemberService.getPossibleCreators(project, request.getRemoteUser());
                    if (allowed.contains(request.getRemoteUser())) {
                        issue.setStatus("close");
                        issue.setCreator(request.getRemoteUser());
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

    private void caseNewStatusClose(HttpServletRequest request, Issue issue, Project project) {
        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
            issue.setStatus("close");
        }
        else {
            throw new NotAllowedToEditIssueException();
        }
    }
}
