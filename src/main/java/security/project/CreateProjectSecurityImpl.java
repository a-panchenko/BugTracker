package security.project;

import dto.ProjectDto;
import model.Project;
import security.exceptions.NotAllowedException;

import java.util.Date;

public class CreateProjectSecurityImpl implements CreateProjectSecurity {

    public Project secureCreateProject(ProjectDto projectDto) {
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
            throw new NotAllowedException();
        }
    }

    private void setDescription(ProjectDto projectDto, Project project) {
        String description = projectDto.getDescription();
        if (description != null) {
            project.setDescription(description);
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setProjectLeed(ProjectDto projectDto, Project project) {
        String projectLeed = projectDto.getProjectLeed();
        if (projectLeed != null) {
            project.setProjectLeed(projectLeed);
        }
        else {
            throw new NotAllowedException();
        }
    }
}
