package controller;

import org.apache.log4j.Logger;
import weblogic.security.Security;
import weblogic.security.auth.Authenticate;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(LogoutServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Subject subject = Security.getCurrentSubject();
            Authenticate.logout(subject);
        }
        catch (LoginException le) {
            LOGGER.error(le);
        }
        finally {
            response.sendRedirect(request.getContextPath());
        }
    }
}
