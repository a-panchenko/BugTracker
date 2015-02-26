package model;

import java.util.Date;

public class Project {

    private int id;
    private String title;
    private Date startDate;
    private Date endDate;

    public Project(int id, String title, Date startDate) {
        setId(id);
        setTitle(title);
        setStartDate(startDate);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}