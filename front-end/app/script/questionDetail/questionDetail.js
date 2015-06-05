'use strict';

angular.module('tqApp')

.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/questionDetail/:questionId', {
			templateUrl: 'script/questionDetail/questionDetail.html',
			controller: 'QuestionDetailController'
		});

	}])
	.controller('QuestionDetailController', ['$scope', '$routeParams', 'loginService', 'Question', 'Answer',
		function($scope, $routeParams, loginService, Question, Answer) {
			$scope.loginData = loginService;

			var curQuestion = Question.get({
				id: $routeParams.questionId
			}, function(data) {
				$scope.question = data;
			})

			$scope.voteAnswerUp = function(answerId) {
				Answer.voteUp({}, $.param({
					answerId: answerId,
					sessionId: loginService.sessionId
				}), function(data) {
					$scope.updateAnswer(answerId);
				})
			}

			$scope.voteAnswerDown = function(answerId) {
				Answer.voteDown({}, $.param({
					answerId: answerId,
					sessionId: loginService.sessionId
				}), function(data) {
					$scope.updateAnswer(answerId);
				})
			}

			$scope.updateAnswer = function(answerId) {
				for (var i = 0; i < $scope.question.answers.length; i++) {
					if ($scope.question.answers[i].id === answerId) {
						$scope.question.answers[i] = Answer.get({
							id: answerId
						});
					}
				}
			}

			$scope.sendAnswer = function(){
				Answer.add({}, $.param({
					sessionId: loginService.sessionId,
					questionId: $scope.question.id,
					text: $scope.userAnswer
				}), function(resultQuestion){
					console.log('Updating question...');
					$scope.question = resultQuestion;
					$scope.userAnswer = '';
				})
			}

		}


	]);