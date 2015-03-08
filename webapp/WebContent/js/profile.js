 var app = angular.module('profileApp', []);

 app.factory('Data', function() {
   return {
     title: "Profile",
     loggined: !getCookie("sessionId") == "",
     user: {}
   };
 });

 var getSessionId = function() {
   return getCookie("sessionId");
 }
 app.controller('profileController', function profCtrl($scope, $http, $timeout, Data) {
   $scope.error = {};
   $scope.data = Data;
   $scope.data.sessionId = getSessionId();
   var update = function() {
	   profCtrl($scope, $http, $timeout, Data);
   }
   Data.update = update;
   if (!$scope.data.sessionId) {
     $scope.error.errorShow = true;
     $scope.error.errorMessage = "You should be loggined.";
     return;
   }
   $scope.shows = {
     'citiesPlaces': true,
     'addPlace': false,
     'addCity':false,
     'createCity': false
   }

   $scope.url = {
      'placeAddURL': placeAddURL,
      'cityAddURL': cityAddURL
   }

   $scope.onAddCityClick = function(){
      $scope.shows.citiesPlaces = false;
      $scope.shows.addCity = true;
   }
   
   $scope.onCreateCityClick = function(){
	   $scope.shows.addCity = false;
	   $scope.shows.createCity = true;
   }

   $scope.onAddPlaceClick = function(){
      $scope.shows.citiesPlaces = false;
      $scope.shows.addPlace = true;
   }

   $scope.resetShows = function(){
      $scope.shows.addPlace = false;
      $scope.shows.addCity = false;
      $scope.shows.createCity = false;
      $scope.shows.citiesPlaces = true;
   }

   var initUser = function() {
     var req = {
       method: 'GET',
       url: guideBySessionURL + $scope.data.sessionId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.user = data;
       $scope.user.photo = imageHostName + $scope.user.photo;
       initCities();
       initPlaces();
     }.bind(this))
   };

   var initCities = function() {
     var req = {
       method: 'GET',
       url: cityByGuideURL + $scope.user.id
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.user.cities = data;
     });
   }

   var initPlaces = function() {
     var req = {
       method: 'GET',
       url: placeByUserURL + $scope.data.sessionId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.user.places = data;
     });
   }

   var loadAllCities = function(){
     var req = {
       method: 'GET',
       url: cityAllURL
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.data.allCities = data;
     })
   }
   
   var loadAllCountries = function() {
		var req = {
			method : 'GET',
			url : countryAllURL
		}
		$http(req).error(function(data, status, headers, config) {
			console.log(data);
			console.log(status);
			$scope.error.errorShow = true;
			$scope.error.errorMessage = commorErrorMessages[status];
		}).success(function(data, status, headers, config) {
			$scope.data.countries = data;
		})
	}

	initUser();
	loadAllCities();
	loadAllCountries();

});

 var QueryString = function() {
   // This function is anonymous, is executed immediately and
   // the return value is assigned to QueryString!
   var query_string = {};
   var query = window.location.search.substring(1);
   var vars = query.split("&");
   for (var i = 0; i < vars.length; i++) {
     var pair = vars[i].split("=");
     // If first entry with this name
     if (typeof query_string[pair[0]] === "undefined") {
       query_string[pair[0]] = pair[1];
       // If second entry with this name
     } else if (typeof query_string[pair[0]] === "string") {
       var arr = [query_string[pair[0]], pair[1]];
       query_string[pair[0]] = arr;
       // If third or later entry with this name
     } else {
       query_string[pair[0]].push(pair[1]);
     }
   }
   return query_string;
 }();