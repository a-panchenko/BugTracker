package controller.project;

import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProjectController extends HttpServlet {

    private ProjectService projectService = new ProjectServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        projectService.removeProject(id);
        response.sendRedirect("/BugTracker/projects");
    }
}
