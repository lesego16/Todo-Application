<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="Bootstrap/bootstrap.min.css">
<title>Register | Todo App</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">

		<h2>User Register Form</h2>

		<div class="col-md-6">

				<p style="color: red">${ NOTIFICATION }</p>
				<div style="color: green">${ success }</div>

			<form action="<%= request.getContextPath() %>/register" method="post">

				<div class="form-group">
					<label for="uname">First Name:</label> <input type="text"
						class="form-control" id="uname" placeholder="First Name"
						name="firstname">
				</div>

				<div class="form-group">
					<label for="uname">Last Name:</label> <input type="text"
						class="form-control" id="uname" placeholder="Last Name"
						name="lastname">
				</div>

				<div class="form-group">
					<label for="uname">Username:</label> <input type="text"
						class="form-control" id="uname" placeholder="Username"
						name="username">
				</div>

				<div class="form-group">
					<label for="uname">Password:</label> <input type="password"
						class="form-control" id="uname" placeholder="Password"
						name="password">
				</div>

				<button type="submit" class="btn btn-primary">Submit</button>

			</form>

		</div>

	</div>

	<jsp:include page="footer.jsp"></jsp:include>

	<script src="Bootstrap/bootstrap.min.js"></script>
</body>
</html>