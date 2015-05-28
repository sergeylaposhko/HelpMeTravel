'use strict';

angular.module('tqApp')

.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/question/:questionId', {
			templateUrl: 'script/question/question.html',
			controller: 'QuestionController'
		});

	}])
	.controller('QuestionController', ['$scope', '$routeParams', 'loginService', 'Question',
		function($scope, $routeParams, LoginService, Question) {
			var question = Question.get({
				id: 10
			});
			console.log(question);
		}
	]);