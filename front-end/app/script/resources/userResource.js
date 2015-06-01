angular.module('tqApp.resources')
	.factory('User', ['$resource', 'RestConfig', function($resource, RestConfig) {
		return $resource(RestConfig.url + '/user/', {}, {
			'login': {
				method: 'POST',
				isArray: false,
				url: RestConfig.url + '/user/login',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			},
			'register': {
				method: 'POST',
				isArray: false,
				url: RestConfig.url + '/user/register',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			},
			'logout': {
				method: 'POST',
				isArray: false,
				url: RestConfig.url + '/user/logout',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}
		});
	}]);