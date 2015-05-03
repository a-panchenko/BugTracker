<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Admin Panel</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <tr valign="top">
                <td width="20%">
                    <div align="center">
                        <a href="/BugTracker/createuser.jsp">Create User</a>
                    </div>
                </td>
                <td>
                    <c:if test="${not empty groupMembers}">
                        <table border="1" cellspacing="0">
                            <tr align="center">
                                <td> Username </td>
                                <td> Group </td>
                            </tr>
                            <c:forEach var="groupMember" items="${groupMembers}">
                                <tr>
                                    <td> <a href="/BugTracker/user?name=${groupMember.name}">${groupMember.name}</a> </td>
                                    <td> ${groupMember.group} </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
