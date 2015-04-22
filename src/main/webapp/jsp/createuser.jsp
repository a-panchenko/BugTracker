<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create User</title>
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
                <td width="20%"></td>
                <td>
                    <form action="createuser" onsubmit="return validatePassword(password.value, retypePassword.value)" method="post">
                        <p>Username:
                            <input type="text" name="username" pattern="[A-Za-z0-9]{4,15}" required/>
                        </p>
                        <p>Password:
                            <input type="password" name="password" pattern="[A-Za-z0-9]{6,20}" required/>
                        </p>
                        <p>Retype Password:
                            <input type="password" name="retypePassword" required/>
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
