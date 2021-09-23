var bookingCar = angular.module('bk_car', []);
bookingCar.controller('tagplace', function ($scope, $stateParams, $http, Check, $state, $rootScope, storage) {
    $scope.firstpla = storage.get(yhyKey.shangcheAddress);
    if ($scope.firstpla == null) {
        $scope.firstpla = {};
    }
    $scope.lastpla = storage.get(yhyKey.xiacheAddress);
    if ($scope.lastpla == null) {
        $scope.lastpla = {};
    }
    $scope.isShow = false;
    $scope.carUse = false;
    $scope.carNo = 2;
    $scope.where = "";
    $scope.messagex = {};
    $scope.date_car = false;
    $scope.serviceType = 1;
    $scope.price = {price: 0};
    $scope.shadowScreen = false;
    $scope.statement_word = false;
    var nowTime = new Date();
    nowTime.setMinutes(nowTime.getMinutes() + 30);
    $scope.startTime = nowTime.format("yyyy-MM-dd hh:mm");
    //行程价格，神舟接口
    $scope.getPrice = function () {
        if (!$scope.checkPhone()) {
            return;
        }
        if ($scope.noTel) {
            $http.post(yhyUrl.updatePersonalTelephone, {
                telephone: $scope.messagex.phone
            }).success(function (data) {
                if (data.success) {
                    getPriceFun();
                }
            });
        } else {
            getPriceFun();
        }

        function getPriceFun() {
            var date = new Date($scope.startTime.replace(/-/g, "/"));
            $scope.nowTime = Math.round(date.getTime() / 1000);
            if ($.isEmptyObject($scope.firstpla) || $.isEmptyObject($scope.lastpla)) {
                $scope.prices = [];
                $scope.price = {price: 0};
                return;
            }
            var params = {
                slng: $scope.firstpla.lng,
                slat: $scope.firstpla.lat,
                elng: $scope.lastpla.lng,
                elat: $scope.lastpla.lat,
                url: "/v1/resource/common/estimate/price"
            };
            if ($scope.serviceType == 1) {
                params.serviceId = 14;
            } else {
                params.serviceId = 13;
                params.departureTime = $scope.nowTime;
            }
            $rootScope.loading = true;
            $http.post(yhyUrl.shenzhouApi, params).success(function (data) {
                $rootScope.loading = false;
                if (Check.loginCheck(data)) {
                    Check.hasToken(data);
                    if (data.code == 1) {
                        $scope.prices = data.content.prices;
                        console.info($scope.prices);
                        selectPrice();
                    }
                }
            }).error(function () {
                $rootScope.loading = false;
            });
        }
    };

    $rootScope.loading = true;
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        $rootScope.loading = false;
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.messagex = {
                name: data.user.userName,
                balance: data.user.balance
            };
            $scope.noTel = (data.user.telephone == null || data.user.telephone == "");
            if (!$scope.noTel) {
                $http.post(yhyUrl.checkShenzhou).success(function (result) {
                    if (!Check.loginCheck(result)) {
                        return;
                    }
                    if (result.success) {
                        $scope.messagex.phone = data.user.telephone;
                    }
                    if ($scope.firstpla != null && $scope.lastpla != null) {
                        $scope.getPrice();
                    }
                });
            }
        }
    });

    var date = new Date();
    var limit = date.format("yyyy-MM-dd");
    date.setDate(date.getDate() + 3);
    limit += "," + date.format("yyyy-MM-dd");
    $("#time").attr("data-lcalendar", limit);
    var calendardatetime = new lCalendar();
    calendardatetime.init({
        'trigger': '#time',
        'type': 'datetime'
    });
    $scope.showStart = function (start, index) {
        $scope.firstpla = start;
        $scope.upaddress = {item: index};
        $scope.isShow = false;
        $scope.getPrice();
    };
    $scope.shadowhide = function () {
        $scope.isShow = false;
    };

    $scope.carTypeIndex = {item: 1};
    $scope.selectCarType = function (index) {
        $scope.carTypeIndex = {item: index};
        $scope.carNo = index + 1;
        selectPrice();
    };

    //$scope.flynum="请输入航班号";
    //$scope.usetime="请输入用车时间";

    $scope.checkName = function () {
        if ($scope.messagex.name == undefined || $scope.messagex.name == "") {
            //$scope.phonenum = '手机号码不能为空！';
            $('#name').css({'border': '#ff5151 solid 1px'});
            return false;
        } else {
            $('#name').css({'border': 'none'});
        }
        var name = /^[\u4E00-\u9FA5]+$/;
        if (!name.test($scope.messagex.name)) {
            //$scope.phonenum = phonenumber + ' ' + '手机号码不正确！';
            $('#name').css({'border': '#ff5151 solid 1px'});
            return false;
        } else {
            $('#name').css({'border': 'none'});
        }
        return true;
    };

    $scope.checkPhone = function () {
        if ($scope.messagex.phone == undefined || $scope.messagex.phone == "") {
            //$scope.phonenum = '手机号码不能为空！';
            $('#phone').css({'border': '#ff5151 solid 1px'});
            return false;
        } else {
            $('#phone').css({'border': 'none'});
        }
        var reg = /(1[3-9]\d{9}$)/;
        if (!reg.test($scope.messagex.phone)) {
            //$scope.phonenum = phonenumber + ' ' + '手机号码不正确！';
            $('#phone').css({'border': '#ff5151 solid 1px'});
            return false;
        } else {
            $('#phone').css({'border': 'none'});
        }
        return true;
    };

    function selectPrice() {
        for (var i = 0; i < $scope.prices.length; i++) {
            if ($scope.prices[i].carGroupId == $scope.carNo) {
                $scope.price = $scope.prices[i];
                $scope.balanceDiff = Math.round(($scope.price.price * 2 - $scope.messagex.balance) * 100) / 100;
            }
        }
    }

    $scope.submitOrder = function () {
        if (!$scope.checkName() || !$scope.checkPhone()) {
            return;
        }
        var date = new Date($scope.startTime);
        if (date.getTime() - new Date().getTime() > 3 * 24 * 60 * 60 * 1000 || date.getTime() - new Date().getTime() < 0) {
            bootbox.alert("时间只能选择3天内");
            return;
        }
        var params = {
            carGroupId: $scope.carNo,
            passengerMobile: $scope.messagex.phone,
            passengerName: $scope.messagex.name,
            slng: $scope.firstpla.lng,
            slat: $scope.firstpla.lat,
            startName: $scope.firstpla.title,
            startAddress: $scope.firstpla.address,
            elng: $scope.lastpla.lng,
            elat: $scope.lastpla.lat,
            endName: $scope.lastpla.title,
            endAddress: $scope.lastpla.address
        };
        if ($scope.serviceType == 1) {
            params.serviceId = 14;
        } else {
            params.serviceId = 13;
            params.departureTime = $scope.nowTime;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.shenzhouOrder, params).success(function (data) {
            $rootScope.loading = false;
            if (Check.loginCheck(data)) {
                Check.hasToken(data);
                if (data.success) {
                    $state.go("payment", {orderId: data.orderId});
                } else {
                    console.info(data.errMsg);
                }
            }
        });
        error(function () {
            $rootScope.loading = false;
        });
    };
    $scope.bkstyle = function (id) {
        $scope.date_car = id == 2;
        $scope.serviceType = id;
        $scope.getPrice();
    };
    $scope.statement = function () {
        $scope.shadowScreen = true;
        $scope.statement_word = true;
    };
    $scope.hideshadowScreen = function () {
        $scope.shadowScreen = false;
        $scope.statement_word = false;
    };
});
bookingCar.controller('shangcheCtrl', function ($scope, $state, storage) {
    $scope.shadow = false;
    $scope.searchList = true;
    //$scope.placelist = [];
    $scope.tempList = [
        {
            title: '邮轮中心南大巴停车场',
            index: 1,
            lat: 24.479939,
            lng: 118.075385,
            address: "东港路2号厦门国际邮轮中心南大巴"
        },
        {title: '厦门轮渡码头', index: 2, lat: 24.454722, lng: 118.073232, address: "鹭江道15号(近中山路)"},
        {title: '海沧嵩屿码头', index: 3, lat: 24.454373, lng: 118.046124, address: "嵩屿建港路2188号"}
    ];
    //var tempList = [];
    //for (var i = 0; i < $scope.places.length; i++) {
    //    tempList.push($scope.places[i]);
    //    if ((i + 1) / 9 == $scope.placelist.length + 1) {
    //        $scope.placelist.push(tempList);
    //        tempList = [];
    //    }
    //}


    $scope.historymess = storage.get(yhyKey.shangcheSearchHistory) == null ? [] : storage.get(yhyKey.shangcheSearchHistory);
    $scope.searchs = [];

    $scope.showplace = function () {
        //关键字查询
        //高德API
        AMap.service(["AMap.PlaceSearch"], function () {
            var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
                pageSize: 10,
                pageIndex: 1,
                city: "0592" //城市
            });
            placeSearch.search($scope.where, function (status, result) {
                $scope.$apply(function () {
                    var hotplace = [];
                    if (status === 'complete' && result.info === 'OK') {
                        angular.forEach(result.poiList.pois, function (poi) {
                            var placemessage = {
                                title: poi.name,
                                lat: poi.location.lat,
                                lng: poi.location.lng,
                                address: poi.address
                            };
                            hotplace.push(placemessage);
                        });
                        $scope.searchs = hotplace;
                        console.info(hotplace);
                    }
                });
            });
        });
    };
    $scope.hidethis = function () {
        $scope.shadow = false;
        $scope.searchList = false;
    };
    $scope.review = function () {
        $scope.shadow = true;
        $scope.searchList = true;
    };


    //热门目的
    if (storage.get("hotplaceIndex")) {
        $scope.hotplace = {item: storage.get("hotplaceIndex")};
    }
    $scope.transfer = function (index, place) {
        storage.set("hotplaceIndex", index);
        $scope.hotplace = {item: index};
        saveHistory(place);
        storage.set(yhyKey.shangcheAddress, place);
        $state.go("bookingCar");
    };


    //搜索历史选择
    if (storage.get("toggleObjectIndex")) {
        $scope.toggleObject = {item: storage.get("toggleObjectIndex")};
    }
    $scope.sel = function (index, place) {
        storage.set("toggleObjectIndex", index);
        $scope.toggleObject = {item: index};
        saveHistory(place);
        storage.set(yhyKey.shangcheAddress, place);
        $state.go("bookingCar");
    };


    //搜索结果
    $scope.mysearch = function (index, search) {
        $scope.mysearching = {item: index};
        saveHistory(search);
        storage.set(yhyKey.shangcheAddress, search);
        $state.go("bookingCar");
    };

    function saveHistory(place) {
        var inHistory = 2;
        angular.forEach($scope.historymess, function (history, i) {
            if (history.title == place.title) {
                inHistory = i;
                return false;
            }
        });
        $scope.historymess.splice(inHistory, 1);
        $scope.historymess.splice(0, 0, place);
        storage.set(yhyKey.shangcheSearchHistory, $scope.historymess);
    }
});
//热门景点选择
bookingCar.controller('xiacheCtrl', function ($scope, $state, storage) {
    $scope.shadow = false;
    $scope.searchList = true;
    $scope.placelist = [];
    $scope.places = [
        {title: '中山路', index: 1, lat: 24.453867, lng: 118.079088, address: "中山路56号"},
        {title: '南普陀寺', index: 2, lat: 24.441026, lng: 118.096527, address: "思明南路515号"},
        {title: '厦门大学', index: 3, lat: 24.436341, lng: 118.102555, address: "思明南路422号"},
        {title: '沙坡尾', index: 4, lat: 24.437961, lng: 118.087826, address: "思明区"},
        {title: '胡里山炮台', index: 5, lat: 24.429742, lng: 118.106292, address: "曾厝垵路2号"},
        {title: '曾厝垵', index: 6, lat: 24.425594, lng: 118.123734, address: "厦门市思明区"},
        {title: '环岛路', index: 7, lat: 24.4313, lng: 118.105289, address: "环岛路111号"},
        {title: '五缘湾游艇港', index: 8, lat: 24.535934, lng: 118.181278, address: "五缘湾游艇码头"},
        {title: '园林植物园', index: 9, lat: 24.447846, lng: 118.109729, address: "虎园路25号"},
        {
            title: '国际邮轮中心',
            index: 10,
            lat: 24.481773,
            lng: 118.074587,
            address: "厦门邮轮中心厦鼓码头-鼓浪屿三丘田码头;厦门邮轮中心厦鼓码头-鼓浪屿内厝澳码头"
        },
        {title: '轮渡市民码头', index: 11, lat: 24.45431, lng: 118.073478, address: "鹭江道15号厦门轮渡码头内"},
        {title: '海沧嵩屿码头', index: 12, lat: 24.454373, lng: 118.046124, address: "嵩屿建港路2188号"},
        {title: '高崎机场T3', index: 13, lat: 24.534602, lng: 118.13173, address: "翔云一路121号厦门高崎国际机场"},
        {title: '高崎机场T4', index: 14, lat: 24.544616, lng: 118.147921, address: "翔云一路121号厦门高崎国际机场内"},
        {title: '厦门站', index: 15, lat: 24.468293, lng: 118.115433, address: "厦禾路900号"},
        {title: '厦门北站', index: 16, lat: 24.636219, lng: 118.074039, address: "后溪镇岩通路"},
        {title: '厦金五通码头', index: 17, lat: 24.531235, lng: 118.191184, address: "环岛东路2500号"},
        {title: '国际会展中心', index: 18, lat: 24.467131, lng: 118.183694, address: "会展路198号"},
        {title: '海湾公园', index: 19, lat: 24.473371, lng: 118.076422, address: "湖滨西路西堤西侧"},
        {title: '白鹭洲公园', index: 20, lat: 24.473858, lng: 118.093804, address: "白鹭洲路"},
        {title: '铁路文化公园', index: 21, lat: 24.454857, lng: 118.093594, address: "文园路与虎园路交汇处东150米"},
        {title: 'SM城市广场', index: 22, lat: 24.501314, lng: 118.127211, address: "嘉禾路468号"},
        {title: '日月谷温泉', index: 23, lat: 24.558749, lng: 117.941306, address: "孚莲路1888号"},
        {title: '集美学村', index: 24, lat: 24.566453, lng: 118.093697, address: "银江路183号"},
        {title: '老院子景区', index: 25, lat: 24.62493, lng: 118.075897, address: "华夏路9号"},
        {title: '厦门科技馆', index: 26, lat: 24.490275, lng: 118.108123, address: "体育路95号厦门文化艺术中心"},
        {title: '园博园', index: 27, lat: 24.57125, lng: 118.07591, address: "杏林园博苑温泉岛园博西路89号"}
    ];
    var tempList = [];
    for (var i = 0; i < $scope.places.length; i++) {
        tempList.push($scope.places[i]);
        if ((i + 1) / 9 == $scope.placelist.length + 1) {
            $scope.placelist.push(tempList);
            tempList = [];
        }
    }


    $scope.historymess = storage.get("searchHistory") == null ? [] : storage.get("searchHistory");
    $scope.searchs = [];

    $scope.showplace = function () {
        //关键字查询
        //高德API
        AMap.service(["AMap.PlaceSearch"], function () {
            var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
                pageSize: 10,
                pageIndex: 1,
                city: "0592" //城市
            });
            placeSearch.search($scope.where, function (status, result) {
                $scope.$apply(function () {
                    var hotplace = [];
                    if (status === 'complete' && result.info === 'OK') {
                        angular.forEach(result.poiList.pois, function (poi) {
                            var placemessage = {
                                title: poi.name,
                                lat: poi.location.lat,
                                lng: poi.location.lng,
                                address: poi.address
                            };
                            hotplace.push(placemessage);
                        });
                        $scope.searchs = hotplace;
                        console.info(hotplace);
                    }
                });
            });
        });
    };
    $scope.hidethis = function () {
        $scope.shadow = false;
        $scope.searchList = false;
    };
    $scope.review = function () {
        $scope.shadow = true;
        $scope.searchList = true;
    };


    //热门目的
    if (storage.get("hotplaceIndex")) {
        $scope.hotplace = {item: storage.get("hotplaceIndex")};
    }
    $scope.transfer = function (index, place) {
        storage.set("hotplaceIndex", index);
        $scope.hotplace = {item: index};
        saveHistory(place);
        storage.set(yhyKey.xiacheAddress, place);
        $state.go("bookingCar");
    };


    //搜索历史选择
    if (storage.get("toggleObjectIndex")) {
        $scope.toggleObject = {item: storage.get("toggleObjectIndex")};
    }
    $scope.sel = function (index, place) {
        storage.set("toggleObjectIndex", index);
        $scope.toggleObject = {item: index};
        saveHistory(place);
        storage.set(yhyKey.xiacheAddress, place);
        $state.go("bookingCar");
    };


    //搜索结果
    $scope.mysearch = function (index, search) {
        $scope.mysearching = {item: index};
        saveHistory(search);
        storage.set(yhyKey.xiacheAddress, search);
        $state.go("bookingCar");
    };

    function saveHistory(place) {
        var inHistory = 2;
        angular.forEach($scope.historymess, function (history, i) {
            if (history.title == place.title) {
                inHistory = i;
                return false;
            }
        });
        $scope.historymess.splice(inHistory, 1);
        $scope.historymess.splice(0, 0, place);
        storage.set("searchHistory", $scope.historymess);
    }
});


bookingCar.controller('takeorder', function ($scope, $http, $stateParams, Check, $interval, storage, $state, $rootScope) {
    $scope.orderId = $stateParams.orderId;
    $scope.canCancel = false;
    $scope.hasPrice = false;
    $scope.score = 0;
    $scope.canComment = true;
    $http.post(yhyUrl.shenzhouOrderDetail, {
        orderId: $scope.orderId
    }).success(function (data) {
        if (Check.loginCheck(data)) {
            Check.hasToken(data);
            if (data.success) {
                detail(data);
                if ($scope.order.status != "completed" && $scope.order.status != "invalid" && $scope.order.status != "canceled") {
                    $scope.timer = $interval(function () {
                        $http.post(yhyUrl.shenzhouOrderDetail, {
                            orderId: $scope.orderId
                        }).success(function (data) {
                            detail(data);
                            if ($scope.order.status == "completed" || $scope.order.status == "invalid" || $scope.order.status == "canceled") {
                                $interval.cancel($scope.timer);
                            }
                        });
                    }, 5000);
                }
            }
        }
    });

    function detail(data) {
        Check.hasToken(data);
        $scope.order = data.order;
        if ($scope.order.score > 0) {
            $scope.canComment = false;
            $scope.score = $scope.order.score;
        }
        $scope.star = "star" + $scope.score;
        $scope.canCancel = $scope.order.status == "created" || $scope.order.status == "dispatched" || $scope.order.status == "arriving" || $scope.order.status == "arrived";
        $scope.isArrived = $scope.order.status == "arrived";
        $scope.hasPrice = $scope.order.status == "feeSubmitted" || $scope.order.status == "paid" || $scope.order.status == "completed";
        if ($scope.canCancel) {
            $http.post(yhyUrl.shenzhouOrderCancelReason, {
                arrival: $scope.isArrived
            }).success(function (data) {
                Check.hasToken(data);
                if (data.success) {
                    $scope.cancelReasonList = data.cancelReasonList;
                    if ($scope.cancelReasonList.length > 0) {
                        if ($scope.selectedReason != null) {
                            var flag = false;
                            angular.forEach($scope.cancelReasonList, function (reason) {
                                if ($scope.selectedReason == reason.id) {
                                    $scope.selectedReason = reason;
                                    flag = true;
                                }
                            });
                            if (!flag) {
                                $scope.selectedReason = $scope.cancelReasonList[0];
                            }
                        } else {
                            $scope.selectedReason = $scope.cancelReasonList[0];
                        }
                    }
                }
                console.info($scope.cancelReasonList);
            });
        } else {

        }
    }

    $scope.back = function () {
        if ($scope.timer != null) {
            $interval.cancel($scope.timer);
        }
        history.go(-1);
    };

    $scope.lis = [1, 2, 3, 4, 5];
    $scope.comstar = function (score) {
        if ($scope.canComment) {
            $scope.score = score;
            $scope.star = "star" + $scope.score;
        }
    };

    $scope.submitComment = function () {
        if ($scope.order.score > 0) {
            return;
        }
        $http.post(yhyUrl.shenzhouOrderComment, {
            orderId: $scope.orderId,
            score: $scope.score
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            Check.hasToken(data);
            if (data.success) {
                bootbox.alert("评价提交成功");
            } else {
                if (data.score != null && data.score > 0) {
                    $scope.score = data.score;
                    bootbox.alert("已评价过或已自动评价");
                } else {
                    bootbox.alert("评价失败");
                }
            }
        });
    };

    $scope.showsel = function () {
        $scope.cancelReason = "";
        $scope.isShow = true;
        $scope.carUse = true;
    };
    $scope.thisconsole = function () {
        $scope.isShow = false;
        $scope.carUse = false;
    };

    $scope.selectCancel = function (reason) {
        $scope.selectedReason = reason;
    };

    $scope.cancel = function () {
        $scope.carUse = false;
        $rootScope.loading = true;
        $http.post(yhyUrl.shenzhouOrderCancel, {
            orderId: $scope.orderId,
            force: false,
            reason: $scope.selectedReason.value,
            reasonId: $scope.selectedReason.id
        }).success(function (data) {
            $rootScope.loading = false;
            if (Check.loginCheck(data)) {
                $scope.isShow = false;
                if (data.success) {
                    bootbox.alert("订单取消成功");
                    $scope.order = data.order;
                    $scope.canCancel = $scope.order.status == "created" || $scope.order.status == "dispatched" || $scope.order.status == "arriving" || $scope.order.status == "arrived";
                    $scope.hasPrice = $scope.order.status == "feeSubmitted" || $scope.order.status == "paid" || $scope.order.status == "completed";
                } else if (data.needPay != null && data.needPay) {
                    bootbox.confirm({
                        buttons: {
                            confirm: {
                                label: "确认"
                            },
                            cancel: {
                                label: "取消"
                            }
                        },
                        message: "取消该订单需要扣除" + data.cost + "元费用，确认取消？",
                        callback: function (result) {
                            if (result) {
                                $rootScope.loading = true;
                                $http.post(yhyUrl.shenzhouOrderCancel, {
                                    orderId: $scope.orderId,
                                    force: true,
                                    reason: $scope.cancelReason
                                }).success(function (data) {
                                    $rootScope.loading = false;
                                    if (data.success) {
                                        bootbox.alert("订单取消成功");
                                    }
                                }).error(function () {
                                    $rootScope.loading = false;
                                });
                            }
                        }
                    });
                } else {
                    bootbox.alert("取消失败，请重试");
                }
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    $scope.toFeeDetail = function () {
        storage.set(yhyKey.shenzhouOrder, $scope.order);
        $state.go("payment2");
    }
});

bookingCar.controller("takeorderDetail", function ($scope, storage) {
    $scope.order = storage.get(yhyKey.shenzhouOrder);
    console.info($scope.order);
    if ($scope.order == null) {
        history.go(-1);
        return;
    }
    var carTypes = ["公务轿车", "商务轿车", "豪华轿车"];
    $scope.order.carType = carTypes[$scope.order.carGroupId - 2];
    $scope.totalPrice = Math.round(($scope.order.startPrice + $scope.order.kilometrePrice + $scope.order.timePrice + $scope.order.longDistancePrice) * 100) / 100;
});

bookingCar.controller('loginCtrl', ['$scope', '$http', '$location', 'storage', '$state', function ($scope, $http, $location, storage, $state) {
    $scope.user = storage.get("user") == null ? {} : storage.get("user");
    $scope.account = $scope.user.telephone;
    var indexOf = $location.absUrl().indexOf('?url=');
    var url = $location.absUrl().substring(indexOf + 5);
    $scope.password = '';
    $scope.loginForm = function () {
        if ($scope.login_form.$valid) {
            $http.post(yhyUrl.login, {
                    account: $scope.account,
                    password: $scope.password
                }
            ).success(function (data) {
                    if (data.success) {
                        $scope.user = data.user;
                        storage.set("user", $scope.user);
                        history.go(-1);
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
}]);

//上车地点显示
bookingCar.directive('shadowBox', function () {
    return {
        restrict: "AE",
        scope: {
            isShow: "="
        },
        link: function (scope, element) {
            element.click(function () {
                scope.$apply(function () {
                    scope.isShow = true;
                })

            })
        }
    }
});

//选择下车地点
bookingCar.directive('listLeave', function () {
    return {
        restrict: "AE",
        link: function (scope, element) {
            element.click(function () {
                $('.mysearch ul li').removeClass('tagbgx');
                $('.mysearch ul li').addClass('tagbg');
                $(this).addClass('tagbgx');

            })
        }
    }
});

//取消用车
var cons = $('#cansolecar');
cons.click(function () {
    var Wwindow = $(window).width();
    var Wfor = $('.forcans').width();
    var lef = (Wwindow - Wfor) / 2;
    createshadow();
    $('body').css({'overflow': 'hidden'});
    $('.forcans').show();
    $('.forcans').css({'position': 'fixed', 'top': '200px', 'left': lef, 'z-index': '11'});
});
var yes = $('#yesB');
var no = $('#noB');
yes.click(function () {
    $('.shadow').remove();
    $('.forcans').hide();
});
no.click(function () {
    $('.shadow').remove();
    $('.forcans').hide();
});