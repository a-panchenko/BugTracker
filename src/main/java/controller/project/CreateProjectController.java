package controller.project;

import dto.ProjectDto;
import model.Project;
import org.apache.log4j.Logger;
import security.exceptions.NotAllowedException;
import security.project.CreateProjectSecurity;
import security.project.CreateProjectSecurityImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProjectController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateProjectController.class);

    private ProjectService projectService = new ProjectServiceImpl();

    private CreateProjectSecurity createProjectSecurity = new CreateProjectSecurityImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
            ProjectDto projectDto = new ProjectDto();
            projectDto.setTitle(request.getParameter("title"));
            projectDto.setDescription(request.getParameter("description"));
            projectDto.setProjectLeed(request.getParameter("projectLeed"));
            Project project = createProjectSecurity.secureCreateProject(projectDto);
            projectService.addProject(project);
            response.sendRedirect("/BugTracker/myprojects");
        }
        catch (NotAllowedException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
