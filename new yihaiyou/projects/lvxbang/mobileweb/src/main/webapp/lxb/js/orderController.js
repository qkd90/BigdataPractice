/**
 * Created by huangpeijie on 2016-07-20,0020.
 */
//选择日期
var orderDateModule = angular.module("orderDateModule", []);
orderDateModule.controller("orderDateCtrl", function ($scope, $http, $stateParams, $state, storage) {
    $scope.linetypepriceId = Number($stateParams.lineTypePriceId);
    $scope.adultNum = storage.get(LXB_KEY.adultNum) == null ? 1 : storage.get(LXB_KEY.adultNum);
    $scope.childNum = storage.get(LXB_KEY.childNum) == null ? 0 : storage.get(LXB_KEY.childNum);

    $("div.ajaxloading").show();
    $http.post(LXB_URL.orderDate, {
        linetypepriceId: $scope.linetypepriceId
    }).success(function (data) {
        if (data.success) {
            var calendarData = [];
            angular.forEach(data.linetypepricedates, function (datePrice) {
                calendarData.push({
                    id: datePrice.day.substring(0, 10),
                    discountPrice: (datePrice.discountPrice + datePrice.rebate),
                    childPrice: (datePrice.childPrice + datePrice.childRebate),
                    date: datePrice.day.substring(0, 10),
                    title: '¥' + (datePrice.discountPrice + datePrice.rebate) + "起",
                    hasChild: datePrice.childPrice > 0
                });
            });
            $scope.calendarData = calendarData;
        }
        $("div.ajaxloading").hide();
    });

    $scope.eventClick = function (calEvent) {
        var date = new Date(calEvent.date);
        $scope.selectedDate = (date.getMonth() + 1) + "月" + date.getDate() + "日";
        $scope.adultPrice = calEvent.discountPrice;
        $scope.childPrice = calEvent.childPrice;
        $scope.startDate = calEvent.date;
    };

    $scope.dayClick = function (date) {
        var calEvent = $("#calendar").fullCalendar('clientEvents', date)[0];
        if (calEvent != null) {
            var time = new Date(date);
            $scope.selectedDate = (time.getMonth() + 1) + "月" + time.getDate() + "日";
            $scope.adultPrice = calEvent.discountPrice;
            $scope.childPrice = calEvent.childPrice;
            $scope.startDate = calEvent.date;
        } else {
            $(".fc-highlight-skeleton").remove();
            $("#calendar").fullCalendar('select', $.fullCalendar.moment($scope.startDate));
        }
    };

    $scope.next = function () {
        if ($scope.startDate != null && $scope.startDate != "") {
            storage.set(LXB_KEY.startDate, $scope.startDate);
            storage.set(LXB_KEY.adultNum, $scope.adultNum);
            storage.set(LXB_KEY.childNum, $scope.childNum);
            storage.set(LXB_KEY.adultPrice, $scope.adultPrice);
            storage.set(LXB_KEY.childPrice, $scope.childPrice);
            $state.go("orderone", {
                request: JSON.stringify({
                    linetypepriceId: $scope.linetypepriceId
                })
            });
        } else {
            bootbox.alert("请选择团期");
        }
    }
});

//选择资源
var orderOneModule = angular.module("orderOneModule", []);
orderOneModule.controller("orderOneCtrl", function ($scope, $http, storage, $state, $stateParams, Check) {
    var request = $stateParams.request == null || $stateParams.request == "" ? {} : JSON.parse($stateParams.request);
    $scope.linetypepriceId = request.linetypepriceId;
    $scope.lineStartDate = storage.get(LXB_KEY.startDate) == null ? "" : storage.get(LXB_KEY.startDate);
    $scope.adultNum = storage.get(LXB_KEY.adultNum) == null ? 1 : storage.get(LXB_KEY.adultNum);
    $scope.childNum = storage.get(LXB_KEY.childNum) == null ? 0 : storage.get(LXB_KEY.childNum);
    $scope.oasiaHotel = 0;
    $scope.insurancePrice = 0;
    $scope.changeInsurance = false;

    $("div.ajaxloading").show();
    $http.post(GetUrl.orderLine, {
        linetypepriceId: $scope.linetypepriceId,
        lineStartDate: $scope.lineStartDate
    }).success(function (data) {
        if (Check.loginCheckAndBack(data)) {
            if (data.success) {
                $scope.line = data.line;
                $scope.adultPrice = data.adultPrice;
                $scope.childPrice = data.childPrice;
                $scope.needGuarantee = data.needGuarantee;
                if ($scope.adultNum % 2 == 1) {
                    $scope.oasiaHotel = data.oasiaHotel;
                }
                angular.forEach(data.insuranceList, function (insurance, i) {
                    insurance.show = i == 0;
                    insurance.selected = i == 0;
                });
                $scope.insurancePrice = data.insuranceList[0].price * ($scope.adultNum + $scope.childNum);
                $scope.insuranceList = data.insuranceList;
                $scope.totalPrice = $scope.adultNum * $scope.adultPrice + $scope.childNum * $scope.childPrice + $scope.oasiaHotel;
            }
        }
        $("div.ajaxloading").hide();
    });

    $scope.insuChange = function ($event) {
        var $this = angular.element($event.target);
        if ($scope.changeInsurance) {
            angular.forEach($scope.insuranceList, function (insurance) {
                insurance.show = insurance.selected;
            });
            if ($scope.insurancePrice == 0) {
                $scope.insuranceList[0].show = true;
            }
            $this.html('更改保险');
        } else {
            angular.forEach($scope.insuranceList, function (insurance) {
                insurance.show = true;
            });
            $this.html('收起保险');
        }
        $scope.changeInsurance = !$scope.changeInsurance;
    };

    $scope.insuranceInfo = function ($event) {
        var $this = angular.element($event.target);
        if ($this.hasClass("icon-transform180")) {
            $this.removeClass("icon-transform180").siblings(".tog-cell").hide();
        } else {
            $this.addClass("icon-transform180").siblings(".tog-cell").show();
        }
    };

    $scope.forSure = function ($event, obj) {
        var sure = angular.element($event.target);
        if (sure.hasClass('for_green')) {
            sure.removeClass('for_green');
            sure.addClass('for_gray');
            obj.insurance.selected = false;
        } else {
            sure.removeClass('for_gray');
            sure.addClass('for_green');
            obj.insurance.selected = true;
        }
        $scope.insurancePrice = 0;
        angular.forEach($scope.insuranceList, function (insurance) {
            if (insurance.selected) {
                $scope.insurancePrice += insurance.price;
            }
        });
        $scope.insurancePrice *= $scope.adultNum + $scope.childNum;
        $scope.totalPrice = $scope.adultNum * $scope.adultPrice + $scope.childNum * $scope.childPrice + $scope.oasiaHotel;
    };

    $scope.next = function () {
        storage.set(LXB_KEY.adultNum, $scope.adultNum);
        storage.set(LXB_KEY.adultPrice, $scope.adultPrice);
        storage.set(LXB_KEY.childNum, $scope.childNum);
        storage.set(LXB_KEY.childPrice, $scope.childPrice);
        storage.set(LXB_KEY.oasiaHotel, $scope.oasiaHotel);
        storage.set(LXB_KEY.insurancePrice, $scope.insurancePrice);
        storage.set(LXB_KEY.orderTotalPrice, $scope.totalPrice);
        storage.set(LXB_KEY.lineStartDate, $scope.lineStartDate);
        storage.set(LXB_KEY.linetypepriceId, $scope.linetypepriceId);
        storage.set(LXB_KEY.lineId, $scope.line.id);
        storage.set(LXB_KEY.needGuarantee, $scope.needGuarantee);
        var insurances = [];
        angular.forEach($scope.insuranceList, function (insurance) {
            if (insurance.selected) {
                insurances.push(insurance.id);
            }
        });
        storage.set(LXB_KEY.insurances, insurances);
        $state.go("ordertwo");
    };

    fareDetail();

    function fareDetail() {
        var fare = $('#fare_detail');
        var k = 0;
        fare.click(function () {
            if (k == 0) {
                createShade();
                $('.fare_detail').show();
                fare.removeClass('fare_green');
                k = 1;
                $('#mainShade').click(function () {
                    $('#mainShade').remove();
                    $('.fare_detail').hide();
                    fare.addClass('fare_green');
                    k = 0;
                });
            } else {
                $('#mainShade').remove();
                $('.fare_detail').hide();
                fare.addClass('fare_green');
                k = 0;
            }
        });
    }

    function createShade() {
        var pro_mainHeight = $('#totalDiv').height();
        var parent = $('#totalDiv');
        var pro_shade = $('<div></div>');
        pro_shade.appendTo(parent);
        pro_shade.attr('id', 'mainShade');
        pro_shade.addClass('produc_shade');
        pro_shade.css({'height': pro_mainHeight});
    }

});

//填写订单
var orderTwoModule = angular.module("orderTwoModule", []);
orderTwoModule.controller("orderTwoCtrl", function ($scope, $http, storage, Check, $state) {
    $scope.selectedAdultTourist = storage.get(LXB_KEY.selectedAdultTourist) == null ? [] : storage.get(LXB_KEY.selectedAdultTourist);
    $scope.selectedChildTourist = storage.get(LXB_KEY.selectedChildTourist) == null ? [] : storage.get(LXB_KEY.selectedChildTourist);
    $scope.adultNum = storage.get(LXB_KEY.adultNum);
    $scope.adultPrice = storage.get(LXB_KEY.adultPrice);
    $scope.childNum = storage.get(LXB_KEY.childNum);
    $scope.childPrice = storage.get(LXB_KEY.childPrice);
    $scope.oasiaHotel = storage.get(LXB_KEY.oasiaHotel);
    $scope.insurancePrice = storage.get(LXB_KEY.insurancePrice);
    $scope.totalPrice = storage.get(LXB_KEY.orderTotalPrice);
    $scope.lineStartDate = storage.get(LXB_KEY.lineStartDate);
    $scope.linetypepriceId = storage.get(LXB_KEY.linetypepriceId);
    $scope.lineId = storage.get(LXB_KEY.lineId);
    $scope.insurances = storage.get(LXB_KEY.insurances);
    $scope.needGuarantee = storage.get(LXB_KEY.needGuarantee);
    $scope.contact = storage.get(LXB_KEY.contact) == null ? {} : storage.get(LXB_KEY.contact);
    $scope.creditCard = storage.get(LXB_KEY.creditCard);

    $scope.goBack = function () {
        storage.set(LXB_KEY.contact, $scope.contact);
        history.go(-1);
    };

    $scope.toGuarantee = function () {
        storage.set(LXB_KEY.contact, $scope.contact);
        $state.go("orderLineGuarantee");
    };

    $scope.toTouristList = function () {
        storage.set(LXB_KEY.contact, $scope.contact);
        $state.go("touristlist");
    };

    $scope.submitOrder = function () {
        if ($("#limit-agree").hasClass("icon-no-choice")) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请核实你的出游人信息'
            });
            return;
        }
        if ($("#note-agree").hasClass("icon-no-choice")) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请阅读预定须知和合同'
            });
            return;
        }
        if ($scope.selectedAdultTourist.length == 0 && $scope.selectedChildTourist.length == 0) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请编辑游客信息'
            });
            return;
        }
        if ($scope.selectedAdultTourist.length != $scope.adultNum || $scope.selectedChildTourist.length != $scope.childNum) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '游客数量有误'
            });
            return;
        }
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
        if ($scope.needGuarantee && $scope.creditCard == null) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写担保信息'
            });
            return;
        }
        $http.post(LXB_URL.checkLineOrder, {
            linetypepriceId: $scope.linetypepriceId,
            lineStartDate: $scope.lineStartDate,
            num: $scope.adultNum
        }).success(function (data) {
            if (data.success) {
                var touristsIds = [];
                var childTouristsIds = [];
                angular.forEach($scope.selectedAdultTourist, function (tourist) {
                    touristsIds.push(tourist.touristId);
                });
                angular.forEach($scope.selectedChildTourist, function (tourist) {
                    childTouristsIds.push(tourist.touristId);
                });
                var jsonObj = {
                    id: 0,
                    playDate: $scope.lineStartDate,
                    tourists: touristsIds,
                    childTourists: childTouristsIds,
                    contact: $scope.contact,
                    orderType: "line",
                    insurances: $scope.insurances
                };
                var details = [];
                var adult = {
                    id: $scope.lineId,
                    priceId: $scope.linetypepriceId,
                    price: $scope.adultPrice,
                    startTime: $scope.lineStartDate,
                    endTime: $scope.lineStartDate,
                    num: $scope.adultNum,
                    type: "line",
                    seatType: "成人",
                    singleRoomPrice: $scope.oasiaHotel
                };
                details.push(adult);
                if ($scope.childNum > 0) {
                    var child = {
                        id: $scope.lineId,
                        priceId: $scope.linetypepriceId,
                        price: $scope.childPrice,
                        startTime: $scope.lineStartDate,
                        endTime: $scope.lineStartDate,
                        num: $scope.childNum,
                        type: "line",
                        seatType: "儿童"
                    };
                    details.push(child);
                }
                jsonObj.details = details;
                if ($scope.needGuarantee) {
                    jsonObj.creditCard = $scope.creditCard;
                }
                storage.set(LXB_KEY.contact, $scope.contact);
                $http.post(GetUrl.orderSave, {
                        json: JSON.stringify(jsonObj)
                    }
                ).success(function (data) {
                        $scope.loaded = true;
                        if (Check.loginCheckAndBack(data)) {
                            if (data.success) {
                                storage.remove(LXB_KEY.adultPrice);
                                storage.remove(LXB_KEY.adultNum);
                                storage.remove(LXB_KEY.childPrice);
                                storage.remove(LXB_KEY.childNum);
                                storage.remove(LXB_KEY.oasiaHotel);
                                storage.remove(LXB_KEY.insurancePrice);
                                storage.remove(LXB_KEY.orderTotalPrice);
                                storage.remove(LXB_KEY.lineStartDate);
                                storage.remove(LXB_KEY.linetypepriceId);
                                storage.remove(LXB_KEY.lineId);
                                storage.remove(LXB_KEY.insurances);
                                storage.remove(LXB_KEY.selectedAdultTourist);
                                storage.remove(LXB_KEY.selectedChildTourist);
                                storage.remove(LXB_KEY.creditCard);
                                location.href = GetUrl.payment + JSON.stringify({
                                        id: data.order.id,
                                        price: data.order.price
                                    });
                            } else {
                            }
                        }
                        $("div.ajaxloading").hide();
                    }).error(function (data) {
                        $("div.ajaxloading").hide();
                        alert(data.errorMsg);
                    });
            } else {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '线路库存不足'
                });
            }
        });
    };

    $(".agree-icon").click(function () {
        if ($(this).hasClass("icon-no-choice")) {
            $(this).removeClass("icon-no-choice").addClass("icon-choice");
        } else {
            $(this).removeClass("icon-choice").addClass("icon-no-choice");
        }
    });

    fareDetail();

    function fareDetail() {
        var fare = $('#fare_detail');
        var k = 0;
        fare.click(function () {
            if (k == 0) {
                createShade();
                $('.fare_detail').show();
                fare.removeClass('fare_green');
                k = 1;
                $('#mainShade').click(function () {
                    $('#mainShade').remove();
                    $('.fare_detail').hide();
                    fare.addClass('fare_green');
                    k = 0;
                });
            } else {
                $('#mainShade').remove();
                $('.fare_detail').hide();
                fare.addClass('fare_green');
                k = 0;
            }
        });
    }

    function createShade() {
        var pro_mainHeight = $('#totalDiv').height();
        var parent = $('#totalDiv');
        var pro_shade = $('<div></div>');
        pro_shade.appendTo(parent);
        pro_shade.attr('id', 'mainShade');
        pro_shade.addClass('produc_shade');
        pro_shade.css({'height': pro_mainHeight});
    }
});

//线路担保
var orderLineGuaranteeModule = angular.module("orderLineGuaranteeModule", []);
orderLineGuaranteeModule.controller("orderLineGuaranteeCtrl", function ($scope, $http, storage, Check, $state) {
    $scope.submit = function () {
        if ($scope.cardNum == null || $scope.cardNum == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写银行卡号'
            });
            return;
        }
        if ($scope.expiration == null || $scope.expiration == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写有效期'
            });
            return;
        }
        if ($scope.cvv == null || $scope.cvv == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写cvv'
            });
            return;
        }
        var name = /^[\u4E00-\u9FA5]+$/;
        if ($scope.holderName == null || $scope.holderName == "") {
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
        if (!$scope.holderName.match(name) || $scope.holderName.length > 10) {
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
        if ($scope.idNo == null || $scope.idNo == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写身份证'
            });
            return;
        }
        var creditCard = {
            status: true,
            cardNum: $scope.cardNum,
            cvv: $scope.cvv,
            expirationYear: $scope.expiration.substr(0, 4),
            expirationMonth: $scope.expiration.substr(4, 2),
            holderName: $scope.holderName,
            creditCardIdType: "IdentityCard",
            idNo: $scope.idNo
        };
        storage.set(LXB_KEY.creditCard, creditCard);
        $state.go("ordertwo");
    };
});

//旅客列表
var touristListModule = angular.module("touristListModule", ['angularLocalStorage']);
touristListModule.controller('touristListCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check, $state) {
    $scope.selectedAdultTourist = storage.get(LXB_KEY.selectedAdultTourist) == null ? [] : storage.get(LXB_KEY.selectedAdultTourist);
    $scope.selectedChildTourist = storage.get(LXB_KEY.selectedChildTourist) == null ? [] : storage.get(LXB_KEY.selectedChildTourist);
    $scope.adultNum = storage.get(LXB_KEY.adultNum);
    $scope.childNum = storage.get(LXB_KEY.childNum);
    $scope.loaded = false;
    $scope.touristList = [];

    $("div.ajaxloading").show();
    $http.post(GetUrl.touristList, {}
    ).success(function (data) {
            $scope.loaded = true;
            if (Check.loginCheckAndBack(data)) {
                if (data.success) {
                    angular.forEach(data.touristList, function (tourist) {
                        tourist.check = false;
                        angular.forEach($scope.selectedAdultTourist, function (adult, j, list) {
                            if (tourist.touristId == adult.touristId) {
                                tourist.check = true;
                                list.splice(j, 1, tourist);
                            }
                        });
                        angular.forEach($scope.selectedChildTourist, function (child, j, list) {
                            if (tourist.touristId == child.touristId) {
                                tourist.check = true;
                                list.splice(j, 1, tourist);
                            }
                        });
                    });
                    storage.set(LXB_KEY.selectedAdultTourist, $scope.selectedAdultTourist);
                    storage.set(LXB_KEY.selectedChildTourist, $scope.selectedChildTourist);
                    $scope.touristList = data.touristList;
                    //$("#selectNum").html($scope.selectedAdultTourist.length + $scope.selectedChildTourist.length);
                    if ($scope.touristList.length == 0) {
                        $state.go('edittourist', {tourist: "{}"});
                    }
                } else {
                }
            }
            $("div.ajaxloading").hide();
        }).error(function (data) {
            $scope.loaded = true;
            alert(data.errorMsg);
            $("div.ajaxloading").hide();
        });

    $scope.edit = function (obj) {
        var tourist = obj.tourist;
        if (tourist.check) {
            storage.remove(LXB_KEY.selectedAdultTourist);
            storage.remove(LXB_KEY.selectedChildTourist);
        }
        delete tourist.$$hashKey;
        delete tourist.check;
        //storage.set(LXB_KEY.selectedAdultTourist, $scope.selectedAdultTourist);
        //storage.set(LXB_KEY.selectedChildTourist, $scope.selectedChildTourist);
        $state.go('edittourist', {tourist: JSON.stringify(tourist)});
    };

    $scope.submit = function () {
        console.info($scope.selectedAdultTourist.length);
        console.info($scope.selectedChildTourist.length);
        if ($scope.selectedAdultTourist.length != $scope.adultNum) {
            bootbox.alert("成人数量不正确！");
            return;
        }
        if ($scope.selectedChildTourist.length != $scope.childNum) {
            bootbox.alert("儿童数量不正确！");
            return;
        }
        storage.set(LXB_KEY.selectedAdultTourist, $scope.selectedAdultTourist);
        storage.set(LXB_KEY.selectedChildTourist, $scope.selectedChildTourist);
        $state.go("ordertwo");
    };

    $scope.addTourist = function () {
        //storage.set(LXB_KEY.selectedAdultTourist, $scope.selectedAdultTourist);
        //storage.set(LXB_KEY.selectedChildTourist, $scope.selectedChildTourist);
        $state.go("edittourist", {tourist: JSON.stringify({})});
    }
}]);

//编辑旅客信息
var editTouristModult = angular.module('editTouristModult', ['angularLocalStorage']);
editTouristModult.controller('editTouristCtrl', ['$scope', '$http', '$location', 'storage', '$stateParams', function ($scope, $http, $location, storage, $stateParams) {
    $scope.json = JSON.parse($stateParams.tourist);
    $scope.json.idType = "IDCARD";
    $scope.json.gender = "male";
    $scope.json.peopleType = "ADULT";

    $scope.saveTourist = function () {
        if ($scope.userForm.name.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '用户信息不正确'
            });
            return;
        }
        if ($scope.userForm.idNumber.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '身份证信息不正确'
            });
            return;
        }
        if ($scope.userForm.telephone.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '电话不正确'
            });
            return;
        }
        if ($scope.userForm.$valid) {
            $http.post(GetUrl.saveTourist, {
                    json: JSON.stringify($scope.json)
                }
            ).success(function (data) {
                    if (data.success) {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: '保存成功'
                        });
                        history.go(-1);
                    } else {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: '该旅客已经存在'
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
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "信息不正确"
            });
        }
    }
}]);

//支付
var orderPayModule = angular.module("orderPayModule", ['angularLocalStorage']);
orderPayModule.controller('orderPayCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', 'Wechatpay', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check, Wechatpay) {
    $scope.orderId = $stateParams.orderId;
    $scope.iftaobao = !Wechatpay.isWeiXin() && typeof(plus) != "undefined";

    $("div.ajaxloading").show();
    $http.post(GetUrl.myorderinfo, {
        orderId: $scope.orderId
    }).success(function (data) {
        if (data.success) {
            $scope.order = data.order;
            $("div.ajaxloading").hide();
        }
    });

    $scope.pay = function () {
        $("div.ajaxloading").show();
        if (typeof(auths) != "undefined") {
            Wechatpay.alipayNative($scope.order.id, 'wxpay');
        } else {
            Wechatpay.payOrderWithBack($scope.order.id, Wechatpay.paySuccess);
        }
    };
}]);

//订单详情
var orderDetailModule = angular.module("orderDetailModule", []);
orderDetailModule.controller('orderDetailCtrl', function ($scope, $http, $stateParams, Check) {
    $("div.ajaxloading").show();
    $http.post(GetUrl.myorderinfo, {
        orderId: $stateParams.orderId
    }).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.order = data.order;
                var date = new Date($scope.order.startDate);
                date.setDate(date.getDate() + $scope.order.lineOrderDetail.day);
                var str = date.getYear() + 1900;
                str += "-" + (date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1);
                str += "-" + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
                $scope.order.arriveDate = str;
            }
        }
        $("div.ajaxloading").hide();
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: data.msg
        })
        $("div.ajaxloading").hide();
    });

    $scope.toPay = function () {
        location.href = GetUrl.payment + JSON.stringify({id: $scope.order.id});
    }
});

//游艇帆船下单选择日期
var orderSceneryDateModule = angular.module("orderSceneryDateModule", []);
orderSceneryDateModule.controller("orderSceneryDateCtrl", function ($scope, $http, $state, $stateParams, storage) {
    $scope.ticketPriceId = $stateParams.ticketPriceId;
    $("div.ajaxloading").show();
    $http.post(LXB_URL.ticketDatePrice, {
        ticketPriceId: $scope.ticketPriceId
    }).success(function (data) {
        if (data.success) {
            var calendarData = [];
            angular.forEach(data.ticketDatepriceList, function (datePrice) {
                calendarData.push({
                    id: datePrice.huiDate.substring(0, 10),
                    ticketDatePriceId: datePrice.id,
                    price: (datePrice.priPrice + datePrice.rebate),
                    date: datePrice.huiDate.substring(0, 10),
                    title: '¥' + (datePrice.priPrice + datePrice.rebate),
                });
            });
            $scope.calendarData = calendarData;
        }
        $("div.ajaxloading").hide();
    });

    $scope.eventClick = function (calEvent) {
        $scope.ticketDatePriceId = calEvent.ticketDatePriceId;
        $scope.price = calEvent.price;
        $scope.date = calEvent.date;
    };

    $scope.dayClick = function (date) {
        var calEvent = $("#calendar").fullCalendar('clientEvents', date)[0];
        if (calEvent != null) {
            $scope.ticketDatePriceId = calEvent.ticketDatePriceId;
            $scope.price = calEvent.price;
            $scope.date = calEvent.date;
        } else {
            $(".fc-highlight-skeleton").remove();
            $("#calendar").fullCalendar('select', $.fullCalendar.moment($scope.date));
        }
    };

    $scope.next = function () {
        if ($scope.ticketDatePriceId != null && $scope.ticketDatePriceId > 0) {
            storage.set(LXB_KEY.ticketDatePriceId, $scope.ticketDatePriceId);
            $state.go("orderScenery");
        } else {
            bootbox.alert("请选择日期");
        }
    }

});

//门票下单
var orderTicketModule = angular.module("orderTicketModule", []);
orderTicketModule.controller("orderTicketCtrl", function ($scope, $http, $stateParams, $state, Check, storage) {
    $scope.ticketDatePriceId = storage.get(LXB_KEY.ticketDatePriceId);
    if ($scope.ticketDatePriceId == null) {
        bootbox.alert("选请择日期", function () {
            history.go(-1);
        });
    }
    $scope.selectedtourist = storage.get(LXB_KEY.selectedTourist) == null ? [] : storage.get(LXB_KEY.selectedTourist);
    $scope.num = $scope.selectedtourist.length;
    $scope.contact = storage.get(LXB_KEY.contact) == null ? {} : storage.get(LXB_KEY.contact);

    $("div.ajaxloading").show();
    $http.post(LXB_URL.orderTicket, {
        ticketDatePriceId: $scope.ticketDatePriceId
    }).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.ticket = data.ticket;
            }
        }
        $("div.ajaxloading").hide();
    });

    $scope.toTouristList = function () {
        storage.set(LXB_KEY.contact, $scope.contact);
        $state.go("ticketTouristList");
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
        if ($scope.selectedtourist.length == 0) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请编辑游客信息'
            });
            return;
        }
        $http.post(LXB_URL.checkTicketOrder, {
            ticketDatePriceId: $scope.ticketDatePriceId,
            num: $scope.num
        }).success(function (data) {
            if (data.success) {
                var touristsIds = [];
                angular.forEach($scope.selectedtourist, function (tourist) {
                    touristsIds.push(tourist.touristId);
                });
                var jsonObj = {
                    id: 0,
                    name: "<" + $scope.ticket.name + ">" + $scope.ticket.priceName,
                    tourists: touristsIds,
                    playDate: $scope.ticket.playDate,
                    contact: $scope.contact,
                    orderType: "ticket"
                };
                var details = [];
                var detail = {
                    id: $scope.ticket.ticketId,
                    priceId: $scope.ticket.priceId,
                    price: $scope.ticket.price,
                    startTime: $scope.ticket.playDate,
                    endTime: $scope.ticket.playDate,
                    num: $scope.num,
                    type: "scenic",
                    seatType: $scope.ticket.priceName
                };
                details.push(detail);
                jsonObj.details = details;
                storage.set(LXB_KEY.contact, $scope.contact);
                $http.post(GetUrl.orderSave, {
                        json: JSON.stringify(jsonObj)
                    }
                ).success(function (data) {
                        $scope.loaded = true;
                        if (Check.loginCheckAndBack(data)) {
                            storage.remove(LXB_KEY.selectedtourist);
                            if (data.success) {
                                location.href = GetUrl.payment + JSON.stringify({
                                        id: data.order.id,
                                        price: data.order.price
                                    });
                            } else {
                            }
                        }
                    }).error(function (data) {
                        alert(data.errorMsg);
                    });
            } else {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '门票库存不足'
                });
            }
        });
    };
});

//门票下单旅客列表
var ticketTouristListModule = angular.module("ticketTouristListModule", ['angularLocalStorage']);
ticketTouristListModule.controller('ticketTouristListCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check, $state) {
    $scope.selectedtourist = storage.get(LXB_KEY.selectedtourist) == null ? [] : storage.get(LXB_KEY.selectedtourist);
    $scope.loaded = false;
    $scope.touristList = [];


    $http.post(GetUrl.touristList, {}
    ).success(function (data) {
            $scope.loaded = true;
            if (Check.loginCheckAndBack(data)) {
                if (data.success) {
                    angular.forEach(data.touristList, function (tourist) {
                        tourist.check = false;
                        angular.forEach($scope.selectedtourist, function (adult, j, list) {
                            if (tourist.touristId == adult.touristId) {
                                tourist.check = true;
                                list.splice(j, 1, tourist);
                            }
                        });
                    });
                    storage.set(LXB_KEY.selectedtourist, $scope.selectedtourist);
                    $scope.touristList = data.touristList;
                    if ($scope.touristList.length == 0) {
                        $state.go('edittourist', {tourist: "{}"});
                    }
                } else {
                }
            }

        }).error(function (data) {
            $scope.loaded = true;
            alert(data.errorMsg);
        });

    $scope.submit = function () {
        storage.set(LXB_KEY.selectedTourist, $scope.selectedtourist);
        $state.go("orderTicket");
    };

    $scope.edit = function (obj) {
        var tourist = obj.tourist;
        if (tourist.check) {
            storage.remove(LXB_KEY.selectedtourist);
        }
        delete tourist.$$hashKey;
        delete tourist.check;
        $state.go('edittourist', {tourist: JSON.stringify(tourist)});
    };

    $scope.addTourist = function () {
        $state.go("edittourist", {tourist: JSON.stringify({})});
    };

}]);

//门票订单详情
var ticketOrderDetailModule = angular.module("ticketOrderDetailModule", []);
ticketOrderDetailModule.controller("ticketOrderDetailCtrl", function ($scope, $http, Check, $stateParams) {
    $scope.orderId = $stateParams.orderId;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.ticketOrderDetail, {
        orderId: $scope.orderId
    }).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.order = data.order;
            } else {
                bootbox.alert(data.errMsg, function () {
                    history.go(-1);
                });
            }
        }
        $("div.ajaxloading").hide();
    });

    $scope.toPay = function () {
        location.href = GetUrl.payment + JSON.stringify({id: $scope.order.id});
    }
});

//门票下单选择日期
var orderTicketDateModule = angular.module("orderTicketDateModule", []);
orderTicketDateModule.controller("orderTicketDateCtrl", function ($scope, $http, $state, $stateParams, storage) {
    $scope.ticketPriceId = $stateParams.ticketPriceId;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.ticketDatePrice, {
        ticketPriceId: $scope.ticketPriceId
    }).success(function (data) {
        if (data.success) {
            var calendarData = [];
            angular.forEach(data.ticketDatepriceList, function (datePrice) {
                calendarData.push({
                    id: datePrice.huiDate.substring(0, 10),
                    ticketDatePriceId: datePrice.id,
                    price: (datePrice.priPrice + datePrice.rebate),
                    date: datePrice.huiDate.substring(0, 10),
                    title: '¥' + (datePrice.priPrice + datePrice.rebate),
                });
            });
            $scope.calendarData = calendarData;
        }
        $("div.ajaxloading").hide();
    });

    $scope.eventClick = function (calEvent) {
        $scope.ticketDatePriceId = calEvent.ticketDatePriceId;
        $scope.price = calEvent.price;
        $scope.date = calEvent.date;
    };

    $scope.dayClick = function (date) {
        var calEvent = $("#calendar").fullCalendar('clientEvents', date)[0];
        if (calEvent != null) {
            $scope.ticketDatePriceId = calEvent.ticketDatePriceId;
            $scope.price = calEvent.price;
            $scope.date = calEvent.date;
        } else {
            $(".fc-highlight-skeleton").remove();
            $("#calendar").fullCalendar('select', $.fullCalendar.moment($scope.date));
        }
    };

    $scope.next = function () {
        if ($scope.ticketDatePriceId != null && $scope.ticketDatePriceId > 0) {
            storage.set(LXB_KEY.ticketDatePriceId, $scope.ticketDatePriceId);
            $state.go("orderTicket");
        } else {
            bootbox.alert("请选择日期");
        }
    }

});




//酒店下单
var orderHotelModule = angular.module("orderHotelModule", []);
orderHotelModule.controller("orderHotelCtrl", function ($scope, $http, $stateParams, $state, storage, Check) {
    if ($stateParams.params == null || $stateParams.params == "") {
        bootbox.alert("请选择酒店和日期", function () {
            history.go(-1);
        });
        return;
    }
    var params = JSON.parse($stateParams.params);
    $scope.hotelPriceId = params.hotelPriceId;
    $scope.startDate = params.startDate;
    $scope.endDate = params.endDate;
    $scope.num = 1;
    $scope.touristList = [{}];
    $scope.totalPrice = 0;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.orderHotel, {
        hotelPriceId: $scope.hotelPriceId,
        startDate: $scope.startDate,
        endDate: $scope.endDate
    }).success(function (data) {
        if (Check.loginCheckAndBack(data)) {
            if (data.success) {
                $scope.hotel = data.hotel;
                $scope.hotelPrice = data.hotelPrice;
                $scope.priceCalendar = data.priceCalendar;
                $scope.day = $scope.priceCalendar.length;
                angular.forEach($scope.priceCalendar, function (calendar) {
                    calendar.showDate = calendar.date.substr(0, 10);
                    $scope.totalPrice += calendar.member;
                });
            } else {
                bootbox.alert(data.errMsg, function () {
                    history.go(-1);
                });
            }
        }
        $("div.ajaxloading").hide();
    });

    $scope.showChangeNum = function () {
        if ($("#container").hasClass("hide")) {
            $("#container, .whats-mask").removeClass("hide");
        } else {
            $("#container, .whats-mask").addClass("hide");
        }
    };

    $scope.changeNum = function (num) {
        $scope.num = num;
        $("#container, .whats-mask").addClass("hide");
        var length = $scope.touristList.length;
        if (num > length) {
            for (var i = length; i < num; i++) {
                $scope.touristList.push({});
            }
        } else {
            for (var i = num; i < length; i++) {
                $scope.touristList.splice(num, 1);
            }
        }
    };

    $scope.showDetail = function () {
        if ($("#fysm").hasClass("hide")) {
            $("#fysm").removeClass("hide");
        } else {
            $("#fysm").addClass("hide");
        }
    };

    $scope.showNotice = function () {
        if ($("#bookNotice").hasClass("hide")) {
            $("#bookNotice").removeClass("hide");
            $("#order, #header").addClass("hide");
        } else {
            $("#bookNotice").addClass("hide");
            $("#order, #header").removeClass("hide");
        }
    };

    $scope.next = function () {
        var isReturn = false;
        var name = /^[\u4E00-\u9FA5]+$/;
        angular.forEach($scope.touristList, function (tourist) {
            if (tourist.name == null || tourist.name == "" || !tourist.name.match(name)) {
                isReturn = true;
                return true;
            }
        });
        if (isReturn) {
            bootbox.alert("入住人姓名错误");
            return;
        }
        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        if ($scope.telephone == null || $scope.telephone == "") {
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
        if (!$scope.telephone.match(mobile)) {
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
        storage.set(LXB_KEY.orderHotel, {
            num: $scope.num,
            touristList: $scope.touristList,
            telephone: $scope.telephone,
            hotel: $scope.hotel,
            hotelPrice: $scope.hotelPrice,
            startDate: $scope.startDate,
            endDate: $scope.endDate,
            price: $scope.totalPrice / $scope.day,
            totalPrice: $scope.totalPrice
        });
        $state.go("orderHotelGuarantee");
    };

    $scope.submitOrder = function () {
        var isReturn = false;
        var name = /^[\u4E00-\u9FA5]+$/;
        angular.forEach($scope.touristList, function (tourist) {
            if (tourist.name == null || tourist.name == "" || !tourist.name.match(name)) {
                isReturn = true;
                return true;
            }
        });
        if (isReturn) {
            bootbox.alert("入住人姓名错误");
            return;
        }
        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        if ($scope.telephone == null || $scope.telephone == "") {
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
        if (!$scope.telephone.match(mobile)) {
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
        $http.post(LXB_URL.checkHotelOrder, {
            hotelPriceId: $scope.hotelPrice.id,
            startDate: $scope.startDate,
            endDate: $scope.endDate,
            num: $scope.num
        }).success(function (data) {
            if (data.success) {
                var jsonObj = {
                    id: 0,
                    name: $scope.hotel.name,
                    playDate: $scope.startDate,
                    contact: {
                        name: $scope.touristList[0].name,
                        telephone: $scope.telephone
                    },
                    orderType: "hotel"
                };
                var details = [];
                var detail = {
                    id: $scope.hotel.id,
                    priceId: $scope.hotelPrice.id,
                    price: $scope.totalPrice / $scope.day,
                    startTime: $scope.startDate,
                    endTime: $scope.endDate,
                    num: $scope.num,
                    type: "hotel",
                    seatType: $scope.hotelPrice.roomName
                };
                var touristList = [];
                angular.forEach($scope.touristList, function (tourist) {
                    touristList.push({
                        name: tourist.name
                    });
                });
                detail.tourist = touristList;
                var creditCard = {
                    status: false
                };
                detail.creditCard = creditCard;
                details.push(detail);
                jsonObj.details = details;
                $http.post(GetUrl.orderSave, {
                        json: JSON.stringify(jsonObj)
                    }
                ).success(function (data) {
                        storage.remove(LXB_KEY.orderHotel);
                        if (Check.loginCheckAndBack(data)) {
                            if (data.success) {
                                $state.go("hotelOrderDetail", {orderId: data.order.id});
                            }
                        }
                    }).error(function (data) {
                        alert(data.errorMsg);
                    });
            } else {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '酒店库存不足'
                });
                return;
            }
        });
    };
});

//酒店担保
var orderHotelGuaranteeModule = angular.module("orderHotelGuaranteeModule", []);
orderHotelGuaranteeModule.controller("orderHotelGuaranteeCtrl", function ($scope, $http, storage, Check, $state) {
    var orderHotel = storage.get(LXB_KEY.orderHotel) == null ? {} : storage.get(LXB_KEY.orderHotel);
    $scope.num = orderHotel.num;
    $scope.touristList = orderHotel.touristList;
    $scope.telephone = orderHotel.telephone;
    $scope.hotel = orderHotel.hotel;
    $scope.hotelPrice = orderHotel.hotelPrice;
    $scope.startDate = orderHotel.startDate;
    $scope.endDate = orderHotel.endDate;
    $scope.price = orderHotel.price;
    $scope.totalPrice = orderHotel.totalPrice;

    $scope.submitOrder = function () {
        if ($scope.cardNum == null || $scope.cardNum == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写银行卡号'
            });
            return;
        }
        if ($scope.expiration == null || $scope.expiration == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写有效期'
            });
            return;
        }
        if ($scope.cvv == null || $scope.cvv == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写cvv'
            });
            return;
        }
        var name = /^[\u4E00-\u9FA5]+$/;
        if ($scope.holderName == null || $scope.holderName == "") {
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
        if (!$scope.holderName.match(name) || $scope.holderName.length > 10) {
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
        if ($scope.idNo == null || $scope.idNo == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写身份证'
            });
            return;
        }
        $http.post(LXB_URL.checkHotelOrder, {
            hotelPriceId: $scope.hotelPrice.id,
            startDate: $scope.startDate,
            endDate: $scope.endDate,
            num: $scope.num
        }).success(function (data) {
            if (data.success) {
                var jsonObj = {
                    id: 0,
                    name: $scope.hotel.name,
                    playDate: $scope.startDate,
                    contact: {
                        name: $scope.touristList[0].name,
                        telephone: $scope.telephone
                    },
                    orderType: "hotel"
                };
                var details = [];
                var detail = {
                    id: $scope.hotel.id,
                    priceId: $scope.hotelPrice.id,
                    price: $scope.price,
                    startTime: $scope.startDate,
                    endTime: $scope.endDate,
                    num: $scope.num,
                    type: "hotel",
                    seatType: $scope.hotelPrice.roomName
                };
                var touristList = [];
                angular.forEach($scope.touristList, function (tourist) {
                    touristList.push({
                        name: tourist.name
                    });
                });
                detail.tourist = touristList;
                var creditCard = {
                    status: true,
                    cardNum: $scope.cardNum,
                    cvv: $scope.cvv,
                    expirationYear: $scope.expiration.substr(0, 4),
                    expirationMonth: $scope.expiration.substr(4, 2),
                    holderName: $scope.holderName,
                    creditCardIdType: "IdentityCard",
                    idNo: $scope.idNo
                };
                detail.creditCard = creditCard;
                details.push(detail);
                jsonObj.details = details;
                $http.post(GetUrl.orderSave, {
                        json: JSON.stringify(jsonObj)
                    }
                ).success(function (data) {
                        storage.remove(LXB_KEY.orderHotel);
                        if (Check.loginCheckAndBack(data)) {
                            if (data.success) {
                                $state.go("hotelOrderDetail", {orderId: data.order.id});
                            }
                        }
                    }).error(function (data) {
                        alert(data.errorMsg);
                    });
            } else {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '酒店库存不足'
                });
                return;
            }
        });
    };
});

//酒店订单详情
var hotelOrderDetailModule = angular.module("hotelOrderDetailModule", []);
hotelOrderDetailModule.controller("hotelOrderDetailCtrl", function ($scope, $http, Check, $stateParams) {
    $scope.orderId = $stateParams.orderId;

    $("div.ajaxloading").show();
    $http.post(LXB_URL.hotelOrderDetail, {
        orderId: $scope.orderId
    }).success(function (data) {
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.order = data.order;
                $scope.day = $scope.order.calendarList.length;
                $scope.totalPrice = 0;
                angular.forEach($scope.order.calendarList, function (calendar) {
                    calendar.showDate = calendar.date.substr(0, 10);
                    $scope.totalPrice += calendar.member;
                });
            } else {
                bootbox.alert(data.errMsg, function () {
                    history.go(-1);
                });
            }
        }
        $("div.ajaxloading").hide();
    });

    $scope.showDetail = function () {
        if ($(".details-model").hasClass("in")) {
            $(".details-model").removeClass("in");
        } else {
            $(".details-model").addClass("in");
        }
    };
});