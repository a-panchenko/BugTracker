package controller.user;

import dto.GroupMemberDto;
import dto.UserDto;
import model.GroupMember;
import model.User;
import security.Security;
import security.groupmember.CreateGroupMemberSecurityImpl;
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

    private Security<User, UserDto> createUserSecurity = new CreateUserSecurityImpl();
    private Security<GroupMember, GroupMemberDto> createGroupMemberSecurity = new CreateGroupMemberSecurityImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        UserDto userDto = new UserDto();
        userDto.setName(request.getParameter("username"));
        userDto.setPassword(request.getParameter("password"));
        User user = createUserSecurity.secure(userDto);

        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setName(request.getParameter("username"));
        groupMemberDto.setGroup(request.getParameter("group"));
        GroupMember groupMember = createGroupMemberSecurity.secure(groupMemberDto);

        userService.addUser(user, groupMember);
        response.sendRedirect("/BugTracker/admin");
    }
}
