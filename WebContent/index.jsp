<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Carpooling Application</title>
  <meta name="description" content="The wap project">
  <meta name="author" content="MUM">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <style>
    body {
      display: flex;
      min-height: 100vh;
      flex-direction: column;
    }

    main {
      flex: 1 0 auto;
    }

    body {
      background: #fff;
    }

    .input-field input[type=date]:focus + label,
    .input-field input[type=text]:focus + label,
    .input-field input[type=email]:focus + label,
    .input-field input[type=password]:focus + label {
      color: #e91e63;
    }

    .input-field input[type=date]:focus,
    .input-field input[type=text]:focus,
    .input-field input[type=email]:focus,
    .input-field input[type=password]:focus {
      border-bottom: 2px solid #e91e63;
      box-shadow: none;
    }
  </style>
</head>

<body>
  <!-- start nav -->
  <nav class="light-blue lighten-1">
    <div class="nav-wrapper">
      <a href="" class="brand-logo">Carpooling</a>
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
            Grey navigation panel

                  This content will be:
              3-columns-wide on large screens,
              4-columns-wide on medium screens,
              12-columns-wide on small screens

            </div>

            <div id="post-list" class="col s12 m8 l9">

             <ul class="collection">
                <li class="collection-item avatar email-unread selected">
                  <i class="material-icons circle green">android</i>
                  <span class="title">Title</span>
                  <p>First Line <br>
                     Second Line
                  </p>
                  <div class="secondary-content">
                    <a href="#!"><i class="material-icons">thumb_up</i></a>
                    <a href="#!"><i class="material-icons">chat_bubble</i></a>
                  </div>
                </li>
                <li class="collection-item avatar email-unread hoverable">
                  <i class="material-icons circle green">android</i>
                  <span class="title">Title</span>
                  <p>First Line <br>
                     Second Line
                  </p>
                  <a href="#!" class="secondary-content"><i class="material-icons">thumb_up</i></a>
                </li>
                <li class="collection-item avatar">
                  <i class="material-icons circle green">android</i>
                  <span class="title">Title</span>
                  <p>First Line <br>
                     Second Line
                  </p>
                  <a href="#!" class="secondary-content"><i class="material-icons">thumb_up</i></a>
                </li>
                <li class="collection-item avatar">
                  <i class="material-icons circle red">android</i>
                  <span class="title">Title</span>
                  <p>First Line <br>
                     Second Line
                  </p>
                  <a href="#!" class="secondary-content"><i class="material-icons">thumb_up</i></a>
                </li>
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
              <div class="modal-content">
                <nav class="red">
                  <div class="nav-wrapper">
                    <div class="left col s12 m5 l5">
                      <ul>
                        <li><a href="#!" class="email-menu"><i class="modal-action modal-close  mdi-hardware-keyboard-backspace"></i></a>
                        </li>
                        <li><a href="#!" class="email-type">Compose</a>
                        </li>
                      </ul>
                    </div>
                    <div class="col s12 m7 l7 hide-on-med-and-down">
                      <ul class="right">
                        <li><a href="#!"><i class="mdi-editor-attach-file"></i></a>
                        </li>
                        <li><a href="#!"><i class="modal-action modal-close  mdi-content-send"></i></a>
                        </li>
                        <li><a href="#!"><i class="mdi-navigation-more-vert"></i></a>
                        </li>
                      </ul>
                    </div>

                  </div>
                </nav>
              </div>
              <div class="model-email-content">
                <div class="row">
                  <form class="col s12">
                    <!--<div class="row">
                      <div class="input-field col s12">
                        <input id="from_email" type="email" class="validate">
                        <label for="from_email">From</label>
                      </div>
                    </div> -->
                    <div class="row">
                      <div class="input-field col s12">
                        <input id="to_email" type="email" class="validate">
                        <label for="to_email">To</label>
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s12">
                        <input id="subject" type="text" class="validate">
                        <label for="subject">Subject</label>
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-field col s12">
                        <textarea id="compose" class="materialize-textarea" length="500"></textarea>
                        <label for="compose">Compose email</label>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
  </div>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
  <script>
    (function($){
      $(function(){

        $('.button-collapse').sideNav();
        $('.modal-trigger').leanModal();
      }); // end of document ready
    })(jQuery); // end of jQuery name space
  </script>
</body>

</html>