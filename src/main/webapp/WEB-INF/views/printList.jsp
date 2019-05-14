<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <title>ActualHours Table </title>
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
	  <div class="containerprint">
            <c:url var="saveUrl" value="/api/sprint/save" />
              <form:form id="sprint_form" modelAttribute="sprintAttr" method="POST" action="${saveUrl}">
                <form:hidden path="id" />
                 <h3 id="form_header" class="text-warning" align="center">All Task and subTask of Sprint ${sprintAttr.name}</h3>
                    <tbody>
                         <c:forEach items="${sprintAttr.tasks}" varStatus="spt" var="task">
                           <tr align="left">
                            <div>&nbsp;</div>
                              <table id="subtasks_table" class="table">
                                <tbody>
                                  <c:forEach items="${task.subTasks}" varStatus="st" var="subTask">
                                    <td width="200" height="200"><font size="3" >Task: <c:out value="${task.name}"/>
                                       <div>&nbsp;</div>
                                          ${st.index+1}.SubTask: <c:out value="${subTask.name}"/>
                                         <c:forEach items="${subTask.users}" varStatus="us" var="user">
                                         <div>&nbsp;</div>
                                          Assigned to : <c:out value="${user.name}"/>
                                         </c:forEach>
                                         <div>&nbsp;</div>
                                          Status : <c:out value="${subTask.status}"/>
                                         <div>&nbsp;</div>
                                          OEstimate : <c:out value="${subTask.OEstimate}"/>
                                    </td>
                                  </c:forEach>
                                </tbody>
                               </table>
                         </c:forEach>
                      </form:form>
                      </tbody>
    </body>
</html>