package dao.projectmember;

import dao.ResultParser;
import model.ProjectMember;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMemberResultParser extends ResultParser<ProjectMember> {

    @Override
    public ProjectMember extractSingle(ResultSet result) throws SQLException {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(result.getInt(1));
        projectMember.setName(result.getString(2));
        return projectMember;
    }
}
