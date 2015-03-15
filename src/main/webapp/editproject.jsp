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
            Project project = (Project) request.getAttribute("project");
            if (project != null) { %>
                <form action="editproject" method="post">
                    <input type="hidden" name="id" value="<%= project.getId() %>">
                    <br><input type="text" name="title" placeholder="<%= project.getTitle() %>"/>
                    <br><textarea name="description" rows="10" cols="50" placeholder="<%= project.getDescription() %>"></textarea>
                    <input type="hidden" name="start" value="<%= project.getStartDate().getTime() %>">
                    <br>Close: <input name="close" type="checkbox">
                    <br><input type="submit" value="Submit"/>
                </form> <%
            } %>
        </td>
    </tr>
</table>
</body>
</html>
