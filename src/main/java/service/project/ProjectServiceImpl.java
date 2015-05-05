package service.project;

import dao.exceptions.QueryExecutionException;
import dao.project.*;
import model.Project;
import service.DataSourceProvider;
import service.exceptions.TransactionFailException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    @Override
    public void addProject(Project project) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ProjectDaoImpl(connection).addProject(project);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void editProject(Project project) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ProjectDaoImpl(connection).updateProject(project);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public void removeProject(int projectId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            new ProjectDaoImpl(connection).removeProject(projectId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public List<Project> getProjects(int page) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new ProjectDaoImpl(connection).getProjects(page);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new ProjectDaoImpl(connection).getProjects();
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }

    @Override
    public Project getProject(int projectId) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            return new ProjectDaoImpl(connection).getProject(projectId);
        }
        catch (SQLException | QueryExecutionException e) {
            throw new TransactionFailException(e);
        }
    }
}
