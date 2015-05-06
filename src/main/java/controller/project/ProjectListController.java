package controller.project;

import dao.Utils;
import model.Project;
import org.apache.log4j.Logger;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectListController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ProjectListController.class);

    private ProjectService projectService = new ProjectServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pageValue = request.getParameter("page");
            Integer page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
            List<Project> projectList = projectService.getProjects(page);
            request.setAttribute("projects", projectList);
            request.setAttribute("currentPage", page);
            int pagesCount = (int) Math.ceil(((double) projectService.getAllProjects().size()) / Utils.ROWS_PER_PAGE);
            request.setAttribute("pagesCount", pagesCount);
            RequestDispatcher dispatcher = request.getRequestDispatcher("projects.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
