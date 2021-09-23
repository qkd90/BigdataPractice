/**
 * Created by dy on 2016/8/10.
 */
var hotelListModule = angular.module("HotelListModule", ['infinite-scroll','angularLocalStorage']);

hotelListModule.directive("hotelPriceDir", function($state, storage) {
    return {
        restrict: 'AE',
        scope: {
            lowPrice: "=",
            highPrice: "="
        },
        link: function(scope, element) {
            element.click(function() {
                $(".j_priceList").children().removeClass("on");
                $(element).addClass("on");
                if (scope.lowPrice == scope.highPrice || scope.highPrice == 0) {
                    storage.set("priceRange", [scope.lowPrice]);
                } else {
                    storage.set("priceRange", [scope.lowPrice, scope.highPrice]);
                }
                console.log(" priceRange: "+storage.get("priceRange"));
            });
        }
    }
});

hotelListModule.directive("hotelStarDir", function($state, storage) {
    return {
        restrict: 'AE',
        scope: {
            star: "="
        },
        link: function(scope, element) {
            element.click(function() {

                var liStars = $(".j_starsList").children();
                //var brandLabelArr = $(".brand-ul").children("li").children("label");
                $.each(liStars, function(i, li) {
                    //$(li).removeClass("on");
                    $(li).removeClass("on");
                });

                $("#star_"+scope.star+"").addClass("on");

                storage.set("star", scope.star);

                console.log(" star: "+storage.get("star"));

                //$(element).addClass("on");
                //var stars = storage.get("star");
                //if (!stars || !stars.length > 0) {
                //    stars = [];
                //}

                //if (scope.star != 0) {
                //    stars.push(scope.star);
                //} else {
                //    stars = [];
                //}


               /*

                if ($("#star_"+ scope.star +"").hasClass("on")) {
                    if (scope.star != 0) {
                        $("#star_"+scope.star+"").removeClass("on");
                        //$("#star_"+scope.star+"").addClass("on");
                    } else {
                        stars = [];
                    }
                    for (var i = 0; i < stars.length; i++) {
                        if (stars[i] == scope.star) {
                            stars.splice(i, 1);
                        }
                    }
                    if (stars.length == 0) {
                        //$("#star_0").removeClass("on");
                        $("#star_0").addClass("on");
                    }
                    storage.set("stars", stars);
                } else {
                    if (scope.star != 0) {
                        $("#star_0").removeClass("on");
                        //$("#star_0").addClass("on");
                    } else if (scope.star == 0) {
                        var liStars = $(".j_starsList").children();
                        //var brandLabelArr = $(".brand-ul").children("li").children("label");
                        $.each(liStars, function(i, li) {
                            //$(li).removeClass("on");
                            $(li).removeClass("on");
                        });
                    }
                    //$("#star_"+scope.star+"").removeClass("on");
                    $("#star_"+scope.star+"").addClass("on");

                    if (scope.star != 0) {
                        stars.push(scope.star);
                    } else {
                        stars = [];
                    }
                    console.log("stars:" + stars);
                    storage.set("stars", stars);
                }

                */
            });
        }
    }
});


hotelListModule.directive("hotelOrderDir", function($state, storage) {
    return {
        restrict: 'AE',
        scope: {
            orderColumn: "=",
            orderType: "=",
            label: "="
        },
        link: function(scope, element) {
            element.click(function() {
                var orderLiArr = $(".order_ul").children();
                $.each(orderLiArr, function(i, li) {
                    $(li).removeClass("on");
                    $(li).children("label").removeClass("icon-sure");
                    $(li).children("label").addClass("icon-no-select");
                });
                $("#order_li_"+scope.label+"").addClass("on");
                $("#order_li_"+scope.label+"").children("label").removeClass("icon-no-select");
                $("#order_li_"+scope.label+"").children("label").addClass("icon-sure");
                storage.set("orderColumn", scope.orderColumn);
                storage.set("orderType", scope.orderType);

                //scope.searchHotelList();
                //scope.searchHotelList();
                console.log(" orderColumn: "+storage.get("orderColumn"));
                console.log(" orderType: "+storage.get("orderType"));
            });
        }
    }
});

hotelListModule.controller("HotelListCtrl", function($scope, $http, storage, DateUtils, HotelDate) {

    //##########声明开始###########
    $scope.shaixuanShow = false;
    $scope.xuanAreaShow = false;
    $scope.priceStarShow = false;
    $scope.tuijianShow = false;
    $scope.keyword = "";
    //##########声明结束###########

    //################初始化处理开始##############
    $scope.initStorage = function() {

        if (!storage.get("cities")) {
            storage.set("cities", ["厦门"]);
        }
        if (!storage.get("cityIds")) {
            storage.set("cityIds", [350200]);
        }
        if (!storage.get("brandIdList")) {
            storage.set("brandIdList", [0]);
        }
        if (!storage.get("serviceIdList")) {
            storage.set("serviceIdList", [0]);
        }
        if (!storage.get("regionIdList")) {
            storage.set("regionIdList", "");
        }
        if (!storage.get("dayRange")) {
            storage.set("dayRange", []);
        }
        if (!storage.get("priceRange")) {
            storage.set("priceRange", []);
        }
        if (!storage.get("orderColumn")) {
            storage.set("orderColumn", "productScore");
        }
        if (!storage.get("orderType")) {
            storage.set("orderType", "desc");
        }
        if (!storage.get("keyword")) {
            storage.set("keyword", "");
        } else {
            $scope.keyword = storage.get("keyword");
        }
    };

    $scope.initStorage();

    //################初始化处理结束##############


    //**************日历控件操作开始**************
    // 默认日期

    //storage.set("dayRange", ["2016-08-13", "2016-08-17"]);
    var dayRange = storage.get("dayRange");
    if (!dayRange || dayRange.length <=0 ) {
        $scope.dateRange = DateUtils.getCurrentDateRange();
        var startYear = Number($scope.dateRange[0].getYear());
        var endYear = Number($scope.dateRange[1].getYear());
        var startMonth = Number($scope.dateRange[0].getMonth()) + 1;
        var endMonth = Number($scope.dateRange[1].getMonth()) + 1;
        var startDay = $scope.dateRange[0].getDate();
        var endDay = $scope.dateRange[1].getDate();
        startMonth = startMonth < 10 ?  "0" + startMonth : startMonth;
        endMonth = endMonth < 10 ? "0" + endMonth : endMonth;
        startDay = startDay < 10 ? "0" + startDay : startDay;
        endDay = endDay < 10 ? "0" + endDay : endDay;
        $scope.startDateStr = startMonth + "-" + startDay;
        $scope.endDateStr = endMonth + "-" + endDay;
        $scope.nights = $scope.dateRange[1].getDate() - $scope.dateRange[0].getDate();
        var startDate = startYear + "-" + startMonth + "-" + startDay;
        var endDate = endYear + "-" + endMonth + "-" + endDay;
        storage.set("dayRange", [startDate, endDate]);
        storage.set(LXB_KEY.hotelDetailRoomFilter, {tipInDate:startDate, tipOutDate:endDate});
    } else {
        var startDate = dayRange[0];
        var endDate = dayRange[1];
        $scope.startDateStr = startDate.substr(startDate.indexOf("-") + 1, startDate.length);
        $scope.endDateStr = endDate.substr(endDate.indexOf("-") + 1, endDate.length);
        storage.set(LXB_KEY.hotelDetailRoomFilter, {tipInDate:startDate, tipOutDate:endDate});
    }



    // 日期控件
    $scope.hotelDateOptions = {
        elementId: 'calendar',
        isTodayValid: true,
        controllerDiv:"hotel_list",
        suffix:'hotel',
        //startDate: $scope.dateRange[0].getFullYear() + "-" + (Number($scope.dateRange[0].getMonth()) + 1) + "-" + $scope.dateRange[0].getDate(),
        //endDate: $scope.dateRange[1].getFullYear() + "-" + (Number($scope.dateRange[1].getMonth()) + 1) + "-" + $scope.dateRange[1].getDate(),
        startDate: dayRange[0],
        endDate: dayRange[1],
        success: function(data) {
            $scope.$apply(function () {
                $scope.startDate = data.startDate;
                $scope.endDate = data.endDate;
                var startDateArr = data.startDate.split("-");
                var endDateArr = data.endDate.split("-");
                var sDate = new Date(parseInt(startDateArr[0]), parseInt(startDateArr[1]) - 1, parseInt(startDateArr[2]));
                var eDate = new Date(parseInt(endDateArr[0]), parseInt(endDateArr[1]) - 1, parseInt(endDateArr[2]));
                $scope.startDateStr = startDateArr[1] + "-" + startDateArr[2];
                $scope.endDateStr = endDateArr[1] + "-" + endDateArr[2];
                $scope.nights = Math.floor((eDate.getTime() - sDate.getTime()) / 86400000);
                HotelDate.afterSelect();
                $scope.getHotelList();
                storage.set("dayRange", [data.startDate, data.endDate]);
                storage.set(LXB_KEY.hotelDetailRoomFilter, {tipInDate:data.startDate, tipOutDate:data.endDate});
            });
        }
    };
    // 初始化日期控件
    $scope.hotelDateRange = HotelDate.init( $scope.hotelDateOptions);

    //**************日历控件操作结束**************



    //###########底栏筛选条件弹出和隐藏处理开始###########
    $scope.clickHideFootBar = function() {
        $scope.shaixuanShow = false;
        $scope.xuanAreaShow = false;
        $scope.priceStarShow = false;
        $scope.tuijianShow = false;
        $scope.keywordShow = false;
        $("#hotelKeyword").focusout();
    }

    $scope.clickShowFootBar = function(tab) {
        if (tab == 1) {
            $scope.shaixuanShow = true;
            $scope.xuanAreaShow = false;
            $scope.priceStarShow = false;
            $scope.tuijianShow = false;
            $scope.keywordShow = false;
        } else if (tab == 2) {
            $scope.shaixuanShow = false;
            $scope.xuanAreaShow = true;
            $scope.priceStarShow = false;
            $scope.tuijianShow = false;
            $scope.keywordShow = false;
        } else if (tab == 3) {
            $scope.shaixuanShow = false;
            $scope.xuanAreaShow = false;
            $scope.priceStarShow = true;
            $scope.tuijianShow = false;
            $scope.keywordShow = false;
        } else if (tab == 4) {
            $scope.shaixuanShow = false;
            $scope.xuanAreaShow = false;
            $scope.priceStarShow = false;
            $scope.tuijianShow = true;
            $scope.keywordShow = false;
        } else if (tab == 5) {
            $scope.shaixuanShow = false;
            $scope.xuanAreaShow = false;
            $scope.priceStarShow = false;
            $scope.tuijianShow = false;
            $scope.keywordShow = true;
            $("#hotelKeyword").focus();
        }
    };



    //###########底栏筛选条件弹出和隐藏处理结束###########

    //###########底栏筛选条件内品牌服务显示控制开始###########
    $scope.selectShaixuan = function(index) {
        //$(".shaixuan_filter-nav").children().removeClass("active");
        //$("#shai_"+index+"").addClass("active");
        //$(".shaixuan_filter-content").children().addClass("hide");
        //$("#shaixuan_"+index+"").removeClass("hide");
        $("#shai_"+index+"").parent().children().removeClass("active");
        $("#shai_"+index+"").addClass("active");
        $("#shaixuan_"+index+"").parent().children().addClass("hide");
        $("#shaixuan_"+index+"").removeClass("hide");
    }

    $scope.selectRegion = function(index) {
        $("#regin_"+index+"").parent().children().removeClass("active");
        $("#regin_"+index+"").addClass("active");
        $("#selRegin_"+index+"").parent().children().addClass("hide");
        $("#selRegin_"+index+"").removeClass("hide");

    };
    //###########底栏筛选条件内品牌服务显示控制结束###########


    //************品牌开始*********************
    var brandIdList = storage.get("brandIdList");
    $scope.brandList = [];
    if (!brandIdList || !brandIdList.length > 0 || brandIdList[0] == null) {
        brandIdList = [];
        var initSelectBrand = {"brandId":0,"brandName":"不限","id": 0, "classStyle": "icon-sure"};
        $scope.brandList.push(initSelectBrand);
    } else {
        var initSelectBrand = {"brandId":0,"brandName":"不限","id": 0, "classStyle": "icon-no-select"};
        $scope.brandList.push(initSelectBrand);
    }

    $http.post(
        LXB_URL.hotelBrandList,
        {cityId:storage.get("cityIds")[0]}
    ).success(
        function(data) {
            if (data.length > 0) {
                angular.forEach(data, function(item){
                    if (brandIdList.length > 0) {
                        for (var i=0; i<brandIdList.length; i++) {
                            if (brandIdList[i] == item.brandId) {
                                item['classStyle'] = "icon-sure";
                                break;
                            } else {
                                item['classStyle'] = "icon-no-select";
                            }
                        }
                    } else {
                        item['classStyle'] = "icon-no-select";
                    }
                    $scope.brandList.push(item);
                });
            }
        }
    );
    //************品牌结束*********************

    //************服务开始*********************
    var serviceIdList = storage.get("serviceIdList");
    $scope.serviceList = [];
    if (!serviceIdList || !serviceIdList.length > 0 || serviceIdList[0] == null) {
        serviceIdList = [];
        var initSelectBrand = {id: 0, serviceId: 0, serviceName: "不限", "classStyle": "icon-sure"};
        $scope.serviceList.push(initSelectBrand);
    } else {
        var initSelectBrand = {id: 0, serviceId: 0, serviceName: "不限", "classStyle": "icon-no-select"};
        $scope.serviceList.push(initSelectBrand);
    }

    $http.post(
        LXB_URL.hotelServiceList,
        {cityId:storage.get("cityIds")[0]}
    ).success(
        function(data) {
            if (data.length > 0) {
                angular.forEach(data, function(item){
                    if (serviceIdList.length > 0) {
                        for (var i=0; i<serviceIdList.length; i++) {
                            if (serviceIdList[i] == item.serviceId) {
                                item['classStyle'] = "icon-sure";
                                break;
                            } else {
                                item['classStyle'] = "icon-no-select";
                            }
                        }
                    } else {
                        item['classStyle'] = "icon-no-select";
                    }
                    $scope.serviceList.push(item);
                });
            }
        }
    );
    //************服务结束*********************
    //************区域位置开始*********************
    var regionIdList = storage.get("regionIdList");
    $scope.regionList = [];
    if (!regionIdList || regionIdList == null) {
        regionIdList = "";
        var initSelectBrand = {id: 0, name: "不限", "classStyle": "icon-sure"};
        $scope.regionList.push(initSelectBrand);
    } else {
        var initSelectBrand = {id: 0, name: "不限", "classStyle": "icon-no-select"};
        $scope.regionList.push(initSelectBrand);
    }

    $http.post(
        LXB_URL.hotelRegionList,
        {cityId:storage.get("cityIds")[0]}
    ).success(
        function(data) {
            if (data.length > 0) {
                angular.forEach(data, function(item){
                    if (regionIdList) {
                        //for (var i=0; i<regionIdList.length; i++) {
                            if (regionIdList == item.id) {
                                item['classStyle'] = "icon-sure";
                                //break;
                            } else {
                                item['classStyle'] = "icon-no-select";
                            }
                        //}
                    } else {
                        item['classStyle'] = "icon-no-select";
                    }
                    $scope.regionList.push(item);
                });
            }
        }
    );
    //************区域位置结束*********************

    //************价格筛选初始化开始*********************
    var initHotelPriceList = [
        {"lowPrice":0, "highPrice":0, "title": "不限", "priceClass": 'on', "brClass": ''},
        {"lowPrice":0, "highPrice":150, "title": "￥150以下", "priceClass": '', "brClass": ''},
        {"lowPrice":150, "highPrice":300, "title": "￥150-￥300", "priceClass": '', "brClass": ''},
        {"lowPrice":300, "highPrice":450, "title": "￥301-￥450", "priceClass": '', "brClass": ''},
        {"lowPrice":450, "highPrice":600, "title": "￥451-￥600", "priceClass": '', "brClass": 'br'},
        {"lowPrice":600, "highPrice":1000, "title": "￥600-￥1000", "priceClass": '', "brClass": ''},
        {"lowPrice":1000, "highPrice":0, "title": "￥1000以上", "priceClass": '', "brClass": ''},
    ];

    var storageHotelPriceList = storage.get("priceRange");
    $scope.hotelPriceList = [];
    if (storageHotelPriceList.length == 1) {
        $.each(initHotelPriceList, function(i, item){
            if (storageHotelPriceList[0] == item.lowPrice && item.highPrice == 0 || storageHotelPriceList[0] == item.lowPrice && item.highPrice == 0) {
                item.priceClass = "on";
            } else {
                item.priceClass = "";
            }
            $scope.hotelPriceList.push(item);
        });
    } else {
        $.each(initHotelPriceList, function(i, item){
            if (storageHotelPriceList[0] == item.lowPrice) {
                item.priceClass = "on";
            } else {
                item.priceClass = "";
            }
            $scope.hotelPriceList.push(item);
        });
    }

    //************价格筛选初始化结束*********************

    //************星级筛选初始化开始*********************

    var initHotelStarList = [
        {"star":0, "title": "不限", "starClass": 'on', "brClass": ''},
        {"star":1, "title": "经济", "starClass": '', "brClass": ''},
        {"star":2, "title": "二星及其他", "starClass": '', "brClass": 'br'},
        {"star":3, "title": "三星/舒适", "starClass": '', "brClass": ''},
        {"star":4, "title": "四星/高档", "starClass": '', "brClass": ''},
        {"star":5, "title": "五星/豪华", "starClass": '', "brClass": ''}
    ];
    var storageHotelStar = 0;
    if (storage.get("star") != "undefined" && storage.get("star") != null) {
        storageHotelStar = storage.get("star");
    } else {
        storage.set("star", 0);
    }
    $scope.hotelStarList = [];
    $.each(initHotelStarList, function(i, item){
        if (storageHotelStar == item.star) {
            item.starClass = "on";
        } else {
            item.starClass = "";
        }
        $scope.hotelStarList.push(item);
    });
    //************星级筛选初始化结束*********************

    //************排序筛选初始化开始*********************
    var initHotelOrderList = [
        {"label":0, "title": "推荐排序", "orderColumn": "productScore", "orderType":"desc", "onClass":"on", "selClass": 'icon-sure'},
        {"label":1, "title": "价格 低->高", "orderColumn": "price", "orderType":"asc", "onClass":"", "selClass": 'icon-no-select'},
        {"label":2, "title": "价格 高->低", "orderColumn": "price", "orderType":"desc", "onClass":"", "selClass": 'icon-no-select'},
        {"label":3, "title": "星级 高->低", "orderColumn": "star", "orderType":"asc", "onClass":"", "selClass": 'icon-no-select'},
        {"label":4, "title": "星级 低->高", "orderColumn": "star", "orderType":"desc", "onClass":"", "selClass": 'icon-no-select'}
    ];
    $scope.hotelOrderList = [];

    var orderColumn = storage.get("orderColumn");
    var orderType = storage.get("orderType");

    $.each(initHotelOrderList, function(i, item){
        if (orderColumn == item.orderColumn && orderType == item.orderType) {
            item.onClass = "on";
            item.selClass = "icon-sure";
        } else {
            item.onClass = "";
            item.selClass = "icon-no-select"
        }
        $scope.hotelOrderList.push(item);
    });


    $scope.selOrderLi = function(label, orderColumn, orderType) {

        var orderLiArr = $(".order_ul").children();
        $.each(orderLiArr, function(i, li) {
            $(li).removeClass("on");
            $(li).children("label").removeClass("icon-sure");
            $(li).children("label").addClass("icon-no-select");
        });
        $("#order_li_"+label+"").addClass("on");
        $("#order_li_"+label+"").children("label").removeClass("icon-no-select");
        $("#order_li_"+label+"").children("label").addClass("icon-sure");
        storage.set("orderColumn", orderColumn);
        storage.set("orderType", orderType);

        //searchHotelList();
        $scope.searchHotelList();
        console.log(" orderColumn: "+storage.get("orderColumn"));
        console.log(" orderType: "+storage.get("orderType"));
    };


    //************排序筛选初始化结束*********************


    //%%%%%%%%%%%%%%关键词操作开始%%%%%%%%%%%%%%%%%%%%

    $scope.deleteKeyword = function() {
        $scope.keyword = "";
    }

    $scope.backKeyword = function() {
        $scope.keyword = "";
        $scope.keywordShow = false;
    }

    $scope.hotelKeywordList = storage.get("hotelKeywordList");
    if (!$scope.hotelKeywordList) {
        $scope.hotelKeywordList = [];
    }
    $scope.searchHotelListBykeyword = function(keyword) {
        if (keyword) {
            $scope.keyword = keyword;
        }
        if ($scope.keyword) {
            storage.set("orderColumn", "");
            storage.set("orderType", "");
            $("#order_li_0").removeClass("on");
            $("#order_li_0").children("label").removeClass("icon-sure");
            $("#order_li_0").children("label").addClass("icon-no-select");
        }
        var tempKeywordList = [];
        for (var i = $scope.hotelKeywordList.length - 1; i >= 0; i-- ) {
            tempKeywordList.push($scope.hotelKeywordList[i])
        }
        $scope.hotelKeywordList = [];
        if ($scope.keyword.length > 0) {
            for (var i=0; i<tempKeywordList.length; i++) {
                if (tempKeywordList[i] == $scope.keyword) {
                    tempKeywordList.splice(i, 1);
                }
            }
            tempKeywordList.push($scope.keyword);
        }
        for (var i = tempKeywordList.length - 1; i >= 0; i-- ) {
            if ($scope.hotelKeywordList.length >= 5) {
                break;
            } else {
                $scope.hotelKeywordList.push(tempKeywordList[i])
            }
        }
        console.log($scope.hotelKeywordList);
        $scope.searchHotelList();
        storage.set("hotelKeywordList", $scope.hotelKeywordList)
    };

    $scope.clearKeywordList = function() {
        $scope.hotelKeywordList = [];
        storage.set("hotelKeywordList", [])
    };

    //%%%%%%%%%%%%%%关键词操作结束%%%%%%%%%%%%%%%%%%%%

    $scope.searchHotelList = function() {
        $scope.page = 1;
        $scope.nomore = false;
        $scope.loading = false;
        $scope.hotelList = [];
        $scope.getHotelList ();
        $scope.clickHideFootBar();
    };


    //************获取酒店数据列表开始************
    $scope.page = 1;
    $scope.nomore = false;
    $scope.loading = false;
    $scope.hotelList = [];
    $scope.getHotelList = function() {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;

        var params = {};

        if (storage.get("cities")) {
            params['hotelSearchRequest.cities'] = storage.get("cities");
        } else {
            params['hotelSearchRequest.cities'] = ["厦门"];
        }

        if (storage.get("cityIds")) {
            params['hotelSearchRequest.cityIds'] = storage.get("cityIds");
        } else {
            params['hotelSearchRequest.cityIds'] = [350200];
        }

        if (storage.get("star") != "undefined" && storage.get("star") != null) {
            if (storage.get("star") != 0) {
                params['hotelSearchRequest.star'] = storage.get("star");
            }
        }
        //else {
        //    params['hotelSearchRequest.star'] = 0;
        //}

        if (storage.get("priceRange")) {
            if (storage.get("priceRange").length > 1 || storage.get("priceRange")[0] >= 1000) {
                params['hotelSearchRequest.priceRange'] = storage.get("priceRange");
            }
        }

        if (storage.get("dayRange")) {
            var dateRangeArr = storage.get("dayRange");
            params['hotelSearchRequest.startDate'] = dateRangeArr[0];
            params['hotelSearchRequest.endDate'] = dateRangeArr[1];
        }

        if ($scope.keyword) {
            params['hotelSearchRequest.name'] = $scope.keyword;
        } else {
            if (storage.get("orderColumn")) {
                params['hotelSearchRequest.orderColumn'] = storage.get("orderColumn");
            }

            if (storage.get("orderType")) {
                params['hotelSearchRequest.orderType'] = storage.get("orderType");
            }
        }

        if (storage.get("regionIdList")) {
            params['hotelSearchRequest.region'] = storage.get("regionIdList");
        }

        if (storage.get("brandIdList") && storage.get("brandIdList")[0] != null) {

            params['hotelSearchRequest.brands'] = storage.get("brandIdList");
        }

        if (storage.get("serviceIdList") && storage.get("serviceIdList")[0] != null) {
            params['hotelSearchRequest.serviceAmenities'] = storage.get("serviceIdList");
        }

        //if (storage.get("dayRange")) {
        //    var dateRangeArr = storage.get("dayRange");
        //    params['hotelSearchRequest.startDate'] = dateRangeArr[0];
        //    params['hotelSearchRequest.endDate'] = dateRangeArr[1];
        //}

        params['pageNo'] = $scope.page;
        params['pageSize'] = 10;

        //var cities = ["厦门"];
        //var cityIds = [350200];
        //var star = storage.get("star");
        //var orderColumn = storage.get("orderColumn");
        //var orderType = storage.get("orderType");
        //var regoins = storage.get("regionIdList");
        //var brands = storage.get("brandIdList");
        //var serviceAmenities = storage.get("serviceIdList");
        //var dateRangeArr = storage.get("dayRange");
        //var keyword = $scope.keyword;
        $http.post(
            LXB_URL.hotelList,
            params
        ).success(
            function(data) {
                if (data.success) {
                    angular.forEach(data.hotelList, function (item) {
                        $scope.hotelList.push(item);
                    });
                    $scope.page++;
                    $scope.nomore = data.nomore;
                }
                $scope.loading = false;
            console.log(data);
            }
        ).error(
            function (data) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message:'系统错误'
                })
            }
        );

    };
});