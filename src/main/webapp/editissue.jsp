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
                            <%! private String createOptions(String[] values, String selected) {
                                StringBuilder builder = new StringBuilder();
                                for (String value : values) {
                                    builder.append("<option value=\"" + value + "\"");
                                    if (value.equals(selected)) {
                                        builder.append(" selected");
                                    }
                                    builder.append(">" + value + "</option>");
                                }
                                return builder.toString();
                            } %>
                            <p>Priority:
                                <select name="priority">
                                    <% String[] priorityValues = {"1", "2", "3"}; %>
                                    <%= createOptions(priorityValues, issue.getPriority()) %>
                                </select>
                            </p>
                            <p>Status:
                                <select name="status">
                                    <% String[] statusValues = {"open", "in progress", "resolved", "ready for testing", "closed"}; %>
                                    <%= createOptions(statusValues, issue.getStatus()) %>
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
