package controller.issue;

import service.issue.IssueService;
import service.issue.IssueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveIssueController extends HttpServlet {

    private IssueService issueService = new IssueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        issueService.removeIssue(id);
        response.sendRedirect("/BugTracker/projects");
    }
}
