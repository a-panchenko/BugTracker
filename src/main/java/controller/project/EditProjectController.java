package controller.project;

import dto.ProjectDto;
import model.Project;
import security.Security;
import security.exceptions.NotAllowedException;
import security.project.EditProjectSecurityImpl;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditProjectController extends HttpServlet {

    private ProjectService projectService = new ProjectServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    private Security<Project, ProjectDto> editProjectSecurity = new EditProjectSecurityImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        Project project = projectService.getProject(id);
        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
            request.setAttribute("project", project);
            request.setAttribute("availableMembers", groupMemberService.getAvailableMembers());
            request.setAttribute("currentMembers", groupMemberService.getMembersByProjectId(id));
            if (request.isUserInRole("administrator")) {
                request.setAttribute("projectManagers", groupMemberService.getProjectManagers());
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("editproject.jsp");
            dispatcher.forward(request, response);
        }
        else {
            throw new NotAllowedException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(Integer.valueOf(request.getParameter("id")));
        projectDto.setTitle(request.getParameter("title"));
        projectDto.setDescription(request.getParameter("description"));
        projectDto.setClose(request.getParameter("close"));
        projectDto.setProjectLeed(request.getParameter("projectManagers"));
        projectDto.setRequestPerformer(request.getRemoteUser());
        Project project = editProjectSecurity.secure(projectDto);
        projectService.editProject(project);
        response.sendRedirect("/BugTracker/project?id=" + request.getParameter("id"));
    }
}
