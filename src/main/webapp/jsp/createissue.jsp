<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Create Issue</title>
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
            <tr>
                <td colspan="2" height="10%">
                    <jsp:include page="jsp/common/header.jsp"/>
                </td>
            </tr>
            <tr valign="top">
                <td width="20%">

                </td>
                <td>
                    <c:if test="${not empty projectId}">
                        <form action="createissue" method="post">
                            <p>Title:
                                <input type="text" name="title" placeholder="title" required/>
                            </p>
                            <input type="hidden" name="projectId" value="${projectId}">
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
