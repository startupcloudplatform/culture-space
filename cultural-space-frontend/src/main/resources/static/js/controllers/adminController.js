'use strict';

angular.module('culturalSpaceApp')
    .controller('adminLoginCtrl', function ($scope, $rootScope, $http, $window, $location, common) {

        var admin = this;
        $scope.credentials = {};
        $scope.isAuthenticated = common.isAuthenticated();

        admin.authenticate = function() {
            $http.get('/user').then(successCallback, errorCallback);

            function successCallback(response){
                if(angular.isDefined(response.data.name)){
                    if(response.data.name !== ""){
                        common.setUser(response.data);
                        $scope.isAuthenticated = true;
                        $location.path("/admin");
                    }
                }else{
                    $scope.error = true;
                    $scope.isAuthenticated = false;
                }
            }
            function errorCallback(){
                $scope.error = true;
                $scope.isAuthenticated = false;
            }
        };

        $scope.login = function() {
            $http({
                method: 'POST',
                url: '/login',
                params: $scope.credentials,
                headers: { "content-type" : "application/json", 'accept-type': "application/json" }
            }).then(function success(response){
                admin.authenticate(response);
            }, function error(response){
                $location.path("/login");
                $scope.error = true;
                common.clearUserAll();
            });

        };

        admin.pageLoadData = function () {
            $scope.$parent.loadingMain = false;
        };

        admin.pageLoadData();

    })
    .controller('adminManagementCtrl', function ($scope, $http, $window, $upload, $rootScope, $location, common) {

        var manage = this;
        manage.updatedDate = null;
        manage.dataListLength = null;
        manage.isUpdate = false;
        manage.useApiUpdate = true;
        $scope.isAuthenticated = common.isAuthenticated();

        /**
         * 문화공간 데이터 전체 리스트
         */
        manage.listAllData = function() {

            $scope.$parent.loadingMain = true;

            $http({
                method: 'GET',
                url: '/api/facilities/list',
                headers: {'Content-Type': 'application/json; charset=utf-8'}

            }).then(function success(response) {
                if(response.data.length > 0){
                    manage.updatedDate = response.data[0].created;
                }
                manage.dataListLength = response.data.length;
                $scope.$parent.loadingMain = false;

            }, function error(response) {
                manage.updatedDate = null;
                manage.dataListLength = null;
                $scope.$parent.loadingMain = false;
                alert("데이터 리스트 불러오기에 실패하였습니다");
            });
        };

        /**
         * excel로 데이터 업데이트 기능 사용여부 체크
         */
        manage.useExcelUpdate = function(){
            manage.useApiUpdate = !manage.useApiUpdate;
        };

        /**
         * 문화공간 api로 데이터 업데이트
         */
        manage.updateData = function() {

            $scope.$parent.loadingMain = true;

            manage.useApiUpdate = true;
            // 데이터 연결 성공하면 이동

            $http({
                method: 'POST',
                url: '/api/facilities/update',
                headers: {'Content-Type': 'application/json; charset=utf-8'}

            }).then(function success(response) {

                // 데이터 연결 성공하고 나면 위의 문장들 여기로 이동
                manage.updatedDate = new Date();
                manage.isUpdate = true;
                manage.listAllData();

            }, function error(response) {

                manage.isUpdate = false;
                $scope.$parent.loadingMain = false;
                alert("openData api 서버 오류입니다\n지속적으로 발생 시 열린데이터 광장으로 문의(QnA) 바랍니다");

            });

        };

        /**
         * excel 파일로 데이터 업데이트
         * @param $files
         */
        manage.onFileSelect = function($files) {

            $scope.$parent.loadingMain = true;

            for (var i = 0; i < $files.length; i++) {
                var file = $files[i];

                var fileNames = file.name.split(".");
                if(fileNames[fileNames.length-1] !== 'xls' && fileNames[fileNames.length-1] !== 'xlsx'){
                    $scope.$parent.loadingMain = false;
                    alert("xls, xlsx 파일만 등록할 수 있습니다!");
                    return;
                }

                //$upload 서비스를 통해 실제 비동기 업로드를 수행한다. 이떄 HTTP 경로와 메소드 그리고 해당 파일 필드이름을 지정할 수 있다.
                $scope.upload = $upload.upload({
                    url: '/api/facilities/file/upload',   //경로
                    method: 'POST',   //메소드
                    headers: {
                        'Content-Type': "multipart/form-data"
                    },
                    data: {
                        file: file,        //파일
                        fileFormDataName: 'fileField1'  //필드이름
                    }

                }).success(function (data, status, headers, config) {
                    //upload를 하고 성공시 콜백처리를 success 메소드를 통해 할 수 있다.

                    manage.isUpdate = true;
                    manage.dataListLength = data;
                    manage.listAllData();

                }).error(function (data) {
                    manage.isUpdate = false;
                    $scope.$parent.loadingMain = false;
                    alert("데이터 업데이트를 실패하였습니다");

                });
            }
        };

        manage.pageLoadData = function() {
            //기존 업데이트 되어있는 데이터 정보를 불러오기 위해서 호출
            manage.listAllData();
        };

        manage.pageLoadData();
    });