<html>
<head>
    <meta charset="utf-8">
    <title>index</title>
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
                    <div align="center">
                        <a href="/BugTracker/myprojects">Projects</a>
                        <%
                            if (request.isUserInRole("administrator")) {
                        %>
                                <a href="/BugTracker/admin">Admin Panel</a>
                        <%
                            }
                        %>
                    </div>
                    <div align="right"><a href="/BugTracker/logout">Logout</a></div>
                </td>
            </tr>
            <tr>
                <td width="20%"></td>
                <td></td>
            </tr>
        </table>
    </body>
</html>
