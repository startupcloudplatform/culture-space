'use strict';

angular.module('common.services', ['LocalStorageModule'])
    .factory('common', function ($http, $location, $route, $window, cache ) {
        var common = {};

        common.setUser = function (userInfo) {
            cache.setUser(userInfo);
        };

        common.clearUser = function () {
            cache.clearUser();
        };

        common.getUser = function () {
            return cache.getUser();
        };

        common.clearUserAll = function () {
            common.clearUser();
        };

        common.clearAll = function () {
            cache.clearAll();
        };

        common.isAuthenticated = function () {
            return common.getUser() != null;
        };

        common.moveLoginPage = function () {
            $location.path("/login");
        };

        return common;

    })
    .factory('cache', function (localStorageService) {

        var cache = {};

        cache.setUser = function (userInfo) {
            localStorageService.add(_USER_INFO_CASHE_NAME_, JSON.stringify(userInfo));
        };

        cache.getUser = function () {
            return JSON.parse(localStorageService.get(_USER_INFO_CASHE_NAME_));
        };

        cache.clearUser = function () {
            localStorageService.remove(_USER_INFO_CASHE_NAME_);
        };
        cache.clearAll = function () {
            localStorageService.clearAll();
        };

        return cache;
    });
