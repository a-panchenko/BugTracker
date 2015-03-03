package dao.resultparser;

import model.Issue;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IssueResultParser extends ResultParser<Issue> {

    @Override
    public Issue extractSingle(ResultSet result) throws SQLException {
        Issue issue = new Issue(result.getInt(1));
        issue.setProjectId(result.getInt(2));
        issue.setTitle(result.getString(3));
        issue.setDescription(result.getString(4));
        issue.setPriority(result.getString(5));
        issue.setStatus(result.getString(6));
        issue.setCreationDate(result.getDate(7));
        issue.setSolvingDate(result.getDate(8));
        return issue;
    }
}
