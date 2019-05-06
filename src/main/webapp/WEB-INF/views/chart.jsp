<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
window.onload = function() {
var dps = [[]];
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light2", // "light1", "dark1", "dark2"
	animationEnabled: true,
	title: {
		text: "Average Sea Temperature - Amsterdam"
	},
	axisX: {
		valueFormatString: "MMM"
	},
	axisY: {
		title: "Temperature (in °C)",
		suffix: " °C"
	},
	data: [{
		type: "line",
		xValueType: "dateTime",
		xValueFormatString: "MMM",
		yValueFormatString: "#,##0 °C",
		dataPoints: dps[0]
	}]
});
var xValue;
var yValue;
<c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">
	<c:forEach items="${dataPoints}" var="dataPoint">
		xValue = parseInt("${dataPoint.x}");
		yValue = parseFloat("${dataPoint.y}");
		dps[parseInt("${loop.index}")].push({
			x : xValue,
			y : yValue
		});
	</c:forEach>
</c:forEach>
chart.render();
}
</script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
           <ul class="nav navbar-nav">
            <li><a class="navbar-brand" href="/" th:href="@{/}">Home</a></li>
            <li><a href="/api/user/users"style="color:red;" th:href="@{/api/user/users}">USERS</a></li>
            <li><a href="/api/team/teams" style="color:red;"th:href="@{api/team/teams}">TEAMS</a></li>
            <li><a href="/api/team/add" style="color:red;"th:href="@{/api/team/add}">Create Team</a></li>
            <li><a href="/api/sprint/add"style="color:red;" th:href="@{/api/sprint/add}">Create Sprint</a></li>
           </ul>

        </div>
      </div>
    </nav>
     </div>
	<div id="chartContainer" style="height: 370px; width: 100%;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>