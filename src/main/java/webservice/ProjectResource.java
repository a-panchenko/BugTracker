package webservice;

import model.Project;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;

import javax.ws.rs.*;
import java.util.List;

@Path("project")
public class ProjectResource {

    private ProjectService projectService = new ProjectServiceImpl();

    @GET
    @Produces("text/xml")
    public String getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        StringBuilder xml = new StringBuilder();
        xml.append("<projects>");
        for (Project project : projects) {
            xml.append("<project>");
            xml.append("<id>").append(project.getId()).append("</id>");
            xml.append("<title>").append(project.getTitle()).append("</title>");
            xml.append("<description>").append(project.getDescription()).append("</description>");
            xml.append("<startDate>").append(project.getStartDate()).append("</startDate>");
            xml.append("<endDate>").append(project.getEndDate()).append("</endDate>");
            xml.append("<projectLead>").append(project.getProjectLead()).append("</projectLead>");
            xml.append("</project>");
        }
        xml.append("</projects>");
        return xml.toString();
    }
}
