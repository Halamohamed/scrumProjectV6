<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Sprint</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
<c:url var="saveUrl" value="/api/sprint/backlog" />
       <form:form id="backlog_form" modelAttribute="backlogAttr" method="POST" action="${saveUrl}">
         <form:hidden path="id" />
    <!-- Div to add a vew backlog from the mongo database -->
    <h3 id="form_header" class="text-warning" align="left">Backlog for Sprint ${backlogAttr.name}</h3>
<form:hidden path="name" />
          <tbody>
          <thead>
          <c:forEach items="${backlogAttr.tasks}" varStatus="spt" var="task">
          <tr align="left">
          <label ><font color="red">Task Name: </font></label><td><c:out value="${task.name}"/></td>
            <td><form:hidden path="tasks[${spt.index}].id"/></td>
            <td><form:hidden path="tasks[${spt.index}].name"/></td>
            <td><form:hidden path="tasks[${spt.index}].priority" /></td>
             <label >Story Points: </label><td><c:out value="${task.storyPoints}"/></td>
            <div>&nbsp;</div>
            <table id="subtasks_table" class="table">
              <tbody>
               <c:forEach items="${task.subTasks}" varStatus="st" var="subTask">
                 <tr align="left">
                 <td><label >SubTask Name:  </label><c:out  value="${subTask.name}"/></td>
                 <td><form:hidden path="tasks[${spt.index}].subTasks[${st.index}].id"/></td>
                 <td><label> Status:  </label><c:out value="${subTask.status}"/></td>
                 <td><label> OEstimate:  </label><c:out value="${subTask.OEstimate}"/></td>
                 <c:forEach items="${subTask.users}" varStatus="us" var="user">
                 <td><label> ASSIGNED To:</label><c:out  value="${user.name}"/>
                 <div>&nbsp;</div></td>
                 </tr>
                 </c:forEach>
                 </tbody>
            <table id="actualHours_table" class="table">
               <tbody><tr>
                 <c:forEach items="${subTask.actualHours}" varStatus="ah" var="actualHour">
                   <td style="width: 50px;"> Day${ah.index+1}</td>
                 </c:forEach></tr>
               </tbody>
               <table id="actualHours_table" class="table">
            <tbody><tr>
                  <c:forEach items="${subTask.actualHours}" varStatus="ah" var="actualHour">
                    <td>
                      <form:input style="width: 30px;" type="number" min="0" path="tasks[${spt.index}].subTasks[${st.index}].actualHours[${ah.index}]" value="${actualHour}" />
                    </td>
                  </c:forEach></tr>
               </tbody>
            </table>
                 </tr>
                 </c:forEach>
                 </tbody>
                 </table>
                 </tr>
                  </c:forEach>
                </tbody>
            </table>
            </tbody>
    </table>
</div>
</body>
</html>