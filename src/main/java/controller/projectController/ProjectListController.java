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
        try{
            String pageValue = req.getParameter("page");
            Integer page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
            PrintWriter writer = resp.getWriter();
            List<Project> projectList = new ProjectServiceImpl().getProjects((page!=null) ? page : 1);
            writer.println("<html> " +
            "<head><title>" +
                    "ProjectListController" +
                    "</title></head><body>");
            for(Project p : projectList){
                writer.print("<a href=\"project?id=" + p.getId() + "\">" + "  " + p.getTitle() +  "</a>"  + "  " + p.getStartDate());
            }
            writer.println("</body></html>");
        } catch (IllegalArgumentException e){
            logger.error(e);
        }
    }
}
