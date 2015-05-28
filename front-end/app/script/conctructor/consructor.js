'use strict';

angular.module('daywalk.constructor', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/constructor', {
		templateUrl: 'script/constructor/constructor.html',
		controller: 'ConstructorController'
	});
}])

.controller('ConstructorController', ['$scope', function($scope) {
    initializeViews();
}]);