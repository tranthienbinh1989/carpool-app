(function($){
      $(function(){
        $('.button-collapse').sideNav();
        $('.modal-trigger').leanModal();
        $('#depature').on('focus', geolocate);
        $('#destination').on('focus', geolocate);
        
        $('ul.tabs').tabs();
        
    function initAutocomplete() {
        // Create the autocomplete object, restricting the search to geographical
        // location types.
        autocompleteDeparture = new google.maps.places.Autocomplete(
            (document.getElementById('departure')),
            {types: ['geocode']});
        autocompleteDestination = new google.maps.places.Autocomplete(
                (document.getElementById('destination')),
                {types: ['geocode']});
        autocompleteDeparture.addListener('place_changed', function() {
            var place = autocompleteDeparture.getPlace();
            if (!place.geometry) {
              window.alert("Autocomplete's returned place contains no geometry");
              return;
            }
            $("#departureLat").val(place.geometry.location.lat());
            $("#departureLong").val(place.geometry.location.lng());
            
        });
        
        autocompleteDestination.addListener('place_changed', function() {
            var place = autocompleteDestination.getPlace();
            if (!place.geometry) {
              window.alert("Autocomplete's returned place contains no geometry");
              return;
            }
            $("#destinationLat").val(place.geometry.location.lat());
            $("#destinationLong").val(place.geometry.location.lng());
            
        });
      }
     // Bias the autocomplete object to the user's geographical location,
        // as supplied by the browser's 'navigator.geolocation' object.
        function geolocate() {
          if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
              var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
              };
              var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
              });
              autocompleteDeparture.setBounds(circle.getBounds());
              autocompleteDestination.setBounds(circle.getBounds());
            });
          }
        }
        google.maps.event.addDomListener(window, 'load', initAutocomplete);
    }); // end of document ready
})(jQuery); // end of jQuery name space