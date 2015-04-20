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

public class ReplyDaoImpl extends AbstractDao<Reply> implements ReplyDao {

    private static final String SELECT_REPLY_BY_REPLY_ID = "SELECT * FROM REPLY WHERE reply_id = ?";
    private static final String SELECT_REPLIES_BY_ISSUE_ID = "SELECT * FROM REPLY WHERE issue_id = ?";
    private static final String DELETE_REPLY_BY_REPLY_ID = "DELETE FROM REPLY WHERE reply_id = ?";
    private static final String INSERT_INTO_REPLY = "INSERT INTO REPLY (issue_id, message, post_date, poster) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_REPLY = "UPDATE REPLY SET issue_id = ?, message = ?, post_date = ?, poster = ? WHERE reply_id = ?";

    private static final Logger LOGGER = Logger.getLogger(IssueDaoImpl.class);

    private final ResultParser<Reply> replyResultParser = new ReplyResultParser();
    private final PlaceholderCompleter<Reply> placeholderCompleter = new PlaceholderCompleter<Reply>() {
        @Override
        public void completeAdd(PreparedStatement statement, Reply reply) throws SQLException {
            statement.setInt(1, reply.getIssueId());
            statement.setString(2, reply.getMessage());
            statement.setDate(3, Utils.utilDateToSql(reply.getDate()));
            statement.setString(4, reply.getPoster());
        }
        @Override
        public void completeUpdate(PreparedStatement statement, int id, Reply reply) throws SQLException {
            completeAdd(statement, reply);
            statement.setInt(5, id);
        }
    };

    @Override
    public Reply getReply(int replyId) {
        return selectById(replyId, SELECT_REPLY_BY_REPLY_ID, replyResultParser);
    }

    @Override
    public List<Reply> getReplies(int issueId) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REPLIES_BY_ISSUE_ID)) {
            statement.setInt(1, issueId);
            try (ResultSet result = statement.executeQuery()) {
                return replyResultParser.extractAll(result);
            }
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return new ArrayList<Reply>();
        }
    }

    @Override
    public void addReply(Reply reply) {
        insert(reply, INSERT_INTO_REPLY, placeholderCompleter);
    }

    @Override
    public void removeReply(int replyId) {
        deleteById(replyId, DELETE_REPLY_BY_REPLY_ID);
    }

    @Override
    public void updateReply(int replyId, Reply reply) {
        update(replyId, reply, UPDATE_REPLY, placeholderCompleter);
    }
}
