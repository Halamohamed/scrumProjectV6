<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Sub Task form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<style>#subtaskT{
font-family:Arial;
background-color: red;
color:black;
font-size: 20px;
font-weight: bold;
}
</style>
<div class="container">
                <nav class="navbar navbar-default">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <ul class="nav navbar-nav">
                                <li><a class="navbar-brand" href="/" th:href="@{/}">Home</a></li>
                                <li><a href="/api/user/users"style="color:red;" th:href="@{/api/user/users}">USERS</a></li>
                                <li><a href="/api/team/teams"style="color:red;" th:href="@{/api/team/teams}">Teams</a></li>
                                <li><a href="/api/team/add" style="color:red;"th:href="@{/api/team/add}">Create TEAM</a></li>
                                <li><a href="/api/sprint/sprints" style="color:red;"th:href="@{api/sprint/sprints}">SPRINTS</a></li>
                            </ul>

                        </div>
                    </div>
                </nav>
         </div>
<div class="container">
   <div id="subtaskT"> <h3 id="form_header" class="text-warning" align="center">New Sub Task</h3></div>
    <div>&nbsp;</div>
    <!-- Sprint input form to add a new sprint or update the existing sprint-->
    <c:url var="saveUrl" value="/api/subtask/save" />
    <form:form id="subtask_form" modelAttribute="subtaskAttr" method="POST" action="${saveUrl}">
        <form:hidden path="id" />
        <label for="subtask_name">Enter Name: </label>
        <form:input id="subtask_name" cssClass="form-control" path="name" />
        <label for="subtask_name">Enter Status: </label>
         <select id="subtask_name" cssClass="form-control" path="status" >
                    <c:forEach items="${subtasks}" var="subtask"/>
                    <option id="${subtasks}" value="">select Status</option>
                    <option value="${subtask.taskStatus}">${subtask.taskStatus}</option>
                </select>
                <div>&nbsp; </div>
        <label for="subtask_name">Enter Start OEstimate:</label>
        <form:input id="subtask_name" cssClass="form-control" path="Estimate"/>
        <label for="subtask_name">Enter actualHours day1: </label>
        <form:input id="subtask_name"  cssClass="form-control"  path="${actualHours[0]}" />
        <label for="subtask_name">Enter actualHours day2: </label>
        <form:input id="subtask_name" cssClass="form-control" path="${actualHours[1]}" />
        <label for="subtask_name">Enter actualHours day3: </label>
        <form:input id="subtask_name" cssClass="form-control" path="${actualHours[2]}" />
        <label for="subtask_name">Enter actualHours day4: </label>
        <form:input id="subtask_name" cssClass="form-control" path="${actualHours[3]}" />
        <label for="subtask_name">Enter actualHours day5: </label>
        <form:input id="subtask_name" cssClass="form-control" path="${actualHours[4]}" />
        <div>&nbsp;</div>
        <button id="saveBtn" type="submit" class="btn btn-primary">Save</button>
        <button id="cancelBtn" style="color:blue;" type="submit" class="btn cancelBtn">Back</button>
    </form:form>
</div>


</body>
</html>
