<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>Create Team</title>
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
                                <li><a href="/api/team/teams" style="color:red;"th:href="@{/api/team/teams}">Teams</a></li>
                                <li><a href="/api/sprint/sprints"style="color:red;" th:href="@{api/sprint/sprints}">SPRINTS</a></li>
                                <li><a href="/api/sprint/add"style="color:red;" th:href="@{/api/sprint/add}">Create Sprint</a></li>
                            </ul>

                        </div>
                    </div>
                </nav>
         </div>
	    <div class="container">
	        <h3 id="form_header" class="text-warning" align="center"> Team</h3>
	        <div>&nbsp;</div>

			<!-- Team input form to add a new team or update the existing team-->
	        <c:url var="saveUrl" value="/api/team/save" />
	        <form:form id="team_form" modelAttribute="teamAttr"   method="POST" action="${saveUrl}">
	        	<form:hidden path="id" />
	            <label for="team_name">Enter Name: </label>
	            <form:input id="team_name" cssClass="form-control" path="name" />
	            <label for="team_name">Team Activity: </label>
                <form:input id="team_name" cssClass="form-control" path="Active" />
	            <div>&nbsp;</div>
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
            	                	<tr align="left">
            	                    	<td><c:out value="${user.id}" /></td>
            	                    	<td><c:out value="${user.name}" /></td>
            	                    	<td>
            	                        	<c:url var="deleteUrl" value="/api/team/deletemember?id=${user.id}" /><a id="delete" href="${deleteUrl}" class="btn btn-danger">Remove</a>
            	                    	</td>
            	                	</tr>
            	            	</c:forEach>
            	        	</tbody>
            	 </table>
            	<button  id="saveBtn" type="submit" class="btn btn-primary">Save</button>
            	</form:form>
	    </div>
	</body>
</html>