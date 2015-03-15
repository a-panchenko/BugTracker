<%@ page import="model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Issue" %>
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
            <a href="/BugTracker/createproject.jsp">Create Project</a> <%
            Project project = (Project) request.getAttribute("project");
            if (project != null) { %>
                <br><a href="/BugTracker/editproject?id=<%= project.getId() %>">Edit Project</a>
                <br><a href="/BugTracker/removeproject?id=<%= project.getId() %>">Remove Project</a> <%
            } %>
        </td>
        <td> <%
            if (project != null) { %>
                <br>Title: <%= project.getTitle() %>
                <br>Description: <%= project.getDescription() %>
                <br>Start date: <%= project.getStartDate() %>
                <br>End date: <%= project.getEndDate() %> <%
                List<Issue> issues = (List<Issue>) request.getAttribute("issues");
                if (issues != null && issues.size() > 0) { %>
                    <table width="50%" height="5%" border="1" cellspacing="0"> <%
                        for (Issue issue : issues) { %>
                            <tr>
                                <td> <%= issue.getId() %> </td>
                                <td> <%= issue.getTitle() %> </td>
                                <td> <%= issue.getDescription() %> </td>
                                <td> <%= issue.getPriority() %> </td>
                                <td> <%= issue.getStatus() %> </td>
                                <td> <%= issue.getCreationDate() %> </td>
                                <td> <%= issue.getSolvingDate() %> </td>
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
