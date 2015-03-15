package controller.projectController;

import model.Project;
import org.apache.log4j.Logger;
import service.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProjectListController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProjectListController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pageValue = request.getParameter("page");
            Integer page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
            List<Project> projectList = new ProjectServiceImpl().getProjects(page);
            HttpSession session = request.getSession(true);
            session.setAttribute("myProjects", projectList);
        }
        catch (IllegalArgumentException e){
            logger.error(e);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("myprojects.jsp");
            dispatcher.forward(request, response);
        }
    }
}
