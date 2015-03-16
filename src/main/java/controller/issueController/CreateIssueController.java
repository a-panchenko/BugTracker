package controller.issueController;

import model.Issue;
import service.IssueServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CreateIssueController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            if (id > 0) {
                request.setAttribute("projectId", id);
            }
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("createissue.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            Issue issue = new Issue();
            int projectId = Integer.valueOf(request.getParameter("projectId"));
            issue.setProjectId(projectId);
            String title = request.getParameter("title");
            issue.setTitle(title);
            String description = request.getParameter("description");
            issue.setDescription(description);
            String priority = request.getParameter("priority");
            issue.setPriority(priority);
            issue.setStatus("open");
            issue.setCreationDate(new Date());
            new IssueServiceImpl().addIssue(issue);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
