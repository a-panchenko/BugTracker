package controller.projectController;

import model.Project;
import org.apache.log4j.Logger;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class EditProjectController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditProjectController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            if (id > 0) {
                Project project = new ProjectServiceImpl().getProject(id);
                request.setAttribute("project", project);
            }
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("editproject.jsp");
            dispatcher.forward(request, response);
            LOGGER.debug(request.toString());
            LOGGER.debug(response.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Project project = new Project(id);
            String title = request.getParameter("title");
            project.setTitle(title);
            String description = request.getParameter("description");
            project.setDescription(description);
            long start = Long.valueOf(request.getParameter("start"));
            project.setStartDate(new Date(start));
            boolean close = request.getParameter("close") != null;
            if (close) {
                project.setEndDate(new Date());
            }
            new ProjectServiceImpl().editProject(id, project);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            LOGGER.debug(request.toString());
            LOGGER.debug(response.toString());
        }
    }
}
