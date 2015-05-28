var resources = angular.module('tqApp.resources', ['ngResource', 'tqApp.constants']);

resources

	.factory('Question', ['$resource', 'RestConfig', function($resource, RestConfig) {
	return $resource(RestConfig.url + '/api/question/:id');
}]);