package service.reply;

import dao.reply.ReplyDaoImpl;
import model.Reply;
import service.AbstractService;
import service.TransactionScript;

import java.sql.Connection;
import java.util.List;

public class ReplyServiceImpl extends AbstractService implements ReplyService {

    public Void addReply(final Reply reply) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ReplyDaoImpl(connection).addReply(reply);
                return null;
            }
        });
    }

    public Void editReply(final Reply reply) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ReplyDaoImpl(connection).updateReply(reply);
                return null;
            }
        });
    }

    public Void removeReply(final int replyId) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ReplyDaoImpl(connection).removeReply(replyId);
                return null;
            }
        });
    }

    public List<Reply> getReplies(final int issueId) {
        return service(new TransactionScript<List<Reply>>() {
            @Override
            public List<Reply> transact(Connection connection) {
                return new ReplyDaoImpl(connection).getReplies(issueId);
            }
        });
    }

    public Reply getReply(final int replyId) {
        return service(new TransactionScript<Reply>() {
            @Override
            public Reply transact(Connection connection) {
                return new ReplyDaoImpl(connection).getReply(replyId);
            }
        });
    }
}
