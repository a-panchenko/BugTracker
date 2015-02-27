package dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.Hashtable;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    private static String SERVER_URL = "t3://localhost:7001";
    private static String DATASOURCE = "jndi_jdbc_ds";

    static DataSource getDataSource() {
        Context context = null;
        try {
            Hashtable ht = new Hashtable();
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            ht.put(Context.PROVIDER_URL, SERVER_URL);
            context = new InitialContext(ht);
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
}
