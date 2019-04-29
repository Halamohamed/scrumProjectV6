@@ -0,0 +1,56 @@
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>Team</title>
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
                            <li><a href="/api/team/add"style="color:red;" th:href="@{/api/team/add}">Create Team</a></li>
                            <li><a href="/api/sprint/sprints"style="color:red;" th:href="@{api/sprint/sprints}">SPRINT</a></li>
                            <li><a href="/api/sprint/add" style="color:red;"th:href="@{/api/sprint/add}">Create Sprint</a></li>
                        </ul>

                    </div>
                </div>
            </nav>
     </div>
     <div class="container">
           	<h2 id="article_header" class="text-warning" align="center">All Teams</h2>

	    	<!-- Table to display the user list from the mongo database -->
	    	<table id="teams_table" class="table">
	        	<thead>
	            	<tr align="center">
	            		<th>Id</th>
	            		<th>Name</th>
	            		<th>Active</th>
	            		<th colspan="2"></th>

	            	</tr>
	        	</thead>
	        	<tbody>
	            	<c:forEach items="${teams}" var="team">
	                	<tr align="left">
	                    	<td><c:out value="${team.id}" /></td>
	                    	<td><c:out value="${team.name}" /></td>
	                    	<td><c:out value="${team.active}" /></td>
	                        <td>
	                        	<c:url var="addUrl" value="/api/sprint/addteam?id=${sprintid}&teamid=${team.id}" /><a id="add" href="${addUrl}" class="btn btn-success">Add</a>
	                    	</td>

	                	</tr>
	            	</c:forEach>
	        	</tbody>
	    	</table>
	 </div>
	</body>
</html>