package controller.user;

import security.exceptions.EditPasswordException;
import dto.GroupMemberDto;
import dto.UserDto;
import model.GroupMember;
import model.User;
import org.apache.log4j.Logger;
import security.exceptions.NotAllowedException;
import security.groupmember.EditGroupMemberSecurity;
import security.groupmember.EditGroupMemberSecurityImpl;
import security.user.EditUserSecurity;
import security.user.EditUserSecurityImpl;
import service.GroupMemberService;
import service.GroupMemberServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditUserController.class);

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();
    private UserService userService = new UserServiceImpl();

    private EditUserSecurity editUserSecurity = new EditUserSecurityImpl();
    private EditGroupMemberSecurity editGroupMemberSecurity = new EditGroupMemberSecurityImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("name");
            if (request.isUserInRole("administrator") || request.getRemoteUser().equals(username)) {
                GroupMember groupMember = groupMemberService.getMemberByName(username);
                request.setAttribute("group", groupMember.getGroup());
                RequestDispatcher dispatcher = request.getRequestDispatcher("edituser.jsp");
                dispatcher.forward(request, response);
            }
            else {
                throw new NotAllowedException();
            }
        }
        catch (NotAllowedException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            UserDto userDto = new UserDto();
            userDto.setUsername(request.getParameter("name"));
            userDto.setPassword(request.getParameter("newPassword"));
            userDto.setOldPassword(request.getParameter("oldPassword"));
            User user = editUserSecurity.secureEditUser(userDto, request.getRemoteUser());

            GroupMemberDto groupMemberDto = new GroupMemberDto();
            groupMemberDto.setUsername(request.getParameter("name"));
            groupMemberDto.setGroup(request.getParameter("group"));
            GroupMember groupMember = editGroupMemberSecurity.secureEditGroupMember(groupMemberDto, request.getRemoteUser());

            userService.updateUser(user, groupMember);
            response.sendRedirect("/BugTracker/user?name=" + request.getParameter("name"));
        }
        catch (NotAllowedException | EditPasswordException editException) {
            LOGGER.error(editException);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
