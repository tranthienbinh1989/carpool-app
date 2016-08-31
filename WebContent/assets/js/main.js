(function($){
      $(function(){
        $('.button-collapse').sideNav();
        $('.modal-trigger').leanModal();
        
     // Determine the correct object to use
        var notification = window.Notification || window.mozNotification || window.webkitNotification;

        // The user needs to allow this
        if ('undefined' === typeof notification) {
        	alert('Web notification not supported');
        } else {
        	notification.requestPermission(function(permission){});
        }
        $("#notify").click(notifyMe);
        function notifyMe() {
          if (!("Notification" in window)) {
            alert("This browser does not support desktop notification");
          }
          else if (Notification.permission === "granted") {
                var options = {
                        body: "This is the body of the notification",
                        icon: "assets/img/icon.png",
                        dir : "ltr"
                     };
                  var notification = new Notification("Carpooling",options);
          }
          else if (Notification.permission !== 'denied') {
            Notification.requestPermission(function (permission) {
              if (!('permission' in Notification)) {
                Notification.permission = permission;
              }
            
              if (permission === "granted") {
                var options = {
                      body: "This is the body of the notification",
                      icon: "assets/img/icon.png",
                      dir : "ltr"
                  };
                var notification = new Notification("Carpooling",options);
              }
            });
          }
        };
//        window.setInterval(function(){
//        	notifyMe();
//        }, 5000);
    }); // end of document ready
})(jQuery); // end of jQuery name space