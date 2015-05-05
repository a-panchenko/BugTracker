package service;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceProvider {

    private static final Logger LOGGER = Logger.getLogger(DataSourceProvider.class);
    private static final String DATASOURCE = "jndi_jdbc_ds";

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
}
