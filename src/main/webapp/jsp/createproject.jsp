<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create Project</title>
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

                </td>
                <td>
                    <form action="createproject" method="post">
                        <p>Title:
                            <input type="text" name="title" placeholder="title" size="50" maxlength="50" required/>
                        </p>
                        <p>Description:
                            <br><textarea name="description" rows="10" cols="50" placeholder="description" required></textarea>
                        </p>
                        <input type="hidden" name="projectLeed" value="${pageContext.request.remoteUser}">
                        <input type="submit" value="Create Project"/>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
