var resources = angular.module('tqApp.resources');

resources

	.factory('Answer', ['$resource', 'RestConfig', function($resource, RestConfig) {
	return $resource(RestConfig.url + '/answer/byid?id=:id', {}, {
		'voteUp': {
			method: 'POST',
			isArray: false,
			url: RestConfig.url + 'answerVote/voteUp',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		},
		'voteDown': {
			method: 'POST',
			isArray: false,
			url: RestConfig.url + 'answerVote/voteDown',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		},
		'add': {
			method: 'POST',
			isArray: false,
			url: RestConfig.url + 'answer/add',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}
	});
}]);