package dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    private static final String DATASOURCE = "jndi_jdbc_ds";

    public static final String SELECT_PROJECT_BY_PROJECT_ID = "SELECT * FROM PROJECT WHERE project_id = ?";
    public static final String SELECT_PROJECTS = "SELECT * FROM (SELECT /*+ FIRST_ROWS(20) */ a.*, ROWNUM rnum FROM " +
            "(SELECT * FROM PROJECT ORDER BY project_id) a WHERE ROWNUM <= ?) WHERE rnum >= ?";
    public static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT";
    public static final String SELECT_ISSUE_BY_ISSUE_ID = "SELECT * FROM ISSUE WHERE issue_id = ?";
    public static final String SELECT_ISSUES = "SELECT * FROM (SELECT /*+ FIRST_ROWS(20) */ a.*, ROWNUM rnum FROM " +
            "(SELECT * FROM ISSUE WHERE project_id = ? ORDER BY issue_id) a WHERE ROWNUM <= ?) WHERE rnum >= ?";
    public static final String SELECT_REPLY_BY_REPLY_ID = "SELECT * FROM REPLY WHERE reply_id = ?";
    public static final String SELECT_REPLIES_BY_ISSUE_ID = "SELECT * FROM REPLY WHERE issue_id = ?";
    public static final String SELECT_MEMBERS_BY_GROUP = "SELECT * FROM GROUPMEMBERS WHERE g_name = ?";
    public static final String DELETE_PROJECT_BY_PROJECT_ID = "DELETE FROM PROJECT WHERE project_id = ?";
    public static final String DELETE_ISSUE_BY_ISSUE_ID = "DELETE FROM ISSUE WHERE issue_id = ?";
    public static final String DELETE_REPLY_BY_REPLY_ID = "DELETE FROM REPLY WHERE reply_id = ?";
    public static final String INSERT_INTO_PROJECT = "INSERT INTO " +
            "PROJECT (project_title, project_description, start_date, project_leed) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_PROJECT = "UPDATE PROJECT " +
            "SET project_title = ?, project_description = ?, start_date = ?, project_leed = ?, end_date = ? WHERE project_id = ?";
    public static final String INSERT_INTO_ISSUE = "INSERT INTO " +
            "ISSUE (project_id, issue_title, issue_description, priority, status, creation_date) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ISSUE = "UPDATE ISSUE SET project_id = ?, issue_title = ?, " +
            "issue_description = ?, priority = ?, status = ?, creation_date = ?, solving_date = ? WHERE issue_id = ?";
    public static final String INSERT_INTO_REPLY = "INSERT INTO REPLY (issue_id, message, post_date) VALUES (?, ?, ?)";
    public static final String UPDATE_REPLY = "UPDATE REPLY SET issue_id = ?, message = ?, post_date = ? WHERE reply_id = ?";
    public static final int ROWS_PER_PAGE = 20;

    public static DataSource getDataSource() {
        Context context = null;
        try {
            context = new InitialContext();
            return (DataSource) context.lookup(DATASOURCE);
        }
        catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
        finally {
            try {
                if (context != null) {
                    context.close();
                    context = null;
                }
            }
            catch (NamingException ne) {
                LOGGER.error(ne);
            }
        }
    }

    public static java.sql.Date utilDateToSql(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }
}
