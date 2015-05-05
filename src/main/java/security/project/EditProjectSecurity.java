package security.project;

import dto.ProjectDto;
import model.Project;

public interface EditProjectSecurity {

    Project secureEditProject(ProjectDto projectDto, String username);
}
