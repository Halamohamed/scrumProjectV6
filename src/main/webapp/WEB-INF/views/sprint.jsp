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
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <ul class="nav navbar-nav">
                            <li><a class="navbar-brand" href="/" th:href="@{/}">Home</a></li>
                            <li><a href="/api/user/users"style="color:red;" th:href="@{/api/user/users}">USERS</a></li>
                            <li><a href="/api/team/teams" style="color:red;"th:href="@{api/team/teams}">TEAMS</a></li>
                            <li><a href="/api/team/add" style="color:red;"th:href="@{/api/team/add}">Create Team</a></li>
                            <li><a href="/api/sprint/add"style="color:red;" th:href="@{/api/sprint/add}">Create Sprint</a></li>
                        </ul>

                    </div>
                </div>
            </nav>
     </div>
<div class="container">
    <h2 id="article_header" class="text-warning" align="center">All sprints</h2>
    <div>&nbsp;</div>

    <!-- Div to add a new sprint to the mongo database -->

    <div>&nbsp;</div>
    <!-- Table to display the sprint list from the mongo database -->
    <table id="sprint_table" class="table">
        <thead>
        <tr align="center">

            <th>Name</th>
            <th>Goal</th>
            <th>Delivery</th>
            <th>Retrospective</th>
            <th>Demo</th>
            <th>Review</th>
            <th>Daily meeting</th>
            <th colspan="2"></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sprints}" var="sprint">
            <tr align="left">
                <td><c:out value="${sprint.name}" /></td>
                <td><c:out value="${sprint.goal}" /></td>
                <td><c:out value="${sprint.delivery}" /></td>
                <td><c:out value="${sprint.retrospective}" /></td>
                <td><c:out value="${sprint.demo}" /></td>
                <td><c:out value="${sprint.review}" /></td>
                <td><c:out value="${sprint.daily_meeting}" /></td>
                <td>
                    <c:url var="editUrl" value="/api/sprint/edit?id=${sprint.id}" /><a id="update" href="${editUrl}" class="btn btn-warning">Update</a>
                </td>
                <td>
                    <c:url var="deleteUrl" value="/api/sprint/delete?id=${sprint.id}" /><a id="delete" href="${deleteUrl}" class="btn btn-danger">Delete</a>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>