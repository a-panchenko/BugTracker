package dao.reply;

import dao.AbstractDao;
import dao.PlaceholderCompleter;
import dao.Utils;
import dao.issue.IssueDaoImpl;
import dao.ResultParser;
import model.Reply;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplyDaoImpl extends AbstractDao<Reply, Integer> implements ReplyDao {

    private static final String SELECT_REPLY_BY_REPLY_ID = "SELECT * FROM REPLY WHERE reply_id = ?";
    private static final String SELECT_REPLIES_BY_ISSUE_ID = "SELECT * FROM REPLY WHERE issue_id = ?";
    private static final String DELETE_REPLY_BY_REPLY_ID = "DELETE FROM REPLY WHERE reply_id = ?";
    private static final String INSERT_INTO_REPLY = "INSERT INTO REPLY (issue_id, message, post_date, poster) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_REPLY = "UPDATE REPLY SET issue_id = ?, message = ?, post_date = ?, poster = ? WHERE reply_id = ?";

    private static final Logger LOGGER = Logger.getLogger(IssueDaoImpl.class);

    private final ResultParser<Reply> resultParser = new ReplyResultParser();

    @Override
    public Reply getReply(int replyId) {
        return select(replyId, SELECT_REPLY_BY_REPLY_ID, resultParser);
    }

    @Override
    public List<Reply> getReplies(int issueId) {
        return select(SELECT_REPLIES_BY_ISSUE_ID, resultParser, issueId);
    }

    @Override
    public void addReply(Reply reply) {
        insert(reply, INSERT_INTO_REPLY, new PlaceholderCompleter<Reply>() {
            @Override
            public void complete(PreparedStatement statement, Reply reply) throws SQLException {
                completeAdd(statement, reply);
            }
        });
    }

    @Override
    public void removeReply(int replyId) {
        delete(replyId, DELETE_REPLY_BY_REPLY_ID);
    }

    @Override
    public void updateReply(Reply reply) {
        update(reply, UPDATE_REPLY, new PlaceholderCompleter<Reply>() {
            @Override
            public void complete(PreparedStatement statement, Reply reply) throws SQLException {
                completeAdd(statement, reply);
                statement.setInt(5, reply.getId());
            }
        });
    }

    private void completeAdd(PreparedStatement statement, Reply reply) throws SQLException {
        statement.setInt(1, reply.getIssueId());
        statement.setString(2, reply.getMessage());
        statement.setLong(3, reply.getDate().getTime());
        statement.setString(4, reply.getPoster());
    }
}
