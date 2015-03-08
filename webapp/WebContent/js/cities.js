 var app = angular.module('scroll', []);
 app.factory('Data', function(){
     return {
      title:"Cities",
      loggined: !getCookie("sessionId") == "",
      user:{}
     };
 });


 app.directive('whenScrolled', function() {
   return function(scope, elm, attr) {
     var raw = elm[0];

     elm.bind('scroll', function() {
       if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight - 10) {
         scope.$apply(attr.whenScrolled);
       }
     });
   };
 });
 app.controller('cityController', function($scope, $http, $timeout, Data) {
   $scope.data = Data;
   $scope.cities = [];
   $scope.busy = false;
   $scope.error = {
     showError: false,
     errorMessage: ""
   }
   $scope.searchWord = "";
   var allDataLoaded = false;
   var lastLoaded = 0;
   var step = 20;
   var defBaseUrl = hostname + cityGet + cityAll;
   var baseUrl = defBaseUrl;
   var busy = false;

   $scope.loadMore = function() {
     if (busy || $scope.busy || allDataLoaded) {
       return;
     }
     busy = true;
     $timeout(function() {
       if (busy) {
         $scope.busy = true;
       }
     }, 350);
     var req = {
       method: 'GET',
       url: baseUrl + cityFromParam + lastLoaded + '&' + cityToParam + (lastLoaded + step)
     }
     $http(req).
     error(function(data, status, headers, config) {
       $scope.error.showError = true;
       $scope.error.errorMessage = commorErrorMessages[status];
       $scope.busy = false;
       busy = false;
     }).
     success(function(data, status, headers, config) {
       $scope.error.showError = false;
       $scope.error.errorMessage = "";
       var items = data;
       if (data.length == 0) {
         allDataLoaded = true;
         $scope.busy = false;
         busy = false;
         return;
       }
       for (var i = 0; i < items.length; i++) {
         items[i].photo = imageHostName + items[i].photo;
         this.cities.push(items[i]);
       }
       $scope.busy = false;
       busy = false;
       lastLoaded += step;
     }.bind(this))
   };
   $scope.loadMore();

   $scope.serachEnterPressed = function(event) {
     $scope.searchWord = event.target.value;
     if (event.keyCode == 13) {
       startSearch($scope.searchWord);
       return false;
     }
   };


   var startSearch = function(word) {
     console.log(word);
     if (word === "" || word === undefined) {
       baseUrl = defBaseUrl;
     } else {
       baseUrl = hostname + cityGet + cityWhere + word + '&';
     }
     $scope.cities = [];
     lastLoaded = 0;
     allDataLoaded = false;
     $scope.loadMore();
     $scope.bind;
   };
   $scope.startSearch = startSearch;

 });