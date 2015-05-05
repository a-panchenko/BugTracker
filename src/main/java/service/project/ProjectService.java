package service.project;

import model.Project;

import java.util.List;

public interface ProjectService {

    void addProject(Project project);

    void editProject(Project project);

    void removeProject(int projectId);

    List<Project> getAllProjects();

    List<Project> getProjects(int page);

    Project getProject(int projectId);
}
