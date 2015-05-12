package controller.project;

import dao.Utils;
import model.Issue;
import model.Project;
import service.issue.IssueService;
import service.issue.IssueServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectController extends HttpServlet {

    private ProjectService projectService = new ProjectServiceImpl();
    private IssueService issueService = new IssueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        Project project = projectService.getProject(id);
        String pageValue = request.getParameter("page");
        int page = (pageValue == null) ? 1 : Integer.valueOf(pageValue);
        request.setAttribute("currentPage", page);

        //pagination: 20 rows per page
        int pagesCount = (int) Math.ceil(((double) issueService.getIssues(id).size()) / Utils.ROWS_PER_PAGE);

        request.setAttribute("pagesCount", pagesCount);
        request.setAttribute("project", project);
        List<Issue> issues = issueService.getIssues(id, page);
        request.setAttribute("issues", issues);
        RequestDispatcher dispatcher = request.getRequestDispatcher("project.jsp");
        dispatcher.forward(request, response);
    }
}
