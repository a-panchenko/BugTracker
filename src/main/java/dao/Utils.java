package dao;

import dao.exceptions.NoConnectionException;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Hashtable;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);

    static DataSource getDataSource() throws NoConnectionException {
        Context context = null;
        NoConnectionException exception = null;
        try {
            Hashtable ht = new Hashtable();
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
            context = new InitialContext(ht);
            DataSource dataSource = (DataSource) context.lookup("jndi_jdbc_ds");
            return dataSource;
        }
        catch (Exception e) {
            LOGGER.error(e);
            throw new NoConnectionException(e);
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
