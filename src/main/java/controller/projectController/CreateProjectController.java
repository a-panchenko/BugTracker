package controller.projectController;

import model.Project;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CreateProjectController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String projectLeed = request.getParameter("projectLeed");
            Project project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            project.setStartDate(new Date());
            project.setProjectLeed(projectLeed);
            new ProjectServiceImpl().addProject(project);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
