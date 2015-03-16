package dao.project;

import model.Project;

import java.util.List;

public interface ProjectDao {

    public Project getProject(int projectId);

    public List<Project> getProjects();

    public List<Project> getProjects(int page);

    public void addProject(Project project);

    public void removeProject(int projectId);

    public void updateProject(int projectId, Project project);
}