<%@ page import="model.Issue" %>
<%@ page import="model.Reply" %>
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
            <%
            Issue issue = (Issue) request.getAttribute("issue");
            if (issue != null) { %>
                <br><a href="/BugTracker/editissue?id=<%= issue.getId() %>">Edit Issue</a>
                <br><a href="/BugTracker/removeissue?id=<%= issue.getId() %>">Remove Issue</a> <%
            } %>
        </td>
        <td> <%
            String projectTitle = (String) request.getAttribute("projectTitle");
            if (projectTitle != null && issue != null) { %>
                <br> <%= projectTitle %> > <%= issue.getTitle() %>
                <br> Priority: <%= issue.getPriority() %>
                <br> Status: <%= issue.getStatus() %>
                <br> Creation date: <%= issue.getCreationDate() %>
                <br> Description: <%= issue.getDescription() %> <%
                List<Reply> replies = (List<Reply>) request.getAttribute("replies");
                if (replies != null) { %>
                    <br><textarea style="overflow: scroll;" rows=10 cols=50> <%
                        for (Reply reply : replies) { %>
                            <%= reply.getDate() %> : <%= reply.getMessage() %> <%
                        } %>
                    </textarea> <%
                } %>
                <form action="postreply" method="post">
                    <br><textarea name="message" rows="10" cols="50" placeholder="post reply"></textarea>
                    <input type="hidden" name="issueId" value="<%= issue.getId() %>">
                    <br><input type="submit" value="Submit"/>
                </form> <%
            } %>
        </td>
    </tr>
</table>
</body>
</html>
