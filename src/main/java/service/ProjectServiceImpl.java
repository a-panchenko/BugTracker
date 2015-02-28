package service;

import dao.ProjectDao;
import dao.ProjectDaoImpl;
import model.Project;

import java.util.List;

public class ProjectServiceImpl implements ProjectService{
    @Override
    public void addProject(Project project) {
        ProjectDao projectDao = new ProjectDaoImpl();
        projectDao.addProject(project);
    }

    @Override
    public void editProject(int project_id, Project project) {
        ProjectDao projectDao = new ProjectDaoImpl();
        projectDao.updateProject(project_id, project);
    }

    @Override
    public void removeProject(int project_id) {
        ProjectDao projectDao = new ProjectDaoImpl();
        projectDao.removeProject(project_id);
    }

    @Override
    public List<Project> getAll() {
        ProjectDao projectDao = new ProjectDaoImpl();
            return projectDao.getAllProjects();

    }

    @Override
    public Project getProject(int project_id) {
        ProjectDao projectDao = new ProjectDaoImpl();
        return projectDao.getProject(project_id);
    }
}
