<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit Issue</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <c:if test="${not empty project && not empty issue}">
                <tr>
                    <td colspan="2" height="5%">
                        <a href="/BugTracker/projects">
                            Projects
                        </a> >
                                <a href="/BugTracker/project?id=${project.id}">
                                    <c:choose>
                                        <c:when test="${fn:length(project.title) > 15}">
                                            ${fn:substring(project.title, 0, 15)}...
                                        </c:when>
                                        <c:otherwise>
                                            ${project.title}
                                        </c:otherwise>
                                    </c:choose>
                                </a> >
                                        <a href="/BugTracker/issue?id=${issue.id}">
                                            <c:choose>
                                                <c:when test="${fn:length(issue.title) > 15}">
                                                    ${fn:substring(issue.title, 0, 15)}...
                                                </c:when>
                                                <c:otherwise>
                                                    ${issue.title}
                                                </c:otherwise>
                                            </c:choose>
                                        </a> > Edit Issue
                    </td>
                </tr>
            </c:if>
            <tr valign="top">
                <td width="20%">
                    <%
                        if (request.isUserInRole("administrator")) {
                    %>
                            <div align="center">
                                <a href="/BugTracker/removeissue?id=${issue.id}">Remove Issue</a>
                            </div>
                    <%
                        }
                    %>
                </td>
                <td>
                    <c:if test="${not empty issue}">
                        <form action="editissue" method="post">
                            <input type="hidden" name="id" value="${issue.id}">
                            <p>Title:
                                <input type="text" name="title" value="${issue.title}" size="100" maxlength="100" required/>
                            </p>
                            <p>Description:
                                <br>
                                <textarea name="description" rows="10" cols="50" required>${issue.description}</textarea>
                            </p>
                            <p>Priority:
                                <select name="priority">
                                    <option value="low" <c:if test="${'low' eq issue.priority}"> selected </c:if> > low </option>
                                    <option value="middle" <c:if test="${'middle' eq issue.priority}"> selected </c:if> > middle </option>
                                    <option value="high" <c:if test="${'high' eq issue.priority}"> selected </c:if> > high </option>
                                </select>
                            </p>
                            <p>Assigned:
                                <select name="assigned">
                                    <option/>
                                    <c:forEach var="name" items="${projectMembersToAssign}">
                                        <option value="${name}" <c:if test="${name eq issue.assigned}"> selected </c:if> > ${name} </option>
                                    </c:forEach>
                                </select>
                            </p>
                            <p>Status:
                                <select name="status">
                                    <option value="open" <c:if test="${'open' eq issue.status}"> selected </c:if> > open </option>
                                    <option value="in progress" <c:if test="${'in progress' eq issue.status}"> selected </c:if> > in progress </option>
                                    <option value="resolved" <c:if test="${'resolved' eq issue.status}"> selected </c:if> > resolved </option>
                                </select>
                            </p>
                            <input type="submit" value="Edit Issue"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
