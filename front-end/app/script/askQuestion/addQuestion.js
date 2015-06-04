'use strict';

angular.module('tqApp')

.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/askQuestion/', {
			templateUrl: 'script/askQuestion/askQuestion.html',
			controller: 'AskQuestionController'
		});
 
	}])
	.controller('AskQuestionController', ['$scope', '$routeParams', 'loginService', 'Question',
		function($scope, $routeParams, loginService, Question) {
			$scope.questions = Question.query();

            $scope.askQuestion = function(){
                
            }
            
		}


	]);