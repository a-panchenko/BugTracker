<%@ page import="model.Issue" %>
<%@ page import="model.Reply" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Issue</title>
        <link href="css/content.css" type="text/css" rel="stylesheet">
        <style>
            td {
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
                        <a href="/BugTracker/myprojects">Projects</a> > <a href="/BugTracker/project?id=${project.id}">${project.title}</a> > <a href="/BugTracker/issue?id=${issue.id}">${issue.title}</a>
                    </td>
                </tr>
            </c:if>
            <tr valign="top">
                <td width="20%">
                    <c:if test="${not empty issue}">
                        <div align="center"><a href="/BugTracker/editissue?id=${issue.id}">Edit Issue</a></div>
                        <%
                            if (request.isUserInRole("administrator")) {
                        %>
                                <div align="center"><a href="/BugTracker/removeissue?id=${issue.id}">Remove Issue</a></div>
                        <%
                            }
                        %>
                    </c:if>
                </td>
                <td>
                    <%
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
                    %>
                    <c:if test="${not empty project && not empty issue}">
                        <table width="80%">
                            <tr>
                                <td width="30%">
                                    Title: ${issue.title}
                                </td>
                                <td id="description" valign="top" rowspan="6">
                                    Description:
                                    <br>${issue.description}
                                </td>
                            </tr>
                            <tr>
                                <td>Priority: ${issue.priority} </td>
                            </tr>
                            <tr>
                                <td>Status: ${issue.status} </td>
                            </tr>
                            <tr>
                                <td>Creation date: <%= dateFormat.format(((Issue) request.getAttribute("issue")).getCreationDate()) %> </td>
                            </tr>
                            <tr>
                                <td>Solving date:
                                    <c:if test="${not empty issue.solvingDate}">
                                        <%= dateFormat.format(((Issue) request.getAttribute("issue")).getSolvingDate()) %>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>Created By:
                                    <c:if test="${not empty issue.creator}">
                                        <a href="/BugTracker/user?name=${issue.creator}"> ${issue.creator} </a>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>Assigned:
                                    <c:if test="${not empty issue.assigned}">
                                        <a href="/BugTracker/user?name=${issue.assigned}"> ${issue.assigned} </a>
                                    </c:if>
                                </td>
                            </tr>
                        </table>
                    </c:if>
                    <c:if test="${not empty replies}">
                        <p>Replies:
                            <table border="1" width="80%">
                                <tr>
                                    <td>Date</td>
                                    <td>Posted By</td>
                                    <td>Message</td>
                                </tr>
                                <c:forEach var="reply" items="${replies}">
                                    <tr>
                                        <td><%= dateFormat.format(((Reply) pageContext.getAttribute("reply")).getDate()) %></td>
                                        <td>
                                            <a href="/BugTracker/user?name=${reply.poster}"> ${reply.poster} </a>
                                        </td>
                                        <td width="80%"> ${reply.message} </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </p>
                    </c:if>
                    <form action="postreply" method="post">
                        <br>
                        <textarea name="message" rows="5" cols="50" placeholder="post reply" required></textarea>
                        <input type="hidden" name="issueId" value="${issue.id}">
                        <br>
                        <input type="submit" value="Post"/>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
