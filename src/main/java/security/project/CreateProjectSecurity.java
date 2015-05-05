package security.project;

import dto.ProjectDto;
import model.Project;

public interface CreateProjectSecurity {

    Project secureCreateProject(ProjectDto projectDto);
}
