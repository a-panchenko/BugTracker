package controller;

import dao.exceptions.NotFoundException;
import org.apache.log4j.Logger;
import security.exceptions.EditPasswordException;
import security.exceptions.InvalidAssignedMemberException;
import security.exceptions.NotAllowedException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(ExceptionFilter.class);

    @Override
    public void init(FilterConfig config) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        }
        catch (NotAllowedException | InvalidAssignedMemberException | EditPasswordException notAllowed) {
            LOGGER.error(notAllowed);
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        catch (NotFoundException notFound) {
            LOGGER.error(notFound);
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (Exception e) {
            LOGGER.error(e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void destroy() {

    }
}
