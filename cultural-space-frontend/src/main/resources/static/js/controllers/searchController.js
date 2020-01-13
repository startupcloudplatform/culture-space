'use strict';

angular.module('culturalSpaceApp')
    .controller('searchMainCtrl', function ($scope, $http, $window) {

        var main = this;

        $scope.resultBodytemplateUri = 'views/portal/searchResult.html';
        $scope.resultBodyStatusTemplatUri = '/views/portal/searchResultStatus.html';
        $scope.detailBodytemplateUri = 'views/portal/searchDetail.html';

        $scope.TypeList = [];
        $scope.feeGroupList = [];
        $scope.facilityList = [];
        $scope.addressTypeList = ['전체보기','주소입력'];
        $scope.useDetailSearch = false;
        $scope.isDetailSearch = false;
        $scope.isIntegratedSearch = false;
        $scope.checkResultView = {num: 1};

        main.keyword = '';

        // 통합검색 기능
        main.clickSearchAll = function(){

            $scope.$parent.loadingMain = true;
            $scope.isIntegratedSearch = true;
            var params = {
                query:main.keyword
            };

            $http({
                method: 'GET',
                url: '/api/search/list/',
                params: params,
                headers: {'Content-Type': 'application/json; charset=utf-8'}

            }).then(function success(response) {
                $scope.facilityList = angular.copy(response.data);
                if($scope.facilityList.length === 0){
                    $scope.checkResultView.num = 3;
                }else{
                    $scope.checkResultView.num = 2;
                }
                $scope.$parent.loadingMain = false;
            }, function error(response) {
                $scope.facilityList = [];
                $scope.checkResultView.num = 3;
                $scope.$parent.loadingMain = false;
            });
        };

        // 초기화
        $scope.reset = function(){
            $window.location.reload();
        };

        // 상세검색 기능 이용
        main.searchDetail = function(){
            $scope.isIntegratedSearch = false;
            $scope.useDetailSearch = !$scope.useDetailSearch;
        };

        main.pageLoadData = function() {
            // $scope.$parent.loadingMain = true;
        };

        main.pageLoadData();


    })

    .controller('searchDetailCtrl',function ($scope, $http, $window, $uibModal, $log) {

        var detail = this;
        detail.sltType = '';
        detail.sltFee = '';
        detail.sltAddress = '';

        detail.addressList = null;
        detail.inputAddress='';

        // 종류 선택
        detail.selectType = function (type) {
            detail.sltType = type;
        };

        // 위치 선택
        detail.selectAddress = function (address) {
            if(address === $scope.addressTypeList[0]){
                detail.sltAddress = address;
            }else{
                detail.sltAddress = '';
            }
        };

        // 요금 선택
        detail.selectFee = function (fee) {
            detail.sltFee = fee;
        };

        /**
         * typeNameList 생성
         */
        detail.listByTypeNameGroup = function () {
            $http({
                method: 'GET',
                url: '/api/search/type/group',
                headers: {'Content-Type': 'application/json; charset=utf-8'}
            }).then(function success(response) {

                $scope.TypeList.push({code:0, name:'전체보기'});
                if (angular.isDefined(response.data)) {
                    for (var i = 0; i < response.data.length; i++) {
                        $scope.TypeList.push(angular.copy(response.data[i]));
                    }
                }

            }, function error(response) {
                $scope.TypeList = [];
            });
        };

        /**
         * feeList 생성
         */
        detail.listByFeeGroup = function () {

            $http({
                method: 'GET',
                url: '/api/search/fee/group',
                headers: {'Content-Type': 'application/json; charset=utf-8'}
            }).then(function success(response) {

                $scope.feeGroupList.push({code:0, name:'전체보기'});
                if (angular.isDefined(response.data)) {
                    for (var i = 0; i < response.data.length; i++) {
                        if (response.data[i].code === 0 || response.data[i].code === 1) {
                            $scope.feeGroupList.push(angular.copy(response.data[i]));
                        }
                    }
                }

            }, function error(response) {
                $scope.feeGroupList = [];
            });

        };

        /**
         * 선택한 조건들을 이용한 상세검색 ( address, fee, type )
         */
        detail.listByTypeAndFeeAndAddress = function () {

            if(detail.sltAddress === '전체보기' && detail.sltType.name === '전체보기' && detail.sltFee.name === '전체보기'){
                alert("최소 1개 이상의 상세검색 조건을 선택해 주세요!");
                return;
            }

            if(detail.sltAddress === '' && detail.sltType === '' && detail.sltFee === ''){
                alert("최소 1개 이상의 상세검색 조건을 선택해 주세요!");
                return;
            }

            $scope.$parent.$parent.$parent.$parent.loadingMain = true;
            $scope.isDetailSearch = true;

            if(detail.sltType.name === '전체보기'){
                detail.sltType.code = 0;
            }
            if(detail.sltFee.name === '전체보기'){
                detail.sltFee.code = null;
            }

            var params = {
                type: detail.sltType.code,
                free: detail.sltFee.code,
                address: detail.sltAddress === $scope.addressTypeList[0] ? null : detail.sltAddress.toString()
            };

            $http({
                method: 'GET',
                url: '/api/search/condition',
                params: params,
                headers: {'Content-Type': 'application/json; charset=utf-8'}

            }).then(function success(response) {
                $scope.facilityList = angular.copy(response.data);
                if($scope.facilityList.length === 0){
                    $scope.checkResultView.num = 3;
                }else{
                    $scope.checkResultView.num = 2;
                }
                $scope.$parent.$parent.$parent.$parent.loadingMain = false;
            }, function error(response) {

                $scope.facilityList = [];
                $scope.checkResultView.num = 3;
                $scope.$parent.$parent.$parent.$parent.loadingMain = false;

            });


        };

        // 데이터 초기화
        detail.setData = function () {
            detail.sltType = '';
            detail.sltFee = '';
            detail.sltAddress = '';
            $scope.isIntegratedSearch = false;
        };


        detail.pageLoadData = function () {

            $scope.$parent.$parent.$parent.$parent.loadingMain = true;
            detail.setData();
            if($scope.TypeList.length == 0){
                detail.listByTypeNameGroup();
            }
            if ($scope.feeGroupList.length == 0) {
                detail.listByFeeGroup();
            }
            $scope.$parent.$parent.$parent.$parent.loadingMain = false;
        };

        detail.pageLoadData();

        /**
         * 주소 검색 modal
         */

        detail.listByAddress = function(){

            $scope.$parent.$parent.$parent.$parent.loadingMain = true;

            if(detail.inputAddress === ''){
                $scope.$parent.$parent.$parent.$parent.loadingMain = false;
                alert("검색할 주소를 입력해 주세요!");
                return;
            }

            var params = {
                query: detail.inputAddress
            };

            $http({
                method: 'GET',
                url: '/api/search/address/list/',
                params: params,
                headers: {'Content-Type': 'application/json; charset=utf-8'}

            }).then(function success(response) {
                if(angular.isDefined(response.data)){
                    detail.addressList = angular.copy(response.data);
                }
                $scope.$parent.$parent.$parent.$parent.loadingMain = false;
            }, function error(response) {
                detail.addressList = null;
                $scope.$parent.$parent.$parent.$parent.loadingMain = false;
            });
        };

        var modalPopup =  function() {
            return $scope.modalInstance = $uibModal.open({
                templateUrl: "views/portal/modalAddress.html",
                scope:$scope
            });
        };

        $scope.openModalPopup = function () {

            detail.inputAddress = '';
            detail.addressList=null;
            modalPopup().result
                .then(function (data) {
                    $scope.handleSuccess(data);
                })
                .then(null, function (reason) {
                    $scope.handleDismiss(reason);
                });
        };

        // ok 버튼 선택
        $scope.yes = function () {

            if(detail.sltAddress.length > 1){
                alert("주소를 하나만 선택해 주세요!");
                return;
            }
            detail.sltAddress = detail.sltAddress.toString();
            $scope.modalInstance.close('Yes Button Clicked')
        };

        // cancel 버튼 선택
        $scope.no = function () {

            detail.sltAddress='';
            $scope.modalInstance.dismiss('No Button Clicked')
        };

        // Log Success message
        $scope.handleSuccess = function (data) {
            $log.info('Modal closed: ' + data);
        };

        // Log Dismiss message
        $scope.handleDismiss = function (reason) {
            $log.info('Modal dismissed: ' + reason);
        }

    })

    .controller('searchResultCtrl',function ($scope, $uibModal, $log) {

        var res = this;
        res.sltAddress = null;

        res.pageLoadData = function(){
        };

        /**
         * kakao map api 이용 modal
         * @param xCoord
         * @param yCoord
         * @param facilityAddress
         */
        res.loadMap = function(xCoord, yCoord, facilityAddress){

            if(xCoord === 0){
                alert("좌표 정보가 존재하지 않습니다");
                return;
            }

            $scope.sltAddrX = xCoord;
            $scope.sltAddrY = yCoord;
            res.sltAddress = facilityAddress;

            $scope.openMapPopup();
        };

        var mapPopup =  function(size) {
            return $scope.mapModalInstance = $uibModal.open({
                templateUrl: "views/portal/modalMap.html",
                controller: "searchResultCtrl",
                scope:$scope,
                size:size
            });
        };

        $scope.openMapPopup = function () {
            mapPopup('lg').result
                .then(function (data) {
                    $scope.handleSuccess(data);
                })
                .then(null, function (reason) {
                    $scope.handleDismiss(reason);
                });
        };


        // cancel 버튼 선택
        $scope.cancel = function () {
            $scope.mapModalInstance.dismiss('No Button Clicked')
        };

        // Log Success message
        $scope.handleSuccess = function (data) {
            $log.info('Modal closed: ' + data);
        };

        // Log Dismiss message
        $scope.handleDismiss = function (reason) {
            $log.info('Modal dismissed: ' + reason);
        }

        res.pageLoadData();
    });