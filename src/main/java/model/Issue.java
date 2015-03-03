package model;

import java.util.Date;

public class Issue {

    private int id;
    private int projectId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Date creationDate;
    private Date solvingDate;

    public Issue(int id) {
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setSolvingDate(Date solvingDate) {
        this.solvingDate = solvingDate;
    }

    public Date getSolvingDate() {
        return solvingDate;
    }
}
