<%@ page import="model.Issue" %>
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
        <script type="text/javascript" src="js/jquery-2.1.3.js"></script>
        <script type="text/javascript" src="js/reply.js"></script>
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <c:if test="${not empty project}">
                <tr>
                    <td colspan="2" height="5%">
                        <a href="/BugTracker/projects">Projects</a> > <a href="/BugTracker/project?id=${project.id}">${project.title}</a> > <a href="/BugTracker/issue?id=${issue.id}">${issue.title}</a>
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
                    <p>
                        <textarea id="message" rows="5" cols="50" placeholder="post reply" required></textarea>
                        <br>
                        <input type="button" value="Post" onclick="addReply(${issue.id})"/>
                    </p>
                    <div id="replies"></div>
                </td>
            </tr>
        </table>
        <script>
            $(document).ready(function() {
                $("replies").load(getReplies(${issue.id}))
            });
        </script>
    </body>
</html>
