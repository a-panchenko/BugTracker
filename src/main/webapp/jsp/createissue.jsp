<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create Issue</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <c:if test="${not empty project}">
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
                                </a> > Create Issue
                    </td>
                </tr>
            </c:if>
            <tr valign="top">
                <td width="20%"></td>
                <td>
                    <c:if test="${not empty project}">
                        <form action="createissue" method="post">
                            <p>Title:
                                <input type="text" name="title" placeholder="title" size="100" maxlength="100" required/>
                            </p>
                            <input type="hidden" name="projectId" value="${project.id}"/>
                            <p>Description:
                                <br><textarea name="description" rows="10" cols="50" placeholder="description" required></textarea>
                            </p>
                            <p>Assigned:
                                <c:if test="${not empty projectMembersToAssign}">
                                    <select name="assigned">
                                        <option/>
                                        <c:forEach var="memberName" items="${projectMembersToAssign}">
                                            <option>${memberName}</option>
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </p>
                            <p>Created By:
                                <c:if test="${not empty possibleCreators}">
                                    <select name="creator">
                                        <option/>
                                        <c:forEach var="creator" items="${possibleCreators}">
                                            <option>${creator}</option>
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </p>
                            <p>Priority:
                                <select name="priority">
                                    <option>low</option>
                                    <option>middle</option>
                                    <option>high</option>
                                </select>
                            </p>
                            <input type="submit" value="Create Issue"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
