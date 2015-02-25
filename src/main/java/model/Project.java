package model;

public class Project {

    private int id;
    private String title;

    public Project(int id, String title) {
        setId(id);
        setTitle(title);
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
}