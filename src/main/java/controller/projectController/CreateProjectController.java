package controller.projectController;

import model.Project;
import service.ProjectService;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CreateProjectController extends HttpServlet {

    private ProjectService projectService = new ProjectServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String projectLeed = request.getParameter("projectLeed");
        if (title != null && description != null & projectLeed != null) {
            Project project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            project.setStartDate(new Date());
            project.setProjectLeed(projectLeed);
            projectService.addProject(project);
        }
    }
}
