package service;

import model.Project;

import java.util.List;

public interface ProjectService {
    public void addProject(Project project);
    public void editProject(int project_id, Project project);
    public void removeProject(int project_id);
    public List<Project> getProjects(int page);
    public Project getProject(int project_id);
}
