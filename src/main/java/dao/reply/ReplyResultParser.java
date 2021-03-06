package dao.reply;

import dao.ResultParser;
import model.Reply;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ReplyResultParser extends ResultParser<Reply> {

    @Override
    public Reply extractSingle(ResultSet result) throws SQLException {
        Reply reply = new Reply(result.getInt(1));
        reply.setIssueId(result.getInt(2));
        reply.setMessage(result.getString(3));
        reply.setDate(new Date(result.getLong(4)));
        reply.setPoster(result.getString(5));
        return reply;
    }
}
