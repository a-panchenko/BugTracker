package security.project;

import dto.ProjectDto;
import model.Project;
import security.exceptions.NotAllowedException;
import service.*;

import java.util.Date;

public class EditProjectSecurityImpl  implements EditProjectSecurity {

    private ProjectService projectService = new ProjectServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public Project secureEditProject(ProjectDto projectDto, String username) {
        Project project = getPresentProject(projectDto, username);
        editTitle(projectDto, project);
        editDescription(projectDto, project);
        editEndDate(projectDto, project);
        editProjectLeed(projectDto, project, username);
        return project;
    }

    private Project getPresentProject(ProjectDto projectDto, String username) {
        int id = Integer.valueOf(projectDto.getId());
        Project project = projectService.getProject(id);
        if (! groupMemberService.isUserInGroup(username, "administrators") && ! username.equals(project.getProjectLeed())) {
            throw new NotAllowedException();
        }
        return project;
    }

    private void editTitle(ProjectDto projectDto, Project project) {
        String title = projectDto.getTitle();
        if (title != null) {
            project.setTitle(title);
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editDescription(ProjectDto projectDto, Project project) {
        String description = projectDto.getDescription();
        if (description != null) {
            project.setDescription(description);
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editEndDate(ProjectDto projectDto, Project project) {
        if (projectDto.getClose() != null) {
            project.setEndDate(new Date());
        }
        else {
            project.setEndDate(null);
        }
    }

    private void editProjectLeed(ProjectDto projectDto, Project project, String username) {
        if (groupMemberService.isUserInGroup(username, "administrators")) {
            project.setProjectLeed(projectDto.getProjectLeed());
        }
    }
}
