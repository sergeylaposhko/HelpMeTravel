 var app = angular.module('cityApp', []);
 app.factory('Data', function() {
   return {
     title: "City",
     loggined: !getCookie("sessionId") == "",
     user: {}
   };
 });

 app.controller('cityController', ['$scope', '$http', 'Data', function($scope, $http, $timeout, Data) {
   $scope.error = {};
   $scope.city = {
     'id': QueryString['cityId']
   }

   var getCity = function(id) {
     var req = {
       method: 'GET',
       url: cityByIdURL + id
     }
     $http(req).
     error(function(data, status, headers, config) {
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.city = data[0];
       $scope.city.photo = imageHostName + $scope.city.photo;
     }.bind(this))
   };
   $scope.data = Data;
   getCity($scope.city['id']);


   var tabs = document.getElementById("tabs");
   $scope.tabs = {
     'places-tab': true,
     'questions-tab': false,
     'guides-tab': false
   }
   tabs.addEventListener("core-select", function(event) {
     $scope.tabs[event.detail.item.id] = event.detail.isSelected;
     if (event.detail.isSelected) {
       $scope.$apply();
     }
   })

 }]);

 app.controller('placesController', ['$scope', '$http', function($scope, $http) {
   $scope.places = [];
   if ($scope.city == undefined) {
     console.log('city is undefined');
     return;
   }
   var req = {
     method: 'GET',
     url: placesByCityURL + $scope.city.id
   }
   $http(req).
   error(function(data, status, headers, config) {
     console.log(data);
     console.log(status);
     $scope.error.errorShow = true;
     $scope.error.errorMessage = commorErrorMessages[status];
   }).
   success(function(data, status, headers, config) {
     for (var i = 0; i < data.length; i++) {
       data[i].photo = imageHostName + data[i].photo;
     }
     $scope.places = data;
   });
   
 }]);

 app.controller('questionsController', function quessCtrl($scope, $http, Data) {
   $scope.questions = [];
   $scope.data = Data;
   var update = function() {
	   quessCtrl($scope, $http, Data);
   }
   Data.update = update;
   if ($scope.city == undefined) {
     return;
   }
   var req = {
     method: 'GET',
     url: questionByCityURL + $scope.city.id
   }
   $http(req).
   error(function(data, status, headers, config) {
     $scope.error.errorShow = true;
     $scope.error.errorMessage = commorErrorMessages[status];
   }).
   success(function(data, status, headers, config) {
     $scope.questions = data;
     angular.forEach
     for (var i = 0; i < $scope.questions.length; i++) {
       createUser($scope.questions, i);
     }
   });
   var createUser = function(questions, i) {
     var userReq = {
       method: 'GET',
       url: guideByIdURL + questions[i].userId
     }
     $http(userReq).
     success(function(userData, status, headers, config) {
       questions[i].user = userData;
     }).
     error(function(userData, status, headers, config) {
       console.log(userData);
       console.log(status);
     })
   }
   $scope.submitQuestion = function(){
	    var questionText = document.getElementById("user-question-text").value;
	    var questionHeader = document.getElementById("user-question-header").value;
	    var req = {
	      url: questionAddURL,
	      method: "POST",
	      data: {
	        'sessionId': getCookie("sessionId"),
	        'cityId': $scope.city.id,
	        'header': questionHeader,
	        'text': questionText
	      },
	      transformRequest: postRequestTransformatter,
	      headers: {
	        'Content-Type': 'application/x-www-form-urlencoded'
	      }
	    }
	    $http(req).
	    success(function(data, status, headers, config) {
	      document.getElementById("user-question-text").value ="";
	      document.getElementById("user-question-header").value ="";
	      quessCtrl($scope, $http, Data);
	    }).
	    error(function(data, status, headers, config) {
	      console.log(data);
	      console.log(status);
	    });
	   };
 });

 app.controller('guidesController', ['$scope', '$http', function($scope, $http) {
   $scope.guides = [];
   if ($scope.city == undefined) {
     return;
   }
   var req = {
     method: 'GET',
     url: guideByCityURL + $scope.city.id
   }
   $http(req).
   error(function(data, status, headers, config) {
     $scope.error.errorShow = true;
     $scope.error.errorMessage = commorErrorMessages[status];
   }).
   success(function(data, status, headers, config) {
     for (var i = 0; i < data.length; i++) {
       data[i].photo = imageHostName + data[i].photo;
     }
     $scope.guides = data;
     console.log(data);
   });
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