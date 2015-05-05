package controller.issue;

import dao.exceptions.NotFoundException;
import dto.IssueDto;
import security.exceptions.InvalidAssignedMemberException;
import model.Issue;
import model.Project;
import org.apache.log4j.Logger;
import security.exceptions.NotAllowedException;
import security.issue.CreateIssueSecurity;
import security.issue.CreateIssueSecurityImpl;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreateIssueController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateIssueController.class);

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private IssueService issueService = new IssueServiceImpl();

    private CreateIssueSecurity createIssueSecurity = new CreateIssueSecurityImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int projectId = Integer.valueOf(request.getParameter("id"));
            Project project = projectService.getProject(projectId);
            request.setAttribute("project", project);
            List<String> projectMembersToAssign = projectMemberService.getMembersToAssign(project, request.getRemoteUser());
            request.setAttribute("projectMembersToAssign", projectMembersToAssign);
            List<String> possibleCreators = projectMemberService.getPossibleCreators(project, request.getRemoteUser());
            request.setAttribute("possibleCreators", possibleCreators);
            request.getRequestDispatcher("createissue.jsp").forward(request, response);
        }
        catch (NotAllowedException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (NotFoundException noProject) {
            LOGGER.error(noProject);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
            IssueDto issueDto = new IssueDto();
            issueDto.setProjectId(request.getParameter("projectId"));
            issueDto.setTitle(request.getParameter("title"));
            issueDto.setDescription(request.getParameter("description"));
            issueDto.setPriority(request.getParameter("priority"));
            issueDto.setCreator(request.getParameter("creator"));
            issueDto.setAssigned(request.getParameter("assigned"));
            Issue issue = createIssueSecurity.secureCreateIssue(issueDto, request.getRemoteUser());
            issueService.addIssue(issue);
            response.sendRedirect("/BugTracker/project?id=" + request.getParameter("projectId"));
        }
        catch (NotAllowedException | InvalidAssignedMemberException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (NotFoundException noProject) {
            LOGGER.error(noProject);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
