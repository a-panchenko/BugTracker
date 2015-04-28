package controller.usercontroller;

import controller.exceptions.EditPasswordException;
import controller.exceptions.NotAllowedToEditUserException;
import model.GroupMember;
import model.User;
import org.apache.log4j.Logger;
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
                throw new NotAllowedToEditUserException();
            }
        }
        catch (NotAllowedToEditUserException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String username = request.getParameter("name");
            if (request.isUserInRole("administrator")) {
                editUserIfAdmin(request, username);
            }
            else {
                if (request.getRemoteUser().equals(username)) {
                    editUserIfNotAdmin(request, username);
                }
                else {
                    throw new NotAllowedToEditUserException();
                }
            }
            response.sendRedirect("/BugTracker/user?name=" + username);
        }
        catch (NotAllowedToEditUserException | EditPasswordException editException) {
            LOGGER.error(editException);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void editUserIfAdmin(HttpServletRequest request, String username) {
        GroupMember groupMember = groupMemberService.getMemberByName(username);
        String group = request.getParameter("group");
        if (group != null && !group.equals(groupMember.getGroup())) {
            groupMember.setGroup(group);
            groupMemberService.updateMember(groupMember);
        }
        editPassword(request, username);
    }

    private void editUserIfNotAdmin(HttpServletRequest request, String username) {
        String oldPassword = request.getParameter("oldPassword");
        User user = userService.getUser(username);
        if (oldPassword.equals(user.getPassword())) {
            editPassword(request, username);
        }
        else {
            throw new EditPasswordException();
        }
    }

    private void editPassword(HttpServletRequest request, String username) {
        String password = request.getParameter("newPassword");
        if (password != null) {
            User user = userService.getUser(username);
            user.setPassword(password);
            userService.updateUser(user);
        }
    }
}
