/**
 * Created by huangpeijie on 2016-09-30,0030.
 */
var planModule = angular.module("planModule", []);

planModule.controller("planDemandCtrl", function ($scope, storage, $state) {
    var date = new Date();
    date.setDate(date.getDate() + 1);
    $scope.playDate = date.format("yyyy-MM-dd");
    $scope.playDay = 1;
    $scope.adultNum = 1;
    $scope.childNum = 0;
    $scope.needHotel = false;
    $scope.hour = 2;
    $scope.shadow = false;
    $scope.tagnum = 1;
    $scope.tagnum2 = 1;
    $scope.roomlevel = false;
    $scope.hotelSearch = {priceRange: [0, 9999999], star: 0};
    $scope.hotelSearchTem = {priceRange: [0, 9999999], star: 0};
    var limit = date.format("yyyy-MM-dd");
    date.setMonth(date.getMonth() + 3);
    limit += "," + date.format("yyyy-MM-dd");
    $("#time").attr("data-lcalendar", limit);
    var calendardatetime = new lCalendar();
    calendardatetime.init({
        'trigger': '#time',
        'type': 'date'
    });

    $scope.next = function () {
        storage.set(yhyKey.planDemand, {
            playDate: $scope.playDate,
            playDay: $scope.playDay,
            adultNum: $scope.adultNum,
            childNum: $scope.childNum,
            needHotel: $scope.needHotel,
            hour: $scope.hour,
            hotelSearch: $scope.hotelSearch
        });
        storage.remove(yhyKey.selectedScenic);
        $state.go("planSelectScenic");
    };
    $scope.more = [
        {index: 1, name: '宽松安排'},
        {index: 2, name: '适中安排'},
        {index: 3, name: '紧凑安排'}
    ];
    $scope.chooseroom = function (bool) {
        $scope.needHotel = bool;
        if (bool) {
            $scope.shadow = true;
            $scope.roomlevel = true;
            $scope.hotelSearchTem = {priceRange: [0, 9999999], star: 0};
        }
    };
    $scope.changestate = function (num) {
        $scope.hour = num;
    };

    $scope.pricelist = [
        {index: 1, name: '不限', priceRange: [0, 9999999]},
        {index: 2, name: '150元以下', priceRange: [0, 150]},
        {index: 3, name: '150-300元', priceRange: [150, 300]},
        {index: 4, name: '301-450元', priceRange: [300, 450]},
        {index: 5, name: '451-600元', priceRange: [450, 600]},
        {index: 6, name: '600元以上', priceRange: [600, 9999999]}
    ];

    $scope.starlist = [
        {index: 1, name: '不限', star: 0},
        {index: 2, name: '经济/客栈', star: 2},
        {index: 3, name: '三星/舒适', star: 3},
        {index: 4, name: '四星/高档', star: 4},
        {index: 5, name: '五星/豪华', star: 5},
        {index: 6, name: '公寓', star: 0}
    ];

    $scope.pricelevel = function (price) {
        $scope.tagnum = price.index;
        $scope.hotelSearchTem.priceRange = price.priceRange;
    };

    $scope.starlevel = function (star) {
        $scope.tagnum2 = star.index;
        $scope.hotelSearchTem.star = star.star;
    };

    $scope.commitHotelSearch = function () {
        $scope.hotelSearch = $scope.hotelSearchTem;
        $scope.shadow = false;
        $scope.roomlevel = false;
    };

    $scope.closewindow = function () {
        $scope.shadow = false;
        $scope.roomlevel = false;
    }
});

planModule.controller("planSelectScenicCtrl", function ($scope, $http, storage, $state, $rootScope, $stateParams) {
    $scope.planDemand = storage.get(yhyKey.planDemand);
    if ($.isEmptyObject($scope.planDemand)) {
        $state.go("planDemand");
        return;
    }
    $scope.scenicList = [];
    $scope.selectedScenic = storage.get(yhyKey.selectedScenic);
    if ($scope.selectedScenic == null) {
        $scope.selectedScenic = {};
    }

    var params = $stateParams.params;
    if (params != null && params != "") {
        $scope.keyword = JSON.parse(params).keyword;
    } else {
        $scope.keyword = null;
    }

    $scope.hasScenic = !$.isEmptyObject($scope.selectedScenic);
    $scope.pageNo = 1;
    $rootScope.loading = false;
    $scope.nomore = false;

    $scope.getScenicList = function () {
        if ($rootScope.loading) {
            return;
        }
        if ($scope.nomore) {
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.mScenicList, {
            json: JSON.stringify({
                name: $scope.keyword,
                cityIds: [350200],
                orderColumn: "ranking",
                orderType: "asc"
            }),
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.scenicList, function (scenic) {
                    scenic.scoreStar = Math.round(scenic.score);
                    $scope.scenicList.push(scenic);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++;
            }
        });
    };

    $scope.toSearch = function () {
        storage.set(yhyKey.preUrl, "planSelectScenic");
        $state.go("search");
    };

    $scope.next = function () {
        if (!$scope.hasScenic) {
            bootbox.alert("请选择景点");
            return;
        }
        if (Object.keys($scope.selectedScenic).length < $scope.planDemand.playDay) {
            bootbox.alert("所选景点太少");
            return;
        }
        $scope.hideshadow();
        storage.remove(yhyKey.planDetail);
        $state.go("planDetail");
    };
    $scope.sellist = false;
    $scope.showSelectedScenic = false;
    $scope.showsellist = function () {
        if ($scope.sellist) {
            $scope.sellist = false;
            $scope.showSelectedScenic = false;
            $("div[ng-controller]").removeClass('bodyStop');
        } else {
            $scope.sellist = true;
            $scope.showSelectedScenic = true;
            $("div[ng-controller]").addClass('bodyStop');
        }
    };

    $scope.hideshadow = function () {
        $scope.sellist = false;
        $scope.showSelectedScenic = false;
        $("div[ng-controller]").removeClass('bodyStop');
    };

    $scope.removeSelectedScenic = function (id) {
        delete $scope.selectedScenic[id];
        if ($.isEmptyObject($scope.selectedScenic)) {
            $scope.hasScenic = false;
        }
        storage.set(yhyKey.selectedScenic, $scope.selectedScenic);
    };

    $scope.removeSelectedPrice = function (scenicId, priceId) {
        var prices = $scope.selectedScenic[scenicId].prices;
        var index = -1;
        angular.forEach(prices, function (price, i) {
            if (price.id == priceId) {
                index = i;
            }
        });
        if (index > -1) {
            prices.splice(index, 1);
            $scope.selectedScenic[scenicId].prices = prices;
        }
        storage.set(yhyKey.selectedScenic, $scope.selectedScenic);
    };

});

planModule.controller('planScenicDetailCtrl', function ($scope, $http, $location, $stateParams, $state, Check, storage, $rootScope) {
    $scope.selectedScenic = storage.get(yhyKey.selectedScenic);
    if ($scope.selectedScenic == null) {
        $scope.selectedScenic = {};
    }
    $scope.tabIndex = 1;
    $scope.pageNo = 1;
    $scope.commentList = [];
    $scope.showTicketPrice = null;
    $scope.noTicket = true; // 是否有门票标志
    var loadTicketFlag = false; // 是否已加载门票列表，只加载一次
    var loadDetailFlag = false; // 是否已加载详情，只加载一次
    $scope.nomore = false;
    $scope.ticketDescFlag = false; // 票型说明窗口是否显示标志

    var params = $stateParams.params;
    if (params == null || params == "") {
        history.go(-1);
        return;
    }
    var scenicId = params.split("-")[0];
    $scope.fromState = params.split("-")[1];
    // 查询景点概要信息
    $http.post(yhyUrl.scenicTicketInfo, {
        scenicId: scenicId, favorite: 'QUERY_TRUE'
    }).success(function (data) {
        if (data.error) {
            $scope.error = true;
        } else {
            $scope.scenicId = data.scenicId;
            $scope.scenicName = data.scenicName;
            if (data.scenicCover) {
                $scope.scenicCover = QINIU_BUCKET_URL + data.scenicCover;
            }
            $scope.openTime = data.openTime;
            $scope.address = data.address;
            $scope.productRemark = data.productRemark;
            $scope.satisfaction = data.satisfaction;
            $scope.favorite = data.favorite;
            $scope.scenicSelected = !$.isEmptyObject($scope.selectedScenic[data.scenicId]);
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
    // 获取景点门票列表
    $scope.listTicket = function () {
        if (loadTicketFlag) {
            return;
        }
        loadTicketFlag = true;
        $rootScope.loading = true;
        $http.post(yhyUrl.scenicTicketList, {
            scenicId: scenicId
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.error) {
                $scope.error = true;
            } else {
                if (data.ticketList && data.ticketList.length > 0) {
                    $scope.noTicket = false;
                    $scope.ticketList = data.ticketList;
                    angular.forEach(data.ticketList, function (ticket) {
                        ticket.selected = false;
                        var scenic = $scope.selectedScenic[scenicId];
                        if (scenic == null || scenic.prices == null) {
                            return true;
                        }
                        angular.forEach(scenic.prices, function (price) {
                            if (price.id == ticket.id) {
                                ticket.selected = true;
                                return false;
                            }
                        })
                    });
                } else {
                    $scope.noTicket = true;
                }
            }
        }).error(function (data) {
            $rootScope.loading = false;
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '系统错误'
            })
        });
    };
    // 查询景点详情
    $scope.searchScenicDesc = function () {
        if (loadDetailFlag) {
            return;
        }
        loadDetailFlag = true;
        $rootScope.loading = true;
        $http.post(yhyUrl.scenicDesc, {
            scenicId: scenicId
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.error) {
                $scope.error = true;
            } else {
                $scope.descripton = data.descripton;
            }
        }).error(function (data) {
            $rootScope.loading = false;
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '系统错误'
            });
        });
    };
    // 获取评论列表
    $scope.listComment = function () {
        if ($scope.nomore) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.scenicCommentList, {
            scenicId: scenicId,
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.error) {
                $scope.error = true;
            } else {
                $scope.commentCount = data.commentCount;
                angular.forEach(data.commentList, function (item) {
                    if (!item.nickName) {
                        item.nickName = '匿名驴友';
                    }
                    $scope.commentList.push(item);
                });
                $scope.pageNo++;
                $scope.nomore = data.nomore;
            }
        }).error(function (data) {
            $rootScope.loading = false;
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '系统错误'
            })
        });
    };
    // 返回
    $scope.back = function () {
        history.go(-1);
    };
    // 收藏
    $scope.doFavorite = function () {
        $http.post(yhyUrl.favorite, {
            favoriteId: scenicId,
            favoriteType: 'scenic'
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.error) {
                    $scope.error = true;
                    console.log(data.errorMsg);
                } else {
                    $scope.favorite = data.favorite;
                }
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
    };

    //景点加入行程
    $scope.addScenic = function () {
        var scenic = $scope.selectedScenic[scenicId];
        if ($scope.scenicSelected && !$.isEmptyObject(scenic) && scenic.prices.length == 0) {
            delete $scope.selectedScenic[scenicId];
        }
        if (!$scope.scenicSelected && $.isEmptyObject(scenic)) {
            scenic = {name: $scope.scenicName, prices: []};
            $scope.selectedScenic[scenicId] = scenic;
        }
        $scope.scenicSelected = !$scope.scenicSelected;
        storage.set(yhyKey.selectedScenic, $scope.selectedScenic);
    };

    // 门票加入行程
    $scope.doBook = function (ticketPrice) {
        var scenic = $scope.selectedScenic[scenicId];
        if (scenic == null) {
            scenic = {name: $scope.scenicName, prices: []};
        }
        var index = -1;
        angular.forEach(scenic.prices, function (price, i) {
            if (price.id == ticketPrice.id) {
                index = i;
                return false;
            }
        });
        if (ticketPrice.selected) {
            if (index > -1) {
                scenic.prices.splice(index, 1);
                if ($scope.scenicSelected) {
                    $scope.selectedScenic[scenicId] = scenic;
                } else {
                    delete $scope.selectedScenic[scenicId];
                }
            }
        } else {
            if (index == -1) {
                scenic.prices.push(ticketPrice);
                $scope.selectedScenic[scenicId] = scenic;
            }
        }
        ticketPrice.selected = !ticketPrice.selected;
        storage.set(yhyKey.selectedScenic, $scope.selectedScenic);
    };
    // 票型说明 - 显示
    $scope.showTicketDesc = function (ticketPriceId) {
        // 查找对应的票型记录
        for (var i = 0; i < $scope.ticketList.length; i++) {
            if (ticketPriceId == $scope.ticketList[i].id) {
                $scope.showTicketPrice = $scope.ticketList[i];
                break;
            }
        }
        // 查询票型详情
        if (!$scope.showTicketPrice.addinfoDetailList) {    // 判断是否已经加载过
            $rootScope.loading = true;
            $http.post(yhyUrl.ticketAddinfoDetail, {
                ticketPriceId: ticketPriceId
            }).success(function (data) {
                $rootScope.loading = false;
                if (data.error) {
                    $scope.error = true;
                } else {
                    $scope.showTicketPrice.addinfoDetailList = data.addinfoDetailList;
                    $scope.ticketDescFlag = true;
                }
            }).error(function (data) {
                $rootScope.loading = false;
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '系统错误'
                })
            });
        } else {
            $scope.ticketDescFlag = true;
        }
    };
    // 票型说明 - 关闭
    $scope.closeTicketDesc = function () {
        $scope.ticketDescFlag = false;
        $scope.showTicketPrice = null;
    };
    // 票型说明 - 预订
    $scope.doDescBook = function () {
        if ($scope.showTicketPrice) {
            $scope.doBook($scope.showTicketPrice);
        }
    };
    // 预订须知
    $scope.goOrderKnow = function () {
        $state.go("scenicOrderKnow", {scenicId: scenicId}, {reload: true});
    };
    // 交通信息
    $scope.goTrafficGuide = function () {
        $state.go("scenicTrafficGuide", {scenicId: scenicId}, {reload: true});
    };

    //$scope.complete = function () {
    //    storage.set(yhyKey.selectedScenic, $scope.selectedScenic);
    //    history.back();
    //};
});

planModule.controller("planDetailCtrl", function ($scope, $http, storage, $state, Check, $rootScope, NumberHandle) {
    $scope.planDemand = storage.get(yhyKey.planDemand);
    $scope.morefun = false;
    if ($scope.planDemand == null) {
        $state.go("planDemand");
        return;
    }
    $scope.selectedScenic = storage.get(yhyKey.selectedScenic);
    if ($.isEmptyObject($scope.selectedScenic)) {
        history.go(-1);
        return;
    }
    var selectedScenicList = [];
    for (var id in $scope.selectedScenic) {
        selectedScenicList.push({id: id, type: 1});
    }
    $scope.selectedScenicList = selectedScenicList;
    $scope.day = $scope.planDemand.playDay;
    $scope.personalNum = $scope.planDemand.adultNum + $scope.planDemand.childNum;


    function init() {
        var ferryPrice = 0;
        var ticketPrice = 0;
        var hotelPrice = 0;
        angular.forEach($scope.plan.days, function (day, i, days) {
            var dayPrice = 0;
            if (day.needShip && day.ferry != null) {
                dayPrice += NumberHandle.roundTwoDecimal(day.ferry.price);
                ferryPrice += NumberHandle.roundTwoDecimal(day.ferry.price);
            }
            if ($scope.planDemand.needHotel) {
                day.changingHotel = false;
                if (day.hotel != null) {
                    if (day.scenics.length > 0) {
                        day.hotel.distance = Math.round(BMapLib.GeoUtils.getDistance(new BMap.Point(day.hotel.lng, day.hotel.lat), new BMap.Point(day.scenics[0].longitude, day.scenics[0].latitude)) / 10) / 100;
                    }
                    dayPrice += NumberHandle.roundTwoDecimal(day.hotel.price);
                    hotelPrice += NumberHandle.roundTwoDecimal(day.hotel.price);
                }
            }
            angular.forEach(day.scenics, function (scenic, j, scenics) {
                scenic.adviceDate = NumberHandle.roundTwoDecimal(scenic.adviceMinute / 60);
                var select = $scope.selectedScenic[scenic.id];
                if (!$.isEmptyObject(select) && select.prices != null) {
                    angular.forEach(select.prices, function (price) {
                        dayPrice += NumberHandle.roundTwoDecimal(price.discountPrice);
                        ticketPrice += NumberHandle.roundTwoDecimal(price.discountPrice);
                    });
                }
                if (j == scenics.length - 1 && i == days.length - 1) {
                    return false;
                }
                if (j == scenics.length - 1) {
                    if (days[i + 1].hotel != null) {
                        scenic.distance = Math.round(BMapLib.GeoUtils.getDistance(new BMap.Point(scenic.longitude, scenic.latitude), new BMap.Point(days[i + 1].hotel.lng, days[i + 1].hotel.lat)) / 10) / 100;
                    } else {
                        scenic.distance = Math.round(BMapLib.GeoUtils.getDistance(new BMap.Point(scenic.longitude, scenic.latitude), new BMap.Point(days[i + 1].scenics[0].longitude, days[i + 1].scenics[0].latitude)) / 10) / 100;
                    }
                } else {
                    scenic.distance = Math.round(BMapLib.GeoUtils.getDistance(new BMap.Point(scenic.longitude, scenic.latitude), new BMap.Point(scenics[j + 1].longitude, scenics[j + 1].latitude)) / 10) / 100;
                }
            });
            day.price = NumberHandle.roundTwoDecimal(dayPrice * $scope.personalNum);
        });
        $scope.plan.ferryPrice = NumberHandle.roundTwoDecimal(ferryPrice * $scope.personalNum);
        $scope.plan.ticketPrice = NumberHandle.roundTwoDecimal(ticketPrice * $scope.personalNum);
        $scope.plan.hotelPrice = NumberHandle.roundTwoDecimal(hotelPrice * $scope.personalNum);
        $scope.plan.price = NumberHandle.roundTwoDecimal($scope.plan.ferryPrice + $scope.plan.ticketPrice + $scope.plan.hotelPrice);
        if ($scope.plan.days > 0) {
            storage.set(yhyKey.planDetail, $scope.plan);
        }
    }

    $scope.plan = storage.get(yhyKey.planDetail);
    if ($scope.plan == null) {
        $rootScope.loading = true;
        $http.post(yhyUrl.planOptimize, {
            json: JSON.stringify({
                type: 2,
                day: $scope.day,
                hour: $scope.planDemand.hour,
                cityDays: {3502: $scope.day},
                scenicList: $scope.selectedScenicList
            }),
            startDate: $scope.planDemand.playDate,
            needHotel: $scope.planDemand.needHotel,
            hotelSearch: JSON.stringify($scope.planDemand.hotelSearch)
        }).success(function (data) {
            $rootScope.loading = false;
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                $scope.plan = data.plan;
                angular.forEach($scope.plan.days, function (day) {
                    if (day.ferry != null) {
                        day.ferry.startTime = day.ferry.departTime.substring(12, day.ferry.departTime.length);
                    }
                    if ($scope.planDemand.needHotel) {
                        if (!$.isEmptyObject(day.hotel)) {
                            day.hotel.priceIds = [day.hotel.priceId];
                        }
                    }
                });
                init();
            }
            console.info(data);
        }).error(function () {
            $rootScope.loading = false;
        });
    } else {
        init();
    }

    $scope.doSavePlan = function() {
        if ($scope.plan == null) {
            bootbox.alert("请先规划行程!");
            return;
        }
        if ($scope.plan.isSave && $scope.plan.isSave == true) {
            bootbox.alert("已保存!");
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.doSavePlan, {
            planJson: JSON.stringify($scope.plan)
        }).success(function(result) {
            if (result && result.success) {
                $scope.plan.isSave = true;
                storage.set(yhyKey.planDetail, $scope.plan);
                $rootScope.loading = false;
                bootbox.alert("保存成功!");
                return;
            }
        }).error(function() {
            $rootScope.loading = false;
            bootbox.alert("保存失败, 请重试!");
            return;
        });
    };

    $scope.changeFerry = function (day) {
        angular.forEach($scope.plan.days, function (day) {
            day.changingFerry = false;
        });
        day.changingFerry = true;
        storage.set(yhyKey.planDetail, $scope.plan);
        $state.go("planferrylist");
    };

    $scope.hotelDetail = function (day) {
        if ($.isEmptyObject(day.hotel)) {
            return;
        }
        angular.forEach($scope.plan.days, function (day) {
            day.changingHotel = false;
        });
        day.changingHotel = true;
        storage.set(yhyKey.planDetail, $scope.plan);
        $state.go("planChangeHotelDetail", {hotelId: day.hotel.id});
    };

    $scope.changeHotel = function (day) {
        angular.forEach($scope.plan.days, function (day) {
            day.changingHotel = false;
        });
        day.changingHotel = true;
        storage.set(yhyKey.planDetail, $scope.plan);
        $state.go("planChangeHotel");
    };

    $scope.morefun = function (item) {
        if ($.isEmptyObject(item)) {
            return;
        }
        item.morefun = !item.morefun;
    };

    $scope.deleteScenic = function (day, scenic, index) {
        scenic.morefun = false;
        if (day.scenics.length <= 1) {
            return;
        }
        delete $scope.selectedScenic[scenic.id];
        day.scenics.splice(index, 1);
        init();
    };

    $scope.deleteHotel = function (day) {
        day.deleteHotel = true;
        delete day.hotel;
        init();
    };

    $scope.toOrder = function () {
        var days = [];
        var ferry = {};
        angular.forEach($scope.plan.days, function (day) {
            if (day.needShip && !$.isEmptyObject(day.ferry)) {
                ferry = day.ferry;
            }
            var scenics = {};
            angular.forEach(day.scenics, function (scenic) {
                var select = $scope.selectedScenic[scenic.id];
                if (select) {
                    var priceIds = [];
                    angular.forEach(select.prices, function (price) {
                        priceIds.push(price.id);
                    });
                    scenics[scenic.id] = priceIds;
                }
            });
            var obj = {
                day: day.day,
                scenics: scenics
            };
            if (!$.isEmptyObject(day.hotel)) {
                obj.hotels = day.hotel.priceIds;
            }
            days.push(obj);
        });
        storage.set(yhyKey.planSelectedFerry, ferry);
        storage.set(yhyKey.planDaysRequest, days);
        storage.remove(yhyKey.selectedTourist);
        $state.go("planTouristList");
    };


    var top = ($(window).height() - 76) / 2;
    var top2 = ($(window).height() - 54) / 2;
    $('.mytravel').css({'top': top});
    $('.hidetravelList').css({'top': top2});
    $scope.fareDetail = -1;
    $scope.shadowbox = -1;
    $scope.mytravel = true;
    $scope.hidebutton = false;
    $scope.showfareDetail = function (num) {
        $scope.fareDetail = $scope.fareDetail * num;
        $scope.shadowbox = $scope.shadowbox * num;
    };
    $scope.clearshow = function (num) {
        $scope.fareDetail = $scope.fareDetail * num;
        $scope.shadowbox = $scope.shadowbox * num;
    };
    $scope.showMytravel = function () {
        $("div[ng-controller]").addClass('bodyStop');
        $('.travelList').animate({'left': '0'}, 600, function () {
            $scope.$apply(function () {
                $scope.hidebutton = true;
            })
        });
        $scope.mytravel = false;
    };
    $scope.hidetravelList = function () {
        $("div[ng-controller]").removeClass('bodyStop');
        $('.travelList').animate({'left': '-70%'}, 300, function () {
            $scope.$apply(function () {
                $scope.hidebutton = false;
            })
        });
        $scope.mytravel = true;
    };
});

planModule.controller("planChangeHotelCtrl", function ($scope, $http, storage, $rootScope) {
    $scope.plan = storage.get(yhyKey.planDetail);
    if ($scope.plan == null) {
        history.go(-1);
        return;
    }

    angular.forEach($scope.plan.days, function (day) {
        if (day.changingHotel) {
            $scope.day = day;
            return false;
        }
    });

    $scope.pageNo = 1;

    var params = {};
    params["hotelSearchRequest.startDate"] = $scope.day.startDate;
    params["hotelSearchRequest.endDate"] = $scope.day.endDate;
    params["coreScenic"] = $scope.day.coreScenic;
    params["pageSize"] = 10;
    $scope.hotelList = [];
    $rootScope.loading = false;
    $scope.nomore = false;
    $scope.getHotelList = function () {
        if ($rootScope.loading) return;
        if ($scope.nomore) return;
        $rootScope.loading = true;
        params["pageNo"] = $scope.pageNo;
        $http.post(yhyUrl.hotelList, params).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.hotelList, function (hotel) {
                    hotel.score = Math.round((hotel.score / 20) * 10) / 10;
                    $scope.hotelList.push(hotel);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };
});

planModule.controller('planChangeHotelDetailCtrl', function ($scope, $http, $location, $stateParams, $state, storage, Check, $rootScope) {
    $scope.plan = storage.get(yhyKey.planDetail);
    if ($scope.plan == null) {
        history.go(-1);
        return;
    }

    $scope.hotelTotalPrice = 0;

    angular.forEach($scope.plan.days, function (day) {
        if (day.changingHotel) {
            $scope.day = day;
            return false;
        }
    });

    $scope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
        $scope.showAlbumFlag = from.name == "hotel_album";   // 是否显示相册
    });
    $scope.hasRoomFilter = false;       // 是否有附加条件
    //$scope.bntBookChoose = false;       // 可订
    //$scope.bntConfirmChoose = false;   // 立即确认
    //$scope.bntHourRoom = false;         // 钟点房
    //$scope.bntCancelChoose = false;     // 免费取消
    //$scope.bntAddBed = false;           // 可加床
    $scope.priceIds = [];

    // 查询酒店概要信息
    $scope.score = 0;
    var hotelId = $stateParams.hotelId;
    $http.post(yhyUrl.hotelInfo, {
        id: hotelId
    }).success(function (data) {
        if (!data.success) {
            $scope.error = true;
        } else {
            $scope.hotelId = data.hotelId;
            $scope.hotelName = data.hotelName;
            $scope.score = data.score;
            $scope.star = data.star;
            $scope.starDesc = data.starDesc;
            $scope.address = data.address;
            $scope.hotelCover = data.hotelCover.indexOf("http") == 0 ? data.hotelCover : QINIU_BUCKET_URL + data.hotelCover;
            $scope.images = data.images;
            $scope.imageCount = data.images.length;
            $scope.commentCount = data.commentCount;
            $scope.shortDesc = data.shortDesc;
            $scope.description = data.description;
            $scope.favorite = data.favorite;
            $scope.lat = data.lat;
            $scope.lng = data.lng;
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

    // 初始化选中条件
    $scope.initFilter = function () {
        var roomFilter = storage.get(yhyKey.hotelDetailRoomFilter);
        if (!roomFilter) {
            roomFilter = {};
        }
        ;
        $scope.bntBedType = roomFilter.bntBedType;       // 床型
        $scope.bntBreakfast = roomFilter.bntBreakfast;     // 早餐
        //$scope.bntNetwork = roomFilter.bntNetwork;       // 宽带
        $scope.bntPayWay = roomFilter.bntPayWay;        // 支付方式
        $scope.bntPrice = roomFilter.bntPrice;         // 价格范围
        //$scope.bntBookChoose = roomFilter.bntBookChoose;       // 可订
        //$scope.bntConfirmChoose = roomFilter.bntConfirmChoose;   // 立即确认
        //$scope.bntHourRoom = roomFilter.bntHourRoom;         // 钟点房
        //$scope.bntCancelChoose = roomFilter.bntCancelChoose;     // 免费取消
        //$scope.bntAddBed = roomFilter.bntAddBed;           // 可加床
        if (roomFilter.bntBedType || roomFilter.bntBreakfast || roomFilter.bntPayWay || roomFilter.bntPrice) {
            $scope.hasRoomFilter = true;       // 是否有附加条件
        }
        $scope.tipInDateDispaly = $scope.day.startDate.substr(5);            // 入住日期显示
        $scope.tipOutDateDispaly = $scope.day.endDate.substr(5);           // 离店日期显示
        storage.set(yhyKey.hotelDetailRoomFilter, roomFilter);
    };
    $scope.initFilter();
    // 获取酒店房型列表
    $scope.nomore = false;
    var loadFlag = false; // 是否已加载，只加载一次
    $scope.listHotelPrice = function () {
        if (loadFlag) {
            return;
        }
        loadFlag = true;
        // 处理参数
        var roomFilter = storage.get(yhyKey.hotelDetailRoomFilter);
        if (!roomFilter) {
            roomFilter = {};
        }
        ;
        var bntBedType = roomFilter.bntBedType;       // 床型
        if (!bntBedType) {
            bntBedType = null;
        }
        var bntBreakfast = roomFilter.bntBreakfast;     // 早餐
        if (!bntBreakfast) {
            bntBreakfast = null;
        }
        var bntPayWay = roomFilter.bntPayWay;        // 支付方式
        if (!bntPayWay) {
            bntPayWay = null;
        }
        var bntPrice = roomFilter.bntPrice;         // 价格范围
        var priceLow = null;
        var priceHigh = null;
        if (!bntPrice) {
            bntPrice = null;
        } else {
            priceLow = roomFilter.priceLow;
            priceHigh = roomFilter.priceHigh;
        }

        $rootScope.loading = true;
        $http.post(yhyUrl.listHotelPrice, {
            hotelId: hotelId,
            tipInDate: $scope.day.startDate,
            tipOutDate: $scope.day.endDate,
            bntBedType: bntBedType,
            bntBreakfast: bntBreakfast,
            bntPayWay: bntPayWay,
            bntPrice: bntPrice,
            priceLow: priceLow,
            priceHigh: priceHigh
        }).success(function (data) {
            $rootScope.loading = false;
            if (!data.success) {
                $scope.error = true;
            } else {
                if (data.hotelPriceList && data.hotelPriceList.length > 0) {
                    $scope.noHotelPrice = false;
                    angular.forEach(data.hotelPriceList, function (price) {
                        price.selected = false;
                        if (!$.isEmptyObject($scope.day.hotel)) {
                            angular.forEach($scope.day.hotel.priceIds, function (id) {
                                if (price.id == id) {
                                    $scope.priceIds.push(id);
                                    price.selected = true;
                                    $scope.hotelTotalPrice += price.price;
                                    return false;
                                }
                            });
                        }
                    });
                    $scope.hotelPriceList = data.hotelPriceList;
                } else {
                    $scope.noHotelPrice = true;
                }
                $scope.lineList = data.lineList;
            }
        }).error(function (data) {
            $rootScope.loading = false;
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '系统错误'
            })
        });
    };
    // 返回
    $scope.back = function () {
        history.go(-1);
    };
    //收藏
    $scope.doFavorite = function () {
        $http.post(yhyUrl.favorite, {
            favoriteId: hotelId,
            favoriteType: 'hotel'
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.error) {
                    $scope.error = true;
                    console.log(data.errorMsg);
                } else {
                    $scope.favorite = data.favorite;
                }
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
    };
    // 条件筛选面板
    $scope.filterPanel = function () {
        // 设置条件
        //var roomFilter = storage.get(yhyKey.hotelDetailRoomFilter);
        //roomFilter.bntBookChoose = $scope.bntBookChoose;      // 可订
        //roomFilter.bntConfirmChoose = $scope.bntConfirmChoose;   // 立即确认
        //roomFilter.bntHourRoom = $scope.bntHourRoom;         // 钟点房
        //roomFilter.bntCancelChoose = $scope.bntCancelChoose;     // 免费取消
        //roomFilter.bntAddBed = $scope.bntAddBed;           // 可加床
        //storage.set(yhyKey.hotelDetailRoomFilter, roomFilter);
        $state.go("hotelDetailRoomFilter", {}, {reload: true});
    };
    // 打开相册
    $scope.openAlbum = function () {
        $scope.showAlbumFlag = true;
    };
    // 关闭相册
    $scope.closeAlbum = function () {
        $scope.showAlbumFlag = false;
    };
    // 图片预览
    $scope.viewAlbumImg = function (index) {
        storage.set(yhyKey.hotelAlbum, {
            index: index,
            images: $scope.images
        });
        $state.go("hotel_album");
    };
    // 酒店评论页面
    $scope.goHotelComments = function () {
        $state.go("hotelComments", {hotelId: hotelId}, {reload: true});
    };
    // 更换
    $scope.change = function (hotelPrice) {
        var index = -1;
        angular.forEach($scope.priceIds, function (id, i) {
            if (hotelPrice.id == id) {
                index = i;
                return false;
            }
        });
        if (hotelPrice.selected && index > -1) {
            $scope.priceIds.splice(index, 1);
            $scope.hotelTotalPrice -= hotelPrice.price;
        }
        if (!hotelPrice.selected && index < 0) {
            $scope.priceIds.push(hotelPrice.id);
            $scope.hotelTotalPrice += hotelPrice.price;
        }
        hotelPrice.selected = !hotelPrice.selected;
    };

    $scope.complete = function () {
        $scope.day.hotel = {
            name: $scope.hotelName,
            address: $scope.address,
            cover: $scope.hotelCover,
            distance: 0,
            id: hotelId,
            lat: $scope.lat,
            lng: $scope.lng,
            startDate: $scope.day.startDate,
            endDate: $scope.day.endDate,
            priceIds: $scope.priceIds,
            price: $scope.hotelTotalPrice
        };
        storage.set(yhyKey.planDetail, $scope.plan);
        $state.go("planDetail");
    };
});

planModule.controller("orderPlanCtrl", function ($scope, $http, storage, Check, $state, $rootScope, PeopleType, NumberHandle, ObjectHandle) {
    $scope.planDemand = storage.get(yhyKey.planDemand);
    if ($scope.planDemand == null) {
        $state.go("planDemand");
        return;
    }
    $scope.planDaysRequest = storage.get(yhyKey.planDaysRequest);
    if ($scope.planDaysRequest == null) {
        $state.go("planDetail");
        return;
    }
    $scope.selectedTourist = storage.get(yhyKey.selectedTourist);
    if ($scope.selectedTourist == null) {
        $state.go("planTouristList");
        return;
    }
    $scope.planSelectedFerry = storage.get(yhyKey.planSelectedFerry);
    $scope.ferryList = [];
    angular.forEach($scope.selectedTourist, function (tourist) {
        tourist.selected = true;
    });
    if (!$.isEmptyObject($scope.planSelectedFerry)) {
        var ferryNum = 0;
        angular.forEach($scope.planSelectedFerry.ticketLst.Ticket, function (ticket) {
            ticket.selected = false;
            ticket.touristList = [];
            var touType = "";
            if (ticket.number.indexOf("E") > -1) {
                touType = "KID";
            } else if (ticket.number.indexOf("Q") > -1) {
                touType = "ADULT";
            }
            angular.forEach($scope.selectedTourist, function (tourist) {
                if (tourist.peopleType == touType) {
                    ticket.touristList.push(tourist);
                }
            });
            ticket.totalPrice = NumberHandle.roundTwoDecimal(ticket.price * ticket.touristList.length);
            ticket.selectedNum = ticket.touristList.length;
            if (ticket.selectedNum > 0) {
                ticket.selected = true;
            }
            ferryNum += ticket.selectedNum;
        });
        $scope.planSelectedFerry.selectedNum = ferryNum;
    }
    $scope.order = storage.get(yhyKey.planOrder);
    $scope.contact = {};

    console.info($scope.order);

    if ($scope.order == null) {
        $rootScope.loading = true;
        $http.post(yhyUrl.orderNoPlan, {
            json: JSON.stringify({
                startDate: $scope.planDemand.playDate,
                days: $scope.planDaysRequest
            })
        }).success(function (data) {
            $rootScope.loading = false;
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                $scope.order = data.order;
                angular.forEach($scope.order.scenics, function (scenic) {
                    scenic.selected = true;
                    scenic.selectedTourist = ObjectHandle.clone($scope.selectedTourist);
                    scenic.totalPrice = 0;
                    angular.forEach(scenic.tickets, function (ticket) {
                        ticket.selectedNum = scenic.selectedTourist.length;
                    });
                });
                angular.forEach($scope.order.hotels, function (hotel) {
                    hotel.selected = true;
                    hotel.selectedTourist = ObjectHandle.clone($scope.selectedTourist);
                    hotel.totalPrice = 0;
                    angular.forEach(hotel.hotelPrices, function (price) {
                        price.selectedNum = hotel.selectedTourist.length;
                    });
                });
                storage.set(yhyKey.planOrder, $scope.order);
                changeCost();
                console.info($scope.order);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    } else {
        changeCost();
    }

    $scope.selectDay = function (day) {
        day.selected = !day.selected;
        angular.forEach(day.scenics, function (scenic) {
            scenic.selected = day.selected;
        });
        changeCost();
    };

    $scope.checkright = function (boat) {
        boat.selected = !boat.selected;
        changeCost();
    };

    $scope.removeFerryTourist = function (ferry, tourist) {
        var num = 0;
        angular.forEach(ferry.touristList, function (tourist) {
            if (tourist.selected) {
                num++;
            }
        });
        if (num < 2) {
            return;
        }
        tourist.selected = false;
        ferry.selectedNum = num - 1;
        changeCost();
    };

    $scope.removeTourist = function (touristList, tourist, itemList) {
        var num = 0;
        angular.forEach(touristList, function (tourist) {
            if (tourist.selected) {
                num++;
            }
        });
        if (num < 2) {
            return;
        }
        tourist.selected = false;
        angular.forEach(itemList, function (item) {
            if (item.selectedNum >= num) {
                item.selectedNum = num - 1;
            }
        });
        changeCost();
    };

    $scope.changeCost = function () {
        $scope.$apply(function () {
            changeCost();
        });
    };

    function changeCost() {
        var totalPrice = 0;
        if (!$.isEmptyObject($scope.planSelectedFerry)) {
            angular.forEach($scope.planSelectedFerry.ticketLst.Ticket, function (ticket) {
                ticket.totalPrice = NumberHandle.roundTwoDecimal(ticket.price * ticket.selectedNum);
                if (ticket.selected) {
                    totalPrice += ticket.totalPrice;
                }
            });
        }
        angular.forEach($scope.order.scenics, function (scenic) {
            var scenicTotal = 0;
            angular.forEach(scenic.tickets, function (ticket) {
                scenicTotal += ticket.selectedNum * ticket.price;
            });
            scenic.totalPrice = NumberHandle.roundTwoDecimal(scenicTotal);
            if (scenic.selected) {
                totalPrice += scenic.totalPrice;
            }
        });
        angular.forEach($scope.order.hotels, function (hotel) {
            var hotelTotal = 0;
            angular.forEach(hotel.hotelPrices, function (price) {
                hotelTotal += price.selectedNum * price.price;
            });
            hotel.totalPrice = NumberHandle.roundTwoDecimal(hotelTotal);
            if (hotel.selected) {
                totalPrice += hotel.totalPrice;
            }
        });
        $scope.totalPrice = NumberHandle.roundTwoDecimal(totalPrice);
    }
    $scope.showthisone = function (ferry, event) {
        event.stopPropagation();
        ferry.showMore = !ferry.showMore;
        $(event.target).text(ferry.showMore ? "收起" : "更多");
    };

    $scope.submitOrder = function () {
        if ($scope.totalPrice <= 0) {
            bootbox.alert("未选择购买项目");
            return;
        }
        var jsonObj = {
            id: 0,
            name: $scope.order.name,
            day: $scope.order.day,
            playDate: $scope.order.playDate,
            contact: $scope.contact,
            orderType: "plan"
        };
        var details = [];
        angular.forEach($scope.order.scenics, function (scenic) {
            if (scenic.selected) {
                var tourists = [];
                angular.forEach(scenic.selectedTourist, function (selected) {
                    if (!selected.selected) {
                        return true;
                    }
                    var tourist = {
                        name: selected.name,
                        phone: selected.telephone,
                        peopleType: selected.peopleType,
                        idType: selected.idType,
                        idNum: selected.idNumber
                    };
                    tourists.push(tourist);
                });
                angular.forEach(scenic.tickets, function (ticket) {
                    if (ticket.selectedNum < 1) {
                        return true;
                    }
                    var detail = {
                        id: ticket.ticketId,
                        priceId: ticket.priceId,
                        price: ticket.price,
                        startTime: scenic.playDate,
                        endTime: scenic.playDate,
                        num: ticket.selectedNum,
                        type: "scenic",
                        seatType: ticket.ticketName
                    };
                    detail.tourist = tourists;
                    details.push(detail);
                });
            }
        });
        angular.forEach($scope.order.hotels, function (hotel) {
            if (hotel.selected) {
                var tourists = [];
                angular.forEach(hotel.selectedTourist, function (selected) {
                    if (!selected.selected) {
                        return true;
                    }
                    var tourist = {
                        name: selected.name,
                        phone: selected.telephone,
                        peopleType: selected.peopleType,
                        idType: selected.idType,
                        idNum: selected.idNumber
                    };
                    tourists.push(tourist);
                });
                angular.forEach(hotel.hotelPrices, function (price) {
                    if (price.selectedNum < 1) {
                        return true;
                    }
                    var detail = {
                        id: hotel.id,
                        priceId: price.priceId,
                        price: price.price,
                        startTime: price.startDate,
                        endTime: price.endDate,
                        num: price.selectedNum,
                        type: "hotel",
                        seatType: price.priceName
                    };
                    detail.tourist = tourists;
                    details.push(detail);
                });
            }
        });
        jsonObj.details = details;
        var ferryTicketList = [];
        var ferryTotal = 0;
        if (!$.isEmptyObject($scope.planSelectedFerry)) {
            angular.forEach($scope.planSelectedFerry.ticketLst.Ticket, function (ticket) {
                if (!ticket.selected) {
                    return true;
                }
                angular.forEach(ticket.touristList, function (tourist) {
                    if (tourist.idType == "IDCARD") {
                        tourist.idType = "ID_CARD";
                    } else {
                        tourist.idType = "OTHER";
                    }
                    ferryTicketList.push({
                        ticketId: ticket.id,
                        ticketName: ticket.name,
                        price: ticket.price,
                        number: ticket.number,
                        idType: tourist.idType,
                        name: tourist.name,
                        idnumber: tourist.idNumber,
                        mobile: tourist.telephone
                    });
                });
                ferryTotal += ticket.totalPrice;
            });
            var ferry = {
                dailyFlightGid: $scope.planSelectedFerry.dailyFlightGid,
                flightNumber: $scope.planSelectedFerry.number,
                flightLineName: $scope.planSelectedFerry.line.name,
                departTime: $scope.planSelectedFerry.departTime,
                amount: NumberHandle.roundTwoDecimal(ferryTotal),
                seat: ferryTicketList.length,
                ferryOrderItemList: ferryTicketList
            };
        }
        var params = {
            json: JSON.stringify(jsonObj)
        };
        if (ferryTotal > 0) {
            params.ferryJson = JSON.stringify(ferry);
        }
        $http.post(yhyUrl.saveOrder, params).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                storage.remove(yhyKey.planDemand);
                storage.remove(yhyKey.selectedScenic);
                storage.remove(yhyKey.planDaysRequest);
                storage.remove(yhyKey.order);
                $state.go("orderPay", {orderId: data.order.id});
            } else if (data.noReal) {
                bootbox.alert(data.errMsg, function () {
                    storage.set(yhyKey.preUrl, "orderPlan");
                    $state.go("ferryRealname");
                });
            } else {
                bootbox.alert(data.errMsg);
            }
        });
    };
});

planModule.controller("planTouristListCtrl", function ($scope, $http, storage, $state, Check, $rootScope) {
    $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
        $rootScope.previousState = from;
        $rootScope.previousParams = fromParams;
    });
    $scope.planDemand = storage.get(yhyKey.planDemand);
    if ($scope.planDemand == null) {
        history.go(-1);
    }
    $scope.selectedTourist = storage.get(yhyKey.selectedTourist);
    if ($scope.selectedTourist == null) {
        $scope.selectedTourist = [];
    }
    $scope.touristList = [];

    $scope.search = function () {
        if ($scope.touristList.length == 0) {
            return;
        }
        if ($scope.keyword == null || $scope.keyword == "") {
            angular.forEach($scope.touristList, function (tourist) {
                tourist.show = true;
            });
            return;
        }
        if (!$scope.keyword.match(yhyKey.nameReg)) {
            return;
        }
        angular.forEach($scope.touristList, function (tourist) {
            tourist.show = tourist.name.indexOf($scope.keyword) > -1;
        });
    };

    $http.post(yhyUrl.touristList, {}
    ).success(function (data) {
            $scope.loaded = true;
            if (Check.loginCheck(data)) {
                if (data.success) {
                    if (data.touristList.length == 0) {
                        if ($rootScope.previousState != null && $rootScope.previousState.name == "personalWriteTourMess") {
                            $state.go("planDetail");
                        } else {
                            $state.go("personalWriteTourMess");
                        }
                        return;
                    }
                    angular.forEach(data.touristList, function (tourist) {
                        tourist.check = false;
                        if (tourist.peopleType == "ADULT") {
                            tourist.showType = "成人";
                        } else {
                            tourist.showType = "儿童";
                        }
                        angular.forEach($scope.selectedTourist, function (selected, j, list) {
                            if (tourist.touristId == selected.touristId) {
                                tourist.check = true;
                                list.splice(j, 1, tourist);
                            }
                        });
                    });
                    $scope.touristList = data.touristList;
                    $scope.search();
                } else {
                }
            }
        }).error(function (data) {
            alert(data.errorMsg);
        });

    $scope.selectTourist = function (tourist, event) {
        event.stopPropagation();
        tourist.check = !tourist.check;
        if (tourist.check) {
            $scope.selectedTourist.push(tourist);
        } else {
            var index = -1;
            angular.forEach($scope.selectedTourist, function (selected, i) {
                if (tourist.touristId == selected.touristId) {
                    index = i;
                }
            });
            $scope.selectedTourist.splice(index, 1);
        }
        storage.set(yhyKey.selectedTourist, $scope.selectedTourist);
    };

    $scope.submit = function () {
        //var adultNum = 0;
        //var childNum = 0;
        //angular.forEach($scope.selectedTourist, function (tourist) {
        //    if (tourist.peopleType == "ADULT") {
        //        adultNum++;
        //    } else {
        //        childNum++;
        //    }
        //});
        //if ($scope.planDemand.adultNum != adultNum || $scope.planDemand.childNum != childNum) {
        //    bootbox.alert("游客人数错误");
        //    return;
        //}
        if ($scope.selectedTourist.length == 0) {
            bootbox.alert("请选择游客");
            return;
        }
        storage.set(yhyKey.selectedTourist, $scope.selectedTourist);
        storage.remove(yhyKey.planOrder);
        $state.go("orderPlan");
    };
});

planModule.controller("planOrderDetailCtrl", function ($scope, $http, $stateParams, Check, storage, $state) {
    $scope.id = $stateParams.id;
    if ($scope.id == null || $scope.id < 1) {
        history.go(-1);
    }
    $scope.number = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];

    $http.post(yhyUrl.myorderinfo, {
        orderId: $scope.id
    }).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.order = data.order;
            console.info($scope.order);
        }
    });

    $scope.back = function() {
        var url = storage.get(yhyKey.preUrl);
        storage.remove(yhyKey.preUrl);
        if ("personalOrder" == url) {
            history.back();
        } else {
            $state.go("personalOrder", {index: 0});
        }
    };
});
planModule.controller("planferrylistCtrl", function ($scope, $http, storage, $state) {
    $scope.plan = storage.get(yhyKey.planDetail);
    angular.forEach($scope.plan.days, function (day) {
        if (day.changingFerry) {
            $scope.day = day;
            return false;
        }
    });
    $scope.flightList = [];

    $http.post(yhyUrl.ferryGetDailyFlight, {
        date: $scope.day.startDate,
        flightLineId: $scope.day.ferry.line.number
    }).success(function (data) {
        if (data.success) {
            angular.forEach(data.flightList, function (ferry) {
                ferry.startTime = ferry.departTime.substring(12, ferry.departTime.length);
            });
            $scope.flightList = data.flightList;
        }
    });

    $scope.changeFerry = function (ferry) {
        ferry.line = $scope.day.ferry.line;
        $scope.day.ferry = ferry;
        $scope.day.changingFerry = false;
        storage.set(yhyKey.planDetail, $scope.plan);
        $state.go("planDetail");
    }
});