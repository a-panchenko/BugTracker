package controller.issue;

import org.apache.log4j.Logger;
import service.IssueService;
import service.IssueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveIssueController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RemoveIssueController.class);

    private IssueService issueService = new IssueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            issueService.removeIssue(id);
            response.sendRedirect("/BugTracker/myprojects");
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
