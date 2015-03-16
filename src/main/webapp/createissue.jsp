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
            <a href="/BugTracker/myprojects">My projects</a>
        </td>
    </tr>
    <tr>
        <td width="20%">

        </td>
        <td> <%
            Integer projectId = (Integer) request.getAttribute("projectId");
            if (projectId != null) { %>
            <form action="createissue" method="post">
                <br><input type="text" name="title" placeholder="title"/>
                <input type="hidden" name="projectId" value="<%= projectId %>">
                <br><textarea name="description" rows="10" cols="50" placeholder="description"></textarea>
                <br><select name="priority">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </select>
                <br><input type="submit" value="Submit"/>
            </form> <%
            } %>
        </td>
    </tr>
</table>
</body>
</html>
