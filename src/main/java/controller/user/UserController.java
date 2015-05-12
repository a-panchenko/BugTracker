package controller.user;

import dao.exceptions.NotFoundException;
import model.GroupMember;
import org.apache.log4j.Logger;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("name");
            GroupMember user = groupMemberService.getMemberByName(username);
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
            dispatcher.forward(request, response);
        }
        catch (NotFoundException notFound) {
            LOGGER.error(notFound);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
