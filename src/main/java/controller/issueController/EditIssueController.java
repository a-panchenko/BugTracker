package controller.issuecontroller;

import controller.exceptions.NoSuchIssueException;
import controller.exceptions.NotAllowedToEditIssueException;
import dto.IssueDto;
import model.Issue;
import model.Project;
import org.apache.log4j.Logger;
import security.EditIssueSecurity;
import service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EditIssueController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditIssueController.class);

    private IssueService issueService = new IssueServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    private EditIssueSecurity editIssueSecurity = new EditIssueSecurity();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Issue issue = issueService.getIssue(id);
            editIssueSecurity.checkIssue(issue);
            request.setAttribute("issue", issue);
            if (request.isUserInRole("administrator")) {
                List<Project> projects = projectService.getAllProjects();
                request.setAttribute("projects", projects);
            }
            Project project = projectService.getProject(issue.getProjectId());
            request.setAttribute("project", project);
            if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
                List<String> projectMembersToAssign = projectMemberService.getMembersToAssign(project, request.getRemoteUser());
                request.setAttribute("projectMembersToAssign", projectMembersToAssign);
                List<String> possibleCreators = projectMemberService.getPossibleCreators(project, request.getRemoteUser());
                request.setAttribute("possibleCreators", possibleCreators);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("editissue.jsp");
            dispatcher.forward(request, response);
        }
        catch (NoSuchIssueException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            IssueDto issueDto = new IssueDto();
            issueDto.setId(request.getParameter("id"));
            issueDto.setProjectId(request.getParameter("project"));
            issueDto.setTitle(request.getParameter("title"));
            issueDto.setDescription(request.getParameter("description"));
            issueDto.setPriority(request.getParameter("priority"));
            issueDto.setStatus(request.getParameter("status"));
            issueDto.setAssigned(request.getParameter("assigned"));
            issueDto.setCreator(request.getParameter("creator"));
            Issue issue = editIssueSecurity.checkEditIssue(issueDto, request.getRemoteUser());
            issueService.editIssue(issue);
            response.sendRedirect("/BugTracker/issue?id=" + issue.getId());
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
}
