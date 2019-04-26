<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>It project management</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<div class="container">
  <div th:fragment="header">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/" th:href="@{/}">Home</a>
                    <ul class="nav navbar-nav">
                        <li><a href="api/user/users" style="color:red;" th:href="@{/api/user/users}">USERS</a></li>
                        <li><a href="api/team/teams" style="color:red;"th:href="@{/api/team/teams}">TEAMS</a></li>
                        <li><a href="api/team/add" style="color:red;"th:href="@{/api/team/add}">Create Team</a></li>
                         <li><a href="api/sprint/sprints" style="color:red;"th:href="@{api/sprint/sprints}">SPRINTS</a></li>
                         <li><a href="api/sprint/add"style="color:red;" th:href="@{/api/sprint/add}">Create Sprint</a></li>
                          <li><a href="api/user/edit"style="color:red;" th:href="@{/api/user/edit}">Profile</a></li>

                         <a style="color:black;" onclick="document.forms['logoutForm'].submit()">Logout</a>
                    </ul>

                </div>
            </div>
        </nav>

        <div class="jumbotron">
            <div class="row text-center">
                <div class="">
                    <h2>IT PROJECTS MANAGEMENT</h2>
                     <h2><font color="red">Welcome ${pageContext.request.userPrincipal.name}  </h2>
                </div>
            </div>
            <div class="row text-center">
                      <img src="${pageContext.request.contextPath}/images/logo-sigma-technology.png"/>

            </div>
        </div>


    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

          <!--    <a href="/api/team/teams" target="_blank"><h4>- Teams</h4></a>
              <a href="/api/user/profile" target="_blank"><h4>- Profile</h4></a>-->
            <div>&nbsp;</div>
    </c:if>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
