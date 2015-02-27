package dao;

import dao.exceptions.NoConnectionException;
import model.Project;

import java.util.List;

public interface ProjectDao {

    public Project getProject(int projectId) throws NoConnectionException;

    public List<Project> getAllProjects() throws NoConnectionException;

    public void addProject(Project project) throws NoConnectionException;

    public void removeProject(int projectId) throws NoConnectionException;

    public void updateProject(int projectId, Project project) throws NoConnectionException;
}
