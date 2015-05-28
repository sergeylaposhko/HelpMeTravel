angular.module('tqApp')

.service('loginService', function(){
	this.logined = true;
	this.triger = function triger () {
		this.logined = !this.logined;
	}
	console.log('Hello from service.');
})