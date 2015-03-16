package service;

import model.Project;

import java.util.List;

public interface ProjectService {

    public void addProject(Project project);

    public void editProject(int projectId, Project project);

    public void removeProject(int projectId);

    public List<Project> getAllProjects();

    public List<Project> getProjects(int page);

    public Project getProject(int projectId);
}
