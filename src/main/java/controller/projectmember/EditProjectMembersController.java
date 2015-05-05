package controller.projectmember;

import model.Project;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditProjectMembersController extends HttpServlet {

    private ProjectService projectService = new ProjectServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        int id = Integer.valueOf(request.getParameter("id"));
        Project project = projectService.getProject(id);
        request.setAttribute("project", project);
        request.getRequestDispatcher("editprojectmembers.jsp").forward(request, response);
    }
}
