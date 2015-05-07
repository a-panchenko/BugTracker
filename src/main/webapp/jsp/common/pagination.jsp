<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int currentPage = (Integer) request.getAttribute("currentPage");
    int pagesCount = (Integer) request.getAttribute("pagesCount");
    if (currentPage > 0 && pagesCount > 0) {
        if (pagesCount > 1) {
%>
            <br>
<%
            if (currentPage == 1) {
%>
                <%= currentPage %>
                <a href="/BugTracker/projects?page=<%= pagesCount %>"><%= pagesCount %></a>
                <a href="/BugTracker/projects?page=<%= currentPage + 1 %>">Next</a>
<%
            }
            if (currentPage == pagesCount) {
%>
                <a href="/BugTracker/projects?page=<%= currentPage - 1 %>">Prev</a>
                <a href="/BugTracker/projects?page=1">1</a>
                <%= currentPage %>
<%
            }
            if (currentPage > 1 && currentPage < pagesCount) {
%>
                <a href="/BugTracker/projects?page=<%= currentPage - 1 %>">Prev</a>
                <a href="/BugTracker/projects?page=1">1</a>
                <%= currentPage %>
                <a href="/BugTracker/projects?page=<%= pagesCount %>"><%= pagesCount %></a>
                <a href="/BugTracker/projects?page=<%= currentPage + 1 %>">Next</a>
<%
            }
        }
    }
%>