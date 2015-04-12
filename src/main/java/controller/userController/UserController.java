package controller.userController;

import model.GroupMember;
import service.GroupMemberService;
import service.GroupMemberServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("name");
        if (username != null) {
            GroupMember user = groupMemberService.getMemberByName(username);
            request.setAttribute("user", user);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);
    }
}
