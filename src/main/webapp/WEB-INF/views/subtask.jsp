<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Sub Task</title>
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
			<h2 id="article_header" class="text-warning" align="center">Sub tasks</h2>
	    	<div>&nbsp;</div>

	    	<!-- Div to add a new sub task to the mongo database -->
	    	<div id="add_new_subtask">
	    			<c:url var="addUrl" value="/api/subtask/add?sprintid=${sprintid}" /><a id="add" href="${addUrl}" class="btn btn-success">Add subtask</a>
	    	</div>
	    	<div>&nbsp;</div>

	    	<!-- Table to display the sub task list from the mongo database -->
	    	<table id="subtasks_table" class="table">
	        	<thead>
	            	<tr align="center">
	            		<th>priority</th>
	            		<th>Name</th>
	            		<th>StoryPoints</th>
	            		<th colspan="2"></th>

	            	</tr>
	        	</thead>
	        	<tbody>
	            	<c:forEach items="${subtasks}" var="subtask">
	                	<tr align="left">
	                	     <form:hidden path="id" />
	                    	<td><c:out value="${subtask.priority}" /></td>
	                    	<td><c:out value="${subtask.name}" /></td>
	                    	<td><c:out value="${subtask.storyPoints}" /></td>


	                    	<td>
	                        	<c:url var="editUrl" value="/api/subtask/edit?id=${subtask.id}&sprintid=${sprintid}" /><a id="update" href="${editUrl}" class="btn btn-warning">Update</a>
	                    	</td>
	                    	<td>
	                        	<c:url var="deleteUrl" value="/api/subtask/delete?id=${subtask.id}&sprintid=${sprintid}" /><a id="delete" href="${deleteUrl}" class="btn btn-danger">Delete</a>
	                    	</td>

	                	</tr>
	            	</c:forEach>
	        	</tbody>
	    	</table>
	    	  <c:url var="CancelUrl" value="/api/sprint/edit?sprintid=${sprintid}" /><a id="cancel" href="${CancelUrl}" class="btn btn-danger">Back</a>
		</div>
	</body>
</html>