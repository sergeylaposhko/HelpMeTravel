angular.module('tqApp.resources')
	.factory('Guide', ['$resource', 'RestConfig', '$http', function($resource, RestConfig, $http) {
		function appendTransform(defaults, transform) {
			defaults = angular.isArray(defaults) ? defaults : [defaults];
			return defaults.concat(transform);
		}
		var guideResource = $resource(RestConfig.url + '/guide/', {}, {
			'get': {
				method: 'GET',
				isArray: false,
				url: RestConfig.url + '/guide/byid?id=:id',
				transformResponse: appendTransform($http.defaults.transformResponse, function(value) {
					console.log(value);
					value.photo = RestConfig.hostName + value.photo;
					console.log(value);
					return value;
				})
			}
		});

		return guideResource;
	}]);