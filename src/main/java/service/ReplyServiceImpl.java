package service;

import dao.reply.ReplyDao;
import dao.reply.ReplyDaoImpl;
import model.Reply;

import java.util.List;

public class ReplyServiceImpl implements ReplyService {

    public void addReply(Reply reply) {
        ReplyDao replyDao = new ReplyDaoImpl();
        replyDao.addReply(reply);
    }

    public void editReply(Reply reply) {
        ReplyDao replyDao = new ReplyDaoImpl();
        replyDao.updateReply(reply);
    }

    public void removeReply(int replyId) {
        ReplyDao replyDao = new ReplyDaoImpl();
        replyDao.removeReply(replyId);
    }

    public List<Reply> getReplies(int issueId) {
        ReplyDao replyDao = new ReplyDaoImpl();
        return replyDao.getReplies(issueId);
    }

    public Reply getReply(int replyId) {
        ReplyDao replyDao = new ReplyDaoImpl();
        return replyDao.getReply(replyId);
    }
}
