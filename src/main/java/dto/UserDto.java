package dto;

import model.User;

public class UserDto extends User {

    private String oldPassword;
    private String requestPerformer;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getRequestPerformer() {
        return requestPerformer;
    }

    public void setRequestPerformer(String requestPerformer) {
        this.requestPerformer = requestPerformer;
    }
}
