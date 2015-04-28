package controller.projectcontroller;

import controller.exceptions.NotAllowedToCreateProjectException;
import dto.ProjectDto;
import model.Project;
import org.apache.log4j.Logger;
import security.CreateProjectSecurity;
import service.ProjectService;
import service.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProjectController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateProjectController.class);

    private ProjectService projectService = new ProjectServiceImpl();

    private CreateProjectSecurity createProjectSecurity = new CreateProjectSecurity();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
            ProjectDto projectDto = new ProjectDto();
            projectDto.setTitle(request.getParameter("title"));
            projectDto.setDescription(request.getParameter("description"));
            projectDto.setProjectLeed(request.getParameter("projectLeed"));
            Project project = createProjectSecurity.checkCreateProject(projectDto);
            projectService.addProject(project);
            response.sendRedirect("/BugTracker/myprojects");
        }
        catch (NotAllowedToCreateProjectException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
