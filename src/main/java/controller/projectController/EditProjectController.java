package controller.projectController;

import model.GroupMember;
import model.Project;
import model.ProjectMember;
import org.apache.log4j.Logger;
import service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditProjectController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditProjectController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        Project project = new ProjectServiceImpl().getProject(id);
        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
            request.setAttribute("project", project);
            GroupMemberService groupMemberService = new GroupMemberServiceImpl();
            List<GroupMember> availableMembers = groupMemberService.getMembersByGroup("debugers");
            availableMembers.addAll(groupMemberService.getMembersByGroup("testers"));
            request.setAttribute("availableMembers", availableMembers);
            ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
            List<ProjectMember> currentMembers = projectMemberService.getMembers(id);
            request.setAttribute("currentMembers", currentMembers);
            if (request.isUserInRole("administrator")) {
                List<GroupMember> projectManagers = groupMemberService.getMembersByGroup("administrators");
                projectManagers.addAll(groupMemberService.getMembersByGroup("project-managers"));
                request.setAttribute("projectManagers", projectManagers);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("editproject.jsp");
            dispatcher.forward(request, response);
        }
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        Project project = new ProjectServiceImpl().getProject(id);
        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
            project.setTitle(request.getParameter("title"));
            project.setDescription(request.getParameter("description"));
            long start = Long.valueOf(request.getParameter("start"));
            project.setStartDate(new Date(start));
            if (request.getParameter("close") != null) {
                project.setEndDate(new Date());
            }
            else {
                project.setEndDate(null);
            }
            if (request.isUserInRole("administrator")) {
                project.setProjectLeed(request.getParameter("projectManagers"));
            }
            ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
            projectMemberService.removeMembers(id);
            String[] members = request.getParameterValues("members");
            if (members != null) {
                for (String member : members) {
                    ProjectMember projectMember = new ProjectMember();
                    projectMember.setProjectId(id);
                    projectMember.setName(member);
                    projectMemberService.addMember(projectMember);
                }
            }
            new ProjectServiceImpl().editProject(id, project);
            response.sendRedirect("/BugTracker/project?id=" + id);
        }
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
