<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="Bootstrap/bootstrap.min.css">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: #c2882b">
			<div>
				<a href="#" class="navbar-brand">Todo App</a>
			</div>

			<ul class="navbar-nav navbar-collapse justify-content-end">
				<li><a style="margin-right: 10px"
					href="<%= request.getContextPath() %>/login">Login</a>
				<li><a href="<%= request.getContextPath() %>/register">Register</a>
			</ul>
		</nav>
	</header>

	<script src="Bootstrap/bootstrap.min.js"></script>
</body>
</html>