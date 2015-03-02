package controller.projectController;

import dao.ProjectDao;
import dao.ProjectDaoImpl;
import model.Project;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ProjectController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.valueOf(req.getParameter("id"));
            ProjectDao projectDao = new ProjectDaoImpl();
            Project p = projectDao.getProject(id);
            PrintWriter writer = resp.getWriter();
            writer.print("<html> " +
                            "<head><title>" +
                            "ProjectController" +
                            "</title</head><body>"
                    + p.getId() + "  " + p.getTitle() + "  " + p.getStartDate()
                    + " </body></html>"
            );
        }catch (IllegalArgumentException e){
            logger.error(e);
        }
    }
}
