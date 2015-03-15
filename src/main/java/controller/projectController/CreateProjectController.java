package controller.projectController;

import model.Project;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class CreateProjectController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            Project project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            project.setStartDate(new Date());
            new ProjectServiceImpl().addProject(project);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
