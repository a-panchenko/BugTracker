package controller.projectController;

import dao.issue.*;
import dao.project.*;
import model.Issue;
import model.Project;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ProjectController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.valueOf(req.getParameter("id"));
            String pageValue= req.getParameter("page");
            Integer page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
            ProjectDao projectDao = new ProjectDaoImpl();
            Project p = projectDao.getProject(id);
            IssueDao issueDao = new IssueDaoImpl();
            List<Issue> issues = issueDao.getIssues(id, (page!=null) ? page : 1);
            PrintWriter writer = resp.getWriter();
            // Only for testing
            writer.print("<html> " +
                            "<head><title>" +
                            "ProjectController" +
                            "</title></head><body>"
                    + p.getId() + "  " + p.getTitle() + "  " + p.getStartDate() + "<br>"
            );
            for(Issue i : issues){
                writer.println("<a href=\"issue?id=" + i.getId() + "\">" + i.getTitle() + "</a>" + i.getDescription() + " " + i.getCreationDate() + " " +
                i.getPriority() + " " + i.getSolvingDate() + "<br>");
            }
            writer.print("</body></html>");
        }catch (IllegalArgumentException e){
            logger.error(e);
        }
    }
}
