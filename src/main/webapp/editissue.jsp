<%@ page import="model.Issue" %>
<%@ page import="model.Project" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit Issue</title>
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
                <td width="20%">
                    <% Issue issue = (Issue) request.getAttribute("issue");
                    if (issue != null) {
                        if (request.isUserInRole("administrator")) { %>
                            <div align="center"><a href="/BugTracker/removeissue?id=<%= issue.getId() %>">Remove Issue</a></div> <%
                        }
                    } %>
                </td>
                <td> <%
                    if (issue != null) { %>
                        <form action="editissue" method="post">
                            <input type="hidden" name="id" value="<%= issue.getId() %>">
                            <p>Title:
                                <input type="text" name="title" value="<%= issue.getTitle() %>" required/>
                            </p> <%
                            List<Project> projects = (List<Project>) request.getAttribute("projects");
                            if (projects != null) { %>
                                <p><select name="project"> <%
                                for (Project project : projects) { %>
                                    <option value="<%= project.getId() %>" <% if (project.getId() == issue.getProjectId()) { %> selected<% } %> > <%= project.getTitle() %> </option> <%
                                } %>
                                </select></p> <%
                            } %>
                            <p>Description:
                                <br><textarea name="description" rows="10" cols="50" required><%= issue.getDescription() %></textarea>
                            </p>
                            <p>Priority:
                                <select name="priority">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                </select>
                            </p>
                            <p>Status:
                                <select name="status">
                                    <option value="open">open</option>
                                    <option value="in progress">in progress</option>
                                    <option value="resolved">resolved</option>
                                    <option value="ready for testing">ready for testing</option>
                                    <option value="closed">closed</option>
                                </select>
                            </p>
                            <input type="hidden" name="startDate" value="<%= issue.getCreationDate().getTime() %>">
                            <input type="submit" value="Edit Issue"/>
                        </form> <%
                    } %>
                </td>
            </tr>
        </table>
    </body>
</html>
