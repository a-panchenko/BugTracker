package model;

public class ProjectMember {

    private int projectId;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectId() {

        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
