<%@ page import="java.util.List" %>
<%@ page import="model.Project" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>My Projects</title>
        <style>
            body {
                font: 11pt Arial, Helvetica, sans-serif;
                height: 100vh;
            }
        </style>
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="4" cellspacing="0">
            <tr>
                <td colspan="2" height="10%" align="center">
                    <a href="/BugTracker/myprojects">My Projects</a>
                </td>
            </tr>
            <tr>
                <td width="20%">
                    <a href="/BugTracker/createproject.jsp">Create Project</a>
                </td>
                <td> <%
                    List<Project> projectList = (List<Project>) request.getAttribute("myProjects");
                    if ((projectList != null) && (projectList.size() > 0)) { %>
                        <table width="50%" height="5%" border="1" cellspacing="0"> <%
                        for (Project project : projectList) { %>
                            <tr>
                                <td> <a href="/BugTracker/project?id=<%= project.getId() %>"><%= project.getTitle() %></a> </td>
                                <td> <%= project.getDescription() %> </td>
                                <td> <%= project.getStartDate() %> </td>
                                <td> <%= project.getEndDate() %> </td>
                            </tr> <%
                        } %>
                        </table> <%
                        int currentPage = (Integer) request.getAttribute("currentPage");
                        int pagesCount = (Integer) request.getAttribute("pagesCount");
                        if (currentPage > 0 && pagesCount > 0) {
                            if (pagesCount > 1) { %>
                                <br> <%
                                if (currentPage == 1) { %>
                                    <%= currentPage %>
                                    <a href="/BugTracker/myprojects?page=<%= pagesCount %>"><%= pagesCount %></a>
                                    <a href="/BugTracker/myprojects?page=<%= currentPage + 1 %>">Next</a> <%
                                }
                                if (currentPage == pagesCount) { %>
                                    <a href="/BugTracker/myprojects?page=<%= currentPage - 1 %>">Prev</a>
                                    <a href="/BugTracker/myprojects?page=1">1</a>
                                    <%= currentPage %> <%
                                }
                                if (currentPage > 1 && currentPage < pagesCount) { %>
                                    <a href="/BugTracker/myprojects?page=<%= currentPage - 1 %>">Prev</a>
                                    <a href="/BugTracker/myprojects?page=1">1</a>
                                    <%= currentPage %>
                                    <a href="/BugTracker/myprojects?page=<%= pagesCount %>"><%= pagesCount %></a>
                                    <a href="/BugTracker/myprojects?page=<%= currentPage + 1 %>">Next</a> <%
                                }
                            }
                        }
                    } %>
                </td>
            </tr>
        </table>
    </body>
</html>