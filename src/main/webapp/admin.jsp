<%@ page import="model.GroupMember" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Admin Panel</title>
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
                    <div align="center"><a href="/BugTracker/createuser.jsp">Create User</a></div>
                </td>
                <td>
                    <%
                        List<GroupMember> groupMembers = (List<GroupMember>) request.getAttribute("groupMembers");
                        if ((groupMembers != null) && (groupMembers.size() > 0)) {
                    %>
                            <table border="1" cellspacing="0">
                                <tr align="center">
                                    <td> Username </td>
                                    <td> Group </td>
                                </tr>
                                <%
                                    for (GroupMember groupMember : groupMembers) {
                                %>
                                        <tr>
                                            <td> <a href="<%= "/BugTracker/user?name=" + groupMember.getName() %>"><%= groupMember.getName() %></a> </td>
                                            <td> <%= groupMember.getGroup() %> </td>
                                        </tr>
                                <%
                                    }
                                %>
                            </table>
                    <%
                        }
                    %>
                </td>
            </tr>
        </table>
    </body>
</html>
