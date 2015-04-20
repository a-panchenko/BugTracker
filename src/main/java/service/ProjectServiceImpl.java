package service;

import dao.project.*;
import model.Project;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public void addProject(Project project) {
        ProjectDao projectDao = new ProjectDaoImpl();
        projectDao.addProject(project);
    }

    @Override
    public void editProject(Project project) {
        ProjectDao projectDao = new ProjectDaoImpl();
        projectDao.updateProject(project);
    }

    @Override
    public void removeProject(int projectId) {
        ProjectDao projectDao = new ProjectDaoImpl();
        projectDao.removeProject(projectId);
    }

    @Override
    public List<Project> getProjects(int page) {
        ProjectDao projectDao = new ProjectDaoImpl();
        return projectDao.getProjects(page);
    }

    @Override
    public List<Project> getAllProjects() {
        ProjectDao projectDao = new ProjectDaoImpl();
        return projectDao.getProjects();
    }

    @Override
    public Project getProject(int projectId) {
        ProjectDao projectDao = new ProjectDaoImpl();
        return projectDao.getProject(projectId);
    }
}
