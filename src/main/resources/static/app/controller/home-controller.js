/**
 * Created by Infant on 27.08.2017.
 */



rootApp.controller('HomeController',
    ['$scope', '$http', 'auth_id',  function ($scope, $http, auth_id)  {

        NProgress.start();
        $scope.user = null;
        $scope.auth_id = auth_id;
        $scope.isDone = false;
        NProgress.start();
        $http.get('/user/' + auth_id).then(function (response) {
            $scope.user = response.data;
            NProgress.done();
            $scope.isDone = true;
        })


}])