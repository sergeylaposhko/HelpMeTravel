'use strict';

angular.module('tqApp')

.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/question/:questionId', {
			templateUrl: 'script/question/question.html',
			controller: 'QuestionController'
		});

	}])
	.controller('QuestionController', ['$scope', '$routeParams', 'loginService', 'Question',
		function($scope, $routeParams, loginService, Question) {
			$scope.questions = Question.query();

            $scope.loginData = loginService;
            
			$scope.voteUp = function(questionId) {
				Question.voteUp({}, $.param({
					sessionId: loginService.sessionId,
					questionId: questionId
				}), function(data) {
					console.log('Vote up is succed for questionID ' + questionId);
					$scope.updateQuestion(questionId);
				})
			}  

			$scope.voteDown = function(questionId) {
				Question.voteDown({}, $.param({
					sessionId: loginService.sessionId, 
					questionId: questionId
				}), function(data){
					$scope.updateQuestion(questionId);
				});
			};

			$scope.updateQuestion = function updateQuestion(questionId) {
				var newQuestion = Question.get({id: questionId}, function(data){
					for (var i = 0; i < $scope.questions.length; i++) {
						var curQuestion = $scope.questions[i]
						if(curQuestion.id === questionId){
							$scope.questions[i] = data;
							break;
						}
					};
				})
			}
		}


	]);