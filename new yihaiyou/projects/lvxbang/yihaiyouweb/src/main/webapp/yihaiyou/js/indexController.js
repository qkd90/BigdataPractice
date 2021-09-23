/**
 * Created by dy on 2016/9/26.
 */
var indexModule = angular.module("indexModule", []);
indexModule.filter("ArrayFilter", function() {
    return function(array, beginIndex, endIndex) {
        if (beginIndex == null || beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex == null || endIndex > 0) {
            endIndex == array.length;
        }
        var newArr = [];

        if (array != null) {
            newArr = array.slice(beginIndex,endIndex);
        }
        return newArr;
    }
});
indexModule.controller("indexCtrl", function($scope, $http, $state, Banner, storage) {

    $scope.goFerryIndex = function() {
        storage.remove(yhyKey.ferrySelectedTourist);
        $state.go("ferrySearch");
    };


    $scope.topBannerAds = [];

    $http.post(yhyUrl.getHomeTopBannerAds,
        {}
    ).success(
        function(data) {
            if (data.success) {
                $scope.topBannerAds = data.ads;
            }

        }
    ).error(
        function(error) {

        }
    );

    // 广告数据加载状态
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        // 幻灯广告
        Banner.init({
            container: '#home-top-slider-container',
            effect: 'fade',
            autoplay: 3000,
            pagination: '#home-top-slide-page'
        });
    });

    $scope.goHuanguyouList = function () {
        //var paramsObj = {
        //    label:'huanguyou',
        //    labelIndex:4
        //};
        //var paramsStr = JSON.stringify(paramsObj);
        //storage.set("sailboatYachtParams", encodeURIComponent(paramsStr));
        $state.go("sailboat/list", {index: 4});
    };

    $scope.goSailboatList = function() {
        //var paramsObj = {
        //    label:'sailboat',
        //    labelIndex:3
        //};
        //var paramsStr = JSON.stringify(paramsObj);
        //storage.set("sailboatYachtParams", encodeURIComponent(paramsStr));
        $state.go("sailboat/list", {index: 3});
    };

    $scope.goYachtList = function() {
        //var paramsObj = {
        //    label:'yacht',
        //    labelIndex:2
        //};
        //var paramsStr = JSON.stringify(paramsObj);
        //storage.set("sailboatYachtParams", encodeURIComponent(paramsStr));
        $state.go("sailboat/list", {index: 2});
    }

    $scope.goCruiseShipIndex = function() {
        $state.go("cruiseShipIndex");
    }

    $scope.goSailboatYachtDetail = function(id) {
        $state.go("sailling/detail", {sailId: id});
    }

    $scope.goCruiseShipDetail = function(id) {
        $state.go("cruiseShipDetail", {id: id});
    }

    $http.post(yhyUrl.mIndexData, {
    }).success(function (result) {
            if (result.success) {
                $scope.cruiseShip = result.cruiseShip;
                $scope.cruiseShipList =  result.cruiseShipList;
                $scope.ticketList =  result.ticketList;
            }
    });

    $scope.toBookingCar = function () {
        storage.remove(yhyKey.shangcheAddress);
        storage.remove(yhyKey.xiacheAddress);
        $state.go("bookingCar");
    };

});
