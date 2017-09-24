/**
 * Created by Infant on 26.08.2017.
 */
rootApp.controller('HomeController',
    ['$scope', '$http', 'auth_id', 'user_id', function ($scope, $http, user_id, auth_id)  {

    NProgress.start();
    $scope.user = null;
    $scope.user_id = user_id;
    $scope.auth_id = auth_id;
    $scope.isDone = false;
    $scope.messageUrl = $scope.user_id === $scope.auth_id ? '#!/im/' : '#!/im/' + $scope.user_id;

    setTimeout(function () {
        $http.get('/user/' + user_id).then(function (response) {
            $scope.user = response.data;
            NProgress.done();
        })
        $scope.isDone = true;
    }, 1000)

}])