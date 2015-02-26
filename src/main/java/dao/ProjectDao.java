package dao;

import dao.exceptions.NoConnectionException;
import model.Project;

import java.util.List;

public interface ProjectDao {

    public Project getProject(int projectId) throws NoConnectionException;

    public List<Project> getAllProjects() throws NoConnectionException;

    public void addProject(Project project);

    public void removeProject(int projectId);

    public void updateProject(int projectId, Project project);
}
