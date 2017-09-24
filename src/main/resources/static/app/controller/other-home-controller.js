/**
 * Created by Infant on 27.08.2017.
 */
rootApp.controller('OtherHomeController', function ($scope, $http, $routeParams) {

    $scope.user = {};

    $http.get('/user/' + $routeParams.userid).then(function (response) {
        $scope.user = response.data;
    })


})
