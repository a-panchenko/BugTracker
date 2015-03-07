package dao.reply;

import model.Reply;

import java.util.List;

public interface ReplyDao {

    public Reply getReply(int replyId);

    public List<Reply> getReplies(int issueId);

    public void addReply(Reply reply);

    public void removeReply(int replyId);

    public void updateReply(int replyId, Reply reply);
}
