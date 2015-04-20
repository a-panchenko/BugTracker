package service;

import model.Reply;

import java.util.List;

public interface ReplyService {

    public void addReply(Reply reply);

    public void editReply(Reply reply);

    public void removeReply(int replyId);

    public List<Reply> getReplies(int issueId);

    public Reply getReply(int replyId);
}
