package controller.issue;

import dao.exceptions.NotFoundException;
import dto.IssueDto;
import model.Issue;
import model.Project;
import org.apache.log4j.Logger;
import security.exceptions.NotAllowedException;
import security.issue.EditIssueSecurity;
import security.issue.EditIssueSecurityImpl;
import security.issue.IssueSecurity;
import security.issue.IssueSecurityImpl;
import service.issue.IssueService;
import service.issue.IssueServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class EditIssueController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditIssueController.class);

    private IssueService issueService = new IssueServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    private IssueSecurity issueSecurity = new IssueSecurityImpl();
    private EditIssueSecurity editIssueSecurity = new EditIssueSecurityImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Issue issue = issueService.getIssue(id);
            Project project = projectService.getProject(issue.getProjectId());
            if (issueSecurity.isAllowedToEditIssue(request.getRemoteUser(), project)) {
                request.setAttribute("issue", issue);
                Set<String> projectMembersToAssign = projectMemberService.getMembersToAssign(project, request.getRemoteUser());
                request.setAttribute("projectMembersToAssign", projectMembersToAssign);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editissue.jsp");
                dispatcher.forward(request, response);
            }
            else {
                throw new NotAllowedException();
            }
        }
        catch (NotFoundException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (NotAllowedException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
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
            issueDto.setTitle(request.getParameter("title"));
            issueDto.setDescription(request.getParameter("description"));
            issueDto.setPriority(request.getParameter("priority"));
            issueDto.setStatus(request.getParameter("status"));
            issueDto.setAssigned(request.getParameter("assigned"));
            issueDto.setRequestPerformer(request.getRemoteUser());
            Issue issue = editIssueSecurity.secureEditIssue(issueDto);
            issueService.editIssue(issue);
            response.sendRedirect("/BugTracker/issue?id=" + issue.getId());
        }
        catch (NotFoundException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (NotAllowedException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
