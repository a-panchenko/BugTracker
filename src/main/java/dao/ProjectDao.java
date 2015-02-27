package dao;

import model.Project;

import java.util.List;

public interface ProjectDao {

    public Project getProject(int projectId);

    public List<Project> getAllProjects();

    public void addProject(Project project);

    public void removeProject(int projectId);

    public void updateProject(int projectId, Project project);
}