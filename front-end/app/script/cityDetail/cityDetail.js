'use strict';

angular.module('tqApp')

.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/cityDetail/:cityId', {
			templateUrl: 'script/cityDetail/cityDetail.html',
			controller: 'CityDetailController'
		});

	}])
	.controller('CityDetailController', [
			'$scope', '$routeParams', 'loginService', 'Question', 'City',
		function($scope, $routeParams, loginService, Question, City) {
			$scope.loginData = loginService;
            
            City.get({
				id: $routeParams.cityId
			}, function(city) {
				$scope.city = city;

				Question.getByCity({
					id: city.id
				}, function(data) {
					$scope.questions = data;
                    console.log(data);
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