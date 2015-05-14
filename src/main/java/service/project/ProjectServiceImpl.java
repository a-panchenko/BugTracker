package service.project;

import dao.project.*;
import model.Project;
import service.AbstractService;
import service.TransactionScript;

import java.sql.Connection;
import java.util.List;

public class ProjectServiceImpl extends AbstractService implements ProjectService {

    @Override
    public Void addProject(final Project project) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ProjectDaoImpl(connection).addProject(project);
                return null;
            }
        });
    }

    @Override
    public Void editProject(final Project project) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ProjectDaoImpl(connection).updateProject(project);
                return null;
            }
        });
    }

    @Override
    public Void removeProject(final int projectId) {
        return service(new TransactionScript<Void>() {
            @Override
            public Void transact(Connection connection) {
                new ProjectDaoImpl(connection).removeProject(projectId);
                return null;
            }
        });
    }

    @Override
    public List<Project> getProjects(final int page) {
        return service(new TransactionScript<List<Project>>() {
            @Override
            public List<Project> transact(Connection connection) {
                return new ProjectDaoImpl(connection).getProjects(page);
            }
        });
    }

    @Override
    public List<Project> getAllProjects() {
        return service(new TransactionScript<List<Project>>() {
            @Override
            public List<Project> transact(Connection connection) {
                return new ProjectDaoImpl(connection).getProjects();
            }
        });
    }

    @Override
    public Project getProject(final int projectId) {
        return service(new TransactionScript<Project>() {
            @Override
            public Project transact(Connection connection) {
                return new ProjectDaoImpl(connection).getProject(projectId);
            }
        });
    }
}
