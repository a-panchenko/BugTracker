<%@ page import="model.GroupMember" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>User</title>
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
                        GroupMember user = (GroupMember) request.getAttribute("user");
                        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(user.getName())) {
                    %>
                            <div align="center"><a href="<%= "/BugTracker/edituser?name=" + user.getName() %>">Edit User</a></div>
                    <%
                        }
                    %>
                    <%
                        if (request.isUserInRole("administrator")) {
                    %>
                            <div align="center"><a href="/BugTracker/createuser.jsp">Create User</a></div>
                    <%
                        }
                    %>
                </td>
                <td>
                    <%
                        if (user != null) {
                    %>
                            <p>
                                Username: <%= user.getName() %>
                            </p>
                            <p>
                                Group: <%= user.getGroup() %>
                            </p>
                    <%
                        }
                    %>
                </td>
            </tr>
        </table>
    </body>
</html>
