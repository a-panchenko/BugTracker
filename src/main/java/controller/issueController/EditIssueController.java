package controller.issueController;

import model.Issue;
import model.Project;
import org.apache.log4j.Logger;
import service.IssueServiceImpl;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EditIssueController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditIssueController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            if (id > 0) {
                Issue issue = new IssueServiceImpl().getIssue(id);
                request.setAttribute("issue", issue);
                List<Project> projects = new ProjectServiceImpl().getAllProjects();
                request.setAttribute("projects", projects);
            }
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("editissue.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Issue issue = new Issue(id);
            int projectId = Integer.valueOf(request.getParameter("project"));
            issue.setProjectId(projectId);
            String title = request.getParameter("title");
            issue.setTitle(title);
            String description = request.getParameter("description");
            issue.setDescription(description);
            String priority = request.getParameter("priority");
            issue.setPriority(priority);
            String status = request.getParameter("status");
            issue.setStatus(status);
            long creationDate = Long.valueOf(request.getParameter("startDate"));
            issue.setCreationDate(new Date(creationDate));
            if (status.equals("closed")) {
                issue.setSolvingDate(new Date());
            }
            new IssueServiceImpl().editIssue(id, issue);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
