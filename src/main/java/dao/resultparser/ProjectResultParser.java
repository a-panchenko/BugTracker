package dao.resultparser;

import model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectResultParser extends ResultParser<Project> {

    @Override
    public Project extractSingle(ResultSet result) throws SQLException {
        Project project = new Project(result.getInt(1));
        project.setTitle(result.getString(2));
        project.setDescription(result.getString(3));
        project.setStartDate(result.getDate(4));
        project.setEndDate(result.getDate(5));
        return project;
    }
}
