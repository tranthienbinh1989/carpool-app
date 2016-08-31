<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
		rel="stylesheet">
	<link rel="stylesheet" type="text/css"
		href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/main.css" />
</head>

<body>
	<!-- start nav -->
  <nav class="light-blue lighten-1">
    <div class="nav-wrapper">
      <a href="${pageContext.request.contextPath}" class="brand-logo">Carpooling</a>
      <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
      <ul class="right hide-on-med-and-down">
      	<c:choose>
      		<c:when test="${empty sessionScope.currentUser }">
      			<li><a href="login">Login</a></li>
      		</c:when>
      		<c:otherwise>
      			<li><a href="logout">Logout</a></li>
        		<li><a href="weathermap">Weather Map</a></li>
        		<li><a href="profile"><img src="${pageContext.request.contextPath}/assets/img/avatar.jpg" width="50px" height="50px" alt="" class="circle responsive-img valign profile-image"></a></li>
      		</c:otherwise>
      	</c:choose>
      </ul>
      <ul class="side-nav" id="mobile-demo">
        <c:choose>
      		<c:when test="${empty sessionScope.currentUser }">
      			<li><a href="login">Login</a></li>
      		</c:when>
      		<c:otherwise>
      			<li><a href="logout">Logout</a></li>
        		<li><a href="weathermap">Weather Map</a></li>
        		<li><a href="profile"><img src="${pageContext.request.contextPath}/assets/img/avatar.jpg" width="50px" height="50px" alt="" class="circle responsive-img valign profile-image"></a></li>
      		</c:otherwise>
      	</c:choose>
      </ul>
    </div>
  </nav>
  <!-- end nav -->
	<div class="section"></div>
	<main>
	<center>
		<div class="container">
			<div class="row">
            <div class="col s12 m12 l12">
              <div class="card-panel z-depth-1 grey lighten-4">
              	<img style="width: 60px; height: 60px;" src="${pageContext.request.contextPath}/assets/img/logo.gif" alt="" class="responsive-img valign profile-image-login">
                <h4 class="header2">Carpooling Profile</h4>
                <div class="row">
                  <form class="col s12 m12 l12" action="profile" method="post">
                    <div class="row">
                      <div class="input-field col s6">
                        <input id="firstname" name="firstname" type="text" value="${currentUser.firstname}" />
                        <label for="firstname">First Name</label>
                      </div>
                    
                      <div class="input-field col s6">
                        <input id="lastname" name="lastname" type="text" value="${currentUser.lastname}" />
                        <label for="lastname">Last Name</label>
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s12">
                        <input id="email" name="email" type="email" value="${currentUser.email}" class="validate ${ errorEmail }" required>
                        <label for="email" data-error="Invalid" data-success="">Email</label>
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s12">
                        <input id="password" name="password" type="password" class="validate" pattern="(?=^.{6,}$)(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$">
                        <label for="password" data-error="Invalid" data-success="">Password</label> 
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s12">
                        <input id="passwordConfirm" name="passwordConfirm" type="password" class="validate" pattern="(?=^.{6,}$)(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$">
                        <label for="passwordConfirm" data-error="Invalid" data-success="">Confirm Password</label> 
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s6">
                        <select id="gender" name="gender" class="validate">
					      <option value="0">Male</option>
					      <option value="1">Female</option>
					    </select>
					    <label>Gender</label>
                      </div>       
                      <div class="input-field col s6">
                       <input id="birthYear" name="birthYear" type="number" value="${currentUser.birthyear}" min="1900" max="2016" class="validate ${ errorYear }" required>
                        <label for="birthYear" data-error="${ yearMessage }" data-success="">Year of Birth</label>
                      </div>                
                    </div>
                    <div class="row">
                      <div class="input-field col s6">
                        <input id="street" name="street" type="text" value="${currentUser.street}">
                        <label for="street">Street</label>
                      </div>
                      <div class="input-field col s6">
                        <input id="city" name="city" type="text" value="${currentUser.city}">
                        <label for="city">City</label>
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s6">
                        <input id="state" name="state" type="text" value="${currentUser.state}">
                        <label for="state">State</label>
                      </div>
                      <div class="input-field col s6">
                        <input id="zipcode" name="zipcode" type="text" value="${currentUser.zipcode}" pattern="(\d{5}([\-]\d{4})?)" class="validate">
                        <label for="zipcode">Zipcode</label>
                      </div>
                    </div>
					<div class="row">
			          <div class="input-field col s12">
			            <button type='submit' name='btn_register'
								class='btn waves-effect waves-light col s12'>Update</button>
			          </div>
			        </div>
                  </form>
                </div>
              </div>
            </div>
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
	<script>
		$(document).ready(function(){
			var gender = ${currentUser.gender};
			$("#gender").val(gender);
			$('select').material_select();
			var password = document.getElementById("password")
			  , confirm_password = document.getElementById("passwordConfirm");

			function validatePassword(){
			  if(password.value != confirm_password.value) {
			    confirm_password.setCustomValidity("Passwords Don't Match");
			  } else {
			    confirm_password.setCustomValidity('');
			  }
			}

			password.onchange = validatePassword;
			confirm_password.onkeyup = validatePassword;
		});
	</script>
</body>
</html>