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
	  <div class="container">
            <c:url var="saveUrl" value="/api/sprint/save" />
              <form:form id="sprint_form" modelAttribute="sprintAttr" method="POST" action="${saveUrl}">
                <form:hidden path="id" />
                <h3 id="form_header" class="text-warning" align="center">ActualHours for Sprint ${sprintAttr.name}</h3>
                    <form:hidden path="name" />
                                    <form:hidden path="team"  />
                                    <form:hidden path="goal" />
                                    <form:hidden path="startSprint"  />
                                    <form:hidden path="delivery" />
                                    <form:hidden path="retrospective"  />
                                    <form:hidden path="demo" />
                                    <form:hidden path="review"  />
                                    <form:hidden path="daily_meeting" />
                                    <form:hidden path="plannedPeriod"  />
                    <tbody>
                         <c:forEach items="${sprintAttr.tasks}" varStatus="spt" var="task">
                           <tr align="left">
                            <label ><font color="red">Task Name: </font></label><td><c:out value="${task.name}"/></td>
                            <td><form:hidden path="tasks[${spt.index}].id"/></td>
                                                       <td><form:hidden path="tasks[${spt.index}].name"/></td>
                                                       <td><form:hidden path="tasks[${spt.index}].priority" /></td>
                                                       <td><form:hidden path="tasks[${spt.index}].storyPoints" /></td>
                            <div>&nbsp;</div>
                              <table id="subtasks_table" class="table">
                                <tbody>
                                   <c:forEach items="${task.subTasks}" varStatus="st" var="subTask">
                                     <tr align="center">
                                       <label >SubTask Name: </label>
                                       <c:out  value="${subTask.name}"/></td>
                                         <td><form:hidden path="tasks[${spt.index}].subTasks[${st.index}].id"/></td>
                                                                              <td><form:hidden path="tasks[${spt.index}].subTasks[${st.index}].name" /></td>
                                                                              <td><form:hidden path="tasks[${spt.index}].subTasks[${st.index}].status" /></td>
                                                                              <td><form:hidden path="tasks[${spt.index}].subTasks[${st.index}].OEstimate" /></td>
                                                                              <td><form:hidden path="tasks[${spt.index}].subTasks[${st.index}].users"/></td>
                                       <table id="actualHours_table" class="table">
                                          <tbody>
                                             <c:forEach items="${subTask.actualHours}" varStatus="ah" var="actualHour">
                                               <td align="center" width= "30"> Day${ah.index+1}</td>
                                             </c:forEach>
                                          </tbody>
                                       </table>
                                       <table id="actualHours_table" class="table">
                                          <tbody>
                                             <c:forEach items="${subTask.actualHours}" varStatus="ah" var="actualHour">
                                                 <td>
                                                   <form:input align="center" style="width: 120%;" type="number" path="tasks[${spt.index}].subTasks[${st.index}].actualHours[${ah.index}]" value="${actualHour}" />
                                                 </td>
                                             </c:forEach>
                                          </tbody>
                                       </table>
                                     </tr>
                                   </c:forEach>
                                </tbody>
                               </table>
                             </tr>
                         </c:forEach>
                    </tbody>
                 </table>
                  <div>&nbsp;</div>
	            <button id="saveBtn" type="submit" class="btn btn-primary">Save</button>
	             <c:url var="CancelUrl" value="/api/sprint/edit?sprintid=${sprintAttr.id}" /><a id="cancel" href="${CancelUrl}" class="btn btn-danger">Back</a>

               </form:form>

	   </div>
     </body>
</html>