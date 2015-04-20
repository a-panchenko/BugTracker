package dao.project;

import model.Project;

import java.util.List;

public interface ProjectDao {

    Project getProject(int projectId);

    List<Project> getProjects();

    List<Project> getProjects(int page);

    void addProject(Project project);

    void removeProject(int projectId);

    void updateProject(Project project);
}