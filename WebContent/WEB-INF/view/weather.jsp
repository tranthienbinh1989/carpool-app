<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="Post" uri="/WEB-INF/PostTag.tld" %>
<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Carpooling Application - Weather Map</title>
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
  <script>
  	var street = "${currentUser.street}";
  	var city = "${currentUser.city}";
  	var state = "${currentUser.state}";
  	var zipcode = "${currentUser.zipcode}";
  </script>
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
        		<li><a href="ridemap">Ride Map</a></li>
        		<li><a href="profile"><img src="${pageContext.request.contextPath}/assets/img/avatar.jpg" width="50px" height="50px" alt="" class="circle responsive-img valign profile-image"> </a></li>
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
        		<li><a href="ridemap">Ride Map</a></li>
        		<li><a href="profile"><img src="${pageContext.request.contextPath}/assets/img/avatar.jpg" width="50px" height="50px" alt="" class="circle responsive-img valign profile-image"> </a></li>
      		</c:otherwise>
      	</c:choose>
      </ul>
    </div>
  </nav>
  <!-- end nav -->
  <div>
  	<div class="row">
  	
        <div class="col s12 m6 l5 weathermap">
        <h5 id="city"></h5>
        <table class="striped responsive-table" id="forecastTable">
        <thead>
          <tr>
              <th data-field="time">TIME</th>
              <th data-field="description">DESCRIPTION</th>
              <th data-field="temp">TEMP</th>
              <th data-field="precip">PRECIP</th>
              <th data-field="hudimity">HUDIMITY</th>
              <th data-field="wind">WIND</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
            
        </div>
        <div class="col s12 m6 l7">
        	<input id="pac-input" class="controls" type="text" placeholder="Search Box">
        	<div id="map"></div>
        </div>
    </div>
  </div>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?&key=AIzaSyB2K5mYHqHZmHKg0SIrIkmMxjGyv08APHc&libraries=places"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/weather.js"></script>
</body>
</html>