<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tr>
    <td colspan="2" height="10%">
        <div align="right">
            <a href="/BugTracker/user?name=${pageContext.request.remoteUser}">${pageContext.request.remoteUser}</a>
        </div>
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
        <div align="right">
            <a href="/BugTracker/logout">Logout</a>
        </div>
    </td>
</tr>