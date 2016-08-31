$(function(){
	$('.button-collapse').sideNav();
    var address = street + " " + city + " " + state + " " + zipcode;
	var map;
	var geoJSON;
	var request;
	var gettingData = false;
	var openWeatherMapKey = "1b8cdd202c89abaeecade9a428b46b61";
	var action = "getFrom";
	function initialize() {
		  var mapOptions = {
		    zoom: 13,
		    center: new google.maps.LatLng(50,-50)
		  };

		  
		  map = new google.maps.Map(document.getElementById('map'),
		      mapOptions);
		  
		  
		  if((zipcode != 0 && zipcode != "") && street != "" && city != "" && state != "") {
			  geocoder = new google.maps.Geocoder();
			    geocoder.geocode({
			        'address': address
			    }, function(results, status) {
			        if (status == google.maps.GeocoderStatus.OK) {
			        	console.log(address);
			        	map.setCenter(results[0].geometry.location);
			        }
			    });
		  } else {
			//set center to current location
			  if (navigator.geolocation) {
				     navigator.geolocation.getCurrentPosition(function (position) {
				         initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
				         map.setCenter(initialLocation);
				     });
			   }
		  }
		  
		// Create the search box and link it to the UI element.
	      var input = document.getElementById('pac-input');
	      var types = document.getElementById('type-selector');
	      var searchBox = new google.maps.places.SearchBox(input);
	      map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	      map.controls[google.maps.ControlPosition.TOP_LEFT].push(types);
	      
	      // Bias the SearchBox results towards current map's viewport.
	      map.addListener('bounds_changed', function() {
	        searchBox.setBounds(map.getBounds());
	      });

	      var markers = [];
	      // Listen for the event fired when the user selects a prediction and retrieve
	      // more details for that place.
	      searchBox.addListener('places_changed', function() {
	        var places = searchBox.getPlaces();

	        if (places.length == 0) {
	          return;
	        }
        	
	        // Clear out the old markers.
	        markers.forEach(function(marker) {
	          marker.setMap(null);
	        });
	        markers = [];
	        
	        // For each place, get the icon, name and location.
	        var bounds = new google.maps.LatLngBounds();
	        places.forEach(function(place) {
	          if (!place.geometry) {
	            console.log("Returned place contains no geometry");
	            return;
	          }
	          var icon = {
	            url: place.icon,
	            size: new google.maps.Size(71, 71),
	            origin: new google.maps.Point(0, 0),
	            anchor: new google.maps.Point(17, 34),
	            scaledSize: new google.maps.Size(25, 25)
	          };

	          // Create a marker for each place.
//	          markers.push(new google.maps.Marker({
//	            map: map,
//	            icon: icon,
//	            title: place.name,
//	            position: place.geometry.location
//	          }));

	          if (place.geometry.viewport) {
	            // Only geocodes have viewport.
	            bounds.union(place.geometry.viewport);
	          } else {
	            bounds.extend(place.geometry.location);
	          }
	        });
	        map.fitBounds(bounds);
	        checkIfDataRequested();
	      });
	      
	      
	   // Add interaction listeners to make weather requests
		  google.maps.event.addListener(map, 'idle', checkIfDataRequested);

		  // Sets up and populates the info window with details
		  map.data.addListener('click', function(event) {
		    infowindow.setContent(
		     "<img src=" + event.feature.getProperty("icon") + ">"
		     + "<br /><strong>" + event.feature.getProperty("fullname") + "</strong>"
		     + "<br />" + event.feature.getProperty("postContent")
		     );
		    infowindow.setOptions({
		        position:{
		          lat: event.latLng.lat(),
		          lng: event.latLng.lng()
		        },
		        pixelOffset: {
		          width: 0,
		          height: -15
		        }
		      });
		    infowindow.open(map);
		  });
	}
	
	

	var checkIfDataRequested = function() {
	  // Stop extra requests being sent
	  while (gettingData === true) {
	    request.abort();
	    gettingData = false;
	  }
	  getCoords();
	};

	// Get the coordinates from the Map bounds
	var getCoords = function() {
	  var latlng = map.getCenter();
	  console.log(latlng.lat());
	  console.log(latlng.lng());
	  getTrips(latlng.lat(), latlng.lng());
	};
	
	
	// Make the weather request
	var getTrips = function(lat, lng) {
	  gettingData = true;
	  var getRideUrl = "ridemap";
		request = $.post(getRideUrl, {
			'action' : action,
			'fromLat' : lat,
			'fromLong' : lng
		}).done(proccessResults).fail(function() {
			
		});
	};
	
	// Take the JSON results and proccess them
	var proccessResults = function(data) {
      resetData();
      for (var i = 0; i < data.length; i++) {
        geoJSON.features.push(jsonToGeoJson(data[i]));
      }
      drawIcons(geoJSON);
	};

	var infowindow = new google.maps.InfoWindow();

	// For each result that comes back, convert the data to geoJSON
	var jsonToGeoJson = function (postItem) {
	  var feature = {
	    type: "Feature",
	    properties: {
	      fullname: postItem.fullname,
	      postId: postItem.postId,
	      postType: postItem.postType,
	      postContent: postItem.postContent,
	      icon: "/carpool-app/assets/img/icon.png",
	      coordinates: [Number(postItem.fromLong), Number(postItem.fromLat)]
	    },
	    geometry: {
	      type: "Point",
	      coordinates: [Number(postItem.fromLong), Number(postItem.fromLat)]
	    }
	  };
	  // Set the custom marker icon
	  map.data.setStyle(function(feature) {
	    return {
	      icon: {
	        url: "/carpool-app/assets/img/marker.png",
	        anchor: new google.maps.Point(25, 25)
	      }
	    };
	  });

	  // returns object
	  return feature;
	};

	// Add the markers to the map
	var drawIcons = function () {
	   map.data.addGeoJson(geoJSON);
	   // Set the flag to finished
	   gettingData = false;
	};

	// Clear data layer and geoJSON
	var resetData = function () {
	  geoJSON = {
	    type: "FeatureCollection",
	    features: []
	  };
	  map.data.forEach(function(feature) {
	    map.data.remove(feature);
	  });
	};
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
	$('#type-selector input').on('change', function() {
		   action = $(this).val();
		   console.log(action);
		   checkIfDataRequested();
		});
	
});