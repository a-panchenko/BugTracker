package controller.projectController;

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
import java.util.List;

public class ProjectController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            String pageValue = request.getParameter("page");
            Integer page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
            Project project = new ProjectServiceImpl().getProject(id);
            request.setAttribute("project", project);
            List<Issue> issues = new IssueServiceImpl().getIssues(id, page);
            request.setAttribute("issues", issues);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("project.jsp");
            dispatcher.forward(request, response);
        }
    }
}
