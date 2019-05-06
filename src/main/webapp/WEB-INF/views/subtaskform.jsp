<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	    <title>Sub Task </title>
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
	        <h3 id="form_header" class="text-warning" align="center">Sub Task</h3>
	        <div>&nbsp;</div>

			<!-- Sub Task input form to add a new subtask or update the existing subtask-->
	        <c:url var="saveUrl" value="/api/subtask/save?taskid=${taskid}&sprintid=${sprintid}" />
	        <form:form id="subtask_form" modelAttribute="subtaskAttr" method="POST" action="${saveUrl}">
	        	<form:hidden path="id" />
	            <label for="subtask_name">Enter Name: </label>
	            <form:input id="subtask_name" cssClass="form-control" path="name" />
	           	<label for="subtask_name">Enter status: </label>
                <form:select id="subtask_name" cssClass="form-control" path="status">
                        <form:option value="PLANNED" label="PLANNED"/>
                        <form:option value="ONGOING" label="ONGOING"/>
                        <form:option value="REVIEW" label="REVIEW"/>
                        <form:option value="DISCARDED" label="DISCARDED"/>
                        <form:option value="DONE" label="DONE"/>
                        </form:select>
	            <label for="subtask_name">Enter OEstimate: </label>
                <form:input id="subtask_name" type="number" cssClass="form-control" path="OEstimate" />
	            <div>&nbsp;</div>
	             <label for="subtask_name">Assigned to: </label>
                	  <table id="users_table" class="table">
                           <thead>
                            	<tr align="center">
                            	  <th>Name</th>
                            	  <th colspan="2"></th>
                            	</tr>
                           </thead>
                           <tbody>
                            	<c:forEach items="${subtaskAttr.users}" varStatus="st" var="user"   >
                            	    <tr align="left">
                            	      <td><form:input path="users[${st.index}].name" cssClass="form-control" value="${user.name}"/></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].email"  value="${user.email}"/></td>
                                      <td><form:input type="hidden" path="users[${st.index}].phone"  value="${user.phone}"/></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].city"  value="${user.city}"/></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].roles"  value="${user.roles}"/></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].active"  value="${user.active}"/></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].id" value="${user.id}" /></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].username"  value="${user.username}"/></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].password"  value="${user.password}"/></td>
                            	      <td><form:input type="hidden" path="users[${st.index}].passwordConfirm"  value="${user.passwordConfirm}"/></td>
                                      <td>
                                          <c:url var="deleteUrl" value="/api/subtask/deletemember?userid=${user.id}&id=${subtask.id}&taskid=${taskAttr.id}&sprintid=${sprintid}" />
                                          <a id="delete" href="${deleteUrl}" class="btn btn-danger">Remove</a>
                                      </td>
                            	    </tr>
                            	</c:forEach>
                           </tbody>
                      </table>
                    <c:url var="AdduserUrl" value="/api/subtask/selectmember?id=${subtask.id}&taskid=${taskid}&sprintid=${sprintid}" />
                    <a id="adduser" href="${AdduserUrl}" class="btn btn-success">Add member of team</a>
                 <div>&nbsp;</div>
                 <label for="subtask_name">Actual Hours: </label>
                      <table id="actualHours_table" class="table">
                            <thead>
                                <tr align="center">
                                <th>Day</th>
                                 <th>actualHours</th>
                                 <th colspan="2"></th>
                                </tr>
                              </thead>
                            <tbody>
                               <c:forEach items="${subtaskAttr.actualHours}" varStatus="st" var="actualHour"   >
                                  <tr align="left">
                                     <td>Day ${st.index+1}</td>
                                     <td><form:input type="number" path="actualHours[${st.index}]" cssClass="form-control" value="${actualHour}"/></td>
                                    </tr>
                               </c:forEach>
                            </tbody>
                      </table>
	            <button id="saveBtn" type="submit" class="btn btn-primary">Save</button>
                <c:url var="CancelUrl" value="/api/task/edit?taskid=${taskid}&sprintid=${sprintid}" /><a id="cancel" href="${CancelUrl}" class="btn btn-danger">Cancel</a>

	        </form:form>
	    </div>
	</body>
</html>
