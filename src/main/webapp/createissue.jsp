<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create Issue</title>
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

                </td>
                <td> <%
                    Integer projectId = (Integer) request.getAttribute("projectId");
                    if (projectId != null) { %>
                        <form action="createissue" method="post">
                            <p>Title:
                                <input type="text" name="title" placeholder="title" required/>
                            </p>
                            <input type="hidden" name="projectId" value="<%= projectId %>">
                            <p>Description:
                                <br><textarea name="description" rows="10" cols="50" placeholder="description" required></textarea>
                            </p>
                            <p>Priority:
                                <select name="priority">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                </select>
                            </p>
                            <input type="submit" value="Create Issue"/>
                        </form> <%
                    } %>
                </td>
            </tr>
        </table>
    </body>
</html>
