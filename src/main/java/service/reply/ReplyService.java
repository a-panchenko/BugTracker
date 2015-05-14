package service.reply;

import model.Reply;

import java.util.List;

public interface ReplyService {

    Void addReply(Reply reply);

    Void editReply(Reply reply);

    Void removeReply(int replyId);

    List<Reply> getReplies(int issueId);

    Reply getReply(int replyId);
}
