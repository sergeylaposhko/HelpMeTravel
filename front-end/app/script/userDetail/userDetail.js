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
            
			$scope.voteUp = function(questionId) {
				Question.voteUp({}, $.param({
					sessionId: loginService.sessionId,
					questionId: questionId
				}), function(data) {
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