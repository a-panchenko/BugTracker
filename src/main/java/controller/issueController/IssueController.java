package controller.issueController;

import controller.exceptions.NoSuchIssueException;
import model.Issue;
import model.Project;
import model.Reply;
import org.apache.log4j.Logger;
import service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IssueController extends HttpServlet{

    private static final Logger LOGGER = Logger.getLogger(IssueController.class);

    private IssueService issueService = new IssueServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private ReplyService replyService = new ReplyServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Issue issue = issueService.getIssue(id);
            if (issue != null) {
                request.setAttribute("issue", issue);
                Project project = projectService.getProject(issue.getProjectId());
                request.setAttribute("project", project);
                List<Reply> replies = replyService.getReplies(id);
                request.setAttribute("replies", replies);
                RequestDispatcher dispatcher = request.getRequestDispatcher("issue.jsp");
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
}
