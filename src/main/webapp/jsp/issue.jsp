<%@ page import="model.Issue" %>
<%@ page import="model.Reply" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Project" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Issue</title>
        <style>
            body {
                font: 11pt Arial, Helvetica, sans-serif;
                height: 100vh;
            }
            textarea {
                width: 80%;
            }
            #description {
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
                        Issue issue = (Issue) request.getAttribute("issue");
                        if (issue != null) {
                    %>
                            <div align="center"><a href="/BugTracker/editissue?id=<%= issue.getId() %>">Edit Issue</a></div>
                    <%
                            if (request.isUserInRole("administrator")) {
                    %>
                                <div align="center"><a href="/BugTracker/removeissue?id=<%= issue.getId() %>">Remove Issue</a></div>
                    <%
                            }
                        }
                    %>
                </td>
                <td>
                    <%
                        Project project = (Project) request.getAttribute("project");
                        if (project != null && issue != null) {
                    %>
                            <table width="80%">
                                <tr>
                                    <td width="30%"><p> <a href="/BugTracker/project?id=<%= project.getId() %>"><%= project.getTitle() %></a> > <%= issue.getTitle() %> </p></td>
                                    <td id="description" valign="top" rowspan="6">
                                        Description:
                                        <br><%= issue.getDescription() %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Priority: <%= issue.getPriority() %></td>
                                </tr>
                                <tr>
                                    <td>Status: <%= issue.getStatus() %> </td>
                                </tr>
                                <tr>
                                    <td>Creation date: <%= issue.getCreationDate() %> </td>
                                </tr>
                                <tr>
                                    <td>Created By:
                                        <%
                                            if (issue.getCreator() != null) {
                                        %>
                                                <a href="<%= "/BugTracker/user?name=" + issue.getCreator() %>"><%= issue.getCreator() %></a>
                                        <%
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Assigned:
                                        <%
                                            if (issue.getAssigned() != null) {
                                        %>
                                                <a href="<%= "/BugTracker/user?name=" + issue.getAssigned() %>"><%= issue.getAssigned() %></a>
                                        <%
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                    <%
                            List<Reply> replies = (List<Reply>) request.getAttribute("replies");
                            if (replies != null) {
                    %>
                                <p> Replies: </p>
                                <textarea style="overflow: scroll; word-break: break-all;" rows="10" cols="50" readonly> <% for (Reply reply : replies) { %> <%= reply.getPoster() %> : <%= reply.getMessage() + " (" + reply.getDate() + ")\n" %> <% } %> </textarea>
                    <%
                            }
                    %>
                        <form action="postreply" method="post">
                            <br><textarea name="message" rows="10" cols="50" placeholder="post reply" required></textarea>
                            <input type="hidden" name="issueId" value="<%= issue.getId() %>">
                            <br><input type="submit" value="Post"/>
                        </form>
                    <%
                        }
                    %>
                </td>
            </tr>
        </table>
    </body>
</html>
