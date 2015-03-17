package controller.projectController;

import dao.Utils;
import model.Project;
import org.apache.log4j.Logger;
import service.ProjectService;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectListController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProjectListController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pageValue = request.getParameter("page");
            Integer page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
            ProjectService projectService = new ProjectServiceImpl();
            List<Project> projectList = projectService.getProjects(page);
            request.setAttribute("myProjects", projectList);
            request.setAttribute("currentPage", page);
            int pagesCount = (int) Math.ceil(((double) projectService.getAllProjects().size()) / Utils.ROWS_PER_PAGE);
            request.setAttribute("pagesCount", pagesCount);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("myprojects.jsp");
            dispatcher.forward(request, response);
        }
    }
}
