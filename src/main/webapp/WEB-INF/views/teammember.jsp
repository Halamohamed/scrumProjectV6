<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>User</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	</head>
	<body>
		<div class="container">
			<h2 id="article_header" class="text-warning" align="center">All teams members</h2>
	    	<div>&nbsp;</div>

	    	<!-- Div to add a new user to the mongo database -->
	    	<div id="add_new_user">
	    			<c:url var="addUrl" value="/api/team/detail/list" /><a id="add" href="${addUrl}" class="btn btn-success">Add user</a>
	    	</div>
	    	<div>&nbsp;</div>

	    	<!-- Table to display the user list from the mongo database -->
	    	<table id="members_table" class="table">
	        	<thead>
	            	<tr align="center">
	            		<th>Id</th>
	            		<th>Name</th>
	            		<th colspan="2"></th>

	            	</tr>
	        	</thead>
	        	<tbody>
	            	<c:forEach items="${members}" var="user">
	                	<tr align="center">
	                    	<td><c:out value="${user.id}" /></td>
	                    	<td><c:out value="${user.name}" /></td>
	                    	<td>
	                        	<c:url var="deleteUrl" value="/api/user/delete?id=${user.id}" /><a id="delete" href="${deleteUrl}" class="btn btn-danger">Delete</a>
	                    	</td>
	                	</tr>
	            	</c:forEach>
	        	</tbody>
	    	</table>
		</div>
	</body>
</html>