<%@ page import="java.util.List" %>
<%@ page import="model.Project" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Projects</title>
        <style>
            body {
                font: 11pt Arial, Helvetica, sans-serif;
                height: 100vh;
            }
            td {
                word-break: break-all;
            }
        </style>
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <tr>
                <td colspan="2" height="10%">
                    <jsp:include page="jsp/common/header.jsp"/>
                </td>
            </tr>
            <tr valign="top">
                <td width="20%">
                    <%
                        if (request.isUserInRole("administrator") || request.isUserInRole("project-manager")) {
                    %>
                            <div align="center"><a href="/BugTracker/createproject.jsp">Create Project</a></div>
                    <%
                        }
                    %>
                </td>
                <td>
                    <%
                        List<Project> projectList = (List<Project>) request.getAttribute("myProjects");
                        if ((projectList != null) && (projectList.size() > 0)) {
                    %>
                            <table width="90%" height="5%" border="1" cellspacing="0">
                                <tr align="center">
                                    <td> Title </td>
                                    <td> Description </td>
                                    <td> Start date </td>
                                    <td> End date </td>
                                    <td> Project Leed </td>
                                </tr>
                            <%
                                for (Project project : projectList) {
                            %>
                                    <tr>
                                        <td width="30%">
                                            <a href="/BugTracker/project?id=<%= project.getId() %>">
                                                <%
                                                    if (project.getTitle().length() > 30) {
                                                %>
                                                        <%= project.getTitle().substring(0, 30) + "..." %>
                                                <%
                                                    }
                                                    else {
                                                %>
                                                        <%= project.getTitle() %>
                                                <%
                                                    }
                                                %>
                                            </a>
                                        </td>
                                        <td width="30%">
                                            <%
                                                if (project.getDescription().length() > 30) {
                                            %>
                                                    <%= project.getDescription().substring(0, 30) + "..." %>
                                            <%
                                                }
                                                else {
                                            %>
                                                    <%= project.getDescription() %>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <%
                                            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
                                        %>
                                        <td width="15%">
                                            <%= dateFormat.format(project.getStartDate()) %>
                                        </td>
                                        <td width="15%">
                                            <%
                                                if (project.getEndDate() != null) {
                                            %>
                                                    <%= dateFormat.format(project.getEndDate()) %>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <td width="10%">
                                            <%
                                                if (project.getProjectLeed() != null) {
                                            %>
                                                    <a href="<%= "/BugTracker/user?name=" + project.getProjectLeed() %>"><%= project.getProjectLeed() %></a>
                                            <%
                                                }
                                            %>
                                        </td>
                                    </tr>
                            <%
                                }
                            %>
                            </table>
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
                                        <a href="/BugTracker/myprojects?page=<%= pagesCount %>"><%= pagesCount %></a>
                                        <a href="/BugTracker/myprojects?page=<%= currentPage + 1 %>">Next</a>
                                <%
                                    }
                                    if (currentPage == pagesCount) {
                                %>
                                        <a href="/BugTracker/myprojects?page=<%= currentPage - 1 %>">Prev</a>
                                        <a href="/BugTracker/myprojects?page=1">1</a>
                                        <%= currentPage %>
                                <%
                                    }
                                    if (currentPage > 1 && currentPage < pagesCount) {
                                %>
                                        <a href="/BugTracker/myprojects?page=<%= currentPage - 1 %>">Prev</a>
                                        <a href="/BugTracker/myprojects?page=1">1</a>
                                        <%= currentPage %>
                                        <a href="/BugTracker/myprojects?page=<%= pagesCount %>"><%= pagesCount %></a>
                                        <a href="/BugTracker/myprojects?page=<%= currentPage + 1 %>">Next</a>
                    <%
                                    }
                                }
                            }
                        }
                    %>
                </td>
            </tr>
        </table>
    </body>
</html>
