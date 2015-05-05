package controller.user;

import model.GroupMember;
import service.GroupMemberService;
import service.GroupMemberServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserListController extends HttpServlet {

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<GroupMember> groupMembers = groupMemberService.getAllMembers();
        request.setAttribute("groupMembers", groupMembers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }
}
