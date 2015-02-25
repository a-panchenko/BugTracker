package service;

import model.Project;

import java.util.List;

public interface ProjectService {
    public void addProject(Project project);
    public boolean editProject(int project_id, Project project);
    public boolean removeProject(int project_id);
    public List<Project> getAll();
    public Project getProject(int project_id);
}
