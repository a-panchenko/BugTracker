<%@ page import="model.Project" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit Project</title>
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
                    <div align="center"><a href="/BugTracker/createproject.jsp">Create Project</a></div>
                </td>
                <td> <%
                    Project project = (Project) request.getAttribute("project");
                    if (project != null) { %>
                        <form action="editproject" method="post">
                            <input type="hidden" name="id" value="<%= project.getId() %>">
                            <p>Title:
                                <input type="text" name="title" value="<%= project.getTitle() %>" required/>
                            </p>
                            <p>Description:
                                <br><textarea name="description" rows="10" cols="50" required><%= project.getDescription() %></textarea>
                            </p>
                            <input type="hidden" name="start" value="<%= project.getStartDate().getTime() %>">
                            <p>Close: <input name="close" type="checkbox"></p>
                            <input type="submit" value="Edit Project"/>
                        </form> <%
                    } %>
                </td>
            </tr>
        </table>
    </body>
</html>
