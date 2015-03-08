 var app = angular.module('placeApp', []);
 app.factory('Data', function() {
   return {
     title: "Place",
     loggined: !getCookie("sessionId") == "",
     user: {}
   };
 });

 app.controller('placeController', ['$scope', '$http', 'Data', function($scope, $http, $timeout, Data) {
   $scope.error = {};
   $scope.place = {
     'id': QueryString['placeId']
   }

   var getPlace = function(id) {
     var req = {
       method: 'GET',
       url: placeByIdUrl + id
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.place = data;
       $scope.place.photo = imageHostName + $scope.place.photo;
       initCity();
       initUser();
     }.bind(this))
   };
   $scope.data = Data;
   getPlace($scope.place['id']);

   var initCity = function() {
     var req = {
       method: 'GET',
       url: cityByIdURL + $scope.place.cityId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.place.city = data[0];
       $scope.place.city.photo = imageHostName + $scope.place.city.photo;
     }.bind(this))
   }

   var initUser = function() {
     var req = {
       method: 'GET',
       url: guideByIdURL + $scope.place.userId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.place.user = data;
       $scope.place.user.photo = imageHostName + $scope.place.user.photo;
     }.bind(this))
   }

 }]);

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