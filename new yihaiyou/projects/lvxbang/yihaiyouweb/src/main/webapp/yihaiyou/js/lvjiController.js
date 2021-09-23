/**
 * Created by Administrator on 2017/8/4.
 */
var LvjiModule = angular.module('LvjiModule', []);
LvjiModule.controller("lvjiOrderController", function ($scope, $http, storage, $state, storage, $stateParams, $rootScope, Check) {

    /*判断从哪个页面过来
     * num:1 船票列表
     * num:2 电子预览
     * */
    $scope.num = $stateParams.num;
    $scope.whereFrom = function () {
        switch ($scope.num) {
            case "1":
                $state.go("ferrySearch");
                break;
            case "2":
                $state.go("lvjiList");
                break;
            default:
                $state.go("ferryList");
        }

    };

    $scope.ljData = storage.get(yhyKey.lvjiList);
    $scope.submitLjOrder = function (id) {

        if ($scope.contact == 0) {
            $scope.contact = {};
        }

        switch (id) {
            case 1:
                $scope.productId = 3819;
                break;

            case 2:
                $scope.productId = 3820;
                break;

            case 3:
                $scope.productId = 3821;
                break;

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
        if ($scope.contact.mobile == null || $scope.contact.mobile == "") {
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
        if (!$scope.contact.mobile.match(mobile)) {
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


        var detail = {
            productId: $scope.productId,
            name: $scope.contact.name,
            mobile: $scope.contact.mobile
        }

        $http.post(yhyUrl.saveLjOrder, {

            contactJson: JSON.stringify(detail)

        }).success(function (data) {
            $state.go("lvjiPay", {orderId: data.orderId});

        }).error(function () {

        });
    };

});
LvjiModule.controller("lvjiPayController", function ($scope, $http, storage, $state, $rootScope, Wechatpay, storage, $stateParams, $interval, Check) {

    $scope.selectedType = 1;
    $scope.get = function (num) {
        $scope.selectedType = num;
    };

    $scope.orderId = $stateParams.orderId;
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.balance = data.user.balance;
            $scope.hasPayPassword = data.user.hasPayPassword;
            $http.post(yhyUrl.lvjiOrderInfo, {
                orderId: $scope.orderId
            }).success(function (data) {
                if (!Check.loginCheck(data)) {
                    return;
                }
                if (data.success) {
                    $scope.order = data.response;
                    if ($scope.order.orderStatus != "WAIT") {
                        bootbox.alert("订单不处于待支付状态", function () {
                            $scope.lvjiOrderDetail();
                        });
                    }
                    if ($scope.order.price > $scope.balance) {
                        $scope.data.walletStatus = false;
                        $scope.data.weChartStatus = true;
                    }
                    var seconds = $scope.order.waitTime;
                    $scope.timer = $interval(function () {
                        if (seconds <= 0) {
                            $interval.cancel($scope.timer);
                            $scope.lvjiOrderDetail();
                        }
                        $scope.minute = Math.floor(seconds / 60);
                        $scope.second = Math.floor(seconds % 60);
                        seconds--;
                    }, 1000);
                }

            });
        }
    });

    $scope.lvjiOrderDetail = function () {
        //storage.remove(yhyKey.preUrl);
        $state.go("lvjiOrderDetail", {orderId: $scope.orderId});
    };

    /*数据*/
    $scope.data = {
        popStatus: false,  //弹窗状态
        walletStatus: true, //支付方式选择  钱包支付状态
        weChartStatus: false, //支付方式选择  微信支付状态
        isBalancePay: false   //输入密码弹出状态
    };
    /*方法*/
    $scope.method = {
        switchPopStatus: function () {    //切换弹窗状态
            if ($scope.data.popStatus) {
                $scope.data.popStatus = false;
            } else {
                $scope.data.popStatus = true;
            }
        },
        payWayChoose: function (condition) {   //选择支付方式
            switch (condition) {
                case 1:    //选择钱包支付
                    if ($scope.order.price > $scope.balance) {
                        $scope.data.walletStatus = false;
                        $scope.data.weChartStatus = true;
                    } else {
                        $scope.data.walletStatus = true;
                        $scope.data.weChartStatus = false;
                    }

                    break;
                case 2:    //选择微信支付
                    $scope.data.walletStatus = false;
                    $scope.data.weChartStatus = true;
                    break;
            }
        },


        payPopStatus: function () {  //切换输入密码的弹窗状态
            if ($scope.data.isBalancePay) {
                $scope.data.isBalancePay = false;
            } else {
                $scope.data.isBalancePay = true;

            }

            //确认支付
            if ($scope.data.walletStatus) {
                if ($scope.hasPayPassword) {
                    $scope.isBalancePay = true;
                    $scope.payPassword = "";
                } else {
                    bootbox.alert("还未设置支付密码", function () {
                        $state.go("personalChangePayPassword");
                    });
                }
            } else if ($scope.data.weChartStatus) {
                Wechatpay.payOrderWithBack($scope.orderId, $scope.order.type, Wechatpay.paySuccess);
            }
        }

    };

    $scope.cancelLvji = function () {
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: '确定',
                    className: 'confirmBoxBtn'
                },
                cancel: {
                    label: '取消',
                    className: 'confirmBoxBtn'
                }
            },
            message: '确定取消订单',
            callback: function (r) {
                if (r) {
                    $http.post(yhyUrl.cancelLvjiOrder, {
                        orderId: $scope.orderId
                    }).success(function (data) {
                        if (data.success) {
                            bootbox.alert("订单取消成功");
                            $rootScope.loading = true;
                            $state.go("lvjiOrderDetail", {orderId: $scope.orderId});
                            $http.post(yhyUrl.lvjiOrderInfo, {
                                orderId: $scope.orderId
                            }).success(function (data) {
                                $rootScope.loading = false;
                                if (!Check.loginCheck(data)) {
                                    return;
                                }
                                if (data.success) {
                                    $scope.order = data.response;
                                }
                            }).error(function () {
                                $rootScope.loading = false;
                            });
                        } else {
                            bootbox.alert(data.errMsg);
                        }
                    });
                }
            }
        });
    };


    //余额支付
    $scope.payBalance = function () {
        if ($scope.payPassword == null || $scope.payPassword == "") {
            bootbox.alert("请输入支付密码");
            return;
        }
        if (!$scope.payPassword.match(yhyKey.passwordReg)) {
            bootbox.alert("支付密码格式错误");
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.payBalance, {
            payPassword: $scope.payPassword,
            orderId: $scope.orderId,
            orderType: $scope.order.type
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                bootbox.alert("请前往一海游公众号首页点击“电子导览”。选择相应景区输入您所收到的短信授权码进行使用。", $scope.lvjiOrderDetail);
            } else {
                bootbox.alert(data.errMsg);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    $scope.balanceCancel = function () {
        $scope.isBalancePay = false;
    };
});

LvjiModule.controller("lvjiOrderDetailController", function ($scope, $http, storage, $state, storage, $stateParams, $rootScope, Check) {

    $scope.orderId = $stateParams.orderId;
    $http.post(yhyUrl.lvjiOrderInfo, {
        orderId: $scope.orderId
    }).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.order = data.response;
        }
    })

    $scope.returnLvjiOrder = function () {
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: '是',
                    className: 'confirmBoxBtn'
                },
                cancel: {
                    label: '否',
                    className: 'confirmBoxBtn'
                }
            },
            message: '您是否要申请退款',
            callback: function (r) {
                if (r) {
                    $rootScope.loading = true;
                    $http.post(yhyUrl.refundLvjiOrder, {
                        orderId: $scope.orderId
                    }).success(function (data) {
                        if (data.success) {
                            bootbox.alert("订单退款中");
                            $rootScope.loading = true;
                            $state.go("lvjiOrderDetail", {orderId: $scope.orderId});
                            $http.post(yhyUrl.lvjiOrderInfo, {
                                orderId: $scope.orderId
                            }).success(function (data) {
                                $rootScope.loading = false;
                                if (!Check.loginCheck(data)) {
                                    return;
                                }
                                if (data.success) {
                                    $scope.order = data.response;
                                }
                            }).error(function () {
                                $rootScope.loading = false;
                            });
                        } else {
                            bootbox.alert(data.errMsg);
                        }
                    })
                }
            }

        });
    };

    $scope.daolan = function (data) {
        storage.set(yhyKey.lvjiDaoLan, data);
        $state.go("lvjiPage");
    }
});

LvjiModule.controller("lvjiPageController", function ($scope, $http, storage, $sce) {

    $scope.url = $sce.trustAsResourceUrl(storage.get(yhyKey.lvjiDaoLan));

});