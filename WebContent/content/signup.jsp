<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Carpooling Signup</title>
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/assets/css/bootstrap-united.css" rel="stylesheet" />

<style>
.green {
	font-weight: bold;
	color: green;
}

.message {
	margin-bottom: 10px;
}

.error {
	color: #ff0000;
	font-size: 0.9em;
	font-weight: bold;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>

	<div class="navbar navbar-default">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-responsive-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>

		<div class="navbar-collapse collapse navbar-responsive-collapse">
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control" placeholder="Search">
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/carpool-app">Home</a></li>
				<li class="active"><a href="signup.jsp">Signup</a></li>
				<li><a href="login.jsp">Login</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Explore<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Contact us</a></li>
						<li class="divider"></li>
						<li><a href="#">Further Actions</a></li>
					</ul></li>
			</ul>
		</div>
		<!-- /.nav-collapse -->
	</div>

	<script src="${pageContext.request.contextPath}/jquery-1.8.3.js">
		
	</script>

	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js">
		
	</script>

	<div class="container">
		<div class="jumbotron">
			<div>
				<h1>Welcome to Carpooling Signup</h1>
				<p>Its absolutely quick!</p>
			</div>
		</div>

		<div></div>
	</div>

	<c:if test="${not empty message}">
		<div class="message green">${message}</div>
	</c:if>

	<div class="col-lg-6 col-lg-offset-3">
		<div class="well">
			<div class="container">
				<div class="row">
					<div class="col-lg-6">
						<form id="myForm" method="post" class="bs-example form-horizontal"
							action="${pageContext.request.contextPath}/user">
							<fieldset>
								<legend>Signup Form</legend>
								
								<input type="hidden" name="pageName" value="signup">

								<div class="form-group">
									<label for="email" class="col-lg-3 control-label">User
										Name</label>
									<div class="col-lg-9">
										<input type="text" class="form-control" name="email"
											id="email" placeholder="Email" />
									</div>
								</div>

								<div class="form-group">
									<label for="passwordInput" class="col-lg-3 control-label">Password</label>
									<div class="col-lg-9">
										<input type="password" class="form-control" name="password"
											id="passwordInput" placeholder="Password" />
									</div>
								</div>

								<div class="form-group">
									<label for="fullNameInput" class="col-lg-3 control-label">Full
										Name</label>
									<div class="col-lg-9">
										<input type="text" class="form-control" name="fullname"
											id="fullNameInput" placeholder="Full Name" />
									</div>
								</div>

								<div class="form-group">
									<label for="cityInput" class="col-lg-3 control-label">City</label>
									<div class="col-lg-9">
										<input type="text" class="form-control" name="city"
											id="cityInput" placeholder="City" />
									</div>
								</div>
								
								<div class="form-group">
									<label for="stateInput" class="col-lg-3 control-label">State</label>
									<div class="col-lg-9">
										<input type="text" class="form-control" name="State"
											id="cityInput" placeholder="State" />
									</div>
								</div>
								
								<div class="form-group">
									<label for="zipcodeInput" class="col-lg-3 control-label">Zipcode</label>
									<div class="col-lg-9">
										<input type="text" class="form-control" name="city"
											id="zipcodeInput" placeholder="Zipcode" />
									</div>
								</div>
								<div class="col-lg-9 col-lg-offset-3">
									<button class="btn btn-default">Cancel</button>
									<button type="submit" class="btn btn-primary">Submit</button>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>