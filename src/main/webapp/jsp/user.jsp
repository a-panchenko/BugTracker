<%@ page import="model.GroupMember" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>User</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <tr valign="top">
                <td width="20%">
                    <%
                        GroupMember user = (GroupMember) request.getAttribute("user");
                        if (request.isUserInRole("administrator") || request.getRemoteUser().equals(user.getName())) {
                    %>
                            <div align="center">
                                <a href="/BugTracker/edituser?name=${user.name}"> Edit User </a>
                            </div>
                    <%
                        }
                    %>
                    <%
                        if (request.isUserInRole("administrator")) {
                    %>
                            <div align="center">
                                <a href="/BugTracker/removeuser?name=${user.name}"> Remove User </a>
                            </div>
                    <%
                        }
                    %>
                </td>
                <td>
                    <c:if test="${not empty user}">
                        <p>
                            Username: ${user.name}
                        </p>
                        <p>
                            Group: ${user.group}
                        </p>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
