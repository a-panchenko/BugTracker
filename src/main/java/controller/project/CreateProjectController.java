package controller.project;

import dto.ProjectDto;
import model.Project;
import security.Security;
import security.project.CreateProjectSecurityImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProjectController extends HttpServlet {

    private ProjectService projectService = new ProjectServiceImpl();

    private Security<Project, ProjectDto> createProjectSecurity = new CreateProjectSecurityImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        ProjectDto projectDto = new ProjectDto();
        projectDto.setTitle(request.getParameter("title"));
        projectDto.setDescription(request.getParameter("description"));
        projectDto.setProjectLeed(request.getRemoteUser());
        Project project = createProjectSecurity.secure(projectDto);
        projectService.addProject(project);
        response.sendRedirect("/BugTracker/projects");
    }
}
