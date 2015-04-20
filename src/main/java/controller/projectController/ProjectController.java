package controller.projectController;

import controller.exceptions.NoSuchProjectException;
import model.Issue;
import model.Project;
import org.apache.log4j.Logger;
import service.IssueService;
import service.IssueServiceImpl;
import service.ProjectService;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ProjectController.class);

    private ProjectService projectService = new ProjectServiceImpl();
    private IssueService issueService = new IssueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Project project = projectService.getProject(id);
            if (project != null) {
                String pageValue = request.getParameter("page");
                int page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
                request.setAttribute("project", project);
                List<Issue> issues = issueService.getIssues(id, page);
                request.setAttribute("issues", issues);
                RequestDispatcher dispatcher = request.getRequestDispatcher("project.jsp");
                dispatcher.forward(request, response);
            }
            else {
                throw new NoSuchProjectException();
            }
        }
        catch (NoSuchProjectException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
