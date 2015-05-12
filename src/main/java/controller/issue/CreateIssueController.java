package controller.issue;

import dto.IssueDto;
import model.Issue;
import model.Project;
import security.exceptions.NotAllowedException;
import security.issue.CreateIssueSecurity;
import security.issue.CreateIssueSecurityImpl;
import security.issue.IssueSecurity;
import security.issue.IssueSecurityImpl;
import service.issue.IssueService;
import service.issue.IssueServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CreateIssueController extends HttpServlet {

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private IssueService issueService = new IssueServiceImpl();

    private IssueSecurity issueSecurity = new IssueSecurityImpl();
    private CreateIssueSecurity createIssueSecurity = new CreateIssueSecurityImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int projectId = Integer.valueOf(request.getParameter("id"));
        Project project = projectService.getProject(projectId);
        if (issueSecurity.isAllowedToEditIssue(request.getRemoteUser(), project)) {
            request.setAttribute("project", project);
            Set<String> membersToAssign = projectMemberService.getMembersToAssign(project, request.getRemoteUser());
            request.setAttribute("membersToAssign", membersToAssign);
            request.getRequestDispatcher("createissue.jsp").forward(request, response);
        }
        else {
            throw new NotAllowedException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        IssueDto issueDto = new IssueDto();
        issueDto.setRequestPerformer(request.getRemoteUser());
        issueDto.setProjectId(request.getParameter("projectId"));
        issueDto.setTitle(request.getParameter("title"));
        issueDto.setDescription(request.getParameter("description"));
        issueDto.setPriority(request.getParameter("priority"));
        issueDto.setAssigned(request.getParameter("assigned"));
        Issue issue = createIssueSecurity.secureCreateIssue(issueDto);
        issueService.addIssue(issue);
        response.sendRedirect("/BugTracker/project?id=" + request.getParameter("projectId"));
    }
}
