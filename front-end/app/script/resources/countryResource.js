angular.module('tqApp.resources')
	.factory('Country', ['$resource', 'RestConfig', function($resource, RestConfig) {
		console.log('Hello from country resource!!')
		return $resource(RestConfig.url + '/country/all');
	}]);