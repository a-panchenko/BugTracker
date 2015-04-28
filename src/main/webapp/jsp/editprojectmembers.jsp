<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <meta charset="utf-8">
      <title>Edit Project Members</title>
      <style>
          body {
              font: 11pt Arial, Helvetica, sans-serif;
              height: 100vh;
          }
          #modal_form {
              width: 300px;
              height: 300px;
              border-radius: 5px;
              border: 3px #000 solid;
              background: #fff;
              position: fixed;
              top: 45%;
              left: 50%;
              margin-top: -150px;
              margin-left: -150px;
              display: none;
              opacity: 0;
              z-index: 5;
              padding: 20px 10px;
          }
          #modal_form #modal_close {
              width: 21px;
              height: 21px;
              position: absolute;
              top: 10px;
              right: 10px;
              cursor: pointer;
              display: block;
          }
          #overlay {
              z-index: 3;
              position: fixed;
              background-color: #000;
              opacity: 0.8;
              width: 100%;
              height: 100%;
              top: 0;
              left: 0;
              cursor: pointer;
              display: none;
          }
          #users-to-select {
              width:250px;
              height:200px;
              background: #fff;
              border: 1px solid #C1C1C1;
              overflow: auto;
          }
      </style>
      <script type="text/javascript" src="js/jquery-2.1.3.js"></script>
      <script type="text/javascript" src="js/getProjectMembers.js"></script>
      <script type="text/javascript" src="js/searchMembers.js"></script>
      <script type="text/javascript" src="js/addMembers.js"></script>
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
                  <div align="center">

                  </div>
              </td>
              <td>
                  Current Members
                  <div id="members">

                  </div>
                  <button id="add-button">Add</button>
              </td>
          </tr>
      </table>
      <div id="modal_form">
          <span id="modal_close">X</span>
          Search: <input id="search" type="text" oninput="searchMembers()">
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
              <button onclick="addMembers(<%= request.getParameter("id") %>)">Add</button>
          </p>
      </div>
      <div id="overlay"></div>
      <script>
          $('#members').load(getProjectMembers(<%= request.getParameter("id") %>));
          $(document).ready( function() {
              $('#add-button').click( function(event){
                  event.preventDefault();
                  $('#overlay').fadeIn(400,
                          function(){
                              $('#modal_form')
                                      .css('display', 'block')
                                      .animate({opacity: 1, top: '50%'}, 200);
                          });
              });
              $('#modal_close, #overlay').click( function(){
                  $('#modal_form')
                          .animate({opacity: 0, top: '45%'}, 200,
                          function(){
                              $(this).css('display', 'none');
                              $('#overlay').fadeOut(400);
                          }
                  );
              });
          });
      </script>
  </body>
</html>
