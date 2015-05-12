package controller.reply;

import dto.ReplyDto;
import model.Reply;
import security.reply.AddReplySecurity;
import security.reply.AddReplySecurityImpl;
import service.reply.ReplyService;
import service.reply.ReplyServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddReplyController extends HttpServlet {

    private ReplyService replyService = new ReplyServiceImpl();

    private AddReplySecurity addReplySecurity = new AddReplySecurityImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ReplyDto replyDto = new ReplyDto();
        replyDto.setIssueId(request.getParameter("issueId"));
        replyDto.setMessage(request.getParameter("message"));
        Reply reply = addReplySecurity.secureAddReply(replyDto, request.getRemoteUser());
        replyService.addReply(reply);
    }
}
