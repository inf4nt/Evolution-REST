/**
 * Created by Infant on 27.08.2017.
 */
var rootApp = angular.module('RootApp', [
    "ngRoute"
]).config(function($routeProvider){

    $routeProvider.when('/',
        {
            templateUrl:'/app/view/home.html',
            controller:'HomeController'
        });

    $routeProvider.when('/dialog',
        {
            templateUrl:'/app/view/dialog.html',
            controller:'DialogController'
        });

    $routeProvider.when('/user/:userid',
        {
            templateUrl:'/app/view/other-home.html',
            controller:'OtherHomeController'
        });

});