angular.module('tqApp', [
	'ngRoute',
	'ngCookies',
	'tqApp.resources'
])
.config(['$routeProvider', '$sceDelegateProvider', function($routeProvider, $sceDelegateProvider) {
	$sceDelegateProvider.resourceUrlWhitelist([
    	'self',
    	'http://nn.radio-t.com/rtfiles/**',
		'http://youtube.com/**',
		'https://www.youtube.com/**'
	]);
}]);

angular.module('tqApp.resources', ['ngResource', 'tqApp.constants']);