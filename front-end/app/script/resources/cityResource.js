var resources = angular.module('tqApp.resources');

resources

	.factory('City', ['$resource', 'RestConfig', function($resource, RestConfig) {
	return $resource(RestConfig.url + '/city/byid?id=:id', {});
}]);