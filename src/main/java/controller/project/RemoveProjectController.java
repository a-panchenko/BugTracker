package controller.project;

import org.apache.log4j.Logger;
import service.ProjectService;
import service.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProjectController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RemoveProjectController.class);

    private ProjectService projectService = new ProjectServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            projectService.removeProject(id);
            response.sendRedirect("/BugTracker/myprojects");
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
