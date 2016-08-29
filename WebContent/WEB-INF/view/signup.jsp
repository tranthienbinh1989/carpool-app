<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
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
		<div class="container">
			<div class="z-depth-1 grey lighten-4 row"
				style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">

				<form class="login-form">
					<input type="hidden" name="pageName" value="login" />
			        <div class="row">
			          <div class="input-field col s12 center">
			            <img style="width: 60px; height: 60px;" src="${pageContext.request.contextPath}/assets/img/logo.gif" alt="" class="responsive-img valign profile-image-login">
			            <p class="center login-form-text">Carpooling Application</p>
			          </div>
			        </div>
			        <div class="row margin">
			          <div class="input-field col s12">
			            <i class="mdi-social-person-outline prefix"></i>
			            <input id="username" type="text" class="validate">
			            <label for="username" class="center-align">Username</label>
			          </div>
			        </div>
			        <div class="row margin">
			          <div class="input-field col s12">
			            <i class="mdi-communication-email prefix"></i>
			            <input id="email" type="email" class="validate">
			            <label for="email" class="center-align">Email</label>
			          </div>
			        </div>
			        <div class="row margin">
			          <div class="input-field col s12">
			            <i class="mdi-action-lock-outline prefix"></i>
			            <input id="password" type="password" class="validate">
			            <label for="password">Password</label>
			          </div>
			        </div>
			        <div class="row margin">
			          <div class="input-field col s12">
			            <i class="mdi-action-lock-outline prefix"></i>
			            <input id="password-again" type="password">
			            <label for="password-again">Re-type password</label>
			          </div>
			        </div>
			        <div class="row">
			          <div class="input-field col s12">
			            <a href="register.html" class="btn waves-effect waves-light col s12">Register Now</a>
			          </div>
			          <div class="input-field col s12">
			            <p class="margin center medium-small sign-up">Already have an account? <a href="${pageContext.request.contextPath}/content/login.jsp">Login</a></p>
			          </div>
			        </div>
			      </form>
			</div>
		</div>
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