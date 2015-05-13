package dto;

import model.Issue;

public class IssueDto extends Issue {

    private String requestPerformer;

    public String getRequestPerformer() {
        return requestPerformer;
    }

    public void setRequestPerformer(String requestPerformer) {
        this.requestPerformer = requestPerformer;
    }
}
