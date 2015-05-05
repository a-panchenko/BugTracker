package controller.project;

import dao.exceptions.NotFoundException;
import model.Issue;
import model.Project;
import org.apache.log4j.Logger;
import service.issue.IssueService;
import service.issue.IssueServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

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
            String pageValue = request.getParameter("page");
            int page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
            request.setAttribute("project", project);
            List<Issue> issues = issueService.getIssues(id, page);
            request.setAttribute("issues", issues);
            RequestDispatcher dispatcher = request.getRequestDispatcher("project.jsp");
            dispatcher.forward(request, response);
        }
        catch (NotFoundException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
