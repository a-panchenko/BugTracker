package controller.reply;

import model.Reply;
import org.apache.log4j.Logger;
import service.reply.ReplyService;
import service.reply.ReplyServiceImpl;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class GetRepliesController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetRepliesController.class);

    private ReplyService replyService = new ReplyServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        int id = Integer.valueOf(request.getParameter("id"));
        List<Reply> replies = replyService.getReplies(id);
        try (PrintWriter writer = response.getWriter()) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (Reply reply : replies) {
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("date", dateFormat.format(reply.getDate()))
                        .add("poster", reply.getPoster())
                        .add("message", reply.getMessage()));
            }
            JsonArray jsonArray = jsonArrayBuilder.build();
            writer.append(jsonArray.toString());
        }
        catch (IOException ioe) {
            LOGGER.error(ioe);
        }
    }
}
