<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
		rel="stylesheet">
	<link rel="stylesheet" type="text/css"
		href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css" />
</head>

<body>
	<div class="section"></div>
	<main>
	<center>
		<img class="responsive-img" style="width: 250px;"
			src="https://media.giphy.com/media/26ufjLH9RMApqfQ0E/giphy.gif" />
		<div class="section"></div>

		<h5 class="indigo-text">Please, login into your account</h5>
		<div class="section"></div>

		<div class="container">
			<div class="z-depth-1 grey lighten-4 row"
				style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">

				<form class="col s12" method="post" action="${pageContext.request.contextPath}/login">
					<div class='row'>
						<div class='col s12'></div>
					</div>
					<div class='row'>
						<div class='input-field col s12'>
							<i class="mdi-social-person-outline prefix"></i>
							<input class='validate ${ validClass }' type='email' name='email' id='email' />
							<label for='email' data-error="Invalid" data-success="">Enter your email</label>
						</div>
					</div>
					<div class='row'>
						<div class='input-field col s12'>
							<i class="mdi-action-lock-outline prefix"></i>
							<input class='validate ${ validClass }' type='password' name='password'
								id='password' />
							<label for='password' data-error="" data-success="">Enter your password</label>
						</div>
						<label style='float: right;'> <a class='pink-text'
							href='#!'><b>Forgot Password?</b></a>
						</label>
					</div>
					
					<br />
					<center>
						<div class='row'>
							<button type='submit' name='btn_login'
								class='col s12 btn btn-large waves-effect indigo'>Login</button>
						</div>
					</center>
				</form>
			</div>
		</div>
		<a href="${pageContext.request.contextPath}/register">Create account</a>
	</center>

	<div class="section"></div>
	<div class="section"></div>
	</main>

	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
</body>
</html>