package controller.userController;

import model.GroupMember;
import model.User;
import service.GroupMemberService;
import service.GroupMemberServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserController extends HttpServlet {

    private UserService userService = new UserServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String group = request.getParameter("group");
        if (username != null && password != null && group != null) {
            User user = new User(username, String.valueOf(password.hashCode()));
            GroupMember groupMember = new GroupMember(username, group);
            userService.addUser(user);
            groupMemberService.addMember(groupMember);
            response.sendRedirect("/BugTracker/admin");
        }
    }
}
