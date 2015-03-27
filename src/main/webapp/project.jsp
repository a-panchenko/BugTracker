<%@ page import="model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Issue" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Project</title>
        <style>
            body {
                font: 11pt Arial, Helvetica, sans-serif;
                height: 100vh;
            }
        </style>
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <tr>
                <td colspan="2" height="10%">
                    <div align="right"><%= request.getRemoteUser() %></div>
                    <div align="center"><a href="/BugTracker/myprojects">Projects</a></div>
                    <div align="right"><a href="/BugTracker/logout">Logout</a></div>
                </td>
            </tr>
            <tr valign="top">
                <td width="20%"> <%
                    if (request.isUserInRole("administrator") || request.isUserInRole("project-manager")) { %>
                        <div align="center"><a href="/BugTracker/createproject.jsp">Create Project</a></div> <%
                    }
                    Project project = (Project) request.getAttribute("project");
                    if (project != null) { %>
                        <div align="center"><a href="/BugTracker/createissue?id=<%= project.getId() %>">Create Issue</a></div> <%
                        if (request.isUserInRole("administrator") || request.isUserInRole("project-manager")) { %>
                            <div align="center"><a href="/BugTracker/editproject?id=<%= project.getId() %>">Edit Project</a></div> <%
                        }
                        if (request.isUserInRole("administrator")) { %>
                            <div align="center"><a href="/BugTracker/removeproject?id=<%= project.getId() %>">Remove Project</a></div> <%
                        }
                    } %>
                </td>
                <td> <%
                    if (project != null) { %>
                        <p>Title: <%= project.getTitle() %> </p>
                        <p>Description: <%= project.getDescription() %> </p>
                        <p>Project Leed: <%= project.getProjectLeed() %> </p>
                        <p>Start date: <%= project.getStartDate() %> </p>
                        <p>End date: <% if (project.getEndDate() != null) { %> <%= project.getEndDate() %> <% } %> </p> <%
                        List<Issue> issues = (List<Issue>) request.getAttribute("issues");
                        if (issues != null && issues.size() > 0) { %>
                            <br><table width="50%" height="5%" border="1" cellspacing="0">
                                <tr align="center">
                                    <td> ID </td>
                                    <td width="20"> Title </td>
                                    <td width="40%"> Description </td>
                                    <td> Priority </td>
                                    <td> Status </td>
                                    <td> Creation date </td>
                                    <td> Solving date </td>
                                </tr> <%
                                for (Issue issue : issues) { %>
                                    <tr>
                                        <td> <%= issue.getId() %> </td>
                                        <td> <a href="/BugTracker/issue?id=<%= issue.getId() %>"><%= issue.getTitle() %></a> </td>
                                        <td> <%= issue.getDescription() %> </td>
                                        <td> <%= issue.getPriority() %> </td>
                                        <td> <%= issue.getStatus() %> </td>
                                        <td> <%= issue.getCreationDate() %> </td>
                                        <td> <% if (issue.getSolvingDate() != null) { %> <%= issue.getSolvingDate() %> <% } %> </td>
                                    </tr> <%
                                } %>
                            </table> <%
                        }
                    } %>
                </td>
            </tr>
        </table>
    </body>
</html>
