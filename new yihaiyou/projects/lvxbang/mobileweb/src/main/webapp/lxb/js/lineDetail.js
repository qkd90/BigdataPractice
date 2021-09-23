/**
 * Created by dy on 2016/7/19.
 */
//线路详情
var lineDetailModule = angular.module('lineDetailModule', []);
lineDetailModule.controller('lineDetailCtrl', function ($scope, $http, $stateParams, $state) {
    $scope.lineId = $stateParams.id;
    $scope.completeFlag = false;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.mXianlu_detail, {
        lineId: $scope.lineId
    }).success(function (data) {
        if (data.success) {
            $scope.line = data.line;
            $scope.line.linetypepriceList[0].checked = "checked";
            changePriceDate($scope.line.linetypepriceList[0].id);
            $scope.typePriceId = $scope.line.linetypepriceList[0].id;
        }
        $scope.completeFlag = true;
        $("div.ajaxloading").hide();
    });

    $scope.toList = function () {
        var tag;
        switch ($scope.line.productAttr) {
            case "精品定制":
                tag = 1;
                break;
            case "自助游":
                tag = 2;
                break;
            case "自驾游":
                tag = 3;
                break;
            case "跟团游":
                tag = 4;
                break;
        }
        $state.go("lineList", {params: JSON.stringify({
            cityCode: $scope.line.arriveCityId,
            tag: tag
        })});
    };

    $scope.changeTypePrice = function (obj) {
        angular.forEach($scope.line.linetypepriceList, function (typePrice) {
            typePrice.checked = "";
        });
        obj.typePrice.checked = "checked";
        changePriceDate(obj.typePrice.id);
        $scope.typePriceId = obj.typePrice.id;
    };

    function changePriceDate(linetypepriceId) {
        $http.post(LXB_URL.orderDate, {
            linetypepriceId: linetypepriceId
        }).success(function (data) {
            if (data.success) {
                $scope.typePriceDate = [];
                var str = "";
                var price = 99999999999999;
                angular.forEach(data.linetypepricedates, function (priceDate, i) {
                    if (price > priceDate.discountPrice + priceDate.rebate) {
                        price = priceDate.discountPrice + priceDate.rebate;
                    }
                    if (i > 0 && i < 3) {
                        str += ",";
                    }
                    if (i < 3) {
                        str += priceDate.day.substr(5, 5).replace("-", "/");
                    } else if (i == 3) {
                        str += "...";
                    }
                });
                if (data.linetypepricedates.length == 0) {
                    price = 0;
                }
                $scope.priceDate = str;
                $scope.price = price;
            }
        });
    }
});

//线路详情行程介绍
var lineDayModule = angular.module("lineDayModule", []);
lineDayModule.controller("lineDayCtrl", function ($scope, $http, $stateParams) {
    $scope.lineId = $stateParams.lineId;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.mXianlu_day, {
        lineId: $scope.lineId
    }).success(function (data) {
        if (data.success) {
            $scope.lineDayList = data.lineDayList;
        }
        $("div.ajaxloading").hide();
    });
});

//线路详情费用说明
var lineFareDetailModule = angular.module("lineFareDetailModule", []);
lineFareDetailModule.controller("lineFareDetailCtrl", function ($scope, $http, $stateParams) {
    $scope.lineTypePriceId = $stateParams.lineTypePriceId;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.mXianlu_fare, {
        lineTypePriceId: $scope.lineTypePriceId
    }).success(function (data) {
        if (data.success) {
            $scope.quoteContainDesc = data.quoteContainDesc;
            $scope.quoteNoContainDesc = data.quoteNoContainDesc;
        }
        $("div.ajaxloading").hide();
    });
});

//线路详情预订须知
var lineBookingModule = angular.module("lineBookingModule", []);
lineBookingModule.controller("lineBookingCtrl", function ($scope, $http, $stateParams) {
    $scope.lineId = $stateParams.lineId;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.mXianlu_booking, {
        lineId: $scope.lineId
    }).success(function (data) {
        if (data.success) {
            $scope.lineExplain = data.lineExplain;
        }
        $("div.ajaxloading").hide();
    });
//回到顶部
    var timer=null;
    $("#fare_main").scroll(function(){
        if($('#fare_main').scrollTop()>600){
            $('#scrolltoTop').addClass('toshow');
        }else{
            $('#scrolltoTop').removeClass('toshow');
        }
        $('#scrolltoTop').click(function(){
            clearInterval(timer);
            $('#fare_main').animate({"scrollTop": 0}, 80);
        });
    });
});