package dto;

import model.Reply;

public class ReplyDto extends Reply {

    private String requestPerformer;

    public String getRequestPerformer() {
        return requestPerformer;
    }

    public void setRequestPerformer(String requestPerformer) {
        this.requestPerformer = requestPerformer;
    }
}
