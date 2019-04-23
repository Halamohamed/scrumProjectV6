<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Sprint form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
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
    <h3 id="form_header" class="text-warning" align="center">New Sprint</h3>
    <div>&nbsp;</div>
    <!-- Sprint input form to add a new sprint or update the existing sprint-->
    <c:url var="saveUrl" value="/api/sprint/save" />
    <form:form id="sprint_form" modelAttribute="sprintAttr" method="POST" action="${saveUrl}">
        <form:hidden path="id" />
        <label for="sprint_name">Enter Name: </label>
        <form:input id="sprint_name" cssClass="form-control" path="name" />
        <label for="sprint_name">Enter Goal: </label>
        <form:input id="sprint_name" cssClass="form-control" path="goal" />
        <label for="sprint_name">Enter Delivery: </label>
        <form:input id="sprint_name"  cssClass="form-control"  path="delivery" />
        <label for="sprint_name">Enter Retrospective: </label>
        <form:input id="sprint_name" cssClass="form-control" path="retrospective" />
        <label for="sprint_name">Enter Demo: </label>
        <form:input id="sprint_name" cssClass="form-control" path="demo" />
        <label for="sprint_name">Enter Review: </label>
        <form:input id="sprint_name" cssClass="form-control" path="review" />
        <label for="sprint_name">Enter Daily meeting: </label>
        <form:input id="sprint_name" cssClass="form-control" path="daily_meeting" />
        <div>&nbsp;</div>
        <button id="saveBtn" type="submit" class="btn btn-primary">Save</button>
        <c:url var="selectUrl" value="/api/sprint/detail/list" />

            <button id="selectBtn" type="submit" class="btn btn-primary">select team</button>
        <select>
            <c:forEach items="${teams}" var="team"/>
            <option value="${team.name}">${team.name}</option>

            <option value="${team.name}">${team.name}</option>
        </select>
        <a href="/api/task/tasks" target="_blank"><h4>-Tasks</h4></a>


    </form:form>
</div>


</body>
</html>
