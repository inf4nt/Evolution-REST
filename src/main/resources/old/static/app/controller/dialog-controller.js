/**
 * Created by Infant on 27.08.2017.
 */
rootApp.controller('DialogController', function ($scope, $http, $routeParams) {

    $scope.dialogs = []

    $http.get('/im/' + $routeParams.userid).then(function (response) {
        $scope.dialogs = response.data;
    })

})