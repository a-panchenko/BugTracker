package controller.projectmember;

import model.ProjectMember;
import org.apache.log4j.Logger;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProjectMembersController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddProjectMembersController.class);

    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        int projectId = Integer.valueOf(request.getParameter("id"));
        String[] usersToAdd = request.getParameterValues("users[]");
        for (String user : usersToAdd) {
            ProjectMember member = new ProjectMember();
            member.setName(user);
            member.setProjectId(projectId);
            try {
                projectMemberService.addMember(member);
            }
            catch (Exception e) {
                LOGGER.error(e);
            }
        }
    }
}
