<%@ page import="model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Issue" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
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
                    <jsp:include page="jsp/common/header.jsp"/>
                </td>
            </tr>
            <tr valign="top">
                <td width="20%">
                    <%
                        Project project = (Project) request.getAttribute("project");
                        if (project != null) {
                    %>
                            <div align="center"><a href="/BugTracker/createissue?id=<%= project.getId() %>">Create Issue</a></div>
                    <%
                            if (request.isUserInRole("administrator") || request.isUserInRole("project-manager")) {
                    %>
                            <div align="center"><a href="/BugTracker/editproject?id=<%= project.getId() %>">Edit Project</a></div>
                    <%
                            }
                            if (request.isUserInRole("administrator")) {
                    %>
                            <div align="center"><a href="/BugTracker/removeproject?id=<%= project.getId() %>">Remove Project</a></div>
                    <%
                            }
                        }
                    %>
                </td>
                <td>
                    <%
                        if (project != null) {
                            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
                    %>
                        <p>Title: <%= project.getTitle() %> </p>
                        <p>Description: <%= project.getDescription() %> </p>
                        <p>Project Leed:
                            <%
                                if (project.getProjectLeed() != null) {
                            %>
                                    <a href="<%= "/BugTracker/user?name=" + project.getProjectLeed() %>"><%= project.getProjectLeed() %></a>
                            <%
                                }
                            %>
                        </p>
                        <p>Start date: <%= dateFormat.format(project.getStartDate()) %> </p>
                        <p>End date:
                            <%
                                if (project.getEndDate() != null) {
                            %>
                                    <%= dateFormat.format(project.getEndDate()) %>
                            <%
                                }
                            %>
                        </p>
                    <%
                        List<Issue> issues = (List<Issue>) request.getAttribute("issues");
                        if (issues != null && issues.size() > 0) {
                    %>
                            <br><table width="80%" height="5%" border="1" cellspacing="0">
                                <tr align="center">
                                    <td> ID </td>
                                    <td width="20"> Title </td>
                                    <td width="40%"> Description </td>
                                    <td> Priority </td>
                                    <td> Status </td>
                                    <td> Creation date </td>
                                    <td> Solving date </td>
                                    <td> Created By </td>
                                    <td> Assigned </td>
                                </tr>
                    <%
                                for (Issue issue : issues) {
                    %>
                                    <tr>
                                        <td> <%= issue.getId() %> </td>
                                        <td> <a href="/BugTracker/issue?id=<%= issue.getId() %>"><%= issue.getTitle() %></a> </td>
                                        <td>
                                            <%
                                                if (issue.getDescription().length() > 30) {
                                            %>
                                                    <%= issue.getDescription().substring(0, 30) %>
                                            <%
                                                }
                                                else {
                                            %>
                                                    <%= issue.getDescription() %>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <td> <%= issue.getPriority() %> </td>
                                        <td> <%= issue.getStatus() %> </td>
                                        <td> <%= dateFormat.format(issue.getCreationDate()) %> </td>
                                        <td>
                                            <%
                                                if (issue.getSolvingDate() != null) {
                                            %>
                                                    <%= dateFormat.format(issue.getSolvingDate()) %>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <td>
                                            <%
                                                if (issue.getCreator() != null) {
                                            %>
                                                    <a href="<%= "/BugTracker/user?name=" + issue.getCreator() %>"><%= issue.getCreator() %></a>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <td>
                                            <%
                                                if (issue.getAssigned() != null) {
                                            %>
                                                    <a href="<%= "/BugTracker/user?name=" + issue.getAssigned() %>"><%= issue.getAssigned() %></a>
                                            <%
                                                }
                                            %>
                                        </td>
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
