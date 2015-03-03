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
        String sql = "INSERT INTO REPLY (issue_id, message, post_date) VALUES (?, ?, ?)";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reply.getIssueId());
            statement.setString(2, reply.getMessage());
            statement.setDate(3, Utils.utilDateToSql(reply.getDate()));
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    @Override
    public void removeReply(int replyId) {
        String sql = "DELETE FROM REPLY WHERE reply_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, replyId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }

    @Override
    public void updateReply(int replyId, Reply reply) {
        String sql = "UPDATE REPLY SET issue_id = ?, message = ?, post_date = ? WHERE reply_id = ?";
        try (Connection connection = Utils.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reply.getIssueId());
            statement.setString(2, reply.getMessage());
            statement.setDate(3, Utils.utilDateToSql(reply.getDate()));
            statement.setInt(4, replyId);
            statement.executeUpdate();
        }
        catch (SQLException se) {
            LOGGER.error(se);
            return;
        }
    }
}
