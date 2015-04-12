package controller.issueController;

import controller.exceptions.InvalidAssignedMemberException;
import controller.exceptions.NoSuchProjectException;
import controller.exceptions.NotAllowedToCreateIssueException;
import model.Issue;
import model.Project;
import model.ProjectMember;
import org.apache.log4j.Logger;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateIssueController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(CreateIssueController.class);

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Project project = getProject(id);
            List<String> allowed = getAllowed(project);
            if (allowed.contains(request.getRemoteUser()) || request.isUserInRole("administrator")) {
                request.setAttribute("projectId", id);
                List<String> projectMembersToAssign = projectMemberService.getMembersToAssign(project, request);
                request.setAttribute("projectMembersToAssign", projectMembersToAssign);
                List<String> possibleCreators = projectMemberService.getPossibleCreators(project, request);
                request.setAttribute("possibleCreators", possibleCreators);
                request.getRequestDispatcher("createissue.jsp").forward(request, response);
            }
            else {
                throw new NotAllowedToCreateIssueException();
            }
        }
        catch (NotAllowedToCreateIssueException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (NoSuchProjectException noProject) {
            LOGGER.error(noProject);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int projectId = Integer.valueOf(request.getParameter("projectId"));
            Project project = getProject(projectId);
            List<String> allowed = getAllowed(project);
            if (allowed.contains(request.getRemoteUser()) || request.isUserInRole("administrator")) {
                Issue issue = new Issue();
                issue.setProjectId(projectId);
                issue.setTitle(request.getParameter("title"));
                issue.setDescription(request.getParameter("description"));
                issue.setPriority(request.getParameter("priority"));
                issue.setStatus("open");
                issue.setCreationDate(new Date());
                String creator = request.getParameter("creator");
                checkMembership(creator, allowed, project, request);
                issue.setCreator(creator);
                String assigned = request.getParameter("assigned");
                if (! assigned.isEmpty()) {
                    checkMembership(assigned, allowed, project, request);
                    issue.setAssigned(assigned);
                }
                new IssueServiceImpl().addIssue(issue);
                response.sendRedirect("/BugTracker/project?id=" + projectId);
            }
            else {
                throw new NotAllowedToCreateIssueException();
            }
        }
        catch (NotAllowedToCreateIssueException | InvalidAssignedMemberException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (NoSuchProjectException noProject) {
            LOGGER.error(noProject);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Project getProject(int projectId) {
        Project project = projectService.getProject(projectId);
        if (project == null) {
            throw new NoSuchProjectException();
        }
        return project;
    }

    private List<String> getAllowed(Project project) {
        List<String> allowed = new ArrayList<>();
        for (ProjectMember projectMember : projectMemberService.getMembers(project.getId())) {
            allowed.add(projectMember.getName());
        }
        allowed.add(project.getProjectLeed());
        return allowed;
    }

    private void checkMembership(String member, List<String> allowed, Project project, HttpServletRequest request) {
        if (allowed.contains(member)) {
            if (member.equals(project.getProjectLeed()) && !request.getRemoteUser().equals(project.getProjectLeed())) {
                throw new InvalidAssignedMemberException();
            }
        }
        else {
            throw new InvalidAssignedMemberException();
        }
    }
}
