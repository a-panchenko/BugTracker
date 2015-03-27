package controller.replyController;

import model.Reply;
import service.ReplyServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class PostReplyController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Reply reply = new Reply();
            int issueId = Integer.valueOf(request.getParameter("issueId"));
            reply.setIssueId(issueId);
            String message = request.getParameter("message");
            reply.setMessage(message);
            reply.setDate(new Date());
            String poster = request.getParameter("poster");
            reply.setPoster(poster);
            new ReplyServiceImpl().addReply(reply);
        }
        finally {
            response.sendRedirect("/BugTracker/issue?id=" + request.getParameter("issueId"));
        }
    }
}
