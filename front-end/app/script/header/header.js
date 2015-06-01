'use strict';

angular.module('tqApp')
	.controller('headerController', ['$scope', 'loginService', 'Country', 'User',
		function($scope, loginService, Country, User) {
			$scope.login = {};
			$scope.register = {};
			$scope.errors = {};

			$scope.loginData = loginService;

			$scope.countries = Country.query();

			$scope.doLogin = function(){
				var login = $scope.login.login;
				var password = $scope.login.password; 
				User.login({}, $.param({
					login: login,
					password: password
				}), function (data){
					loginService.makeLogin(data.sessionId);
					$scope.errors.login = false;
					$scope.login.password = '';
				}, function(errorData){
					$scope.errors.login = true;
					$scope.login.password = '';
				});
			} 

			$scope.doLogout = function() {
				User.logout({}, $.param({
					sessionId: loginService.sessionId
				}), function(data){
					loginService.makeLogout();
				});
			}
		}
	]);