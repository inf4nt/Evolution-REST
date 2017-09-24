/**
 * Created by Infant on 26.08.2017.
 */
var rootApp = angular.module('RootApp', [
    "ngRoute"
]).config(function($routeProvider){
    $routeProvider.when('/im/:userid',
        {
            templateUrl:'app/view/dialog-list.html',
            controller:'DialogController'
        });
    $routeProvider.when('/home/:userid',
        {
            templateUrl:'app/view/home.html',
            controller:'HomeController'
        });
})



