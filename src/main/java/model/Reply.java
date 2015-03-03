package model;

import java.util.Date;

public class Reply {

    private int id;
    private int issueId;
    private String message;
    private Date date;

    public Reply(int id) {
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
