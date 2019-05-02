<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>Task form</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
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
	        <div ><h3 id="form_header" class="text-warning" align="center">Task </h3></div>
	        <div>&nbsp;</div>

			<!-- Task input form to add a new task or update the existing task-->
	        <c:url var="saveUrl" value="/api/task/save?sprintid=${sprintid}" />
	        <form:form id="task_form" modelAttribute="taskAttr" method="POST" action="${saveUrl}">
	        	<form:hidden path="id" />
	        	<label for="task_name">Enter Priority: </label>
                <form:input id="task_name" cssClass="form-control" path="priority" />
	            <label for="task_name">Enter Name: </label>
	            <form:input id="task_name" cssClass="form-control" path="name" />
	            <label for="task_name">Enter StoryPoints: </label>
                <form:input id="task_name" cssClass="form-control" path="storyPoints" />
	            <div>&nbsp;</div>
	             <label for="task_name">Sub Tasks : </label>
	                <table id="subtasks_table" class="table">
                                          <thead>
                                           <tr align="center">
                                           <th>Sub Task Name</th>
                                            <th>status</th>
                                            <th>OEstimate</th>
                                            <th colspan="3"></th>
                                              </tr>
                                               </thead>
                                               	<tbody>
                                               	    <c:forEach items="${taskAttr.subTasks}" varStatus="st" var="subTask"   >
                                               	        <tr align="left">
                                               	            <td><form:input path="subTasks[${st.index}].name" cssClass="form-control" value="${subTask.name}"/></td>
                                                            <td><form:input path="subTasks[${st.index}].status"  value="${subTask.status}"/></td>
                                               	            <td><form:input path="subTasks[${st.index}].OEstimate"  value="${subTask.OEstimate}"/></td>
                                               	            <td><form:input type="hidden" path="subTasks[${st.index}].id" value="${subTask.id}" /></td>
                                               	            <td><form:input type="hidden" path="subTasks[${st.index}].actualHours" value="${subTask.actualHours}" /></td>
                                               	            <td><form:input type="hidden" path="subTasks[${st.index}].users" value="${subTask.users}" /></td>
                                               	        </tr>
                                               	    </c:forEach>
                                               	</tbody>
                                         </table>
	            <button id="saveBtn" type="submit" class="btn btn-primary">Save</button>
	               <c:url var="addUrl" value="/api/task/subtasks?id=${taskAttr.id}&sprintid=${sprintid}" /><a id="add" href="${addUrl}" class="btn btn-info">Add subTask</a>
                   <c:url var="CancelUrl" value="/api/task/tasks?sprintid=${sprintid}" /><a id="cancel" href="${CancelUrl}" class="btn btn-danger">Cancel</a>
   <td>
              </td>
	        </form:form>
	    </div>
	</body>
</html>