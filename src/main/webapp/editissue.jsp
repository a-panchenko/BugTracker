<%@ page import="model.Issue" %>
<%@ page import="model.Project" %>
<%@ page import="java.util.List" %>
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
            <% Issue issue = (Issue) request.getAttribute("issue");
            if (issue != null) { %>
                <br><a href="/BugTracker/removeissue?id=<%= issue.getId() %>">Remove Issue</a> <%
            } %>
        </td>
        <td>
            <form action="editissue" method="post">
                <input type="hidden" name="id" value="<%= issue.getId() %>">
                <br><input type="text" name="title" placeholder="<%= issue.getTitle() %>"/> <%
                List<Project> projects = (List<Project>) request.getAttribute("projects");
                if (projects != null) { %>
                    <br><select name="project"> <%
                    for (Project project : projects) { %>
                        <option value="<%= project.getId() %>" <% if (project.getId() == issue.getProjectId()) { %> selected<% } %> > <%= project.getTitle() %> </option> <%
                    } %>
                    </select> <%
                } %>
                <br><textarea name="description" rows="10" cols="50" placeholder="<%= issue.getDescription() %>"></textarea>
                <br><select name="priority">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </select>
                <br><select name="status">
                    <option value="open">open</option>
                    <option value="in progress">in progress</option>
                    <option value="resolved">resolved</option>
                    <option value="ready for testing">ready for testing</option>
                    <option value="closed">closed</option>
                </select>
                <input type="hidden" name="startDate" value="<%= issue.getCreationDate().getTime() %>">
                <br><input type="submit" value="Submit"/>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
