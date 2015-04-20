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
import java.util.Date;
import java.util.List;

public class EditProjectController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditProjectController.class);

    private ProjectService projectService = new ProjectServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        Project project = projectService.getProject(id);
        if (project != null) {
            if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
                request.setAttribute("project", project);
                request.setAttribute("availableMembers", getAvailableMembers());
                List<GroupMember> currentMembers = groupMemberService.getMembersByProjectId(id);
                request.setAttribute("currentMembers", currentMembers);
                if (request.isUserInRole("administrator")) {
                    request.setAttribute("projectManagers", getProjectManagers());
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("editproject.jsp");
                dispatcher.forward(request, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        Project project = projectService.getProject(id);
        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
            project.setTitle(request.getParameter("title"));
            project.setDescription(request.getParameter("description"));
            editEndDate(request, project);
            editProjectLeed(request, project);
            editProjectMembers(request, id);
            projectService.editProject(project);
            response.sendRedirect("/BugTracker/project?id=" + id);
        }
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private List<GroupMember> getAvailableMembers() {
        List<GroupMember> availableMembers = groupMemberService.getMembersByGroup("debugers");
        availableMembers.addAll(groupMemberService.getMembersByGroup("testers"));
        return availableMembers;
    }

    private List<GroupMember> getProjectManagers() {
        List<GroupMember> projectManagers = groupMemberService.getMembersByGroup("administrators");
        projectManagers.addAll(groupMemberService.getMembersByGroup("project-managers"));
        return projectManagers;
    }

    private void editEndDate(HttpServletRequest request, Project project) {
        if (request.getParameter("close") != null) {
            project.setEndDate(new Date());
        }
        else {
            project.setEndDate(null);
        }
    }

    private void editProjectLeed(HttpServletRequest request, Project project) {
        if (request.isUserInRole("administrator")) {
            project.setProjectLeed(request.getParameter("projectManagers"));
        }
    }

    private void editProjectMembers(HttpServletRequest request, int projectId) {
        projectMemberService.removeMembers(projectId);
        String[] members = request.getParameterValues("members");
        if (members != null) {
            for (String member : members) {
                ProjectMember projectMember = new ProjectMember();
                projectMember.setProjectId(projectId);
                projectMember.setName(member);
                projectMemberService.addMember(projectMember);
            }
        }
    }
}
