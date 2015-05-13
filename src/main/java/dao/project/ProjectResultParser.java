package dao.project;

import dao.ResultParser;
import model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProjectResultParser extends ResultParser<Project> {

    @Override
    public Project extractSingle(ResultSet result) throws SQLException {
        Project project = new Project(result.getInt(1));
        project.setTitle(result.getString(2));
        project.setDescription(result.getString(3));
        project.setStartDate(new Date(result.getLong(4)));
        long endDate = result.getLong(5);
        if (endDate > 0) {
            project.setEndDate(new Date(endDate));
        }
        project.setProjectLead(result.getString(6));
        return project;
    }
}
