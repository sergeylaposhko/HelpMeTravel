'use strict';

angular.module('tqApp')

.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            redirectTo: '/home'
        });
        $routeProvider.when('/home', {
            templateUrl: 'script/home/home.html',
            controller: 'HomeController'
        });

}])
    .controller('HomeController', ['$scope', 'loginService', function ($scope, loginService) {
        $scope.testDate = 'testData';
        $scope.logined = loginService.logined;
}]);