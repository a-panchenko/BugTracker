<%@ page import="java.util.List" %>
<%@ page import="model.GroupMember" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit Project</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <c:if test="${not empty project}">
                <tr>
                    <td colspan="2" height="5%">
                        <a href="/BugTracker/myprojects">Projects</a> > <a href="/BugTracker/project?id=${project.id}">${project.title}</a>
                    </td>
                </tr>
            </c:if>
            <tr valign="top">
                <td width="20%"></td>
                <td>
                    <c:if test="${not empty project}">
                        <form action="editproject" method="post">
                            <input type="hidden" name="id" value="${project.id}">
                            <p>Title:
                                <input type="text" name="title" value="${project.title}" size="50" maxlength="50" required/>
                            </p>
                            <p>Description:
                                <br>
                                <textarea name="description" rows="10" cols="50" required>${project.description}</textarea>
                            </p>
                            <c:if test="${not empty projectManagers}">
                                <p>Project Leed:
                                    <select name="projectManagers">
                                        <c:forEach var="groupMember" items="${projectManagers}">
                                            <option <c:if test="${project.projectLeed == groupMember.name}"> selected </c:if> > ${groupMember.name} </option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </c:if>
                            <p>Close: <input name="close" type="checkbox" <c:if test="${not empty project.endDate}"> checked </c:if></p>
                            <p>
                                <input type="submit" value="Edit Project"/>
                            </p>
                        </form>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
