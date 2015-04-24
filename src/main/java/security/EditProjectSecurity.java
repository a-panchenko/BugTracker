package security;

import controller.exceptions.NoSuchProjectException;
import controller.exceptions.NotAllowedToEditProjectException;
import dto.ProjectDto;
import model.Project;
import model.ProjectMember;
import service.*;

import java.util.Date;

public class EditProjectSecurity {

    private ProjectService projectService = new ProjectServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();

    public Project checkEditProject(ProjectDto projectDto, String username) {
        Project project = getPresentProject(projectDto, username);
        editTitle(projectDto, project);
        editDescription(projectDto, project);
        editEndDate(projectDto, project);
        editProjectLeed(projectDto, project, username);
        editProjectMembers(projectDto, project);
        return project;
    }

    public void checkProject(Project project, String username) {
        if (project != null) {
            if (! groupMemberService.isUserInGroup(username, "administrators") && ! username.equals(project.getProjectLeed())) {
                throw new NotAllowedToEditProjectException();
            }
        }
        else {
            throw new NoSuchProjectException();
        }
    }

    private Project getPresentProject(ProjectDto projectDto, String username) {
        int id = Integer.valueOf(projectDto.getId());
        Project project = projectService.getProject(id);
        checkProject(project, username);
        return project;
    }

    private void editTitle(ProjectDto projectDto, Project project) {
        String title = projectDto.getTitle();
        if (title != null) {
            project.setTitle(title);
        }
        else {
            throw new NotAllowedToEditProjectException();
        }
    }

    private void editDescription(ProjectDto projectDto, Project project) {
        String description = projectDto.getDescription();
        if (description != null) {
            project.setDescription(description);
        }
        else {
            throw new NotAllowedToEditProjectException();
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

    private void editProjectMembers(ProjectDto projectDto, Project project) {
        projectMemberService.removeMembers(project.getId());
        String[] members = projectDto.getMembers();
        if (members != null) {
            for (String member : members) {
                ProjectMember projectMember = new ProjectMember();
                projectMember.setProjectId(project.getId());
                projectMember.setName(member);
                projectMemberService.addMember(projectMember);
            }
        }
    }
}
