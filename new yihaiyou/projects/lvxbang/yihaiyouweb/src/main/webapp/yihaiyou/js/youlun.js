/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
var youlun = angular.module('youlun', ['infinite-scroll']);
youlun.controller("cruiseShipIndexCtrl", function ($scope, $http, $state) {
    $http.post(yhyUrl.shipIndex, {}).success(function (data) {
        if (data.success) {
            $scope.ships = data.ships;
        }
    });

    $scope.toList = function (id, type) {
        var request = {};
        request[type] = id;
        $state.go("cruiseShipList", {request: JSON.stringify(request)});
    };
});

youlun.controller('cruiseShipSearchCtrl', function ($scope, $http, $state, storage) {
    var bodyHeight = $(window).height();
    $('.searchhistory').css({'min-height': bodyHeight - 95});
    $('.searchresult').css({'min-height': bodyHeight - 95});
    $scope.showhistory = false;
    $scope.showresult = false;
    $scope.searchHistory = storage.get(yhyKey.shipSearchHistory) == null ? [] : storage.get(yhyKey.shipSearchHistory);
    $scope.hotRoutes = [];
    $scope.hotBrands = [];
    $scope.suggestions = [];

    $http.post(yhyUrl.shipBrand, {}).success(function (data) {
        if (data.success) {
            $scope.hotBrands = data.brands;
        }
    });

    $http.post(yhyUrl.shipRoute, {}).success(function (data) {
        if (data.success) {
            $scope.hotRoutes = data.routes;
        }
    });

    $scope.showHistory = function (event) {
        event.stopPropagation();
        changeShow();
    };

    $scope.hideHistory = function () {
        $scope.showhistory = false;
    };

    $scope.showSearchResult = function (event) {
        event.stopPropagation();
        changeShow();
        if (!$scope.showresult) {
            return;
        }
        $http.post(yhyUrl.shipSuggest, {
            name: $scope.name,
            pageSize: 5
        }).success(function (data) {
            if (data.success) {
                $scope.suggestions = data.suggestions;
            }
        });
    };

    function changeShow() {
        if ($scope.name == null || $scope.name == "") {
            $scope.showhistory = true;
            $scope.showresult = false;
        } else {
            $scope.showresult = true;
            $scope.showhistory = false;
        }
    }

    $scope.hideSearch = function () {
        $scope.showresult = false;
        $scope.showhistory = false;
    };

    $scope.toList1 = function (name) {
        var request = {name: name};
        var inHistory = 5;
        angular.forEach($scope.searchHistory, function (history, i) {
            if (history == name) {
                inHistory = i;
                return false;
            }
        });
        $scope.searchHistory.splice(inHistory, 1);
        $scope.searchHistory.splice(0, 0, name);
        storage.set(yhyKey.shipSearchHistory, $scope.searchHistory);
        $state.go("cruiseShipList", {request: JSON.stringify(request)});
    };

    $scope.toList2 = function (id, type) {
        var request = {};
        request[type] = id;
        $state.go("cruiseShipList", {request: JSON.stringify(request)});
    };

    $scope.deleteHistory = function (index, event) {
        event.stopPropagation();
        $scope.searchHistory.splice(index, 1);
        storage.set(yhyKey.shipSearchHistory, $scope.searchHistory);
    };

    $scope.clearHistory = function () {
        $scope.searchHistory = [];
        storage.set(yhyKey.shipSearchHistory, $scope.searchHistory);
    };
});

youlun.controller('cruiseShipDetailCtrl', function ($scope, $http, $stateParams, storage, $state, Check) {
    $scope.dateId = $stateParams.id;
    $scope.cangfang = -1;
    var k = 0;
    var week = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
    $scope.day_march = true;
    $scope.boat_introduce = false;
    $scope.xuzhi = false;
    $scope.completeFlag = false;
    $scope.isCollect = false;
    //$scope.date = {
    //    id: null,
    //    minSalePrice: 0
    //};
    //var orderyoulun = storage.get(yhyKey.orderyoulun) == null ? {} : storage.get(yhyKey.orderyoulun);
    //$scope.date.id = orderyoulun.dateId;
    $scope.rooms = [];
    $scope.roomTypes = [];

    $http.post(yhyUrl.shipDetail, {
        dateId: $scope.dateId
    }).success(function (data) {
        if (data.success) {
            $scope.ship = data.ship;
            if (data.ship.date == null) {
                return;
            }
            $scope.date = data.ship.date;
            var date = new Date($scope.date.date);
            var showDate = new Date($scope.date.date);
            angular.forEach($scope.ship.planDays, function (day) {
                showDate.setDate(date.getDate() + day.day - 1);
                day.showDate = showDate.format("yyyy年MM月dd日");
            });
            $scope.date.startDate = date.format("MM月dd日");
            $scope.date.startWeek = week[date.getDay()];
            date.setDate(date.getDate() + $scope.ship.playDay);
            $scope.date.endDate = date.format("MM月dd日");
            $scope.date.endWeek = week[date.getDay()];
            $scope.roomTypes = data.ship.roomTypes;
            //$scope.rooms = data.ship.rooms;
            var orderyoulun = {
                shipId: $scope.ship.id,
                dateId: $scope.date.id
            };
            storage.set(yhyKey.orderyoulun, orderyoulun);
            $scope.completeFlag = true;
            $http.post(yhyUrl.checkSelection, {
                targetType: "cruiseship",
                targetId: $scope.date.id
            }).success(function (data) {
                if (data.success) {
                    $scope.isCollect = data.exists;
                }
            });
        } else {
            history.go(-1);
        }
    });

    $scope.addCollect = function () {
        if ($scope.isCollect) {
            $http.post(yhyUrl.deleteSelection, {
                targetType: "cruiseship",
                targetId: $scope.date.id
            }).success(function (data) {
                if (!Check.loginCheck(data)) {
                    return;
                }
                if (data.success) {
                    $scope.isCollect = false;
                }
            });
        } else {
            $http.post(yhyUrl.addSelection, {
                targetType: "cruiseship",
                targetId: $scope.date.id
            }).success(function (data) {
                if (!Check.loginCheck(data)) {
                    return;
                }
                if (data.success) {
                    $scope.isCollect = true;
                }
            });
        }
    };

    $scope.showheight = function () {
        if (k == 0) {
            $('.youlunlist .view-info').css({'height': 'auto'});
            k = 1;
        } else {
            $('.youlunlist .view-info').css({'height': '55px'});
            k = 0;
        }
    };

    $scope.sliderb = function (num) {
        $('.listbar ul li').css({'color': '#5f5f5f'});
        if (num == 0) {
            $('.sliderbar').animate({'left': 0}, 200);
            $('.listbar ul li:eq(0)').css({'color': '#007eff'});
            $scope.day_march = true;
            $scope.boat_introduce = false;
            $scope.xuzhi = false;
        } else if (num == 1) {
            $('.sliderbar').animate({'left': '33%'}, 200);
            $('.listbar ul li:eq(1)').css({'color': '#007eff'});
            $scope.day_march = false;
            $scope.boat_introduce = true;
            $scope.xuzhi = false;
        } else {
            $('.sliderbar').animate({'left': '66%'}, 200);
            $('.listbar ul li:eq(2)').css({'color': '#007eff'});
            $scope.day_march = false;
            $scope.boat_introduce = false;
            $scope.xuzhi = true;
        }
    };

    $scope.toOrder = function (type) {
        if ($scope.roomTypes.length == 0) {
            return;
        }
        $state.go("orderCruiseShip", {roomType: type});
    }
    $scope.showCangFang = function (index) {
        $scope.cangfang = index;
    }
    $scope.hideCangFang = function () {
        $scope.cangfang = -1;
    }
    var cangHeight = $(window).height();
    $('.cangfang').css({'height': cangHeight});

    $scope.kefushadow = false;
    $scope.kefubox = false;
    $scope.showPhone = function () {
        $scope.kefushadow = true;
        $scope.kefubox = true;
        $("div[ng-controller]").addClass('stopscroll');
    }
    $scope.hidePhone = function () {
        $scope.kefushadow = false;
        $scope.kefubox = false;
        $("div[ng-controller]").removeClass('stopscroll');
    }
    var kefu_left = (window.screen.width - $('.kefu_phone').width()) / 2;
    var kefu_top = (window.screen.height - $('.kefu_phone').height()) / 2;
    $('.kefu_phone').css({'top': kefu_top, 'left': kefu_left})
});

youlun.controller('cruiseShipListCtrl', function ($scope, $http, $stateParams, storage, $rootScope) {
    $scope.showNum = -1;
    $scope.showList1 = 0;
    $scope.showList1Str = "不限";
    $scope.showList2 = 0;
    $scope.showList3 = 0;
    $scope.showList4 = 0;
    $scope.showList5Str = "全部船队";
    $scope.KJline = false;
    $scope.ships = false;
    $scope.sub = false;
    $scope.dropdown = false;
    $scope.shadow = false;
    $scope.search1 = false;
    $scope.showhistory = false;
    $scope.showResult = false;
    $scope.request = $stateParams.request == null || $stateParams.request == "" ? {} : JSON.parse($stateParams.request);
    if ($scope.request.orderColumn == null || $scope.request.orderColumn == "" || $scope.request.orderType == null || $scope.request.orderType == "") {
        $scope.request.orderColumn = "orderNum";
        $scope.request.orderType = "desc";
    }
    $scope.searchHistory = storage.get(yhyKey.shipSearchHistory) == null ? [] : storage.get(yhyKey.shipSearchHistory);
    $scope.pageSize = 5;
    $scope.selectedObj = {};
    $scope.selected = [];
    $scope.brands = [];
    $scope.routes = [];
    $scope.hotBrands = [];
    $scope.hotRoutes = [];
    $rootScope.loading = false;

    $scope.init = function () {
        $scope.pageNo = 1;
        $scope.ships = [];
        $scope.nomore = false;
    };
    $scope.init();

    $scope.showSel = function (num) {
        $scope.showNum = num;
        $scope.shadow = true;
        $("div[ng-controller]").css({'position': 'fixed', 'left': 0, 'top': 0, 'overflow': 'hidden'});
    };

    $scope.hideShadow = function () {
        $scope.shadow = false;
        $scope.showNum = -1;
        $("div[ng-controller]").css({'position': 'static', 'overflow': 'auto'})
    };

    $scope.showSearchbox = function () {
        $scope.hideShadow();
        $scope.search1 = true;
    };

    $scope.consoleSearchbox = function () {
        $scope.showhistory = false;
        $scope.showresult = false;
        $scope.search1 = false;
    };

    $scope.showlist1 = function (num) {
        $scope.showList1 = num;
        $scope.request.route = num;
        $scope.init();
        $scope.shipList();
    };

    $scope.showlist2 = function (num) {
        $scope.showList2 = num;
    };

    $scope.showlist3 = function (num) {
        $scope.showList3 = num;
    };

    $scope.showlist4 = function (num, orderColumn, orderType) {
        $scope.showList4 = num;
        $scope.request.orderColumn = orderColumn;
        $scope.request.orderType = orderType;
        $scope.init();
        $scope.shipList();
    };

    $scope.showlist5 = function (num, string) {
        $scope.showList5 = num;
        if (string.length > 4) {
            $scope.showList5Str = string.substr(0, 3) + "...";
        } else {
            $scope.showList5Str = string;
        }
        $scope.request.brand = num;
        $scope.init();
        $scope.shipList();
    };

    var bodyHeight = $('body').height();
    $('.youlun_list .module-route').css({'height': bodyHeight});
    $('.youlun_list .shadow').css({'height': bodyHeight});

    $scope.shipList = function () {
        if ($rootScope.loading) {
            return;
        }
        if ($scope.nomore) {
            return;
        }
        $rootScope.loading = true;
        $scope.hideShadow();
        $http.post(yhyUrl.shipList, {
            json: JSON.stringify($scope.request),
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.ships, function (ship) {
                    var city = ship.startCity.split("/");
                    ship.showStartCity = city[city.length - 1];
                    $scope.ships.push(ship);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++;
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    $http.post(yhyUrl.shipRoute, {}).success(function (data) {
        if (data.success) {
            $scope.routes = data.routes;
            $scope.hotRoutes = data.routes;
            if ($scope.request.route != null && $scope.request.route > 0) {
                angular.forEach($scope.routes, function (route) {
                    if (route.id == $scope.request.route) {
                        $scope.showList1 = route.id;
                        if (route.name.length > 4) {
                            $scope.showList1Str = route.name.substr(0, 3) + "...";
                        } else {
                            $scope.showList1Str = route.name;
                        }
                    }
                });
            }
        }
    });

    $http.post(yhyUrl.shipBrand, {}).success(function (data) {
        if (data.success) {
            $scope.brands = data.brands;
            $scope.hotBrands = data.brands;
            if ($scope.request.brand != null && $scope.request.brand > 0) {
                angular.forEach($scope.brands, function (brand) {
                    if (brand.id == $scope.request.brand) {
                        $scope.showList5 = brand.id;
                        if (brand.name.length > 4) {
                            $scope.showList5Str = brand.name.substr(0, 3) + "...";
                        } else {
                            $scope.showList5Str = brand.name;
                        }
                    }
                });
            }
        }
    });

    $scope.changeDate = function (date, string) {
        $scope.request.date = date;
        $scope.selectedObj.date = string;
        selected();
    };

    $scope.changePriceRange = function (min, max, string) {
        $scope.request.priceRange = [min, max];
        $scope.selectedObj.priceRange = string;
        selected();
    };

    $scope.changeDayRange = function (min, max, string) {
        $scope.request.dayRange = [min, max];
        $scope.selectedObj.dayRange = string;
        selected();
    };

    function selected() {
        $scope.selected = [];
        for (var i in $scope.selectedObj) {
            $scope.selected.push($scope.selectedObj[i]);
        }
    }

    $scope.reset = function () {
        $scope.request = {};
        $scope.selected = [];
        $scope.selectedObj = {};
    };

    $scope.deleteHistory = function (index, event) {
        event.stopPropagation();
        $scope.searchHistory.splice(index, 1);
        storage.set(yhyKey.shipSearchHistory, $scope.searchHistory);
    };

    $scope.clearHistory = function (event) {
        event.stopPropagation();
        $scope.searchHistory = [];
        storage.set(yhyKey.shipSearchHistory, $scope.searchHistory);
    };

    $scope.showHistory = function (event) {
        event.stopPropagation();
        changeShow();
    };

    $scope.hideHistory = function () {
        $scope.showhistory = false;
        $scope.showresult = false;
    };

    $scope.showSearchResult = function (event) {
        event.stopPropagation();
        changeShow();
        if (!$scope.showresult) {
            return;
        }
        $http.post(yhyUrl.shipSuggest, {
            name: $scope.name,
            pageSize: 5
        }).success(function (data) {
            if (data.success) {
                $scope.suggestions = data.suggestions;
            }
        });
    };

    function changeShow() {
        if ($scope.name == null || $scope.name == "") {
            $scope.showhistory = true;
            $scope.showresult = false;
        } else {
            $scope.showresult = true;
            $scope.showhistory = false;
        }
    }

    $scope.changeName = function (name) {
        if (name == null || name == "") {
            return;
        }
        var inHistory = 5;
        angular.forEach($scope.searchHistory, function (history, i) {
            if (history == name) {
                inHistory = i;
                return false;
            }
        });
        $scope.searchHistory.splice(inHistory, 1);
        $scope.searchHistory.splice(0, 0, name);
        storage.set(yhyKey.shipSearchHistory, $scope.searchHistory);
        $scope.request.name = name;
        $scope.consoleSearchbox();
        $scope.init();
        $scope.shipList();
    };
});

youlun.controller("orderCruiseShipCtrl", function ($scope, $http, storage, $stateParams, Check, $state, $rootScope) {
    $scope.roomType = $stateParams.roomType;
    $scope.orderyoulun = storage.get(yhyKey.orderyoulun) == null ? {} : storage.get(yhyKey.orderyoulun);
    $scope.roomsList = [];
    $scope.selectRooms = [];
    $scope.contact = {};
    $scope.totalCost = 0;
    $scope.roomTypeSelect = {};
    $http.post(yhyUrl.orderShip, {
        shipId: $scope.orderyoulun.shipId,
        dateId: $scope.orderyoulun.dateId
    }).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.roomsList = data.roomsList;
            if ($scope.roomType == "") {
                $scope.roomType = $scope.roomsList[0][0].roomType;
            }
            angular.forEach($scope.roomsList, function (rooms) {
                angular.forEach(rooms, function (room, i) {
                    if (i == 0) {
                        $scope.roomTypeSelect[room.roomType] = 0;
                    }
                    room.selectNum = 0;
                    if (room.forceFlag) {
                        room.addNum = room.peopleNum;
                    } else {
                        room.addNum = 1;
                    }
                });
            });
            $scope.ship = data.ship;
            $scope.date = data.date;
            var date = new Date($scope.date.date);
            date.setDate(date.getDate() + $scope.ship.playDay);
            $scope.date.endDate = date.format("yyyy-MM-dd");
        }
    });

    $scope.changeType = function (roomType) {
        $scope.roomType = roomType;
    };

    $scope.changeCost = function () {
        $scope.$apply(function () {
            var cost = 0;
            var selectRooms = [];
            angular.forEach($scope.roomsList, function (rooms) {
                var num = 0;
                angular.forEach(rooms, function (room) {
                    if (room.selectNum > 0) {
                        num += room.selectNum;
                        cost += room.price * room.selectNum;
                        selectRooms.push(room);
                    }
                });
                $scope.roomTypeSelect[rooms[0].roomType] = num;
            });
            $scope.totalCost = Math.round(cost * 100) / 100;
            $scope.selectRooms = selectRooms;
        });
    };

    $scope.submitOrder = function () {
        var name = /^[\u4E00-\u9FA5]+$/;
        if ($scope.contact.name == null || $scope.contact.name == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写联系人姓名'
            });
            return;
        }
        if (!$scope.contact.name.match(name) || $scope.contact.name.length > 10) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '联系人姓名有误'
            });
            return;
        }
        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        if ($scope.contact.telephone == null || $scope.contact.telephone == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写联系人手机'
            });
            return;
        }
        if (!$scope.contact.telephone.match(mobile)) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '联系人手机有误'
            });
            return;
        }
        var json = {};
        angular.forEach($scope.roomsList, function (rooms) {
            angular.forEach(rooms, function (room) {
                if (room.selectNum > 0) {
                    json[room.roomDateId] = room.selectNum;
                }
            });
        });
        if ($.isEmptyObject(json)) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请选择人数'
            });
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.checkShipOrder, {
            json: JSON.stringify(json)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                var jsonObj = {
                    id: 0,
                    name: $scope.ship.name,
                    playDate: $scope.date.date,
                    contact: $scope.contact,
                    orderType: "cruiseship"
                };
                var details = [];
                angular.forEach($scope.roomsList, function (rooms) {
                    angular.forEach(rooms, function (room) {
                        if (room.selectNum > 0) {
                            var detail = {
                                id: $scope.ship.id,
                                priceId: room.roomId,
                                price: room.price,
                                startTime: $scope.date.date,
                                endTime: $scope.date.endDate,
                                num: room.selectNum,
                                type: "cruiseship",
                                seatType: room.name
                            };
                            detail.tourist = [{
                                name: $scope.contact.name,
                                tel: $scope.contact.telephone
                            }];
                            details.push(detail);
                        }
                    });
                });
                jsonObj.details = details;
                $http.post(yhyUrl.saveOrder, {
                    json: JSON.stringify(jsonObj)
                }).success(function (data) {
                    $rootScope.loading = false;
                    if (data.success) {
                        storage.remove(yhyKey.selectedTourist);
                        $state.go("cruiseShipOrderDetail", {orderId: data.order.id});
                    }
                }).error(function () {
                    $rootScope.loading = false;
                });
            } else {
                bootbox.alert(data.errMsg);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };
});

youlun.controller("cruiseShipDateCtrl", function ($scope, $stateParams, $state, storage, $http) {
    $scope.shipId = $stateParams.shipId;
    $("div.ajaxloading").show();
    $http.post(yhyUrl.orderDateList, {
        shipId: $scope.shipId
    }).success(function (data) {
        if (data.success) {
            var calendarData = [];
            angular.forEach(data.dateList, function (date) {
                calendarData.push({
                    id: date.date,
                    dateId: date.id,
                    price: date.minSalePrice,
                    date: date.date,
                    title: '¥' + date.minSalePrice + "起"
                });
            });
            $scope.calendarData = calendarData;
        }
        $("div.ajaxloading").hide();
    });

    $scope.eventClick = function (calEvent) {
        var date = new Date(calEvent.date);
        $scope.selectedDate = (date.getMonth() + 1) + "月" + date.getDate() + "日";
        $scope.price = calEvent.price;
        $scope.startDate = calEvent.date;
        $scope.dateId = calEvent.dateId;
    };

    $scope.dayClick = function (date) {
        var calEvent = $("#calendar").fullCalendar('clientEvents', date)[0];
        if (calEvent != null) {
            $scope.eventClick(calEvent);
        } else {
            $(".fc-highlight-skeleton").remove();
            $("#calendar").fullCalendar('select', $.fullCalendar.moment($scope.startDate));
        }
    };

    $scope.next = function () {
        if ($scope.dateId != null && $scope.dateId != "") {
            var orderyoulun = {
                shipId: $scope.shipId,
                dateId: $scope.dateId
            };
            storage.set(yhyKey.orderyoulun, orderyoulun);
            history.go(-1);
        } else {
            bootbox.alert("请选择团期");
        }
    }
});

youlun.controller("cruiseShipOrderDetailCtrl", function ($scope, $http, $stateParams, Check, storage, $state) {
    $scope.orderId = $stateParams.orderId;
    $http.post(yhyUrl.cruiseShipOrderDetail, {
        orderId: $scope.orderId
    }).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.order = data.order;
        }
    });

    $scope.back = function () {
        if ("personalOrder" == storage.get(yhyKey.preUrl)) {
            history.back();
        } else {
            $state.go("personalOrder", {index: 0});
        }
    };
});