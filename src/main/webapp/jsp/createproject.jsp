<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create Project</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <tr>
                <td colspan="2" height="5%">
                    <a href="/BugTracker/projects">Projects</a> > Create Project
                </td>
            </tr>
            <tr valign="top">
                <td width="20%"></td>
                <td>
                    <form action="createproject" method="post">
                        <p>Title:
                            <input type="text" name="title" placeholder="title" size="50" maxlength="50" required/>
                        </p>
                        <p>Description:
                            <br>
                            <textarea name="description" rows="10" cols="50" placeholder="description" required></textarea>
                        </p>
                        <input type="submit" value="Create Project"/>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
