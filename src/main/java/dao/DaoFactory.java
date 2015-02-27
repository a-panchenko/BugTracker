package dao;

public class DaoFactory {

    public IssueDao getIssueDao() {
        return new IssueDaoImpl();
    }

    public ProjectDao getProjectDao() {
        return new ProjectDaoImpl();
    }
}