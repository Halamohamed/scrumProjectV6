<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Sub Tasks</title>
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
    <h2 id="article_header" class="text-warning" align="center">All Sub Tasks</h2>
    <div>&nbsp;</div>

   <!-- Div to add a new sub task to the mongo database -->
   	    	<div id="add_new_subtask">
   	    			<c:url var="addUrl" value="/api/subtask/add" /><a id="add" href="${addUrl}" class="btn btn-success">Add sub task</a>
   	    	</div>
   	    	<div>&nbsp;</div>
    <!-- Table to display the sub task list from the mongo database -->
    <table id="subtask_table" class="table">
        <thead>
        <tr align="center">

            <th>Name</th>
            <th>User</th>
            <th>Status</th>
            <th>Original Estimate</th>
            <th>Actual Hours day1</th>
            <th> day2</th>
            <th> day3</th>
            <th> day4</th>
            <th> day5</th>
            <th> day6</th>
            <th> day7</th>

            <th colspan="2"></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${subtasks}" var="subtask">
            <tr align="left">
                <td><c:out value="${subtask.name}" /></td>
                <td><c:out value="${subtask.users[users.name]}" /></td>
                <td><c:out value="${subtask.status}" /></td>
                <td><c:out value="${subtask.OEstimate}" /></td>
                <td><c:out value="${subtask.actualHours[0]}" /></td>
                <td><c:out value="${subtask.actualHours[1]}" /></td>
                <td><c:out value="${subtask.actualHours[2]}" /></td>
                <td><c:out value="${subtask.actualHours[3]}" /></td>
                <td><c:out value="${subtask.actualHours[4]}" /></td>
                 <td><c:out value="${subtask.actualHours[5]}" /></td>
                 <td><c:out value="${subtask.actualHours[6]}" /></td>
                <td>
                    <c:url var="editUrl" value="/api/subtask/edit?id=${subtask.id}" /><a id="update" href="${editUrl}" class="btn btn-warning">Update</a>
                </td>
                <td>
                    <c:url var="deleteUrl" value="/api/subtask/delete?id=${subtask.id}" /><a id="delete" href="${deleteUrl}" class="btn btn-danger">Delete</a>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>