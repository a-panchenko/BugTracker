<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Edit User</title>
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

        </td>
        <td>
            <%
                String user = request.getParameter("name");
                if (user != null) {
                    if (request.isUserInRole("administrator") || user.equals(request.getRemoteUser())) {
            %>
                        <form action="edituser" method="post">
                            <p>User:
                                <input type="text" name="name" value="<%= user %>" readonly/>
                            </p>
                            <%
                                if (request.isUserInRole("administrator")) {
                            %>
                                    <p>Group:
                                        <select name="group" required>
                                            <option>administrators</option>
                                            <option>project-managers</option>
                                            <option>debugers</option>
                                            <option>testers</option>
                                        </select>
                                    </p>
                            <%
                                }
                            %>
                            <%
                                if (user.equals(request.getRemoteUser())) {
                            %>
                                    <p>Old Password:
                                        <input type="password" name="oldPassword"/>
                                    </p>
                            <%
                                }
                            %>
                            <p>New Password:
                                <input type="password" name="newPassword"/>
                            </p>
                            <p>Retype New Password:
                                <input type="password" name="newPasswordRepeat"/>
                            </p>
                            <p>
                                <input type="submit" value="Edit User"/>
                            </p>
                        </form>
            <%
                    }
                }
            %>
        </td>
    </tr>
</table>
</body>
</html>
