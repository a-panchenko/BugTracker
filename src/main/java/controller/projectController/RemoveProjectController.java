package controller.projectController;

import dao.ProjectDao;
import dao.ProjectDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoveProjectController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RemoveProjectController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.valueOf(req.getParameter("id"));
            ProjectDao projectDao = new ProjectDaoImpl();
            projectDao.removeProject(id);
            PrintWriter writer = resp.getWriter();
            writer.print("Project with id " + id + " was removed");
        }catch (IllegalArgumentException e){
            logger.error(e);
        }
    }
}
