<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
      <meta charset="utf-8">
      <title>Edit Project Members</title>
      <style>
          body {
              font: 11pt Arial, Helvetica, sans-serif;
              height: 100vh;
          }
      </style>
      <script type="text/javascript" src="js/jquery-2.1.3.js"></script>
      <script type="text/javascript" src="js/editprojectmembers.js"></script>
      <link href="css/editprojectmembers.css" type="text/css" rel="stylesheet">
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
              <td width="20%">
                  <div align="center"></div>
              </td>
              <td>
                  Current Members
                  <div id="members"></div>
                  <button id="add-button">Add</button>
              </td>
          </tr>
      </table>
      <div id="modal_form">
          <span id="modal_close">X</span>
          <input id="search" type="text" placeholder="search" oninput="searchMembers()">
          <p>
              <select id="group" onchange="searchMembers()">
                  <option>testers</option>
                  <option>debugers</option>
              </select>
          </p>
          <p>
              <div id="users-to-select"></div>
          </p>
          <p>
              <button onclick="addMembers(${project.id})">Add</button>
          </p>
      </div>
      <div id="overlay"></div>
      <script>
          $(document).ready(function() {
              $('#members').load(getProjectMembers(${project.id}));

              $("#add-button").click(function(event){
                  event.preventDefault();
                  $("#overlay").fadeIn(400,
                          function(){
                              $("#modal_form")
                                      .css('display', 'block')
                                      .animate({opacity: 1, top: '50%'}, 200);
                          });
              });
              $("#modal_close, #overlay").click(function(){
                  $("#modal_form")
                          .animate({opacity: 0, top: '45%'}, 200,
                          function(){
                              $(this).css('display', 'none');
                              $("#overlay").fadeOut(400);
                          }
                  );
              });
          });
      </script>
  </body>
</html>
