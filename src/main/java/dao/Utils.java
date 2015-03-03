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
    public static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT";
    public static final String SELECT_PROJECT_BY_ISSUE_ID = "SELECT * FROM ISSUE WHERE issue_id = ?";
    public static final String SELECT_ISSUES_BY_PROJECT_ID = "SELECT * FROM ISSUE WHERE project_id = ?";
    public static final String SELECT_REPLY_BY_REPLY_ID = "SELECT * FROM REPLY WHERE reply_id = ?";
    public static final String SELECT_REPLIES_BY_ISSUE_ID = "SELECT * FROM REPLY WHERE issue_id = ?";

    static DataSource getDataSource() {
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

    static java.sql.Date utilDateToSql(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }
}
