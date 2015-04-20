package dao.reply;

import model.Reply;

import java.util.List;

public interface ReplyDao {

    Reply getReply(int replyId);

    List<Reply> getReplies(int issueId);

    void addReply(Reply reply);

    void removeReply(int replyId);

    void updateReply(Reply reply);
}
