package controller.project;

import dao.exceptions.NotFoundException;
import dto.ProjectDto;
import model.GroupMember;
import model.Project;
import org.apache.log4j.Logger;
import security.exceptions.NotAllowedException;
import security.project.EditProjectSecurity;
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
import java.util.List;

public class EditProjectController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(EditProjectController.class);

    private ProjectService projectService = new ProjectServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    private EditProjectSecurity editProjectSecurity = new EditProjectSecurityImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            Project project = projectService.getProject(id);
            request.setAttribute("project", project);
            request.setAttribute("availableMembers", groupMemberService.getAvailableMembers());
            List<GroupMember> currentMembers = groupMemberService.getMembersByProjectId(id);
            request.setAttribute("currentMembers", currentMembers);
            if (request.isUserInRole("administrator")) {
                request.setAttribute("projectManagers", groupMemberService.getProjectManagers());
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("editproject.jsp");
            dispatcher.forward(request, response);
        }
        catch (NotFoundException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (NotAllowedException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(request.getParameter("id"));
            projectDto.setTitle(request.getParameter("title"));
            projectDto.setDescription(request.getParameter("description"));
            projectDto.setClose(request.getParameter("close"));
            projectDto.setProjectLeed(request.getParameter("projectManagers"));
            Project project = editProjectSecurity.secureEditProject(projectDto, request.getRemoteUser());
            projectService.editProject(project);
            response.sendRedirect("/BugTracker/project?id=" + request.getParameter("id"));
        }
        catch (NotFoundException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}