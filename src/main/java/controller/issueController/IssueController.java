package controller.issueController;

import model.Issue;
import model.Project;
import model.Reply;
import org.apache.log4j.Logger;
import service.IssueServiceImpl;
import service.ProjectServiceImpl;
import service.ReplyServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IssueController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(IssueController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            if (id > 0) {
                Issue issue = new IssueServiceImpl().getIssue(id);
                request.setAttribute("issue", issue);
                Project project = new ProjectServiceImpl().getProject(issue.getProjectId());
                request.setAttribute("project", project);
                List<Reply> replies = new ReplyServiceImpl().getReplies(id);
                request.setAttribute("replies", replies);
            }
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("issue.jsp");
            dispatcher.forward(request, response);
        }
    }
}
