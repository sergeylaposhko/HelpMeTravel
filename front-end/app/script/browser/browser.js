'use strict';

angular.module('daywalk.browser', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/browser', {
            templateUrl: '/script/browser/browser.html',
            controller: 'BrowserController'
        });
    }])
 

    //.controller('BrowserController', ['$scope', 'Route', function($scope, route) {
    //    $scope.currentPosition = 0;
    //    $scope.isPlaying = false;
    //
    //    var res = route.get();
    //    console.log(res);
    //    $scope.route = res;
    //
    //    for(var i = 0; i < $scope.route.points.length; i++){
    //        $scope.route.points[i].videoUrl += '?enablejsapi=1';
    //    }
    //
    //    initializeViews(res);
    //
    //    $scope.next = function() {
    //        $scope.currentPosition++;
    //        $scope.currentPosition %= $scope.route.points.length;
    //        moveToPoint($scope.route.points[$scope.currentPosition]);
    //    }
    //
    //    $scope.previous = function() {
    //        $scope.currentPosition--;
    //        if ($scope.currentPosition < 0) {
    //            $scope.currentPosition = $scope.route.points.length - 1;
    //        }
    //        moveToPoint($scope.route.points[$scope.currentPosition]);
    //    }
    //
    //    $scope.toggleAudio = function () {
    //        var player = document.getElementById('player');
    //        if($scope.isPlaying){
    //            player.pause();
    //        } else {
    //            player.play();
    //        }
    //        $scope.isPlaying = !$scope.isPlaying;
    //    }
    //}]);

    .controller('BrowserController', ['$scope', 'routesService', function($scope, route) {

        $scope.currentPosition = 0;

        $scope.isPlaying = true;

        $scope.toggleAudio = function () {
            var player = document.getElementById('player');
            if ($scope.isPlaying) {
                player.pause();
            } else {
                player.play();
            }
            $scope.isPlaying = !$scope.isPlaying;
        }

        var res = route.get({id:3}, function(){
            res.points = res.points.sort(function(point1, point2) {
                if (point1.sequenceNumber < point2.sequenceNumber) {
                    return -1;
                }
                if (point1.sequenceNumber > point2.sequenceNumber) {
                    return 1;
                }
                return 0
            });

            initializeViews(res);
            $scope.route = res;

            for (var i = 0; i < $scope.route.points.length; i++) {
                $scope.route.points[i].videoUrl += '?enablejsapi=1';
            }
        });
         $scope.next = function() {
             $scope.currentPosition++;
             $scope.currentPosition %= $scope.route.points.length;
             moveToPoint($scope.route.points[$scope.currentPosition]);
         }
         $scope.previous = function() {
             $scope.currentPosition--;
             if ($scope.currentPosition < 0) {
                 $scope.currentPosition = $scope.route.points.length - 1;
             }
             moveToPoint($scope.route.points[$scope.currentPosition]);
         }
    }]);
