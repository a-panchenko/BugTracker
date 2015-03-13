package controller.issueController;

import dao.issue.*;
import dao.reply.*;
import model.Issue;
import model.Reply;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class IssueController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(IssueController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.valueOf(req.getParameter("id"));
            IssueDao issueDao = new IssueDaoImpl();
            Issue issue = issueDao.getIssue(id);
            ReplyDao replyDao = new ReplyDaoImpl();
            List<Reply> replies = replyDao.getReplies(id);
            //Only for testing without jsp
            PrintWriter writer = resp.getWriter();
            writer.println("<html> <head> <title>" + issue.getTitle() + "</title> <body>");
            writer.println(replies.size());
            for (Reply r : replies){
                writer.println(r.getDate() + "  " + r.getMessage() + "<br>");
            }
            writer.println("</body></html>");
        } catch (IllegalArgumentException e){
            logger.error(e);
        }

    }
}
