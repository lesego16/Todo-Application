<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="Bootstrap/bootstrap.min.css">
<title>User Management Application</title>
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: #c2882b">

			<div>
				<a href="#" class="navbar-brand">Todo App</a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%= request.getContextPath() %>/list"
					class="nav-link">Todos</a></li>
			</ul>

			<ul class="navbar-nav navbar-collapse justify-content-end">
				<li><a href="<%= request.getContextPath() %>/logout"
					class="nav-link">Logout</a></li>
			</ul>

		</nav>
	</header>

	<div class="container col-md">
		<div class="card">
			<div class="card-body">
				<c:if test="${todo != null }">
					<form action="update" method="post">
				</c:if>
				<c:if test="${todo == null }">
					<form action="insert" method="post">
				</c:if>



				<caption>
					<h2>
						<c:if test="${todo != null }">
							Edit Todo
						</c:if>
						<c:if test="${todo == null }">
							Add New Todo
						</c:if>
					</h2>
				</caption>
				
				<c:if test="${ todo != null }">
					<input type="hidden" name="id" value="<c:out value="${ todo.id }"></c:out>">
				</c:if>
				
				<p style="color: red">${ errorTitleUserDescription }</p>
				<fieldset class="form-group">
					<label for="title">Todo Title</label> <input type="text"
						value="<c:out value="${ todo.title }"></c:out>" id="title"
						class="form-control" name="title">
				</fieldset>

				<fieldset class="form-group">
					<label for="uname">Todo Username</label> <input type="text"
						value="<c:out value="${ todo.username }"></c:out>" id="uname"
						class="form-control" name="username">
				</fieldset>

				<fieldset class="form-group">
					<label for="description">Todo Description</label> <input
						type="text" value="<c:out value="${ todo.description }"></c:out>"
						id="description" class="form-control" name="description">
				</fieldset>

				<fieldset class="form-group">
					<label for="status">Todo Status</label> <select
						class="form-control" id="status" name="isDone">
						<option value="false">In Progress</option>
						<option value="true">Complete</option>
					</select>
				</fieldset>
				
				<p style="color: red">${ errorDate }</p>
				<fieldset class="form-group">
					<label for="date">Todo Target Date</label> <input type="text"
						value="<c:out value="${ todo.targetDate }"></c:out>" id="date"
						class="form-control" name="targetDate">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>

			</div>
		</div>
	</div>

	<script src="Bootstrap/bootstrap.min.js"></script>
</body>
</html>

