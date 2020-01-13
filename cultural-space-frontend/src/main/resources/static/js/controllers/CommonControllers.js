'use strict';

angular.module('common.controllers', [])
// 최초 접속 혹은 새로고침 시 as main
    .controller('mainCtrl', function ($rootScope, $scope, $location, $http, $window, cache, common) {
        var main = this;
        // 로그인 여부
        main.isAuthenticated	= false;
        // 사용자 정보
        main.userInfo = {};
        $scope.isAuthenticated = false;

        $scope.loadingMain =false;
        $scope.uploadProgress = false;

        //페이지 이동할 때 마다, 인증 체크
        $rootScope.$on('$routeChangeSuccess', function () {
            main.authenticate();
        });


        // 로그 아웃 초기화
        main.resetInit = function () {
            // 로그인 여부
            main.isAuthenticated = false;
            // 사용자 정보
            main.userInfo = {};

        };

        // 로그인 체크
        main.authenticate =function(){
            if (!common.isAuthenticated()) {
                $scope.isAuthenticated = false;
                if ($location.path() === "/admin") {
                    common.moveLoginPage();
                }
            }else{
                $scope.isAuthenticated = true;
            }
        };

        // 로그인 페이지로 이동
        $scope.signIn = function() {
            $window.location.href='/#!/login';
        }

        // 로그아웃
        $scope.logout = function() {
            $http({
                method: 'POST',
                url: 'logout',
                params: {}
            }).then(function () {
                common.clearUserAll();
                $scope.isAuthenticated = false;
                $location.path("/");
            }, function (reason) {
                $scope.isAuthenticated = false;
            });
        };

        main.pageLoadData =function(){
            main.authenticate();
            // $scope.loadingMain = true;
        };

        main.pageLoadData();
    });