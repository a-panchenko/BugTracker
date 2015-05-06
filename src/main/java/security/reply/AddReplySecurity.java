package security.reply;

import dto.ReplyDto;
import model.Reply;

public interface AddReplySecurity {

    Reply secureAddReply(ReplyDto replyDto, String username);
}
