package dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    private static String DATASOURCE = "jndi_jdbc_ds";

    static DataSource getDataSource() {
        Context context = null;
        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(DATASOURCE);
            return dataSource;
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
