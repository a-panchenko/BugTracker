package service;

import dao.DaoFactory;
import dao.ProjectDao;
import dao.exceptions.NoConnectionException;
import model.Project;
import service.Exception.DaoException;

import java.util.List;

public class ProjectServiceImpl implements ProjectService{
    @Override
    public void addProject(Project project) {

    }

    @Override
    public boolean editProject(int project_id, Project project) {
        return false;
    }

    @Override
    public boolean removeProject(int project_id) {
        return false;
    }

    @Override
    public List<Project> getAll() throws DaoException {
        ProjectDao projectDao = new DaoFactory().getProjectDao();
        try {
            return projectDao.getAllProjects();
        } catch (NoConnectionException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Project getProject(int project_id) {
        return null;
    }
}
