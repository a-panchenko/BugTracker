package controller;

import dao.DaoFactory;
import dao.exceptions.NoConnectionException;
import model.Project;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProjectController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        Project project;
        try {
            project = new DaoFactory().getProjectDao().getProject(id);
            logger.debug("Project id is " + project.getId());
        } catch (NoConnectionException e) {
            logger.error(e);
        }
    }
}
