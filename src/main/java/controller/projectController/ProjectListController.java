package controller.projectController;

import model.Project;
import org.apache.log4j.Logger;
import service.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ProjectListController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProjectListController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Project> projectList = new ProjectServiceImpl().getAll();
        PrintWriter writer = resp.getWriter();
        writer.println("<html> " +
        "<head><title>" +
                "ProjectController" +
                "</title></head><body>");
        for(Project p : projectList){
            writer.print(p.getId() + "  " + p.getTitle() + "  " + p.getStartDate());
        }
        writer.println("</body></html>");
    }
}
