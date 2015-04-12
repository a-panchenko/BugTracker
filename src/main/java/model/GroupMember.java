package model;

public class GroupMember {

    private String name;
    private String group;

    public GroupMember() {

    }

    public GroupMember(String name, String group) {
        setName(name);
        setGroup(group);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
