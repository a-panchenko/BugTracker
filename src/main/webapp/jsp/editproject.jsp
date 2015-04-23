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
        <style>
            body {
                font: 11pt Arial, Helvetica, sans-serif;
                height: 100vh;
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
                    <c:if test="${not empty project}">
                        <form action="editproject" method="post">
                            <input type="hidden" name="id" value="${project.id}">
                            <p>Title:
                                <input type="text" name="title" value="${project.title}" required/>
                            </p>
                            <p>Description:
                                <br><textarea name="description" rows="10" cols="50" required>${project.description}</textarea>
                            </p>
                            <c:if test="${not empty projectManagers}">
                                <p>Project Leed:
                                    <select name="projectManagers">
                                        <c:forEach var="groupMember" items="${projectManagers}">
                                            <option <c:if test="${project.projectLeed == groupMember.name}"> selected </c:if> >
                                                ${groupMember.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </c:if>
                            <p>Close: <input name="close" type="checkbox" <c:if test="${not empty project.endDate}"> checked </c:if></p>
                            <p>Members:
                                <%
                                    List<GroupMember> availableMembers = (List<GroupMember>) request.getAttribute("availableMembers");
                                    List<GroupMember> currentMembers = (List<GroupMember>) request.getAttribute("currentMembers");
                                    if (availableMembers != null && currentMembers != null) {
                                        List<String> currentMembersNames = new ArrayList<String>();
                                        for (GroupMember groupMember : currentMembers) {
                                            currentMembersNames.add(groupMember.getName());
                                %>
                                            <br><input name="members" type="checkbox" value="<%= groupMember.getName() %>" checked/><%= groupMember.getName() %> (<%= groupMember.getGroup() %>)
                                <%
                                        }
                                        for (GroupMember groupMember : availableMembers) {
                                            if (! currentMembersNames.contains(groupMember.getName())) {
                                %>
                                                <br><input name="members" type="checkbox" value="<%= groupMember.getName() %>"/><%= groupMember.getName() %> (<%= groupMember.getGroup() %>)
                                <%
                                            }
                                        }
                                    }
                                %>
                            </p>
                            <input type="submit" value="Edit Project"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
