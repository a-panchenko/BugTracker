package dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    private static final String DATASOURCE = "jndi_jdbc_ds";

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
