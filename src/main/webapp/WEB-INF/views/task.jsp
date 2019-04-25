<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Task</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	</head>
	<body>
		<div class="container">
			<h2 id="article_header" class="text-warning" align="center">All tasks</h2>
	    	<div>&nbsp;</div>

	    	<!-- Div to add a new task to the mongo database -->
	    	<div id="add_new_task">
	    			<c:url var="addUrl" value="/api/task/add" /><a id="add" href="${addUrl}" class="btn btn-success">Add task</a>
	    	</div>
	    	<div>&nbsp;</div>

	    	<!-- Table to display the task list from the mongo database -->
	    	<table id="tasks_table" class="table">
	        	<thead>
	            	<tr align="center">
	            		<th>Id</th>
	            		<th>priority</th>
	            		<th>Name</th>
	            		<th>StoryPoints</th>
	            		<th colspan="2"></th>

	            	</tr>
	        	</thead>
	        	<tbody>
	            	<c:forEach items="${tasks}" var="task">
	                	<tr align="center">
	                    	<td><c:out value="${task.id}" /></td>
	                    	<td><c:out value="${task.priority}" /></td>
	                    	<td><c:out value="${task.name}" /></td>
	                    	<td><c:out value="${task.storyPoints}" /></td>

	                    	<td>
	                        	<c:url var="editUrl" value="/api/task/edit?id=${task.id}" /><a id="update" href="${editUrl}" class="btn btn-warning">Update</a>
	                    	</td>
	                    	<td>
	                        	<c:url var="deleteUrl" value="/api/task/delete?id=${task.id}" /><a id="delete" href="${deleteUrl}" class="btn btn-danger">Delete</a>
	                    	</td>
	                    	<td>
                             <c:url var="detailUrl" value="/api/task/detail?id=${task.id}" /><a id="detail" href="${detailUrl}" class="btn btn-warning">Sub Tasks</a>
                           </td>
	                	</tr>
	            	</c:forEach>
	        	</tbody>
	    	</table>
		</div>
	</body>
</html>