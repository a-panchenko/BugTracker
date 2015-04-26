package dao.issue;

import dao.ResultParser;
import model.Issue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class IssueResultParser extends ResultParser<Issue> {

    @Override
    public Issue extractSingle(ResultSet result) throws SQLException {
        Issue issue = new Issue(result.getInt(1));
        issue.setProjectId(result.getInt(2));
        issue.setTitle(result.getString(3));
        issue.setDescription(result.getString(4));
        issue.setPriority(result.getString(5));
        issue.setStatus(result.getString(6));
        issue.setCreationDate(new Date(result.getLong(7)));
        long solvingDate = result.getLong(8);
        if (solvingDate > 0) {
            issue.setSolvingDate(new Date(solvingDate));
        }
        issue.setCreator(result.getString(9));
        issue.setAssigned(result.getString(10));
        return issue;
    }
}
