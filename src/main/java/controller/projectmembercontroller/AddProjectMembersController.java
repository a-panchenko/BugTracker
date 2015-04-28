package controller.projectmembercontroller;

import model.ProjectMember;
import service.ProjectMemberService;
import service.ProjectMemberServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProjectMembersController extends HttpServlet {

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        int projectId = Integer.valueOf(request.getParameter("id"));
        String[] usersToAdd = request.getParameterValues("users[]");
        for (String user : usersToAdd) {
            ProjectMember member = new ProjectMember();
            member.setName(user);
            member.setProjectId(projectId);
            projectMemberService.addMember(member);
        }
    }
}
