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
            <h2> "${sprint.name}"</h2>
     </div>
<div class="container">
    <h2 id="article_header" class="text-warning" align="center"> Backlog </h2>
    <div>&nbsp;</div>

    <!-- Div to add a new backlog to the mongo database -->

    <div>&nbsp;</div>
    <!-- Table to display the backlog from the mongo database -->
    <table id="sprint_table" class="table">
        <thead>
        <tr align="center">

            <th>BACKLOG TASK & ID</th>
            <th>STORY POINTS</th>
            <th>ASSIGNED TO</th>
            <th>STATUS</th>
            <th>ORIGIN AL ESTIMATE</th>
              <c:forEach items="${subtaskAttr.actualHours}" varStatus="st" var="actualHour"   >
                <tr align="left">
                 <th>Day ${st.index+1}</th>
                 </tr>
                 </c:forEach>
            <th>SPRINT REVIEW/th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <!-- sprint tasks -->
        <tr align="left">
        <c:forEach items="${sprints}" var="sprint">
           <td><c:out value="${sprint.name}" /></td>
             <c:forEach items="${tasks}" var="task">
               <td><c:out value="${task.name}" /></td>
               <c:forEach items="${subtasks}" var="subtask">
                <td><c:out value="${subtask.name}" /></td>
               </c:forEach>
             </c:forEach>
        </tr>
        <tr align="left">
          <c:forEach items="${subtasks}" var="subtask">
             <td><c:out value="${subtask.storyPoints}" /></td>
          </c:forEach>
          </tr>
          <c:forEach items="${subtaskAttr.users}" varStatus="st" var="user"   >
            <tr align="left">
              <td><form:input path="users[${st.index}].name" cssClass="form-control" value="${user.name}"/></td>
              <td><form:input type="hidden" path="users[${st.index}].id" value="${user.id}" /></td>
              </tr>
              <tr align="left">
              <td><form:input path="users[${st.index}].taskStatus" value="${user.taskStatus}" /></td>
            </tr>
             <tr align="left">
               <td><form:input path="users[${st.index}].OEstimate" value="${user.OEstimate}" /></td>
             </tr>
          </c:forEach>
          <c:forEach items="${subtaskAttr.actualHours}" varStatus="st" var="actualHour"   >
             <tr align="left">
              <td><form:input type="number" path="actualHours[${st.index}]" cssClass="form-control" value="${actualHour}"/></td>
             </tr>
          </c:forEach>
           <c:forEach items="${sprints}" var="sprint">
           <tr>
              <td><c:out value="${sprint.review}" /></td>
           </tr>
        </c:forEach>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>