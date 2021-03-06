<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create User</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
        <script type="text/javascript" src="js/validatePassword.js"></script>
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <tr>
                <td colspan="2" height="5%">
                    <a href="/BugTracker/admin">Admin Panel</a> > Create User
                </td>
            </tr>
            <tr valign="top">
                <td width="20%"></td>
                <td>
                    <form action="createuser" onsubmit="return validatePassword(password.value, retypePassword.value)" method="post">
                        <p>Username:
                            <input type="text" name="username" pattern="[A-Za-z0-9]{4,15}" size="15" maxlength="15" required/> (min 4 letters, digits)
                        </p>
                        <p>Password:
                            <input type="password" name="password" pattern="[A-Za-z0-9]{6,20}" size="20" maxlength="20" required/> (min 6 letters, digits)
                        </p>
                        <p>Retype Password:
                            <input type="password" name="retypePassword" size="20" maxlength="20" required/>
                        </p>
                        <p>Group:
                            <select name="group">
                                <option>administrators</option>
                                <option>project-managers</option>
                                <option>debugers</option>
                                <option>testers</option>
                            </select>
                        </p>
                        <p>
                            <input type="submit" value="Create User"/>
                        </p>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
