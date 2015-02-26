package service;

import model.Project;
import service.Exception.DaoException;

import java.util.List;

public interface ProjectService {
    public void addProject(Project project) throws DaoException;
    public void editProject(int project_id, Project project) throws DaoException;
    public void removeProject(int project_id) throws DaoException;
    public List<Project> getAll() throws DaoException;
    public Project getProject(int project_id) throws DaoException;
}
