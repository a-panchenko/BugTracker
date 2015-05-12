package controller.reply;

import dto.ReplyDto;
import model.Reply;
import org.apache.log4j.Logger;
import security.exceptions.NotAllowedException;
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

    private static final Logger LOGGER = Logger.getLogger(AddReplyController.class);

    private ReplyService replyService = new ReplyServiceImpl();

    private AddReplySecurity addReplySecurity = new AddReplySecurityImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            ReplyDto replyDto = new ReplyDto();
            replyDto.setIssueId(request.getParameter("issueId"));
            replyDto.setMessage(request.getParameter("message"));
            Reply reply = addReplySecurity.secureAddReply(replyDto, request.getRemoteUser());
            replyService.addReply(reply);
        }
        catch (NotAllowedException notAllowed) {
            LOGGER.error(notAllowed);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (Exception e) {
            LOGGER.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
