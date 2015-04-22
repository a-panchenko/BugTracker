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
        <script type="text/javascript" src="js/validatePassword.js"></script>
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
                                <form action="edituser" onsubmit="return validatePassword(newPassword.value, retypeNewPassword.value)" method="post">
                                    <p>User:
                                        <input type="text" name="name" value="<%= user %>" readonly/>
                                    </p>
                                    <%
                                        if (request.isUserInRole("administrator")) {
                                            String group = (String) request.getAttribute("group");
                                            if (group != null) {
                                    %>
                                                <p>Group:
                                                    <select name="group">
                                                        <option <% if (group.equals("administrators")) { %> selected <% } %>>administrators</option>
                                                        <option <% if (group.equals("project-managers")) { %> selected <% } %>>project-managers</option>
                                                        <option <% if (group.equals("debugers")) { %> selected <% } %>>debugers</option>
                                                        <option <% if (group.equals("testers")) { %> selected <% } %>>testers</option>
                                                    </select>
                                                </p>
                                    <%
                                            }
                                        }
                                        if (user.equals(request.getRemoteUser()) && ! request.isUserInRole("administrator")) {
                                    %>
                                            <p>Old Password:
                                                <input type="password" name="oldPassword" required/>
                                            </p>
                                    <%
                                        }
                                    %>
                                    <p>New Password:
                                        <input type="password" name="newPassword" pattern="[A-Za-z0-9]{6,20}" <% if (! request.isUserInRole("administrator")) { %> required <% } %>/>
                                    </p>
                                    <p>Retype New Password:
                                        <input type="password" name="retypeNewPassword" <% if (! request.isUserInRole("administrator")) { %> required <% } %>/>
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
