package dto;

public class ProjectDto {

    private String id;
    private String title;
    private String description;
    private String projectLeed;
    private String close;
    private String[] members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectLeed() {
        return projectLeed;
    }

    public void setProjectLeed(String projectLeed) {
        this.projectLeed = projectLeed;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }
}
