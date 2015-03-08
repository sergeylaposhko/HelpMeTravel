 var app = angular.module('guideApp', []);
 app.factory('Data', function() {
   return {
     title: "Guide",
     loggined: !getCookie("sessionId") == "",
     user: {}
   };
 });

 app.controller('guideController', ['$scope', '$http', 'Data', function($scope, $http, $timeout, Data) {
   $scope.error = {};
   $scope.guide = {
     'id': QueryString['guideId']
   }

   var getGuide = function(id) {
     var req = {
       method: 'GET',
       url: guideByIdURL + id
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.guide = data;
       $scope.guide.photo = imageHostName + $scope.guide.photo;
       initCountry();
     }.bind(this))
   };
   $scope.data = Data;
   getGuide($scope.guide['id']);


   var initCountry = function() {
     var req = {
       method: 'GET',
       url: countryByIdURL + $scope.guide.countryId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.guide.country = data;
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