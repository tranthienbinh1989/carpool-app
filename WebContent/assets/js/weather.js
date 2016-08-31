$(function() {
	$('.button-collapse').sideNav();
	var map;
	var geoJSON;
	var request;
	var gettingData = false;
	var openWeatherMapKey = "1b8cdd202c89abaeecade9a428b46b61"
    var address = street + " " + city + " " + state + " " + zipcode;
	
	function initialize() {
	  var mapOptions = {
	    zoom: 10,
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
      var searchBox = new google.maps.places.SearchBox(input);
      map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

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
          markers.push(new google.maps.Marker({
            map: map,
            icon: icon,
            title: place.name,
            position: place.geometry.location
          }));

          if (place.geometry.viewport) {
            // Only geocodes have viewport.
            bounds.union(place.geometry.viewport);
          } else {
            bounds.extend(place.geometry.location);
          }
        });
        map.fitBounds(bounds);
      });

	  
	  // Add interaction listeners to make weather requests
	  google.maps.event.addListener(map, 'idle', checkIfDataRequested);

	  // Sets up and populates the info window with details
	  map.data.addListener('click', function(event) {
	    infowindow.setContent(
	     "<img src=" + event.feature.getProperty("icon") + ">"
	     + "<br /><strong>" + event.feature.getProperty("city") + "</strong>"
	     + "<br />" + event.feature.getProperty("temperature") + "&deg;C"
	     + "<br />" + event.feature.getProperty("weather")
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
	  var bounds = map.getBounds();
	  var NE = bounds.getNorthEast();
	  var SW = bounds.getSouthWest();
	  getWeather(NE.lat(), NE.lng(), SW.lat(), SW.lng());
	};

	// Make the weather request
	var getWeather = function(northLat, eastLng, southLat, westLng) {
	  gettingData = true;
	  var requestString = "http://api.openweathermap.org/data/2.5/box/city?bbox="
	                      + westLng + "," + northLat + "," //left top
	                      + eastLng + "," + southLat + "," //right bottom
	                      + map.getZoom()
	                      + "&cluster=yes&format=json"
	                      + "&APPID=" + openWeatherMapKey;
	  request = new XMLHttpRequest();
	  request.onload = proccessResults;
	  request.open("get", requestString, true);
	  request.send();
	};
	
	// Take the JSON results and proccess them
	var proccessResults = function() {
	  var results = JSON.parse(this.responseText);
	  if (results.list.length > 0) {
	      resetData();
	      for (var i = 0; i < results.list.length; i++) {
	        geoJSON.features.push(jsonToGeoJson(results.list[i]));
	      }
	      drawIcons(geoJSON);
	  }
	};

	var infowindow = new google.maps.InfoWindow();

	// For each result that comes back, convert the data to geoJSON
	var jsonToGeoJson = function (weatherItem) {
	  var feature = {
	    type: "Feature",
	    properties: {
	      city: weatherItem.name,
	      weather: weatherItem.weather[0].main,
	      temperature: weatherItem.main.temp,
	      min: weatherItem.main.temp_min,
	      max: weatherItem.main.temp_max,
	      humidity: weatherItem.main.humidity,
	      pressure: weatherItem.main.pressure,
	      windSpeed: weatherItem.wind.speed,
	      windDegrees: weatherItem.wind.deg,
	      windGust: weatherItem.wind.gust,
	      icon: "http://openweathermap.org/img/w/"
	            + weatherItem.weather[0].icon  + ".png",
	      coordinates: [weatherItem.coord.lon, weatherItem.coord.lat]
	    },
	    geometry: {
	      type: "Point",
	      coordinates: [weatherItem.coord.lon, weatherItem.coord.lat]
	    }
	  };
	  // Set the custom marker icon
	  map.data.setStyle(function(feature) {
	    return {
	      icon: {
	        url: feature.getProperty('icon'),
	        anchor: new google.maps.Point(25, 25)
	      }
	    };
	  });

	  // returns object
	  return feature;
	};

	// Add the markers to the map
	var drawIcons = function (weather) {
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
	
	//===========================================================
	var lat = 38.907200;
	var long = -77.03909;
	var position;
 
	if((zipcode != 0 && zipcode != "") && street != "" && city != "" && state != "") {
		geocoder = new google.maps.Geocoder();
	    geocoder.geocode({
	        'address': address
	    }, function(results, status) {
	        if (status == google.maps.GeocoderStatus.OK) {
	        	getForecastData(results[0].geometry.location.lat(), results[0].geometry.location.lng());
	        }
	    });
	} else {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position){
				getForecastData(position.coords.latitude, position.coords.longitude);
			});
		} else {
				getForecastData(lat, long);
		}
	}
	  

	
	function getForecastData(latitude, longitude) {
		//get forecast 5 days 3 hours data
		var forcastUrl = "http://api.openweathermap.org/data/2.5/forecast";
		$.get(forcastUrl,{
			lat : latitude,
			lon : longitude,
			units : 'imperial',
			cnt: 9,
			appid: openWeatherMapKey
		}).done(function(data) {
			$("#city").text(data.city.name + " Weather");
			var foreCastTable = $("#forecastTable tbody");
			$.each(data.list,function(k, v) {
				var dt = getHour(v.dt);
				var des = v.weather[0].main;
				var icon = "<img src='http://openweathermap.org/img/w/"
		            + v.weather[0].icon  + ".png' />";
				var precip;
				if(v.rain != "undefined") {
					precip = "0%"
				} else {
					v.rain.h3 = v.rain.h3*1000 + "%";
				}
				var temp = v.main.temp + "&#8457;";
				var hudimity = v.main.humidity + "%";
				var wind = v.wind.speed + " m/s";
				foreCastTable.append("<tr>" +
						"<td>" + dt + "</td>" +
						"<td>" + icon + "</td>" +
						"<td>" + temp + "</td>" +
						"<td>" + precip + "</td>" +
						"<td>" + hudimity + "</td>" +
						"<td>" + wind + "</td>" +
						"" + "</tr>");
			});
		}).fail(function() {
			
		});
	}
	
	function getHour(timestamp) {
		var date = new Date(timestamp*1000);
		// Hours part from the timestamp
		var hours = date.getHours();
		// Minutes part from the timestamp
		var minutes = "0" + date.getMinutes();
		return hours +":" + minutes;
	}
});
