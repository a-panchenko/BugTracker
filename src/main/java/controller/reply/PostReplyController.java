package controller.reply;

import model.Reply;
import service.ReplyService;
import service.ReplyServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class PostReplyController extends HttpServlet {

    private ReplyService replyService = new ReplyServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Reply reply = new Reply();
        int issueId = Integer.valueOf(request.getParameter("issueId"));
        reply.setIssueId(issueId);
        String message = request.getParameter("message");
        reply.setMessage(message);
        reply.setDate(new Date());
        reply.setPoster(request.getRemoteUser());
        replyService.addReply(reply);
        response.sendRedirect("/BugTracker/issue?id=" + issueId);
    }
}
