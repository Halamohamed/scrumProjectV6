<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>User</title> <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet"></head>
	<body>
	 <div class="container">
                <nav class="navbar navbar-default">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <ul class="nav navbar-nav">
                                <li><a class="navbar-brand" href="/" th:href="@{/}">Home</a></li>
                                <li><a href="/api/team/teams" style="color:red;"th:href="@{/api/team/teams}">TEAMS</a></li>
                                <li><a href="/api/team/add" style="color:red;"th:href="@{/api/team/add}">Create Team</a></li>
                                 <li><a href="/api/sprint/sprints"style="color:red;" th:href="@{/api/sprint/sprints}">SPRINT</a></li>
                                 <li><a href="/api/sprint/add" style="color:red;"th:href="@{/api/sprint/add}">Create Sprint</a></li>
                            </ul>

                        </div>
                    </div>
                </nav>
         </div>
		<div class="container">
			<h2 id="article_header" class="text-warning" align="center">All Users</h2>
	    	<div>&nbsp;</div>
			
	    	<!-- Table to display the user list from the mongo database -->
	    	<table id="users_table" class="table">
	        	<thead>
	            	<tr align="center">
	            		<th>Id</th>
	            		<th>Name</th>
	            		<th>Email</th>
	            		<th>phone</th>
	            		<th>city</th>
	            		<th>active</th>
	            		<th colspan="2"></th>

	            	</tr>
	        	</thead>
	        	<tbody>
	            	<c:forEach items="${users}" var="user">
	                	<tr align="left">
	                    	<td><c:out value="${user.id}" /></td>
	                    	<td><c:out value="${user.name}" /></td>
	                    	<td><c:out value="${user.email}" /></td>
	                    	<td><c:out value="${user.phone}" /></td>
                            <td><c:out value="${user.city}" /></td>
                            <td><c:out value="${user.active}" /></td>
	                    	<td>
	                        	<c:url var="editUrl" value="/api/user/edit?id=${user.id}" /><a id="update" href="${editUrl}" class="btn btn-warning">Update</a>
	                    	</td>
	                    	<td>
	                        	<c:url var="deleteUrl" value="/api/user/delete?id=${user.id}" /><a id="delete" href="${deleteUrl}" class="btn btn-danger">Dis/Enabled</a>
	                    	</td>
	                	</tr>
	            	</c:forEach>
	        	</tbody>
	    	</table>
		</div>	    
	</body>
</html>