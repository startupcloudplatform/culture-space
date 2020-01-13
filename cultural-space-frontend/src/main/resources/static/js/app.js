'use strict';

// Define the `culturalSpaceApp` module
var app = angular.module('culturalSpaceApp', [
    'common.controllers',
    'common.services',
    'ngRoute',
    'angularFileUpload',
    'ngResource',
    'ui.bootstrap'
]);

app.config(['$routeProvider', '$httpProvider', '$locationProvider',
    function($routeProvider, $httpProvider, $locationProvider) {
        $routeProvider
        .when('/main', {
            templateUrl: '/views/portal/searchMain.html',
            controller: 'searchMainCtrl as main'
        }).when('/login', {
            templateUrl: '/login.html',
            controller: 'adminLoginCtrl as admin'

        }).when('/admin', {
            templateUrl: '/views/admin/adminUpdateData.html',
            controller: 'adminManagementCtrl as manage'
        }).otherwise({
            redirectTo: '/main'
        });
    }
]);

