package service.reply;

import dao.exceptions.QueryExecutionException;
import dao.reply.ReplyDaoImpl;
import model.Reply;
import service.DataSourceProvider;
import service.exceptions.TransactionFailException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReplyServiceImpl implements ReplyService {

    public void addReply(Reply reply) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ReplyDaoImpl(connection).addReply(reply);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public void editReply(Reply reply) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ReplyDaoImpl(connection).updateReply(reply);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public void removeReply(int replyId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ReplyDaoImpl(connection).removeReply(replyId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public List<Reply> getReplies(int issueId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new ReplyDaoImpl(connection).getReplies(issueId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    public Reply getReply(int replyId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new ReplyDaoImpl(connection).getReply(replyId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }
}
