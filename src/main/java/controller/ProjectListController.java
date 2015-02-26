package controller;

import model.Project;
import org.apache.log4j.Logger;
import service.Exception.DaoException;
import service.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectListController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProjectListController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Project> projectList = new ProjectServiceImpl().getAll();
            logger.debug("Size of projectList is:   " + projectList.size());
        } catch (DaoException e) {
            logger.debug(e.getMessage());
        }
    }
}
