<%@ page import="model.Project" %>
<%@ page import="service.ProjectServiceImpl" %>
<%@ page import="model.Issue" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit Issue</title>
        <style>
            body {
                font: 11pt Arial, Helvetica, sans-serif;
                height: 100vh;
            }
            textarea {
                width: 80%;
            }
        </style>
    </head>
    <body>
        <table width="100%" height="100%" border="1" cellpadding="20%">
            <jsp:include page="jsp/common/header.jsp"/>
            <c:if test="${not empty project && not empty issue}">
                <tr>
                    <td colspan="2" height="5%">
                        <a href="/BugTracker/myprojects">Projects</a> > <a href="/BugTracker/project?id=${project.id}">${project.title}</a> > <a href="/BugTracker/issue?id=${issue.id}">${issue.title}</a>
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
                            <%
                                if (request.isUserInRole("administrator")) {
                            %>
                                    <p>Replace Issue To Project:
                                        <c:if test="${not empty projects}">
                                            <br>
                                            <select name="project">
                                                <c:forEach var="project" items="${projects}">
                                                    <option value="${project.id}" <c:if test="${project.id eq issue.projectId}"> selected </c:if> >
                                                        ${project.title}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </p>
                            <%
                                }
                            %>
                            <%
                                Project project = (Project) request.getAttribute("project");
                                Issue issue = (Issue) request.getAttribute("issue");
                                if (request.isUserInRole("administrator")
                                        || request.getRemoteUser().equals(project.getProjectLeed())
                                        || request.getRemoteUser().equals(issue.getCreator())) {
                            %>
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
                            <%
                                }
                            %>
                            <p>Status:
                                <select name="status">
                                    <option value="open" <c:if test="${'open' eq issue.status}"> selected </c:if> > open </option>
                                    <option value="in progress" <c:if test="${'in progress' eq issue.status}"> selected </c:if> > in progress </option>
                                    <option value="resolved" <c:if test="${'resolved' eq issue.status}"> selected </c:if> > resolved </option>
                                    <option value="testing" <c:if test="${'testing' eq issue.status}"> selected </c:if> > testing </option>
                                    <option value="close" <c:if test="${'close' eq issue.status}"> selected </c:if> > close </option>
                                </select>
                            </p>
                            <%
                                if (request.isUserInRole("administrator") || request.getRemoteUser().equals(project.getProjectLeed())) {
                            %>
                                    <p>Assigned:
                                        <c:if test="${not empty projectMembersToAssign}">
                                            <select name="assigned">
                                                <option/>
                                                <c:forEach var="name" items="${projectMembersToAssign}">
                                                    <option value="${name}" <c:if test="${name eq issue.assigned}"> selected </c:if> > ${name} </option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </p>
                                    <p>Created By:
                                        <c:if test="${not empty possibleCreators}">
                                            <select name="creator">
                                                <option/>
                                                <c:forEach var="name" items="${possibleCreators}">
                                                    <option value="${name}" <c:if test="${name eq issue.creator}"> selected </c:if> > ${name} </option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </p>
                            <%
                                }
                            %>
                            <input type="submit" value="Edit Issue"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
