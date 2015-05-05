package service.reply;

import model.Reply;

import java.util.List;

public interface ReplyService {

    void addReply(Reply reply);

    void editReply(Reply reply);

    void removeReply(int replyId);

    List<Reply> getReplies(int issueId);

    Reply getReply(int replyId);
}
