<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit User</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
        <script type="text/javascript" src="js/validatePassword.js"></script>
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <tr valign="top">
                <td width="20%"></td>
                <td>
                    <c:if test="${not empty param.name}">
                        <%
                            if (request.isUserInRole("administrator") || request.getParameter("name").equals(request.getRemoteUser())) {
                        %>
                                <form action="edituser" onsubmit="return validatePassword(newPassword.value, retypeNewPassword.value)" method="post">
                                    <p>User:
                                        <input type="text" name="name" value="${param.name}" readonly/>
                                    </p>
                                    <%
                                        if (request.isUserInRole("administrator")) {
                                    %>
                                            <c:if test="${not empty group}">
                                                <p>Group:
                                                    <select name="group">
                                                        <option <c:if test="${group eq 'administrators'}"> selected </c:if> > administrators </option>
                                                        <option <c:if test="${group eq 'project-managers'}"> selected </c:if> > project-managers </option>
                                                        <option <c:if test="${group eq 'debugers'}"> selected </c:if> > debugers </option>
                                                        <option <c:if test="${group eq 'testers'}"> selected </c:if> > testers </option>
                                                    </select>
                                                </p>
                                            </c:if>
                                    <%
                                        }
                                    %>
                                    <%
                                        if (! request.isUserInRole("administrator")) {
                                    %>
                                            <c:if test="${param.name eq pageContext.request.remoteUser}">
                                                <p>Old Password:
                                                    <input type="password" name="oldPassword" required/>
                                                </p>
                                            </c:if>
                                    <%
                                        }
                                    %>
                                    <p>New Password:
                                        <input type="password" name="newPassword" pattern="[A-Za-z0-9]{6,20}" size="20" maxlength="20" <% if (! request.isUserInRole("administrator")) { %> required <% } %>/> (min 6 letters, digits)
                                    </p>
                                    <p>Retype New Password:
                                        <input type="password" name="retypeNewPassword" size="20" maxlength="20" <% if (! request.isUserInRole("administrator")) { %> required <% } %>/>
                                    </p>
                                    <p>
                                        <input type="submit" value="Edit User"/>
                                    </p>
                                </form>
                        <%
                            }
                        %>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
