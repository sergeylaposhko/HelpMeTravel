var resources = angular.module('tqApp.resources');

resources

	.factory('Question', ['$resource', 'RestConfig', function($resource, RestConfig) {
	return $resource(RestConfig.url + '/question/', {}, {
		'get': {
			method: 'GET',
			isArray: false,
			url: RestConfig.url + 'question/byid?id=:id'
		},
		'query': {
			method: 'GET',
			isArray: true,
			url: RestConfig.url + '/question/all'
		},
		'voteUp': {
			method: 'POST',
			isArray: false,
			url: RestConfig.url + 'questionVote/voteUp',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		},
		'voteDown': {
			method: 'POST',
			isArray: false,
			url: RestConfig.url + 'questionVote/voteDown',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		},
		'getByUserId': {
			method: 'GET',
			isArray: true,
			url: RestConfig.url + 'question/byuser?userId=:id'
		}
	});
}]);