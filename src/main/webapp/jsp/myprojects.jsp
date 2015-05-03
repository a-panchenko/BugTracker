<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="model.Project" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Projects</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <tr valign="top">
                <td width="20%">
                    <%
                        if (request.isUserInRole("administrator") || request.isUserInRole("project-manager")) {
                    %>
                            <div align="center"><a href="/BugTracker/createproject.jsp">Create Project</a></div>
                    <%
                        }
                    %>
                </td>
                <td>
                    <c:if test="${not empty projects}">
                        <table width="90%" height="5%" border="1" cellspacing="0">
                            <tr align="center">
                                <td> Title </td>
                                <td> Description </td>
                                <td> Start date </td>
                                <td> End date </td>
                                <td> Project Leed </td>
                            </tr>
                            <c:forEach var="project" items="${projects}">
                                <tr>
                                    <td width="30%">
                                        <a href="/BugTracker/project?id=${project.id}">
                                            <c:choose>
                                                <c:when test="${fn:length(project.title) > 30}">
                                                    ${fn:substring(project.title, 0, 30)}...
                                                </c:when>
                                                <c:otherwise>
                                                    ${project.title}
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </td>
                                    <td width="30%">
                                        <c:choose>
                                            <c:when test="${fn:length(project.description) > 30}">
                                                ${fn:substring(project.description, 0, 30)}...
                                            </c:when>
                                            <c:otherwise>
                                                ${project.description}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                        <%
                                            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
                                        %>
                                        <td width="15%">
                                            <%= dateFormat.format(((Project) pageContext.getAttribute("project")).getStartDate()) %>
                                        </td>
                                        <td width="15%">
                                            <c:if test="${not empty project.endDate}">
                                                <%= dateFormat.format(((Project) pageContext.getAttribute("project")).getEndDate()) %>
                                            </c:if>
                                        </td>
                                        <td width="10%">
                                            <c:if test="${not empty project.projectLeed}">
                                                <a href="/BugTracker/user?name=${project.projectLeed}">${project.projectLeed}</a>
                                            </c:if>
                                        </td>
                                    </tr>
                            </c:forEach>
                        </table>
                        <jsp:include page="jsp/common/pagination.jsp"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
