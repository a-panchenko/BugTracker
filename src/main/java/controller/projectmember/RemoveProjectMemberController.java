package controller.projectmember;

import model.ProjectMember;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveProjectMemberController extends HttpServlet {

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int projectId = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("member");
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(projectId);
        projectMember.setName(name);
        projectMemberService.removeMember(projectMember);
    }
}
