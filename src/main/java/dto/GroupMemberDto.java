package dto;

import model.GroupMember;

public class GroupMemberDto extends GroupMember {

    private String requestPerformer;

    public String getRequestPerformer() {
        return requestPerformer;
    }

    public void setRequestPerformer(String requestPerformer) {
        this.requestPerformer = requestPerformer;
    }
}
