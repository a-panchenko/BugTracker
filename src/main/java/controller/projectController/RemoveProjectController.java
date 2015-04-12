package controller.projectController;

import service.ProjectService;
import service.ProjectServiceImpl;

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
        Integer id = Integer.valueOf(request.getParameter("id"));
        projectService.removeProject(id);
        response.sendRedirect("/BugTracker/myprojects");
    }
}
