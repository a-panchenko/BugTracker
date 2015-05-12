<%@ page import="model.Project" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="model.Issue" %>
<%@ page import="security.issue.IssueSecurity" %>
<%@ page import="security.issue.IssueSecurityImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Project</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
        <style>
            #description {
                word-break: break-all;
            }
        </style>
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
                                </a>
                    </td>
                </tr>
            </c:if>
            <tr valign="top">
                <td width="20%">
                    <c:if test="${not empty project}">
                        <%
                            IssueSecurity issueSecurity = new IssueSecurityImpl();
                            Project project = (Project) request.getAttribute("project");
                            if (issueSecurity.isAllowedToEditIssue(request.getRemoteUser(), project)) {
                        %>
                                <div align="center"><a href="/BugTracker/createissue?id=${project.id}">Create Issue</a></div>
                        <%
                            }
                            if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
                        %>
                                <div align="center"><a href="/BugTracker/editproject?id=${project.id}">Edit Project</a></div>
                                <div align="center"><a href="/BugTracker/editprojectmembers?id=${project.id}">Edit Members</a></div>
                        <%
                            }
                        %>
                        <%
                            if (request.isUserInRole("administrator")) {
                        %>
                                <div align="center"><a href="/BugTracker/removeproject?id=${project.id}">Remove Project</a></div>
                        <%
                            }
                        %>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty project}">
                        <p>Title:
                            ${project.title}
                        </p>
                        <p id="description">Description:
                            ${project.description}
                        </p>
                        <p>Project Leed:
                            <c:if test="${not empty project.projectLeed}">
                                <a href="/BugTracker/user?name=${project.projectLeed}">${project.projectLeed}</a>
                            </c:if>
                        </p>
                        <%
                            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
                        %>
                        <p>Start date:
                            <%= dateFormat.format(((Project) request.getAttribute("project")).getStartDate()) %>
                        </p>
                        <p>End date:
                            <c:if test="${not empty project.endDate}">
                                <%= dateFormat.format(((Project) request.getAttribute("project")).getEndDate()) %>
                            </c:if>
                        </p>
                        <c:if test="${not empty issues}">
                            <br>
                            <table width="100%" height="5%" border="1" cellspacing="0">
                                <tr align="center">
                                    <td> ID </td>
                                    <td width="30"> Title </td>
                                    <td width="30%"> Description </td>
                                    <td> Priority </td>
                                    <td> Status </td>
                                    <td> Creation date </td>
                                    <td> Solving date </td>
                                    <td> Created By </td>
                                    <td> Assigned </td>
                                </tr>
                                <c:forEach var="issue" items="${issues}">
                                    <tr>
                                        <td> ${issue.id} </td>
                                        <td>
                                            <a href="/BugTracker/issue?id=${issue.id}">
                                                <c:choose>
                                                    <c:when test="${fn:length(issue.title) > 30}">
                                                        ${fn:substring(issue.title, 0, 30)}...
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${issue.title}
                                                    </c:otherwise>
                                                </c:choose>
                                            </a>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:length(issue.description) > 30}">
                                                    ${fn:substring(issue.description, 0, 30)}...
                                                </c:when>
                                                <c:otherwise>
                                                    ${issue.description}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td> ${issue.priority} </td>
                                        <td> ${issue.status} </td>
                                        <td> <%= dateFormat.format(((Issue) pageContext.getAttribute("issue")).getCreationDate()) %> </td>
                                        <td>
                                            <c:if test="${not empty issue.solvingDate}">
                                                <%= dateFormat.format(((Issue) pageContext.getAttribute("issue")).getSolvingDate()) %>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty issue.creator}">
                                                <a href="/BugTracker/user?name=${issue.creator}"> ${issue.creator} </a>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty issue.assigned}">
                                                <a href="/BugTracker/user?name=${issue.assigned}"> ${issue.assigned} </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
