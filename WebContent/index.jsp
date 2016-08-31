<%
if (session == null || session.getAttribute("currentUser")==null)
	response.sendRedirect("/carpool-app/login");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="Post" uri="/WEB-INF/PostTag.tld" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Carpooling Application</title>
  <meta name="description" content="The wap project">
  <meta name="author" content="MUM">
  
	<link rel="apple-touch-icon" sizes="57x57" href="assets/img/icon/apple-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="assets/img/icon/apple-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="assets/img/icon/apple-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="assets/img/icon/apple-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="assets/img/icon/apple-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="assets/img/icon/apple-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="assets/img/icon/apple-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="assets/img/icon/apple-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="assets/img/icon/apple-icon-180x180.png">
	<link rel="icon" type="image/png" sizes="192x192"  href="assets/img/icon/android-icon-192x192.png">
	<link rel="icon" type="image/png" sizes="32x32" href="assets/img/icon/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="assets/img/icon/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="assets/img/icon/favicon-16x16.png">
	<link rel="manifest" href="assets/img/icon/manifest.json">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
  <link rel="stylesheet" type="text/css" href="/carpool-app/assets/css/main.css">
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
        		<li><a href="profile"><img src="${pageContext.request.contextPath}/assets/img/avatar.jpg" width="50px" height="50px" alt="" class="circle responsive-img valign profile-image"> </a></li>
      		</c:otherwise>
      	</c:choose>
      </ul>
      <ul class="side-nav" id="mobile-demo">
        <li><a href="sass.html">Sass</a></li>
        <li><a href="badges.html">Components</a></li>
        <li><a href="collapsible.html">Javascript</a></li>
        <li><a href="mobile.html">Mobile</a></li>
      </ul>
    </div>
  </nav>
  <!-- end nav -->
  <div>
        <div class="row">

          <div class="col s12 m4 l3">
          	<p>The weather in your city today</p>
          </div>
          <div id="post-list" class="col s12 m8 l9">
           <ul class="collection" id="posts">
           	<Post:PostTag/>
            </ul>
          </div>
        </div>
         <!-- Compose Email Trigger -->
            <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
              <a class="btn-floating btn-large red modal-trigger" href="#modal1">
                <i class="large mdi-editor-mode-edit"></i>
              </a>
            </div>

            <!-- Compose Email Structure -->
            <div id="modal1" class="modal">
              <div class="model-email-content">
              <p class="center-align"><span id="title" class="blue-text text-darken-2">Make a ride</span>
                <div class="row">                 
                  	<div class="row">
	                  	<div class="col s12">
		                      <div class="switch">
							    <label>
							      Rider
							      <input type="checkbox" name="postType" id="postType">
							      <span class="lever"></span>
							      Driver
							    </label>
							  </div>
	                  	</div>       
                  	</div>
                  	<div class="row">
                  		<div class="input-field col s12">
                  			<input name="depature" type="text" id="departure" /><label for="departure">Departure</label>
                  			<input type="hidden" name="departureLat" id="departureLat" value="" />
                  			<input type="hidden" name="departureLong" id="departureLong" value="" />
                  		</div>
                  	</div>
                  	<div class="row">
                  		<div class="input-field col s12">
                  			<input name="destination" type="text" id="destination" /><label for="destination">Destination</label>
                  			<input type="hidden" name="destinationLat" id="destinationLat" value="" />
                  			<input type="hidden" name="destinationLong" id="destinationLong" value="" />
                  		</div>
                  	</div>
                  	<div class="row">
                  		<div class="input-field col s12">
                  			<textarea id="compose" class="materialize-textarea validate" length="500" required></textarea>
	                        <label for="compose" data-error="wrong" data-success="right">Content</label>
	                        <div id="error_PostText" class="error"></div>
                  		</div>
                  	</div>
                    <div class="row right">
                      <div class="input-field col s12">
                        <button class="btn waves-effect waves-light" name="action" id="Submit_Post">Post
    					<i class="material-icons right">send</i>
  						</button>  						
                      </div>
                    </div>          
                </div>
              </div>
            </div>
          <div id="deletemodal" class="modal">              
                <div class="row">                 
                  	 <p class="center-align">Are you sure you want to delete the post?</p>                        
                </div>
                    <div class="row center-align">
                    	<a class="modal-action modal-close waves-effect waves-light btn btn-small" style="margin-left: 5px"><i class="material-icons left">close</i>Close</a>
                    	<span> </span>	
                        <a id="deletePostBTN" class="modal-action modal-close waves-effect waves-light btn btn-small red"><i class="material-icons left">delete</i>Delete</a>                    	
    				</div>
            </div>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?&key=AIzaSyB2K5mYHqHZmHKg0SIrIkmMxjGyv08APHc&libraries=places"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/post.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/main.js"></script>
  </div>
</body>
</html>