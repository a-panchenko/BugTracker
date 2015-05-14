package service.project;

import model.Project;

import java.util.List;

public interface ProjectService {

    Void addProject(Project project);

    Void editProject(Project project);

    Void removeProject(int projectId);

    List<Project> getAllProjects();

    List<Project> getProjects(int page);

    Project getProject(int projectId);
}
