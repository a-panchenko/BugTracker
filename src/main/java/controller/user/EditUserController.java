package controller.user;

import dto.GroupMemberDto;
import dto.UserDto;
import model.GroupMember;
import model.User;
import security.exceptions.NotAllowedException;
import security.groupmember.EditGroupMemberSecurity;
import security.groupmember.EditGroupMemberSecurityImpl;
import security.user.EditUserSecurity;
import security.user.EditUserSecurityImpl;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserController extends HttpServlet {

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();
    private UserService userService = new UserServiceImpl();

    private EditUserSecurity editUserSecurity = new EditUserSecurityImpl();
    private EditGroupMemberSecurity editGroupMemberSecurity = new EditGroupMemberSecurityImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
}
