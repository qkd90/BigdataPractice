/**
 * Created by zzl on 2016/8/2.
 */
var mTicketIndexModule = angular.module('mTicketIndexModule', ['infinite-scroll']);
mTicketIndexModule.controller('mTicketIndexCtrl', function ($scope, $http, $state, Banner, storage, Position, $rootScope) {
    $scope.IMG_DOMAIN = QINIU_BUCKET_URL;

    $scope.goToSearch = function () {
        storage.set("preUrl", "scenicList");
        $state.go("search", {});
    };

    $scope.ticketCityCode = storage.get('ticketCityCode');
    $scope.ticketCityName = storage.get('ticketCityName');

    if ($scope.ticketCityCode == null || $scope.ticketCityCode == "") {
        // 定位
        Position.getInfo("ticket", function (positionInfo) {
            $scope.ticketCity = positionInfo['ticketCity'];
            $scope.ticketCityCode = positionInfo['ticketCityCode'];
            $scope.ticketCityName = positionInfo['ticketCityName'];
            storage.set('ticketCityCode', $scope.ticketCityCode);
            storage.set('ticketCityName', $scope.ticketCityName);
        });
    }

    // 加载数据中
    $rootScope.loading = true;

    // 获取新app门票首页数据
    $http.post(yhyUrl.mTicketIndexData, {
        'scenicSearchRequest.cityIds[0]': $scope.ticketCityCode,
        'scenicSearchRequest.orderColumn': 'ranking',
        'scenicSearchRequest.orderType': 'asc'
    }).success(function (result) {
        if (result.success) {
            $scope.result = result;
            $rootScope.loading = false;
        } else {
            alert(result.msg);
        }
    }).error(function (result) {
        // ...
    });

    // 广告数据加载状态
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        // 幻灯广告
        Banner.init({
            container: '#tkei-top-slider-container',
            effect: 'fade',
            autoplay: 3000,
            pagination: '#tkei-top-slide-page'
        });
    });

    $scope.goNearTicketList = function () {
        // 定位
        $rootScope.loading = true;
        var params = {};
        storage.set('distance', 10);
        storage.set('scenicListTitle', '我的附近');
        params['distance'] = 10;
        params['scenicListTitle'] = "我的附近";
        // 定位
        Position.getInfo("ticket", function (positionInfo) {
            storage.set('ticketNearLng', positionInfo['ticketNearLng']);
            storage.set('ticketNearLat', positionInfo['ticketNearLat']);
            params['ticketNearLng'] = positionInfo['ticketNearLng'];
            params['ticketNearLat'] = positionInfo['ticketNearLat'];
            $rootScope.loading = false;
            $state.go('scenicList', {params: JSON.stringify(params)});
        });
    };




});
