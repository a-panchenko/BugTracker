<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int currentPage = (Integer) request.getAttribute("currentPage");
    int pagesCount = (Integer) request.getAttribute("pagesCount");
    if (currentPage > 0 && pagesCount > 0) {
        if (pagesCount > 1) {
            String requestURI = request.getRequestURI().replace(".jsp", "");
            String params = (request.getQueryString() != null) ? request.getQueryString() : "";
            params = params.replaceAll("(&)?page=[0-9]+", "");
            if (! params.isEmpty() && params.lastIndexOf("&") == params.length() - 1) {
                params = params.substring(0, params.length() - 2);
            }
            params = (params.isEmpty()) ? "?page=" : "?" + params + "&page=";
            String urlForPagination = requestURI + params;
%>
            <br>
<%
            if (currentPage == 1) {
%>
                <%= currentPage %>
                <a href="<%= urlForPagination + pagesCount %>"><%= pagesCount %></a>
                <a href="<%= urlForPagination + (currentPage + 1) %>">Next</a>
<%
            }
            if (currentPage == pagesCount) {
%>
                <a href="<%= urlForPagination + (currentPage - 1) %>">Prev</a>
                <a href="<%= urlForPagination + "1" %>">1</a>
                <%= currentPage %>
<%
            }
            if (currentPage > 1 && currentPage < pagesCount) {
%>
                <a href="<%= urlForPagination + (currentPage - 1) %>">Prev</a>
                <a href="<%= urlForPagination + "1" %>">1</a>
                <%= currentPage %>
                <a href="<%= urlForPagination + pagesCount %>"><%= pagesCount %></a>
                <a href="<%= urlForPagination + (currentPage + 1) %>">Next</a>
<%
            }
        }
    }
%>