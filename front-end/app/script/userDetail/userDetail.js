'use strict';

angular.module('tqApp')

.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/userDetail/:userId', {
			templateUrl: 'script/userDetail/userDetail.html',
			controller: 'UserDetailController'
		});

	}])
	.controller('UserDetailController', [
			'$scope', '$routeParams', 'loginService', 'Question', 'Guide',
		function($scope, $routeParams, loginService, Question, Guide) {
			$scope.loginData = loginService;


			Guide.get({
				id: $routeParams.userId
			}, function(user) {
				$scope.user = user;

				Question.getByUserId({
					id: user.id
				}, function(data) {
					$scope.questions = data;
				})
			})

		}

	]);