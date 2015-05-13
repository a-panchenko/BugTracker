package dto;

import model.Project;

public class ProjectDto extends Project {

    private String close;
    private String requestPerformer;

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getRequestPerformer() {
        return requestPerformer;
    }

    public void setRequestPerformer(String requestPerformer) {
        this.requestPerformer = requestPerformer;
    }
}
