package security;

import controller.exceptions.NotAllowedToCreateProjectException;
import dto.ProjectDto;
import model.Project;

import java.util.Date;

public class CreateProjectSecurity {

    public Project checkCreateProject(ProjectDto projectDto) {
        Project project = new Project();
        setTitle(projectDto, project);
        setDescription(projectDto, project);
        setProjectLeed(projectDto, project);
        project.setStartDate(new Date());
        return project;
    }

    private void setTitle(ProjectDto projectDto, Project project) {
        String title = projectDto.getTitle();
        if (title != null) {
            project.setTitle(title);
        }
        else {
            throw new NotAllowedToCreateProjectException();
        }
    }

    private void setDescription(ProjectDto projectDto, Project project) {
        String description = projectDto.getDescription();
        if (description != null) {
            project.setDescription(description);
        }
        else {
            throw new NotAllowedToCreateProjectException();
        }
    }

    private void setProjectLeed(ProjectDto projectDto, Project project) {
        String projectLeed = projectDto.getProjectLeed();
        if (projectLeed != null) {
            project.setProjectLeed(projectLeed);
        }
        else {
            throw new NotAllowedToCreateProjectException();
        }
    }
}
