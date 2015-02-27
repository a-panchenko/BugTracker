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
        new DaoFactory().getProjectDao().addProject(project);
    }

    @Override
    public void editProject(int project_id, Project project) {
        new DaoFactory().getProjectDao().updateProject(project_id, project);
    }

    @Override
    public void removeProject(int project_id) {
        new DaoFactory().getProjectDao().removeProject(project_id);
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
    public Project getProject(int project_id) throws DaoException {
        try {
            return new DaoFactory().getProjectDao().getProject(project_id);
        } catch (NoConnectionException e) {
            throw new DaoException(e);
        }
    }
}
