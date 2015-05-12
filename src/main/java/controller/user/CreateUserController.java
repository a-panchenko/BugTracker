package controller.user;

import dto.GroupMemberDto;
import dto.UserDto;
import model.GroupMember;
import model.User;
import security.groupmember.CreateGroupMemberSecurity;
import security.groupmember.CreateGroupMemberSecurityImpl;
import security.user.CreateUserSecurity;
import security.user.CreateUserSecurityImpl;
import service.user.UserService;
import service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserController extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    private CreateUserSecurity createUserSecurity = new CreateUserSecurityImpl();
    private CreateGroupMemberSecurity createGroupMemberSecurity = new CreateGroupMemberSecurityImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserDto userDto = new UserDto();
        userDto.setUsername(request.getParameter("username"));
        userDto.setPassword(request.getParameter("password"));
        User user = createUserSecurity.secureCreateUser(userDto);
        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setUsername(request.getParameter("username"));
        groupMemberDto.setGroup(request.getParameter("group"));
        GroupMember groupMember = createGroupMemberSecurity.secureCreateGroupMember(groupMemberDto, request.getRemoteUser());
        userService.addUser(user, groupMember);
        response.sendRedirect("/BugTracker/admin");
    }
}
