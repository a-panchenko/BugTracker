package controller.projectmember;

import model.GroupMember;
import org.apache.log4j.Logger;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetProjectMembersController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetProjectMembersController.class);

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/xml");
        int id = Integer.valueOf(request.getParameter("id"));
        List<GroupMember> groupMembers = groupMemberService.getMembersByProjectId(id);
        try (PrintWriter writer = response.getWriter()) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("<members>");
            for (GroupMember groupMember : groupMembers) {
                strBuilder.append("<member>");
                strBuilder.append("<name>").append(groupMember.getName()).append("</name>");
                strBuilder.append("<group>").append(groupMember.getGroup()).append("</group>");
                strBuilder.append("</member>");
            }
            strBuilder.append("</members>");
            writer.write(strBuilder.toString());
        }
        catch (IOException ioe) {
            LOGGER.error(ioe);
        }
    }
}
