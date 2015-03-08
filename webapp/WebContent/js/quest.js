 var app = angular.module('questApp', []);
 app.factory('Data', function() {
   return {
     title: "Question",
     loggined: !getCookie("sessionId") == "",
     user: {}
   };
 });

 app.controller('questController', function quesCtrl($scope, $http, $timeout, Data) {
	 console.log("Data from quest:");
	 console.log(Data);
   $scope.error = {};
   var update = function() {
	   quesCtrl($scope, $http, $timeout, Data);
   }
   Data.update = update;
   $scope.question = {
     'id': QueryString['questionId']
   }
   var sessionId = getCookie("sessionId");
   var getQeustion = function(id) {
     var req = {
       method: 'GET',
       url: questionByIdURL + id
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.question = data['question'];
       $scope.question.answers = data['answers'];
       console.log($scope.question);
       initQuestionUser();
       initCity();
       initAnsersUser();
     }.bind(this))
   };
//   $scope.data = {
//      loggined: !getCookie("sessionId") == ""
//   };
   $scope.data = Data;
   getQeustion($scope.question['id']);

   $scope.submitAnswer = function(){
    var answerText = document.getElementById("user-answer-text").value;
    var req = {
      url: answerAddURL,
      method: "POST",
      data: {
        'sessionId': sessionId,
        'questionId': $scope.question.id,
        'text': answerText
      },
      transformRequest: postRequestTransformatter,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }
    $http(req).
    success(function(data, status, headers, config) {
      document.getElementById("user-answer-text").value ="";
      getQeustion($scope.question['id']);
    }).
    error(function(data, status, headers, config) {
      console.log(data);
      console.log(status);
    });
   };

   var initQuestionUser = function(){
     var req = {
       method: 'GET',
       url: guideByIdURL + $scope.question.userId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.question.user = data;
       console.log($scope.question);
     });
   }

   var initAnsersUser = function(){
      for(var i = 0; i < $scope.question.answers.length; i++){
        initAnswerUser(i);
      }
   }

   var initAnswerUser = function(i){
     var req = {
       method: 'GET',
       url: guideByIdURL + $scope.question.answers[i].userId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.question.answers[i].user = data;
     });
   }

   var initCity = function(){
     var req = {
       method: 'GET',
       url: cityByIdURL + $scope.question.cityId
     }
     $http(req).
     error(function(data, status, headers, config) {
       console.log(data);
       console.log(status);
       $scope.error.errorShow = true;
       $scope.error.errorMessage = commorErrorMessages[status];
     }).
     success(function(data, status, headers, config) {
       $scope.question.city = data[0];
       console.log($scope.question);
     });
   }

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