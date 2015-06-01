angular.module('tqApp')

.service('loginService', ['$cookies', function($cookies) {
	console.log($cookies);
	this.sessionId = $cookies['sessionId'];
	this.logined = this.sessionId;

	this.makeLogin = function(sessionId) {
		this.sessionId = sessionId;
		this.logined = true;  
		$cookies['sessionId'] = sessionId;
	}

	this.makeLogout = function(){
		this.sessionId = null;
		this.logined = false;
		$cookies['sessionId'] = '';
	}
}]);