/**
 * 这里是游记列表模块
 */
var YoujiModule = angular.module("YoujiModule", []);
YoujiModule.controller('YoujiListCtrl', function ($scope, $http) {
    var req = {
        method: 'GET',
        url: GetUrl.yjlist
    };
    $http(req).success(function (data) {
        $scope.yjlists = data.data;
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        })
    });
});
/**
 * 这里是游记详情模块
 */
var YjdetailModule = angular.module("YjdetailModule", []);
YjdetailModule.controller('YjdetailCtrl', function ($scope, $http) {
    var req = {
        method: 'GET',
        url: GetUrl.yjdetail
    };
    $http(req).success(function (data) {
        $scope.yjdetails = data.data;
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        })
    });
});

/**
 * 这里是线路详情模块
 */
var xingxianluinfoModule = angular.module("xingxianluinfoModule", []);
xingxianluinfoModule.controller('xingxianluinfoCtrl', function ($scope, $rootScope, $http, $cookieStore, $stateParams, $location, $anchorScroll, $state, storage, Wechatpay) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.scenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.jiaotong = storage.get(keys.jiaotong) == null ? [] : storage.get(keys.jiaotong);
    $scope.user = storage.get(keys.user);
    $scope.hotels = storage.get(keys.jiudian) == null ? [] : storage.get(keys.jiudian);
    $scope.back = '';
    $scope.loading = true;
    $scope.isWeixin = Wechatpay.isWeiXin();
    var totalDay = 0;
    var url = $cookieStore.get('url');
    if ($rootScope.previousState.name == 'xing-jiudian') {
        back = 'xing-jiudian';
    }
    function buildPlan() {
        var plan = {userId: $scope.user.id};
        var days = [];
        var cityName = "";
        for (var i = 0; i < $scope.scenics.length; i++) {
            var dayScenics = $scope.scenics[i];
            if (cityName.indexOf(dayScenics.city.name) == -1) {
                if (cityName.length == 0) {
                    cityName = dayScenics.city.name;
                } else {
                    cityName = "," + dayScenics.city.name;
                }
            }
            if (i == 0) {
                plan.cover = dayScenics.tripList[0].cover;
            }
            days[i] = {
                cityId: dayScenics.city.id,
                cityName: dayScenics.city.name,
                day: i + 1,
                trips: dayScenics.tripList
            }
        }
        plan.days = days;
        plan.viewNum = 1;
        plan.name = cityName + $scope.scenics.length + "日游";
        plan.startDate = $scope.startDay;
        plan.planDay = $scope.scenics.length;
        plan.quoteNum = 0;
        $scope.xldetails = plan;
        $scope.loading = false;
    }

    if ($rootScope.previousState.name == 'xing-jiudian' || $stateParams.id == -1) {
        buildPlan();
    } else {
        $http.post(GetUrl.xldetail, {
            planId: $stateParams.id
        }).success(function (data) {
            if (data.success) {
                $scope.loading = false;
                $scope.xldetails = data.plan;
            } else {
                bootbox.alert(data.errMsg, function () {
                    history.go(-1);
                });
            }
        }).error(function (data) {
            buildPlan();
        });
    }

    $scope.updateName = function () {
        var request = {
            planId: $scope.xldetails.id,
            planName: $scope.xldetails.name
        };
        $state.go("xing-biaoti", {request: JSON.stringify(request)});
    };

    $scope.jump = function (id) {
        $location.hash(id);
        $anchorScroll.yOffset = 50;
        $anchorScroll();
    };

    function getTraffic() {
        var traffics = [];
        //for (var j = 0; j < $scope.jiaotong.length; j++) {
        //    var jiaotong = $scope.jiaotong[j];
        //    if (city.id == jiaotong.toCityId) {
        //        for (var tl = 0; tl < jiaotong.traffics.length; tl++) {
        //            var trafficItem = jiaotong.traffics[tl];
        //            if (trafficItem.selected) {
        //                traffics.push({
        //                    key: jiaotong.fromCityId + "##" + jiaotong.toCityId + "##" + trafficItem.trafficType + "##" + jiaotong.startDate.replace(/\-/g, ''),
        //                    trafficHash: trafficItem.trafficHash,
        //                    priceHash: trafficItem.selectPrice.priceHash
        //                });
        //                break;
        //            }
        //        }
        //        for (var tl = 0; tl < jiaotong.returnTraffics.length; tl++) {
        //            var trafficItem = jiaotong.returnTraffics[tl];
        //            if (trafficItem.selected) {
        //                traffics.push({
        //                    key: jiaotong.toCityId + "##" + $scope.fromCity.id + "##" + trafficItem.trafficType + "##" + trafficItem.leaveDate.replace(/\-/g, ''),
        //                    trafficHash: trafficItem.trafficHash,
        //                    priceHash: trafficItem.selectPrice.priceHash
        //                });
        //                break;
        //            }
        //        }
        //    }
        //}
        return traffics;
    }
    function getDay() {


        //for (var i = 0; i < $scope.scenics.length; i++) {
        //    var item = $scope.scenics[i];
        //    if (item.fromCity != null) {
        //        city = {};
        //        city.day = getCity(item.city.id).day;
        //        city.cityId = item.city.id;
        //        city.tripList = [];
        //        plans.push(city);
        //    }
        //    var result = [];
        //    for (var j = 0; j < item.tripList.length; j++) {
        //        result.push({
        //            id: item.tripList[j].id,
        //            ranking: item.tripList[j].ranking
        //        });
        //    }
        //    city.tripList = city.tripList.concat(result);
        //}


        var days = [];
        for (var i = 0; i < $scope.scenics.length; i++) {
            var item = $scope.scenics[i];
            var trips = [];
            for (var j = 0; j < item.tripList.length; j++) {
                trips.push({
                    id: 0,
                    scenicId: item.tripList[j].id,
                    type: 1
                });
            }
            days.push({
                cityId: item.city.id,
                trips: trips.reverse()
            });
        }
        return days;
    }

    function getHotel() {
        var hotels = [];
        $.each($scope.hotels, function (i, hotel) {
            if (hotel.checked) {
                hotels.push({
                    name: hotel.name,
                    payType: hotel.payType,
                    hotelId: hotel.id,
                    priceId: hotel.priceId,
                    startDate: hotel.startDate,
                    leaveDate: hotel.endDate
                });
            }
        });
        return hotels;
    }

    function getPlan() {
        var plan = [];
        for (var i = 0; i < $scope.scenics.length; i++) {
            var item = $scope.scenics[i];
            var scenicIds = [];
            for (var j = 0; j < item.tripList.length; j++) {
                scenicIds.push(item.tripList[j].id);
            }
            plan.push({
                cityId: item.city.id,
                cityName: item.city.name,
                day: item.day,
                scenicIds: scenicIds
            });
        }
        return plan;
    }

    function getTitle() {
        var title = "";
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            var city = $scope.selectCitys[i];
            if (i == $scope.selectCitys.length - 1) {
                title += city.name;
            } else {
                title += city.name + "、";
            }
            totalDay += parseInt(city.day);
        }
        title += totalDay + '日游';
        return title;
    }


    $scope.toOrder = function () {
        if(back == 'xing-jiudian'){
            $("div.ajaxloading").show();
            $http.post(GetUrl.planOrder, {
                    json: JSON.stringify({
                        name: getTitle(),
                        playDate: $scope.startDay,
                        traffic: getTraffic(),
                        hotel: getHotel(),
                        plan: getPlan()
                    })
                }
            ).success(function (data) {
                if (data.success != null && !data.success && data.nologin != null && data.nologin) {
                    bootbox.alert('请先去登录', function () {
                        //location.href = '/#/login?url=' + document.location.href;
                        $state.go('login');
                    });
                } else {
                    if (data.success) {
                        storage.set(keys.order, data.order);
                        $state.go('xing-xianlu-order-info', {planIdorderId: JSON.stringify({})});
                    } else {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: data.errorMsg + "<br/>请点击返回重新查询交通"
                        });
                    }
                }
                $("div.ajaxloading").hide();
            }).error(function (data) {
                $("div.ajaxloading").hide();
                alert(data.errorMsg);
            });
        }else{
            var planIdorderId = {};
            planIdorderId.planId = $scope.xldetails.id;
            $state.go("xing-xianlu-order-info", {planIdorderId: JSON.stringify(planIdorderId)});
        }
    };

    $scope.share = function () {
        var msg = {};
        msg.href = apihost + '/#/xing-xianlu-info/' + $scope.xldetails.id;
        msg.title = $scope.xldetails.name;
        msg.content = $scope.xldetails.name + ' ' + $scope.xldetails.startDate + '出发';
        Share.shareHref(msg);
    };

    $scope.quotePlan = function () {
        $scope.loading = true;
        $http.post(GetUrl.quotePlan, {
            planId: $scope.xldetails.id
        }).success(function (data) {
            if (data.success) {
                var length = data.result.data.length;
                for (var i = 0; i < length; i++) {
                    if (data.result.data[i].tripList == null || data.result.data[i].tripList.length == 0) {
                        data.result.data.splice(i, 1);
                        i--;
                        length--;
                    }
                }
                storage.set(keys.optimizeScenics, data.result.data);
                storage.set(keys.addNodes, data.result.addNodes == undefined ? [] : data.result.addNodes);
                storage.set(keys.removeNodes, data.result.removeNodes == undefined ? [] : data.result.removeNodes);
                var selectCitys = [];
                angular.forEach(data.result.data, function (day) {
                    var flag = false;
                    angular.forEach(selectCitys, function (city) {
                        if (day.city.id == city.id) {
                            city.day += 1;
                            flag = true;
                            return false;
                        }
                    });
                    if (!flag) {
                        var city = day.city;
                        city.day = 1;
                        selectCitys.push(city);
                    }
                });
                $scope.loading = false;
                storage.set(keys.selectCitys, selectCitys);
                $state.go("xing-gailan", {fromQuote: true});
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    };

    $("#yjcontainer").delegate(".viewMore", "click", function () {
        $(this).siblings(".moreinfo").css({'height': 'auto', 'overflow': 'none'});
        $(this).hide();
        $(this).siblings(".viewMoreclose").removeClass("hidden");
    });
    $("#yjcontainer").delegate(".viewMoreclose", "click", function () {
        if ($(this).siblings(".moreinfo").hasClass("hotelinfo")) {
            $(this).siblings(".moreinfo").css({'height': '352px', 'overflow': 'hidden'});
        } else {
            $(this).siblings(".moreinfo").css({'height': '352px', 'overflow': 'hidden'});
        }
        $(this).siblings(".viewMore").show();
        $(this).addClass("hidden");
    });
});
/**
 * 修改线路标题
 * @type {angular.Module|*}
 */
var xingbiaotiModule = angular.module("xingbiaotiModule", []);
xingbiaotiModule.controller("xingbiaotiCtrl", function ($scope, $http, $stateParams, $state, Check) {
    $scope.request = $stateParams.request == undefined ? {} : JSON.parse($stateParams.request);

    $scope.updateName = function () {
        if ($scope.request.planName == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入标题！"
            });
            return;
        }
        $http.post(GetUrl.updatePlanName, {
            planId: $scope.request.planId,
            planName: $scope.request.planName
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    history.go(-1);
                }
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            })
        });
    }
});
/**
 * 我的 模块
 */
var MyuserModule = angular.module('MyuserModule', ['angularLocalStorage']);
MyuserModule.controller('MyuserCtrl', ['$scope', '$http', '$location', 'storage', 'MyStorage', function ($scope, $http, $location, storage, MyStorage, Check) {
    $scope.user = storage.get(keys.user) == null ? {} : storage.get(keys.user);
    $scope.isWeixin = navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == "micromessenger";
    $http.post(GetUrl.userinfo).success(function (data) {
        if (data.success) {
            $scope.user = data.user;
            storage.set(keys.user, data.user);
        }
        $scope.login = data.success;
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.errorMsg
        });
    });

    $scope.logout = function () {
        $http.post(GetUrl.logout).success(function () {
            $scope.login = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.errorMsg
            });
        });
    };
    $scope.clean = function () {
        MyStorage.reset();
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: '清空完毕'
        });
    };

    $scope.homePage = function () {
        location.href = GetUrl.homePage;
    };
    //if (!$scope.user.id) {
    //    location.href = '/#/login';
    //} else {
    //
    //}
    //var req = {
    //    method: 'GET',
    //    url: GetUrl.myuser
    //};;
    //$http(req).success(function (data) {
    //    $scope.myusers = JSON.parse(data.data);
    //}).error(function (data) {
    //    alert(data.msg)
    //});
}]);
/**
 * 我的线路 模块
 */
var myxianluModule = angular.module('myxianluModule', ['angularLocalStorage', 'infinite-scroll']);
myxianluModule.controller('myxianluCtrl', ['$scope', '$http', '$location', 'storage', 'Check', function ($scope, $http, $location, storage, Check) {
    $scope.user = storage.get(keys.user) == null ? {} : storage.get(keys.user);
    $scope.pageNo = 1;
    $scope.loading = false;
    $scope.nomore = false;
    $scope.data = [];

    $scope.planList = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(GetUrl.myxianlu, {
                pageNo: $scope.pageNo,
                pageSize: 10
            }
        ).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    angular.forEach(data.planList, function (plan) {
                        $scope.data.push(plan);
                    });
                    $scope.pageNo++;
                    $scope.nomore = data.nomore;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.errorMsg
            });
        });
    };
    $scope.deleteRow = function (obj) {
        bootbox.confirm("确定删除此线路！", function (result) {
            if (result) {
                var planId = obj.myxianlu.id;
                $http.post(GetUrl.delxianlu, {
                    planId: planId
                }).success(function (data) {
                    if (data.success) {
                        angular.forEach($scope.data, function (plan, i, data) {
                            if (plan.id == planId) {
                                data.splice(i, 1);
                            }
                        });
                    }
                }).error(function (data) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: data.errorMsg
                    });
                });
            }
        });
    }
}]);
/**
 * 我的旅途--印象 模块
 */
//var mylvtuModule = angular.module('mylvtuModule', ['angularLocalStorage']);
//mylvtuModule.controller('mylvtuCtrl', ['$scope', '$http', '$location', 'storage', function ($scope, $http, $location, storage) {
//    $scope.user = storage.get(keys.user) == null ? {} : storage.get(keys.user);
//    if (!$scope.user.id) {
//        location.href = '/#/login';
//    } else {
//        $http.post(GetUrl.mylvtu, {
//                id: $scope.id,
//                placeName: $scope.placeName,
//                targetId: $scope.targetId,
//                placeType: $scope.placeType,
//                content: $scope.content,
//                userId: $scope.user.id,
//                type: $scope.type
//            }
//        ).success(function (data) {
//                if (data.success) {
//                    $scope.data = data;
//                }
//            }).error(function (data) {
//                alert(data.errorMsg);
//            });
//    }
//
//    $scope.deleteRow = function(obj){
//
//    }
//}]);
/**
 * 我的印象
 * @type {angular.Module|*}
 */
var myltModule = angular.module("myltModule", ['infinite-scroll']);
myltModule.controller('myltCtrl', function ($scope, $http, Check) {
    $scope.pageNo = 1;
    $scope.pageSize = 10;
    $scope.loading = false;
    $scope.nomore = false;
    $scope.yxlist = [];

    $scope.impressionList = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(GetUrl.mylvtu, {
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    angular.forEach(data.impressionList, function (impr) {
                        $scope.yxlist.push(impr);
                    });
                    $scope.nomore = data.nomore;
                    if (!$scope.nomore) {
                        $scope.pageNo += 1;
                    }
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    };

    $scope.deleteRow = function (obj) {
        bootbox.confirm("确定删除此印象！", function (result) {
            if (result) {
                var impression = obj.data;
                $http.post(GetUrl.delImpr, {
                    imprId: impression.id
                }).success(function (data) {
                    if (Check.loginCheck(data)) {
                        if (data.success) {
                            angular.forEach($scope.yxlist, function (impr, i, list) {
                                if (impr.id == impression.id) {
                                    list.splice(i, 1);
                                }
                            })
                        }
                    }
                }).error(function (data) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: data.msg
                    });
                });
            }
        });
    }
});

/**
 * 我的旅途--游记 模块
 */
var mylvtuyoujiModule = angular.module("mylvtuyoujiModule", []);
mylvtuyoujiModule.controller('mylvtuyoujiCtrl', function ($scope, $http) {
    var req = {
        method: 'GET',
        url: GetUrl.mylvtuyouji
    };
    $http(req).success(function (data) {
        $scope.mylvtuyoujis = data.data;
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        })
    });

    //上拉刷新加载其他页
    $(function () {
        var counter = 0;
        // 每页展示数量
        var num = 1;
        var pageStart = 0, pageEnd = 0;

        // dropload
        $('.lxbcontainer').dropload({
            scrollArea: window,
            domDown: {
                domClass: 'dropload-down',
                domRefresh: '<div class="dropload-refresh">↑上拉加载更多</div>',
                domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
                domNoData: '<div class="dropload-noData">暂无数据</div>'
            },
            loadDownFn: function (me) {
                $.ajax({
                    type: 'GET',
                    url: '../data/mylvtuyouji.json',
                    dataType: 'json',
                    success: function (data) {
                        var result = '';
                        counter++;
                        pageEnd = num * counter;
                        pageStart = pageEnd - num;

                        for (var i = pageStart; i < pageEnd; i++) {
                            result += '<div class="my-lt-yjlist clearfix">'
                                + '<p class="clearfix thumb"><img src="' + data.lists[i].image + '" class="img-responsive" /></p>'
                                + '<a ng-click="deleteRow(this);" class="trash img-circle"><span><img src="../images/ico/trash.png" class="img-responsive center-block" /></span></a>'
                                + '<h2 class="text-center"><a ui-sref="{{data.url}}">' + data.lists[i].title + '</a></h2>'
                                + '<div class="info">'
                                + '<span class="view">' + data.lists[i].view + '</span>'
                                + '<span class="time">' + data.lists[i].time + '</span>'
                                + '</div></div>';
                            if ((i + 1) >= data.lists.length) {
                                // 锁定
                                me.lock();
                                // 无数据
                                me.noData();
                                break;
                            }
                        }
                        // 为了测试，延迟1秒加载
                        setTimeout(function () {
                            $('.myltlist').append(result);
                            // 每次数据加载完，必须重置
                            me.resetload();
                        }, 1000);
                    },
                    error: function (xhr, type) {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: 'Ajax error!'
                        });
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            }
        });
    });

});


/**
 * 我的订单 模块
 */
//var myorderModule = angular.module('myorderModule', ['angularLocalStorage']);
//myorderModule.controller('myorderCtrl', ['$scope', '$http', '$location', 'storage', function ($scope, $http, $location, storage) {
//    $scope.user = storage.get(keys.user) == null ? {} : storage.get(keys.user);
//    if (!$scope.user.id) {
//        location.href = '/#/login';
//    } else {
//        $http.post(GetUrl.myorder, {
//                userId: $scope.user.id,
//                status: 1,
//                pageNo: 1,
//                pageSize: 10
//            }
//        ).success(function (data) {
//                if (data.success) {
//                    $scope.data = data;
//                }
//            }).error(function (data) {
//                alert(data.errorMsg);
//            });
//    }
//
//    $scope.deleteRow = function(obj){
//
//    }
//}]);

/**
 * 我的订单--已支付 模块
 */
var myorderModule = angular.module("myorderModule", ['infinite-scroll']);
myorderModule.controller('myorderCtrl', function ($scope, $http, Check, $state) {
    $scope.pageNo = 1;
    $scope.loading = false;
    $scope.nomore = false;
    $scope.myorders = [];

    $scope.orderList = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(GetUrl.myorder, {
            status: 1,
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    angular.forEach(data.orderList, function (order) {
                        $scope.myorders.push(order);
                    });
                    $scope.pageNo++;
                    $scope.nomore = data.nomore;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            })
        });
    };
    $scope.deleteRow = function (obj) {
        bootbox.confirm("确定删除此订单！", function (result) {
            if (result) {
                var orderId = obj.data.id;
                $http.post(GetUrl.delorder, {
                    orderId: orderId
                }).success(function (data) {
                    if (data.success) {
                        angular.forEach($scope.myorders, function (order, i, myorders) {
                            if (order.id == orderId) {
                                myorders.splice(i, 1);
                            }
                        });
                    }
                }).error(function (data) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: data.errorMsg
                    });
                });
            }
        });
    };

    $scope.orderDetail = function (type, id) {
        if (type == "line") {
            location.href = GetUrl.lineOrderDetail + id;
        } else if (type == "ticket") {
            location.href = GetUrl.ticketOrderDetail + id;
        } else if (type == "hotel") {
            location.href = GetUrl.hotelOrderDetail + id;
        } else {
            $state.go("my-order-info", {id: id});
        }
    };
});

/**
 * 我的订单--待支付 模块
 */
var myorderdModule = angular.module("myorderdModule", ['infinite-scroll']);
myorderdModule.controller('myorderdCtrl', function ($scope, $http, Check, $state) {
    $scope.pageNo = 1;
    $scope.loading = false;
    $scope.nomore = false;
    $scope.myorders = [];

    $scope.orderList = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(GetUrl.myorder, {
            status: 2,
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    angular.forEach(data.orderList, function (order) {
                        $scope.myorders.push(order);
                    });
                    $scope.pageNo++;
                    $scope.nomore = data.nomore;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            })
        });
    };
    $scope.deleteRow = function (obj) {
        bootbox.confirm("确定删除此订单！", function (result) {
            if (result) {
                var orderId = obj.data.id;
                $http.post(GetUrl.delorder, {
                    orderId: orderId
                }).success(function (data) {
                    if (data.success) {
                        angular.forEach($scope.myorders, function (order, i, myorders) {
                            if (order.id == orderId) {
                                myorders.splice(i, 1);
                            }
                        });
                    }
                }).error(function (data) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: data.errorMsg
                    });
                });
            }
        });
    };

    $scope.orderDetail = function (type, id) {
        if (type == "line") {
            location.href = GetUrl.lineOrderDetail + id;
        } else if (type == "ticket") {
            location.href = GetUrl.ticketOrderDetail + id;
        } else if (type == "hotel") {
            location.href = GetUrl.hotelOrderDetail + id;
        } else {
            $state.go("my-order-info", {id: id});
        }
    };
});

/**
 * 我的订单--已退款 模块
 */
var myordertModule = angular.module("myordertModule", ['infinite-scroll']);
myordertModule.controller('myordertCtrl', function ($scope, $http, Check, $state) {
    $scope.pageNo = 1;
    $scope.loading = false;
    $scope.nomore = false;
    $scope.myorders = [];

    $scope.orderList = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(GetUrl.myorder, {
            status: 3,
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    angular.forEach(data.orderList, function (order) {
                        $scope.myorders.push(order);
                    });
                    $scope.pageNo++;
                    $scope.nomore = data.nomore;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            })
        });
    };
    $scope.deleteRow = function (obj) {
        bootbox.confirm("确定删除次订单！", function (result) {
            if (result) {
                var orderId = obj.data.id;
                $http.post(GetUrl.delorder, {
                    orderId: orderId
                }).success(function (data) {
                    if (data.success) {
                        angular.forEach($scope.myorders, function (order, i, myorders) {
                            if (order.id == orderId) {
                                myorders.splice(i, 1);
                            }
                        });
                    }
                }).error(function (data) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: data.errorMsg
                    });
                });
            }
        });
    };

    $scope.orderDetail = function (type, id) {
        if (type == "line") {
            location.href = GetUrl.lineOrderDetail + id;
        } else if (type == "ticket") {
            location.href = GetUrl.ticketOrderDetail + id;
        } else if (type == "hotel") {
            location.href = GetUrl.hotelOrderDetail + id;
        } else {
            $state.go("my-order-info", {id: id});
        }
    };
});

/**
 * 我的订单详情 模块
 */
var myorderinfoModule = angular.module("myorderinfoModule", []);
myorderinfoModule.controller('myorderinfoCtrl', function ($scope, $http, $stateParams, Check) {
    $http.post(GetUrl.myorderinfo, {
        orderId: $stateParams.id
    }).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.myorderinfos = data.order;
            }
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        })
    });
});

/**
 * 我的收藏 模块
 */
var myscModule = angular.module("myscModule", ['infinite-scroll']);
myscModule.controller('myscCtrl', function ($scope, $http, Check) {
    $scope.pageNo = 1;
    $scope.nomore = false;
    $scope.loading = false;
    $scope.myscs = [];
    $scope.collectList = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(GetUrl.mysc, {
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    $.each(data.collectList, function (i, collect) {
                        $scope.myscs.push(collect);
                    });
                    $scope.pageNo++;
                    $scope.nomore = data.nomore;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.errorMsg
            });
        });
    }
});

/**
 * 意见反馈 模块
 */
var yijianModule = angular.module('yijianModule', []);
yijianModule.controller('yijianCtrl', ['$scope', function ($scope) {
    $scope.submitted = false;
    $scope.signupForm = function () {
        $scope.signup;
        if ($scope.signup_form.$valid) {
            // Submit as normal
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "提交成功"
            });
            var names = $scope.signup.name;
            $scope.sName = false;
        } else {
            $scope.signup_form.submitted = true;
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "内容不能为空"
            });
//			var names="";
//            if(names==null||names==""){
//                $scope.sName=true;
//            }
//            $.scope.name;
        }
    }
}]);

/**
 * 登录 模块
 */
var loginModule = angular.module('loginModule', ['angularLocalStorage']);
loginModule.controller('loginCtrl', ['$scope', '$http', '$location', 'storage', function ($scope, $http, $location, storage) {
    $scope.user = storage.get(keys.user) == null ? {} : storage.get(keys.user);
    $scope.account = $scope.user.telephone;
    var indexOf = $location.absUrl().indexOf('?url=');
    var url = $location.absUrl().substring(indexOf + 5);
    $scope.password = '';
    $scope.loginForm = function () {
        if ($scope.login_form.$valid) {
            $http.post(GetUrl.login, {
                    account: $scope.account,
                    password: $scope.password
                }
            ).success(function (data) {
                if (data.success) {
                    $scope.user = data.user;
                    storage.set(keys.user, $scope.user);
                    history.go(-1);
                    //if (indexOf > 0) {
                    //    location.href = decodeURIComponent(url);
                    //} else {
                    //    location.href = '/';
                    //}
                } else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: data.errMsg
                    });
                }
            }).error(function (data) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '系统错误'
                });
            });
        } else {
            $scope.login_form.submitted = true;
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "手机号、密码不能为空"
            });
        }
    };
    if (typeof(auths) != "undefined") {
        $scope.auths = auths;
    }

    console.info('login page is call');


    $scope.weixinLogin = function (obj) {
        var auth = null;
        for (var item in auths) {
            if (auths[item].id == obj.auth.id) {
                auth = obj.auth;
                break;
            }
        }
        var waiting = plus.nativeUI.showWaiting();
        auth.login(function () {
            waiting.close();
            plus.nativeUI.toast("登录认证成功");
            auth.getUserInfo(function () {
                plus.nativeUI.toast("登录中...");
                var name = auth.userInfo.nickname || auth.userInfo.name;
                $http.post(GetUrl.appLogin, {
                        json: JSON.stringify({
                            sex: auth.userInfo.sex,
                            nickname: auth.userInfo.nickname,
                            unionid: auth.userInfo.unionid,
                            province: auth.userInfo.province,
                            openid: auth.userInfo.openid,
                            language: auth.userInfo.language,
                            headimgurl: auth.userInfo.headimgurl,
                            country: auth.userInfo.country,
                            city: auth.userInfo.city
                        })
                    }
                ).success(function (data) {
                    if (data.success) {
                        $scope.user = data.user;
                        storage.set(keys.user, $scope.user);
                        history.go(-1);
                        //if (indexOf > 0) {
                        //    location.href = decodeURIComponent(url);
                        //} else {
                        //    location.href = '/';
                        //}
                    } else {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: data.errMsg
                        });
                    }
                }).error(function (data) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: '系统错误'
                    });
                });
            }, function (e) {
                plus.nativeUI.toast("获取用户信息失败：" + e.message);
            });
        }, function (e) {
            waiting.close();
            plus.nativeUI.toast("登录认证失败：" + e.message);
        });
    }
}]);


/**
 * 注册 模块
 */
var regModule = angular.module('regModule', []);
regModule.controller('regCtrl', ['$scope', '$http', '$state', function ($scope, $http, $state) {
    $http.post(GetUrl.checklogin).success(function (data) {
        if (data.success) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "已登录"
            });
            $state.go("xianlu");
        }
    });

    var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;

    $scope.sendSms = function () {
        if ($scope.account == undefined || $scope.account == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入手机号！"
            });
            return;
        }
        if (!$scope.account.match(mobile)) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "手机号格式不正确！"
            });
            return;
        }
        if ($scope.checkNum == undefined || $scope.checkNum == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入验证码！"
            });
            return;
        }
        $http.post(GetUrl.checkUser, {
            account: $scope.account
        }).success(function (data) {
            if (data.success) {
                if (!data.exists) {
                    $http.post(GetUrl.sendSms, {
                        checkNum: $scope.checkNum,
                        account: $scope.account
                    }).success(function (data) {
                        if (data.success) {
                            bootbox.alert({
                                buttons: {
                                    ok: {
                                        label: '确认'
                                    }
                                },
                                message: "短信验证码发送成功！"
                            });
                        } else {
                            bootbox.alert({
                                buttons: {
                                    ok: {
                                        label: '确认'
                                    }
                                },
                                message: data.errMsg
                            });
                        }
                    }).error(function (data) {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: data.msg
                        });
                    });
                } else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: "该手机号已存在！"
                    });
                }
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    };

    $scope.submit = function () {
        if ($scope.account == undefined || $scope.account == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入手机号！"
            });
            return;
        }
        if (!$scope.account.match(mobile)) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "手机号格式不正确！"
            });
            return;
        }
        if ($scope.checkNum == undefined || $scope.checkNum == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入验证码！"
            });
            return;
        }
        if ($scope.smsCode == undefined || $scope.smsCode == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入短信验证码！"
            });
            return;
        }
        if ($scope.password == undefined || $scope.password == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入密码！"
            });
            return;
        }
        if ($scope.password.length < 6) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "密码长度不能小于6位！"
            });
            return;
        }
        $http.post(GetUrl.register, {
            account: $scope.account,
            password: $scope.password,
            smsCode: $scope.smsCode
        }).success(function (data) {
            if (data.success) {
                $state.go("xianlu");
            } else {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: data.errMsg
                });
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    }
}]);
/**
 * 忘记密码
 * @type {angular.Module|*}
 */
var myresetpwModule = angular.module("myresetpwModule", []);
myresetpwModule.controller("myresetpwCtrl", function ($scope, $state) {
    var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;

    $scope.nextYzm = function () {
        if ($scope.account == undefined || $scope.account == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入手机号！"
            });
            return;
        }
        if (!$scope.account.match(mobile)) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "手机号格式不正确！"
            });
            return;
        }
        $state.go("my-reset-yzm", {account: $scope.account});
    }
});
/**
 * 忘记密码验证码
 * @type {angular.Module|*}
 */
var myresetyzmModule = angular.module("myresetyzmModule", []);
myresetyzmModule.controller("myresetyzmCtrl", function ($scope, $http, $state, $stateParams) {
    $scope.account = $stateParams.account;

    $scope.sendSms = function () {
        if ($scope.checkNum == undefined || $scope.checkNum == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入验证码！"
            });
            return;
        }
        $http.post(GetUrl.checkUser, {
            account: $scope.account
        }).success(function (data) {
            if (data.success) {
                if (data.exists) {
                    $http.post(GetUrl.sendSms, {
                        checkNum: $scope.checkNum,
                        account: $scope.account
                    }).success(function (data) {
                        if (data.success) {
                            bootbox.alert({
                                buttons: {
                                    ok: {
                                        label: '确认'
                                    }
                                },
                                message: "短信验证码发送成功！"
                            });
                        } else {
                            bootbox.alert({
                                buttons: {
                                    ok: {
                                        label: '确认'
                                    }
                                },
                                message: data.errMsg
                            });
                        }
                    }).error(function (data) {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: data.msg
                        });
                    });
                } else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: "用户不存在！"
                    });
                }
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    };

    $scope.nextPw = function () {
        if ($scope.smsCode == undefined || $scope.smsCode == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入短信验证码！"
            });
            return;
        }
        $http.post(GetUrl.checkSms, {
            smsCode: $scope.smsCode
        }).success(function (data) {
            if (data.success) {
                var request = {
                    account: $scope.account,
                    smsCode: $scope.smsCode
                };
                $state.go("my-new-pw", {request: JSON.stringify(request)});
            } else {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: data.errMsg
                });
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    }
});
/**
 * 重置密码
 * @type {angular.Module|*}
 */
var mynewpwModule = angular.module("mynewModule", []);
mynewpwModule.controller("mynewpwCtrl", function ($scope, $http, $state, $stateParams) {
    var request = $stateParams.request == undefined ? {} : JSON.parse($stateParams.request);
    $scope.account = request.account;
    $scope.smsCode = request.smsCode;

    $scope.updatePassword = function () {
        if ($scope.password == undefined || $scope.password == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请输入密码！"
            });
            return;
        }
        if ($scope.password.length < 6) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "密码长度不能小于6位！"
            });
            return;
        }
        $http.post(GetUrl.forgotPassword, {
            account: $scope.account,
            smsCode: $scope.smsCode,
            password: $scope.password
        }).success(function (data) {
            if (data.success) {
                $state.go("my-new-pw-ok");
            } else {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: data.errMsg
                });
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    }
});
/**
 * 我的红包 模块
 */
var myhbModule = angular.module("myhbModule", ['angularLocalStorage']);
myhbModule.controller('myhbCtrl', ['$scope', '$http', 'storage', 'Check', function ($scope, $http, storage, Check) {
    $scope.user = storage.get(keys.user) == null ? {} : storage.get(keys.user);
    $http.post(GetUrl.myhb).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.myhbs = {};
                $scope.myhbs.nouse = data.unusedCouponList;
                $scope.myhbs.use = data.usedCouponlist;
                $scope.myhbs.rule = data.rule;
            }
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        });
    });
    $scope.showUserDesc = function (obj) {
        var coupon = obj.data;
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: coupon.instructions
        });
    }
}]);

/**
 * 编辑个人信息
 * @type {angular.Module|*}
 */
var mygerenModule = angular.module("mygerenModule", []);
mygerenModule.controller("mygerenCtrl", function ($scope, $http, Check, storage) {
    $http.post(GetUrl.userinfo).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.user = data.user;
                storage.set(keys.user, $scope.user);
                if ($scope.user.gender == "male") {
                    $(".iradio_flat-green-geren").eq(0).addClass("checked");
                } else {
                    $(".iradio_flat-green-geren").eq(1).addClass("checked");
                }
            }
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        });
    });

    $scope.uploadHead = function () {
        $("#upload-head").click();
    };

    $("#upload-head").change(function () {
        var file = this.files[0];
        var fd = new FormData();
        fd.append("img", file);
        fd.append("section", "user");
        var request = new XMLHttpRequest();
        request.open("POST", GetUrl.uploadPhoto, true);
        request.onload = function () {
            var data = JSON.parse(request.response);
            if (data.success) {
                $scope.$apply(function () {
                    $scope.user.head = data.photo.imgUrl;
                });
            }
        };
        request.send(fd);
    });

    $scope.updateUser = function () {
        var radio = $(".iradio_flat-green-geren");
        if (radio.eq(0).hasClass("checked")) {
            $scope.user.gender = "male";
        } else {
            $scope.user.gender = "female";
        }
        $http.post(GetUrl.updateuser, {
            json: JSON.stringify($scope.user)
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    storage.set(keys.user, $scope.user);
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: "保存成功"
                    });
                }
            }
        }).error(function () {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: data.msg
            });
        });
    }
});

//余额
var balanceModule = angular.module("balanceModule", []);
balanceModule.controller("balanceCtrl", function ($scope, $http, Check, $state) {
    $http.post(GetUrl.userinfo, {}).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.user = data.user;
            }
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        });
    });

    $scope.recharge = function () {
        if ($scope.price == null || $scope.price <= 0) {
            bootbox.alert("请输入充值金额");
            return;
        }
        var jsonObj = {
            id: 0,
            orderType: "recharge"
        };
        var details = [];
        var detail = {
            price: $scope.price
        };
        details.push(detail);
        jsonObj.details = details;
        $http.post(GetUrl.saveBalanceOrder, {
            json: JSON.stringify(jsonObj)
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    $state.go('payment', {order: JSON.stringify(data.order)});
                }
            }
        }).error(function (data) {
            alert(data.errorMsg);
        });
    };

    $scope.withdraw = function () {
        if ($scope.price == null || $scope.price <= 0) {
            bootbox.alert("请输入提现金额");
            return;
        }
        if ($scope.price < 1) {
            bootbox.alert("提现金额至少为1元");
            return;
        }
        if ($scope.price > $scope.user.balance) {
            bootbox.alert("余额不足，无法提现");
            return;
        }
        var jsonObj = {
            id: 0,
            orderType: "withdraw"
        };
        var details = [];
        var detail = {
            price: $scope.price
        };
        details.push(detail);
        jsonObj.details = details;
        $http.post(GetUrl.saveBalanceOrder, {
            json: JSON.stringify(jsonObj)
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    bootbox.alert("提现请求提交成功");
                }
            }
        }).error(function (data) {
            alert(data.errorMsg);
        });
    };
});

//余额明细
var balanceLogModule = angular.module("balanceLogModule", []);
balanceLogModule.controller("balanceLogCtrl", function ($scope, $http, Check) {
    $http.post(GetUrl.balanceLog, {}).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.balanceLogList = data.balanceLogList;
            }
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        });
    });
});
/**
 * 关于我们
 * @type {angular.Module|*}
 */
var aboutModule = angular.module("aboutModule", []);
aboutModule.controller("aboutCtrl", function ($scope, $http, Check, storage) {

});