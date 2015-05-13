package security.project;

import dto.ProjectDto;
import model.Project;
import security.Security;
import security.exceptions.NotAllowedException;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import java.util.Date;

public class EditProjectSecurityImpl implements Security<Project, ProjectDto> {

    private ProjectService projectService = new ProjectServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public Project secure(ProjectDto projectDto) {
        int id = projectDto.getId();
        Project project = projectService.getProject(id);
        if (groupMemberService.isUserInGroup(projectDto.getRequestPerformer(), "administrators")
                || projectDto.getRequestPerformer().equals(project.getProjectLeed())) {
            editTitle(projectDto, project);
            editDescription(projectDto, project);
            editEndDate(projectDto, project);
            editProjectLeed(projectDto, project);
            return project;
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editTitle(ProjectDto projectDto, Project project) {
        String title = projectDto.getTitle();
        if (title != null && ! title.isEmpty()) {
            project.setTitle(title);
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void editDescription(ProjectDto projectDto, Project project) {
        String description = projectDto.getDescription();
        if (description != null && ! description.isEmpty()) {
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

    private void editProjectLeed(ProjectDto projectDto, Project project) {
        if (groupMemberService.isUserInGroup(projectDto.getRequestPerformer(), "administrators")) {
            project.setProjectLeed(projectDto.getProjectLeed());
        }
    }
}
