package controller.projectController;

import dao.project.ProjectDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProjectController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RemoveProjectController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            new ProjectDaoImpl().removeProject(id);
        }
        catch (IllegalArgumentException e){
            logger.error(e);
        }
        finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
