package dao;

import dao.resultparser.ReplyResultParser;
import dao.resultparser.ResultParser;
import model.Reply;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplyDaoImpl extends AbstractDao<Reply> implements ReplyDao {

    private final Logger LOGGER = Logger.getLogger(IssueDaoImpl.class);
    private final ResultParser<Reply> replyResultParser = new ReplyResultParser();
    private final PlaceholderCompleter<Reply> placeholderCompleter = new PlaceholderCompleter<Reply>() {
        @Override
        public void completeAdd(PreparedStatement statement, Reply reply) throws SQLException {
            statement.setInt(1, reply.getIssueId());
            statement.setString(2, reply.getMessage());
            statement.setDate(3, Utils.utilDateToSql(reply.getDate()));
        }
        @Override
        public void completeUpdate(PreparedStatement statement, int id, Reply reply) throws SQLException {
            completeAdd(statement, reply);
            statement.setInt(4, id);
        }
    };

    @Override
    public Reply getReply(int replyId) {
        return selectById(replyId, Utils.SELECT_REPLY_BY_REPLY_ID, replyResultParser);
    }

    @Override
    public List<Reply> getReplies(int issueId) {
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(Utils.SELECT_REPLIES_BY_ISSUE_ID)) {
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
        insert(reply, Utils.INSERT_INTO_REPLY, placeholderCompleter);
    }

    @Override
    public void removeReply(int replyId) {
        deleteById(replyId, Utils.DELETE_REPLY_BY_REPLY_ID);
    }

    @Override
    public void updateReply(int replyId, Reply reply) {
        update(replyId, reply, Utils.UPDATE_REPLY, placeholderCompleter);
    }
}
