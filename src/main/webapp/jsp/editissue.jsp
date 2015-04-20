<%@ page import="model.Issue" %>
<%@ page import="model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ProjectServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit Issue</title>
        <style>
            body {
                font: 11pt Arial, Helvetica, sans-serif;
                height: 100vh;
            }
            textarea {
                width: 80%;
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
                        if (request.isUserInRole("administrator")) {
                    %>
                            <div align="center">
                                <a href="/BugTracker/removeissue?id=<%= issue.getId()%>">Remove Issue</a>
                            </div>
                    <%
                        }
                    %>
                </td>
                <td>
                    <%
                        if (issue != null) {
                    %>
                            <form action="editissue" method="post">
                                <input type="hidden" name="id" value="<%= issue.getId() %>">
                                <%!
                                    private String createOptions(String[] values, String selected) {
                                        StringBuilder builder = new StringBuilder();
                                        for (String value : values) {
                                            builder.append("<option value=\"" + value + "\"");
                                            if (value.equals(selected)) {
                                                builder.append(" selected");
                                            }
                                            builder.append(">" + value + "</option>");
                                        }
                                        return builder.toString();
                                    }
                                %>
                                <%
                                    if (request.isUserInRole("administrator")) {
                                %>
                                        <p>
                                            <%
                                                List<Project> projects = (List<Project>) request.getAttribute("projects");
                                                if (projects != null) {
                                            %>
                                                    <select name="project">
                                                    <%
                                                        for (Project project : projects) {
                                                    %>
                                                        <option value="<%= project.getId() %>" <% if (project.getId() == issue.getProjectId()) { %> selected<% } %> >
                                                            <%= project.getTitle() %>
                                                        </option>
                                                    <%
                                                        }
                                                    %>
                                                    </select>
                                            <%
                                                }
                                            %>
                                        </p>
                                <%
                                    }
                                %>
                                <%
                                    Project project = new ProjectServiceImpl().getProject(issue.getProjectId());
                                    if (request.isUserInRole("administrator")
                                        || request.getRemoteUser().equals(project.getProjectLeed())
                                        || issue.getCreator().equals(request.getRemoteUser())) {
                                %>
                                    <p>Title:
                                        <input type="text" name="title" value="<%= issue.getTitle() %>" required/>
                                    </p>
                                    <p>Description:
                                        <br><textarea name="description" rows="10" cols="50" required><%= issue.getDescription() %></textarea>
                                    </p>
                                    <p>Priority:
                                        <select name="priority">
                                            <% String[] priorityValues = {"low", "middle", "high"}; %>
                                            <%= createOptions(priorityValues, issue.getPriority()) %>
                                        </select>
                                    </p>
                                <%
                                    }
                                %>
                                <p>Status:
                                    <select name="status">
                                        <% String[] statusValues = {"open", "in progress", "resolved", "testing", "close"}; %>
                                        <%= createOptions(statusValues, issue.getStatus()) %>
                                    </select>
                                </p>
                                <%
                                    if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
                                %>
                                        <p>Assigned:
                                            <%
                                                List<String> projectMembersToAssign = (List<String>) request.getAttribute("projectMembersToAssign");
                                                if (projectMembersToAssign != null) {
                                                    String[] names = projectMembersToAssign.toArray(new String[projectMembersToAssign.size()]);
                                            %>
                                                    <select name="assigned">
                                                        <option/>
                                                        <%= createOptions(names, issue.getAssigned()) %>
                                                    </select>
                                            <%
                                                }
                                            %>
                                        </p>
                                        <p>Created By:
                                            <%
                                                List<String> possibleCreators = (List<String>) request.getAttribute("possibleCreators");
                                                if (possibleCreators != null) {
                                                    String[] names = possibleCreators.toArray(new String[possibleCreators.size()]);
                                            %>
                                                    <select name="creator">
                                                        <option/>
                                                        <%= createOptions(names, issue.getCreator()) %>
                                                    </select>
                                            <%
                                                }
                                            %>
                                        </p>
                                <%
                                    }
                                %>
                                <input type="hidden" name="startDate" value="<%= issue.getCreationDate().getTime() %>">
                                <input type="submit" value="Edit Issue"/>
                            </form>
                    <%
                        }
                    %>
                </td>
            </tr>
        </table>
    </body>
</html>
