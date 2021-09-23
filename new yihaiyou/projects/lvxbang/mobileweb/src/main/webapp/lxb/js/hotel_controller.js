/**
 * Created by zzl on 2016/8/3.
 */
var mHotelIndexModule = angular.module('mHotelIndexModule', ['infinite-scroll']);
mHotelIndexModule.controller('mHotelIndexCtrl', function($scope, $http, $state, Banner, storage, Position, DateUtils, HotelDate) {
    $scope.IMG_DOMAIN = LXB_KEY.IMG_DOMAIN;

    $scope.hotelCityCode = storage.get('hotelCityCode');
    $scope.hotelCityName = storage.get('hotelCityName');

    $scope.hotelPriceCondition = "价格/星级";
    $scope.hotelPlusCondition = "关键字/位置/品牌/酒店名";
    $scope.keyword = null;
    $scope.priceInfo = null;
    $scope.minHotelPrice = null;
    $scope.maxHotelPrice = null;
    $scope.starInfoArr = [];
    $scope.selectStarArr = [];
    $scope.minHotelStar = null;
    $scope.maxHotelStar = null;
    $scope.hotelRegionId = null;
    $scope.hotelRegionName = null;
    $scope.hotelBrandName = null;
    $scope.hotelBrandId = null;
    $scope.hotelServiceName = null;
    $scope.hotelServiceId = null;

    // 加载数据中
    $scope.loading = true;

    // 获取新app酒店首页数据
    $scope.getHotelIndexData = function() {
        $http.post(LXB_URL.mHotelIndexData, {cityId: $scope.hotelCityCode}).success(function (result) {
            if (result.success) {
                $scope.result = result;
                $scope.loading = false;
            } else {
                alert(result.msg);
            }
        }).error(function (result) {
            // ...
        });
    };

    // 广告数据加载状态
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        // 幻灯广告
        Banner.init({
            container: '#htei-top-slider-container',
            effect: 'fade',
            autoplay: 3000,
            pagination: '#htei-top-slide-page'
        });
    });

    if ($scope.hotelCityCode == null) {
        // 定位
        Position.getInfo('hotel', function(positionInfo) {
            $scope.hotelCity = positionInfo['hotelCity'];
            $scope.hotelCityCode = positionInfo['hotelCityCode'];
            $scope.hotelCityName = positionInfo['hotelCityName'];
            storage.set('hotelCityCode', $scope.hotelCityCode);
            storage.set('hotelCityName', $scope.hotelCityName);
            // 定位操作完成后, 获取相关酒店数据
            $scope.getHotelIndexData();
        });
    } else {
        $scope.getHotelIndexData();
    }
    // 手动定位
    $scope.getCurrentPosition = function() {
        // 定位
        Position.getInfo('hotel', function(positionInfo) {
            $scope.hotelCity = positionInfo['hotelCity'];
            $scope.hotelCityCode = positionInfo['hotelCityCode'];
            $scope.hotelCityName = positionInfo['hotelCityName'];
            storage.set('hotelCityCode', $scope.hotelCityCode);
            storage.set('hotelCityName', $scope.hotelCityName);
            // 定位操作完成后, 获取相关酒店数据
            $scope.getHotelIndexData();
        });
    };
    // 酒店日期初始化相关
    // 默认日期
    $scope.dateRange = DateUtils.getCurrentDateRange();
    var startMonth = Number($scope.dateRange[0].getMonth()) + 1;
    var endMonth = Number($scope.dateRange[1].getMonth()) + 1;
    var startDay = $scope.dateRange[0].getDate();
    var endDay = $scope.dateRange[1].getDate();
    startMonth = startMonth < 10 ?  "0" + startMonth : startMonth;
    endMonth = endMonth < 10 ? "0" + endMonth : endMonth;
    startDay = startDay < 10 ? "0" + startDay : startDay;
    endDay = endDay < 10 ? "0" + endDay : endDay;
    $scope.startDateStr = startMonth + "月" + startDay + "日";
    $scope.endDateStr = endMonth + "月" + endDay + "日";
    $scope.nights = $scope.dateRange[1].getDate() - $scope.dateRange[0].getDate();
    $scope.startDate = $scope.dateRange[0].getFullYear() + "-" + startMonth + "-" + startDay;
    $scope.endDate = $scope.dateRange[1].getFullYear() + "-" + endMonth + "-" + endDay;

    // 日期控件参数
    $scope.hotelDateOptions = {
        elementId: 'calendar',
        controllerDiv: 'hotel_index',
        suffix: 'hotel',
        isTodayValid: true,
        startDate: $scope.dateRange[0].getFullYear() + "-" + (Number($scope.dateRange[0].getMonth()) + 1) + "-" + $scope.dateRange[0].getDate(),
        endDate: $scope.dateRange[1].getFullYear() + "-" + (Number($scope.dateRange[1].getMonth()) + 1) + "-" + $scope.dateRange[1].getDate(),
        success: function(data) {
            $scope.$apply(function () {
                $scope.startDate = data.startDate;
                $scope.endDate = data.endDate;
                var startDateArr = data.startDate.split("-");
                var endDateArr = data.endDate.split("-");
                var sDate = new Date(parseInt(startDateArr[0]), parseInt(startDateArr[1]) - 1, parseInt(startDateArr[2]));
                var eDate = new Date(parseInt(endDateArr[0]), parseInt(endDateArr[1]) - 1, parseInt(endDateArr[2]));
                $scope.startDateStr = startDateArr[1] + "月" + startDateArr[2] + "日";
                $scope.endDateStr = endDateArr[1] + "月" + endDateArr[2] + "日";
                $scope.nights = Math.floor((eDate.getTime() - sDate.getTime()) / 86400000);
                HotelDate.afterSelect();
            });
        }
    };
    // 初始化日期控件
    $scope.hotelDateRange = HotelDate.init( $scope.hotelDateOptions);


    // 酒店价格, 星级条件展示
    $scope.showPriceCondition = function() {
        var info = "";
        if ($scope.priceInfo != null && $scope.priceInfo != "不限") {
            info += $scope.priceInfo;
        }
        if ($scope.starInfoArr != null && $scope.starInfoArr.length > 0) {
            info = info.length > 0 ? info + "、" : info;
            angular.forEach($scope.starInfoArr, function(starInfo) {
                info += starInfo;
                info += "、";
            });
            info = info.substring(0, info.length - 1);
        }
        if(info.length > 0) {
            $scope.hotelPriceCondition = info;
            $('#hotel_price_show').removeClass('show');
            $('#clear_hotel_price').removeClass('hide');
        } else {
            $scope.hotelPriceCondition = "价格/星级";
            $('#hotel_price_show').addClass('show');
            $('#clear_hotel_price').addClass('hide');
        }
        if ($('.price-star-area').hasClass('hide')) {
            $('.price-star-area').removeClass('hide');
        } else {
            $('.price-star-area').addClass('hide');
        }
    };

    // 酒店附加条件展示
    $scope.showHotelPlusCondition = function() {
        var info = "";
        if ($scope.keyword != null && $scope.keyword != "") {
            info = info.length > 0 ? info + "、" : info;
            info += $scope.keyword;
        }
        if ($scope.hotelRegionName != null) {
            info = info.length > 0 ? info + "、" : info;
            info += $scope.hotelRegionName;
        }
        if ($scope.hotelBrandName != null) {
            info = info.length > 0 ? info + "、" : info;
            info += $scope.hotelBrandName
        }
        if ($scope.hotelServiceName != null) {
            info = info.length > 0 ? info + "、" : info;
            info += $scope.hotelServiceName;
        }
        if (info.length > 0) {
            $scope.hotelPlusCondition = info;
            $('#hotel_plus_show').removeClass('show');
            $('#clear_hotel_plus').removeClass('hide');
        } else {
            $scope.hotelPlusCondition = "关键字/位置/品牌/酒店名";
            $('#hotel_plus_show').addClass('show');
            $('#clear_hotel_plus').addClass('hide');
        }
        $('#hotel_plus_condition_area').removeClass('active');
    };

    $scope.clearHotelPriceCondition = function() {
        $scope.hotelPriceCondition = "价格/星级";
        $scope.priceInfo = null;
        $scope.minHotelPrice = null;
        $scope.maxHotelPrice = null;
        $scope.starInfoArr = [];
        $scope.selectStarArr = [];
        $scope.minHotelStar = null;
        $scope.maxHotelStar = null;
    };

    $scope.clearHotelPlusCondition = function() {
        $scope.hotelPlusCondition = "关键字/位置/品牌/酒店名";
        $scope.keyword = null;
        $scope.hotelRegionId = null;
        $scope.hotelRegionName = null;
        $scope.hotelBrandName = null;
        $scope.hotelBrandId = null;
        $scope.hotelServiceName = null;
        $scope.hotelServiceId = null;
    };

    $scope.goHotelList = function() {
        storage.set('regionIdList', $scope.hotelRegionId);
        storage.set('brandIdList', [$scope.hotelBrandId]);
        storage.set('serviceIdList', [$scope.hotelServiceId]);
        storage.set('keyword', $scope.keyword);
        storage.set('dayRange', [$scope.startDate, $scope.endDate]);
        storage.set('cities', [$scope.hotelCityName]);
        storage.set('cityIds', [$scope.hotelCityCode]);
        if (!$scope.keyword) {
            storage.set("orderColumn", "productScore");
            storage.set("orderType", "desc");
        }
        // 价格范围
        if ($scope.minHotelPrice != null && $scope.maxHotelPrice != null) {
            storage.set('priceRange', [$scope.minHotelPrice, $scope.maxHotelPrice]);
        } else if ($scope.minHotelPrice == null && $scope.maxHotelPrice != null) {
            storage.set('priceRange', [0, $scope.maxHotelPrice]);
        } else if($scope.minHotelPrice != null && $scope.maxHotelPrice == null) {
            storage.set('priceRange', [$scope.minHotelPrice]);
        } else {
            storage.set('priceRange', [0]);
        }
        // 星级
        storage.set('star', $scope.selectStarArr[0]);
        $state.go('hotelList');
    };
});