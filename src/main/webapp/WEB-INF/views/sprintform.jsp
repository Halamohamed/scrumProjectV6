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
                                <li><a href="text-center" style="color:red;" th:href="${contextPath}/registration">Create an account</a></h4>
                            </ul>

                        </div>
                    </div>
                </nav>
         </div>
<div class="container">
    <h3 id="form_header" class="text-warning" align="center">Sprint</h3>
    <div>&nbsp;</div>
    <!-- Sprint input form to add a new sprint or update the existing sprint-->
    <c:url var="saveUrl" value="/api/sprint/save" />
    <form:form id="sprint_form" modelAttribute="sprintAttr" method="POST" action="${saveUrl}">
        <form:hidden path="id" />
        <label for="sprint_name">Enter Name: </label>
        <form:input id="sprint_name" cssClass="form-control" path="name" />

        <label for="sprint_name">Enter Goal: </label>
        <form:input id="sprint_name" cssClass="form-control" path="goal" />

        <label for="sprint_name">Enter start day: </label>
        <form:input id="sprint_name" type="date" cssClass="form-control"  path="startSprint" />

        <label for="sprint_name">Enter planned Period: </label>
        <form:input id="sprint_name" type="number" min="0" cssClass="form-control"  path="plannedPeriod" />

        <label for="sprint_name">Enter retrospective: </label>
        <form:input id="sprint_name" type="date" cssClass="form-control" path="retrospective" />

        <label for="sprint_name">Enter Demo: </label>
        <form:input id="sprint_name" type="date" cssClass="form-control" path="demo" />

        <label for="sprint_name">Enter Review: </label>
        <form:select id="sprint_name" cssClass="form-control" path="review" >
                <form:option value="MONDAY" label="MONDAY"/>
                <form:option value="TUESDAY" label="TUESDAY"/>
                <form:option value="WEDNESDAY" label="WEDNESDAY"/>
                <form:option value="THURSDAY" label="THURSDAY"/>
                <form:option value="FRIDAY" label="FRIDAY"/>
                </form:select>

        <label for="sprint_name">Enter Daily meeting: </label>
        <form:input id="sprint_name"  cssClass="form-control" path="daily_meeting" />
        <form:hidden path="tasks" />
        <div>&nbsp;</div>
        <button id="saveBtn" type="submit" class="btn btn-primary">Save</button>
        <c:url var="sprintteamUrl" value="/api/team/sprintteam?sprintid=${sprintAttr.id}" /><a id="view" href="${sprintteamUrl}" class="btn btn-info">Sprint team</a>
        <c:url var="sprinttaskUrl" value="/api/task/tasks?sprintid=${sprintAttr.id}" /><a id="viewtask" href="${sprinttaskUrl}" class="btn btn-warning">Sprint tasks</a>
        <c:url var="edithoursUrl" value="/api/sprint/actualHours?sprintid=${sprintAttr.id}" /><a id="editactuslhours" href="${edithoursUrl}" class="btn btn-success">Edit ActualHours</a>
        <c:url var="printUrl" value="/api/sprint/print?sprintid=${sprintAttr.id}" /><a id="print" href="${printUrl}" class="btn btn-default">Print</a>
        <c:url var="CancelUrl" value="/api/sprint/sprints" /><a id="cancel" href="${CancelUrl}" class="btn btn-danger">Cancel</a>
    </form:form>
</div>


</body>
</html>
