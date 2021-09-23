/**
 * Created by huangpeijie on 2016-09-27,0027.
 */
var personalCenter = angular.module('personalCenter', []);
//首页

personalCenter.controller('personalIndexCtrl', function ($scope, storage, $http, MyStorage, $state, Check) {
    $scope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
        $scope.preUrl = from.name;
    });

    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });

    $scope.back = function() {
        if ($scope.preUrl != null && $scope.preUrl.length > 0 && $scope.preUrl.indexOf("personal") < 0) {
            $state.go($scope.preUrl);
        } else {
            $state.go("index");
        }
    };

    $scope.toPersonalOrder = function () {
        storage.remove(yhyKey.personalOrderType);
        $state.go("personalOrder", {index: 0});
    };

    $scope.resetStorage = function () {
        MyStorage.reset();
        bootbox.alert("缓存已清除");
    };
});
//我的信息
personalCenter.controller('personalInfoCtrl', function ($scope, storage, $http, Check, Wechatpay, $state) {
    $scope.shadow = false;
    $scope.isWeixin = Wechatpay.isWeiXin();

    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
            $scope.showSex = $scope.user.gender == "male" ? "男" : "女";
        }
        $scope.login = data.success;
    });

    $scope.hideshadow = function () {
        $scope.shadow = false;
        $scope.changemale = false;
    };

    $scope.changesex = function (sex) {
        $scope.user.gender = sex == "男" ? "male" : "female";
        $http.post(yhyUrl.updatePersonalInfo, {
            json: JSON.stringify($scope.user)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                $scope.showSex = sex;
                storage.set(yhyKey.user, data.user);
            } else {
                bootbox.alert("保存失败");
            }
            $scope.shadow = false;
            $scope.changemale = false;
        });
    };

    $scope.toFerryMember = function () {
        if (!$scope.user.isReal) {
            storage.remove(yhyKey.ferryOrder);
            storage.set(yhyKey.preUrl, "personalInfo");
            if ($scope.user.ferryUserName == null || $scope.user.ferryUserName == "") {
                $state.go("ferryRealname");
            } else {
                $state.go("ferryDoRealname");
            }
        }
    };

    $scope.logout = function () {
        $http.post(yhyUrl.logout, {}).success(function (data) {
            if (data.success) {
                $scope.login = false;
            }
        });
    }
});
//行程列表
personalCenter.controller('personalMarchListCtrl', function ($scope, storage, $http, Check, $rootScope) {
    $scope.planList = [];
    $scope.pageNo = 1;
    $scope.pageSize = 10;
    $scope.nomore = false;
    $scope.getPlanList = function () {
        if ($rootScope.loading) {
            return;
        }
        if ($scope.nomore) {
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.personalPlan, {
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            $rootScope.loading = false;
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                angular.forEach(data.planList, function (plan) {
                    var startDate = new Date(plan.startTime);
                    plan.playDate = startDate.format("yyyy年MM月dd日");
                    $scope.planList.push(plan);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++;
            }
        });
    };
});
//行程详情
personalCenter.controller("personalMarchDetail", function ($scope, $http, $stateParams, NumberHandle) {
    $scope.planId = $stateParams.planId;
    if ($scope.planId == null || $scope.planId == "") {
        history.back();
        return;
    }

    $http.post(yhyUrl.personalPlanDetail, {
        planId: $scope.planId
    }).success(function (data) {
        if (data.success) {
            angular.forEach(data.plan.days, function (day) {
                angular.forEach(day.scenics, function (scenic) {
                    scenic.adviceDate = NumberHandle.roundTwoDecimal(scenic.adviceMinute / 60);
                });
            });
            $scope.plan = data.plan;
        }
    });

    $scope.mytravel = true;
    $scope.hidebutton = false;

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
//更改昵称
personalCenter.controller('personalChangeNameCtrl', function ($scope, storage, $http, Check) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });

    $scope.update = function () {
        if ($scope.user.nickName == null || $scope.user.nickName == "") {
            bootbox.alert("昵称不能为空");
            return;
        }
        if (!$scope.user.nickName.match(yhyKey.nickNameReg)) {
            bootbox.alert("昵称格式错误");
            return;
        }
        $http.post(yhyUrl.updatePersonalInfo, {
            json: JSON.stringify($scope.user)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            }
        });
    };
});
//更改姓名
personalCenter.controller('personalChangeTrueNameCtrl', function ($scope, storage, $http, Check) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });

    $scope.update = function () {
        if ($scope.user.userName == null || $scope.user.userName == "") {
            bootbox.alert("姓名不能为空");
            return;
        }
        if (!$scope.user.userName.match(yhyKey.nameReg)) {
            bootbox.alert("姓名格式错误");
            return;
        }
        $http.post(yhyUrl.updatePersonalInfo, {
            json: JSON.stringify($scope.user)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            }
        });
    };
});
//更改性别
personalCenter.controller("personalChangeSexCtrl", function ($scope, $http, storage, Check) {
    $scope.sex = 0;
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
            if ($scope.user.gender == "male") {
                $scope.sex = 1;
            } else if ($scope.user.gender = "female") {
                $scope.sex = 2;
            }
        }
    });

    $scope.selthis = function (num) {
        $scope.sex = num;
        if (num == 1) {
            $scope.user.gender = "male";
        } else if (num == 2) {
            $scope.user.gender = "female";
        }
    };

    $scope.update = function () {
        $http.post(yhyUrl.updatePersonalInfo, {
            json: JSON.stringify($scope.user)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            }
        });
    };
});
//修改密码
personalCenter.controller('personalChangePasswordCtrl', function ($scope, storage, $http, Check) {
    $http.post(yhyUrl.checkLogin, {}).success(function (data) {
        Check.loginCheck(data);
    });

    $scope.update = function () {
        if ($scope.password == null || $scope.password == "") {
            bootbox.alert("请输入原密码");
            return;
        }
        if (!$scope.password.match(yhyKey.passwordReg)) {
            bootbox.alert("原密码格式错误");
            return;
        }
        if ($scope.newPassword == null || $scope.newPassword == "") {
            bootbox.alert("请输入新密码");
            return;
        }
        if (!$scope.newPassword.match(yhyKey.passwordReg)) {
            bootbox.alert("新密码格式错误");
            return;
        }
        if ($scope.password == $scope.newPassword) {
            bootbox.alert("新密码不能与原密码相同");
            return;
        }
        if ($scope.newPassword != $scope.checkPassword) {
            bootbox.alert("两次密码输入内容不同");
            return;
        }
        $http.post(yhyUrl.updatePersonalPassword, {
            password: $scope.password,
            newPassword: $scope.newPassword
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            } else {
                bootbox.alert(data.errMsg);
            }
        });
    };
});
//修改邮箱
personalCenter.controller('personalChangeEmailCtrl', function ($scope, storage, $http, Check) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });

    $scope.update = function () {
        var email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if ($scope.user.email == null || $scope.user.email == "") {
            bootbox.alert("邮箱不能为空");
            return;
        }
        if (!$scope.user.email.match(email)) {
            bootbox.alert("邮箱格式不正确");
            return;
        }
        $http.post(yhyUrl.updatePersonalInfo, {
            json: JSON.stringify($scope.user)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            }
        });
    };
});
//修改手机号
personalCenter.controller('personalChangePhoneCtrl', function ($scope, storage, $http, Check) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });

    $scope.update = function () {
        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        if ($scope.user.telephone == null || $scope.user.telephone == "") {
            bootbox.alert("手机号不能为空");
            return;
        }
        if (!$scope.user.telephone.match(mobile)) {
            bootbox.alert("手机号格式不正确");
            return;
        }
        $http.post(yhyUrl.updatePersonalInfo, {
            json: JSON.stringify($scope.user)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            }
        });
    };
});
//修改身份证
personalCenter.controller('personalChangeIdNumberCtrl', function ($scope, storage, $http, Check, $state, idCard) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.idNumber = data.user.idNumber;
        }
    });

    $scope.update = function () {
        if ($scope.idNumber == null || $scope.idNumber == "") {
            bootbox.alert("身份证不能为空");
            return;
        }
        var msg = idCard.checkIdcard($scope.idNumber);
        if (msg != "验证通过!") {
            bootbox.alert(msg);
            return;
        }
        $http.post(yhyUrl.updatePersonalIdNumber, {
            idNumber: $scope.idNumber
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            }
        });
    };
});
//修改银行卡号
personalCenter.controller('personalChangeBankNoCtrl', function ($scope, storage, $http, Check) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.bankNo = data.user.bankNo;
        }
    });

    $scope.update = function () {
        if ($scope.bankNo == null || $scope.bankNo == "") {
            bootbox.alert("银行卡号不能为空");
            return;
        }
        $scope.bankNo = $scope.bankNo.trim().replace(" ", "");
        if (!$scope.bankNo.match(yhyKey.bankNoReg)) {
            bootbox.alert("银行卡号格式错误");
            return;
        }
        $http.post(yhyUrl.updatePersonalBankNo, {
            bankNo: $scope.bankNo
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            }
        });
    };
});
//我的订单
personalCenter.controller('personalOrderCtrl', function ($scope, $http, Check, $rootScope, storage, $stateParams, $state) {
    $scope.selNum = 0;
    $scope.shadow = false;
    $scope.selBarType = '全部';
    $scope.selBarStatus = '分类';
    $scope.orderColumn = "createTime";
    $scope.orderBy = "desc";
    $scope.pageSize = 10;
    $scope.selBars1 = [
        {index: 1, name: '景点门票', pic: 'in_scenic', type: "ticket", logo: 'icon-certer_attrction'},
        {index: 2, name: '酒店民宿', pic: 'in_hotel', type: "hotel", logo: 'icon-certer_hotel'},
        {index: 3, name: '船票', pic: 'in_boatticket', type: "ferry", logo: 'icon-certer_cruise'},
        {index: 4, name: '邮轮', pic: 'in_cruis', type: "cruiseship", logo: 'icon-certer_ship'},
        {index: 5, name: 'DIY行程', pic: 'in_diy', type: "plan", logo: 'icon-certer_trip'},
        {index: 6, name: '海上休闲', pic: 'in_sail', type: "sailboat,yacht,huanguyou", logo: 'icon-certer_sailing'},
        {index: 7, name: '专车', pic: 'in_car', type: "shenzhou", logo: 'icon-certer_car'},
        {index: 8, name: '驴迹', pic: 'in_lvji', type: "lvji", logo: 'icon-certer_lvji'}
    ];
    $scope.selBars3 = [
        {index: 1, name: '全部', status: null},
        {index: 2, name: '已取消', status: "CANCELED"},
        {index: 3, name: '待支付', status: "WAIT"},
        {index: 4, name: '预订成功', status: "SUCCESS"}
    ];

    var index = $stateParams.index.split("-");
    if (index == null || index.length == 0 || index[0] < 0 || index[0] > 7) {
        $state.go("personalIndex");
        return;
    }

    if (index[0] == 0) {
        $scope.selcolor = 1;
        $scope.type = null;
        if (index.length == 1) {
            $scope.status = null;
        } else {
            $scope.status = $scope.selBars3[index[1]].status;
        }
    } else {
        $scope.selcolor = 2;
        $scope.type = $scope.selBars1[index[0] - 1].type;
    }

    $scope.searchPersonalOrder = function () {
        if ($rootScope.loading) {
            return;
        }
        if ($scope.nomore) {
            return;
        }
        $rootScope.loading = true;
        if ($scope.selcolor == 1) {
            $scope.type = null;
        } else {
            $scope.status = null;
        }
        $http.post(yhyUrl.personalOrder, {
            type: $scope.type,
            orderColumn: $scope.orderColumn,
            orderBy: $scope.orderBy,
            status: $scope.status,
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            $rootScope.loading = false;
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.orderList.length < 1) {
                return;
            }
            if ($scope.type != null && $scope.type.indexOf(data.orderList[0].type) < 0) {
                return;
            }
            if ($scope.status != null && $scope.status != data.orderList[0].orderStatus) {
                return;
            }
            if (data.success) {
                angular.forEach(data.orderList, function (order) {
                    $scope.orderList.push(order);
                });
                $scope.pageNo++;
                $scope.nomore = data.nomore;
                //console.info(data.orderList);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    init();

    function init() {
        $scope.pageNo = 1;
        $rootScope.loading = false;
        $scope.nomore = false;
        $scope.orderList = [];
    }

    $scope.showOrderList = function (num) {
        //var x = num - 1;
        $scope.selNum = num;
        $scope.selcolor = num;
        $scope.shadow = true;
        $("div[ng-controller]").addClass('stopScroll');
        //$('.personal_myOrder .selbar li img').removeClass('picrotate');
        //$('.personal_myOrder .selbar li:eq(' + x + ') img').addClass('picrotate');
    };

    $scope.hideshadow = function () {
        $scope.shadow = false;
        $scope.selNum = 0;
        //$('.personal_myOrder .selbar li img').removeClass('picrotate');
        $("div[ng-controller]").removeClass('stopScroll');
    };

    function sel() {
        $scope.selNum = 0;
        $scope.shadow = false;
        $("div[ng-controller]").removeClass('stopScroll');
        init();
        $scope.searchPersonalOrder();
    }

    $scope.selTag = function (obj) {
        $scope.hideshadow();
        $state.go("personalOrder", {index: obj.index});
    };

    $scope.selStatus = function (obj) {
        $scope.hideshadow();
        $state.go("personalOrder", {index: 0 + "-" + (obj.index - 1)});
    };

    $scope.deleteOrder = function (id, event) {
        event.stopPropagation();
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: "确定",
                    className: "btn-confirm"
                },
                cancel: {
                    label: "取消",
                    className: "btn-confirm"
                }
            },
            message: "确定删除该订单",
            callback: function (result) {
                if (result) {
                    $http.post(yhyUrl.deleteOrder, {
                        orderId: id
                    }).success(function (data) {
                        if (data.success) {
                            sel();
                        }
                    });
                }
            }
        });
    };

    $scope.deleteFerry = function (id, event) {
        event.stopPropagation();
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: "确定",
                    className: "btn-confirm"
                },
                cancel: {
                    label: "取消",
                    className: "btn-confirm"
                }
            },
            message: "确定删除该订单",
            callback: function (result) {
                if (result) {
                    $http.post(yhyUrl.ferryDeleteOrder, {
                        orderId: id
                    }).success(function (data) {
                        if (data.success) {
                            sel();
                        }
                    });
                }
            }
        });
    };

    $scope.deleteShenzhou = function (id, event) {
        event.stopPropagation();
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: "确定",
                    className: "btn-confirm"
                },
                cancel: {
                    label: "取消",
                    className: "btn-confirm"
                }
            },
            message: "确定删除该订单",
            callback: function (result) {
                if (result) {
                    $http.post(yhyUrl.shenzhouOrderDelete, {
                        orderId: id
                    }).success(function (data) {
                        if (data.success) {
                            sel();
                        }
                    });
                }
            }
        });
    };

    $scope.detail = function (order) {
        if (order.type == "ticket") {
            storage.set(yhyKey.preUrl, "personalOrder");
            $state.go("ticketOrderDetail", {orderId: order.id});
        } else if (order.type == "hotel") {
            storage.set(yhyKey.preUrl, "personalOrder");
            $state.go("hotelOrderDetail", {orderId: order.id});
        } else if (order.type == "cruiseship") {
            storage.set(yhyKey.preUrl, "personalOrder");
            $state.go("cruiseShipOrderDetail", {orderId: order.id});
        } else if (order.type == "plan") {
            storage.set(yhyKey.preUrl, "personalOrder");
            $state.go("planOrderDetail", {id: order.id});
        } else if (order.type == 'sailboat' || order.type == 'yacht' || order.type == 'huanguyou') {
            storage.set(yhyKey.preUrl, "personalOrder");
            $state.go("sailling/orderDetail", {orderId: order.id});
        } else if (order.type == "ferry") {
            storage.set(yhyKey.preUrl, "personalOrder");
            $state.go("ferryOrderDetail", {orderId: order.id});
        } else if (order.type == "shenzhou") {
            storage.set(yhyKey.preUrl, "personalOrder");
            $state.go("payment", {orderId: order.id});
        }
    };

    $scope.back = function () {
        $("div[ng-controller]").removeClass('stopScroll');
        storage.remove(yhyKey.preUrl);
        $state.go("personalIndex");
    };
});

//我的钱包
personalCenter.controller('personalWalletCtrl', function ($scope, $http, Check) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });
});

personalCenter.controller('personalChangePayPasswordCtrl', function ($scope, storage, $http, Check) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });

    $scope.update = function () {
        if ($scope.user.hasPayPassword) {
            if ($scope.password == null || $scope.password == "") {
                bootbox.alert("请输入原密码");
                return;
            }
            if (!$scope.password.match(yhyKey.passwordReg)) {
                bootbox.alert("原密码格式错误");
                return;
            }
        }
        if ($scope.newPassword == null || $scope.newPassword == "") {
            bootbox.alert("请输入新密码");
            return;
        }
        if (!$scope.newPassword.match(yhyKey.passwordReg)) {
            bootbox.alert("新密码格式错误");
            return;
        }
        if ($scope.password == $scope.newPassword) {
            bootbox.alert("新密码不能与原密码相同");
            return;
        }
        if ($scope.newPassword != $scope.checkPassword) {
            bootbox.alert("两次密码输入内容不同");
            return;
        }
        $http.post(yhyUrl.updatePersonalPayPassword, {
            password: $scope.password,
            newPassword: $scope.newPassword
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                history.go(-1);
            } else {
                bootbox.alert(data.errMsg);
            }
        });
    };
});

//我的收藏
personalCenter.controller('personalSelectionCtrl', function ($scope, $http, Check, $rootScope, $state) {
    $scope.state = 0;
    $scope.delbox = false;
    $scope.delBotton = false;
    $scope.director = "产品";
    $scope.directorList = [
        {index: 1, name: "全部", type: null},
        {index: 2, name: "酒店民宿", type: "hotel"},
        {index: 3, name: "景点", type: "scenic"},
        {index: 4, name: "邮轮", type: "cruiseship"},
        {index: 5, name: "帆船游艇", type: "sailboat"},
        {index: 6, name: "游记攻略", type: "recplan"}
    ];

    $scope.targetType = null;

    function init() {
        $scope.pageNo = 1;
        $scope.nomore = false;
        $rootScope.loading = false;
        $scope.collecList = [];
    }

    init();

    $scope.listSelection = function () {
        if ($rootScope.loading) return;
        if ($scope.nomore) return;
        if ($scope.delbox) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.personalSelection, {
            targetType: $scope.targetType,
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            $rootScope.loading = false;
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                angular.forEach(data.collectList, function (collect) {
                    collect.selected = false;
                    $scope.collecList.push(collect);
                });
                $scope.pageNo++;
                $scope.nomore = data.nomore;
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    $scope.showDeleteBox = function () {
        if ($scope.collecList.length == 0) {
            return;
        }
        $('.personal_mySelection .listone .list_left').animate({'left': 50}, 300);
        $('.personal_mySelection .listone .list_right').animate({'right': -30}, 300);
        $scope.delbox = true;
        $scope.state = 1;
        $scope.delBotton = true;
        $('.personal_mySelection .hotlinelist').css({'paddingBottom':'60px'});
    };

    $scope.hideDeleteBox = function () {
        $('.personal_mySelection .listone .list_left').animate({'left': 10}, 300);
        $('.personal_mySelection .listone .list_right').animate({'right': 10}, 300);
        $scope.delbox = false;
        $scope.state = 0;
        $scope.delBotton = false;
        $scope.shadow = false;
        $scope.myproductlist = false;
        $('.personal_mySelection .hotlinelist').css({'paddingBottom':0});
    };

    $scope.changeBox = function (product) {
        product.selected = !product.selected;
    };

    $scope.showmylist = function () {
        $scope.shadow = true;
        $scope.myproductlist = true;
        $("div[ng-controller]").addClass('stopScroll');
    };

    $scope.hideshadow = function () {
        $scope.shadow = false;
        $scope.myproductlist = false;
        $("div[ng-controller]").removeClass('stopScroll');
    };

    $scope.dirTag = function (dir) {
        $scope.director = dir.name;
        $scope.targetType = dir.type;
        $scope.shadow = false;
        $scope.myproductlist = false;
        $("div[ng-controller]").removeClass('stopScroll');
        init();
        $scope.hideDeleteBox();
        $scope.listSelection();
    };

    $scope.delete = function () {
        var ids = [];
        angular.forEach($scope.collecList, function (collect) {
            if (collect.selected) {
                ids.push(collect.id);
            }
        });
        $http.post(yhyUrl.batchDeleteSelection, {
            json: JSON.stringify(ids)
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                $scope.hideDeleteBox();
                init();
                $scope.listSelection();
            }
        });
    };

    $scope.detail = function (item) {
        if ($scope.state == 1) {
            return;
        }
        switch (item.targetType) {
            case "cruiseship":
                $state.go("cruiseShipDetail", {id: item.targetId});
                break;
            case "hotel":
                $state.go("hotelDetail", {hotelId: item.targetId});
                break;
            case "scenic":
                $state.go("scenicDetail", {scenicId: item.targetId});
                break;
            case "sailboat":
                $state.go("sailling/detail", {sailId: item.targetId});
                break;
            case "recplan":
                $state.go("guide/detail", {recommendPlanId: item.targetId});
        }
    };
});

//我的点评
personalCenter.controller('personalCommentCtrl', function ($scope, $http, Check, $state, $rootScope) {
    var screenWidth = $(window).width() / 2;
    $scope.commented = 1;
    $scope.state = 1;
    $scope.commentArea = false;
    $scope.shadow = false;
    $scope.commentTitleList = [
        {index: 1, contain: "已点评"},
        {index: 2, contain: "待点评"}
    ];

    function init() {
        $scope.yesPageNo = 1;
        $scope.yesPageSize = 10;
        $scope.commentList = [];
        $scope.yesNomore = false;
        $scope.yesLoading = false;
        $scope.noPageNo = 1;
        $scope.noPageSize = 10;
        $scope.orderList = [];
        $scope.noNomore = false;
        $scope.noLoading = false;
    }

    init();

    function scopeLoading() {
        $rootScope.loading = $scope.yesLoading || $scope.noLoading;
    }

    $scope.listComment = function () {
        if ($scope.commented != 1) return;
        if ($scope.yesLoading) return;
        if ($scope.yesNomore) return;
        $scope.yesLoading = true;
        scopeLoading();
        $http.post(yhyUrl.personalComment, {
            pageNo: $scope.yesPageNo,
            pageSize: $scope.yesPageSize
        }).success(function (data) {
            $scope.yesLoading = false;
            scopeLoading();
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                angular.forEach(data.commentList, function (comment) {
                    comment.star = Math.round(comment.score / 20);
                    $scope.commentList.push(comment);
                });
                $scope.yesPageNo++;
                $scope.yesNomore = data.nomore;
            }
            //console.info($scope.commentList)
        }).error(function () {
            $scope.yesLoading = false;
            scopeLoading();
        });
    };

    $scope.listNoComment = function () {
        if ($scope.commented != 2) return;
        if ($scope.noLoading) return;
        if ($scope.noNomore) return;
        $scope.noLoading = true;
        scopeLoading();
        $http.post(yhyUrl.personalNoCommentOrder, {
            pageNo: $scope.noPageNo,
            pageSize: $scope.noPageSize
        }).success(function (data) {
            $scope.noLoading = false;
            scopeLoading();
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                angular.forEach(data.orderList, function (order) {
                    $scope.orderList.push(order);
                });
                $scope.noPageNo++;
                $scope.noNomore = data.nomore;
            }
        }).error(function () {
            $scope.noLoading = false;
            scopeLoading();
        });
    };

    $scope.selComState = function (num) {
        var left = screenWidth * (num - 1);
        $scope.state = num;
        $scope.commented = num;
        if (num == 1 && $scope.commentList.length < 1) {
            $scope.listComment();
        }
        if (num == 2 && $scope.orderList.length < 1) {
            $scope.listNoComment();
        }
        $('.personal_myComment .product .sliderbar').animate({'left': left}, 300);
    };
    $scope.showCommentArea = function (order, event) {
        event.stopPropagation();
        $scope.commenting = order;
        $scope.shadow = true;
        $scope.commentArea = true;
    };
    $scope.hideshadow = function () {
        $scope.commentArea = false;
        $scope.shadow = false;
        $scope.level = 0;
        $scope.content = "";
    };

    $scope.submitComment = function () {
        $http.post(yhyUrl.saveComment, {
            'comment.targetId': $scope.commenting.proId,
            'comment.priceId': $scope.commenting.priceId,
            'comment.type': $scope.commenting.proType,
            'comment.content': $scope.content,
            orderDetailId: $scope.commenting.id,
            score: $scope.level
        }).success(function (data) {
            if (data.success) {
                init();
                $scope.listComment();
                $scope.listNoComment();
            }
            $scope.hideshadow();
        });
    };

    $scope.deleteComment = function (commentId, event) {
        event.stopPropagation();
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: "确定",
                    className: "btn-confirm"
                },
                cancel: {
                    label: "取消",
                    className: "btn-confirm"
                }
            },
            message: "确定删除该评论？",
            callback: function (result) {
                if (result) {
                    $http.post(yhyUrl.deleteComment, {
                        commentId: commentId
                    }).success(function (data) {
                        if (data.success) {
                            init();
                            $scope.listComment();
                            $scope.listNoComment();
                        }
                    });
                }
            }
        });
    };

    $scope.detail = function (comment) {
        switch (comment.productType) {
            case "scenic":
                $state.go("scenicDetail", {scenicId: comment.targetId});
                break;
            case "hotel":
                $state.go("hotelDetail", {hotelId: comment.targetId});
                break;
            case "cruiseship":
                $state.go("cruiseShipDetail", {id: comment.targetId});
                break;
            case "sailboat":
                $state.go("sailling/detail", {sailId: comment.targetId});
                break;
            case "recplan":
                $state.go("guide/detail", {recommendPlanId: comment.targetId});
                break;
        }
    };

    $scope.noDetail = function (order) {
        switch (order.proType) {
            case "scenic":
                $state.go("scenicDetail", {scenicId: order.proId});
                break;
            case "hotel":
                $state.go("hotelDetail", {hotelId: order.proId});
                break;
            case "cruiseship":
                $state.go("cruiseShipDetail", {id: order.proId});
                break;
            case "sailboat":
                $state.go("sailling/detail", {sailId: order.proId});
                break;
        }
    };
    $scope.level = 0;
    $scope.clicklist = new Array(5);
    $scope.selStar = function (index) {
        $scope.level = index + 1;
    }
});

//船票订单详情
personalCenter.controller('personal_cruiseDetail', function ($scope) {
});

personalCenter.controller("personalRechargeCtrl", function ($scope, Wechatpay, $http, Check, $rootScope) {
    $scope.iftaobao = !Wechatpay.isWeiXin() && typeof(plus) != "undefined";
    $scope.check = 1;
    $scope.payway = [
        {index: 1, name: "微信支付"},
        {index: 2, name: "支付宝支付"}
    ];
    $scope.changeSel = function (index) {
        $scope.check = index;
    };

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
        $rootScope.loading = true;
        $http.post(yhyUrl.saveBalanceOrder, {
            json: JSON.stringify(jsonObj)
        }).success(function (data) {
            $rootScope.loading = false;
            if (Check.loginCheck(data)) {
                if (data.success) {
                    if ($scope.check == 1) {
                        $rootScope.loading = true;
                        Wechatpay.payOrderWithBack(data.order.id, "recharge", Wechatpay.paySuccess);
                    } else if ($scope.check == 2) {
                        $rootScope.loading = true;
                        Wechatpay.alipayNative($scope.order.id, "recharge", 'wxpay');
                    }
                }
            }
        }).error(function (data) {
            $rootScope.loading = false;
            alert(data.errorMsg);
        });
    };
});

personalCenter.controller("personalWithdrawCtrl", function ($scope, $http, Check, $state) {
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.user = data.user;
        }
    });

    $scope.isBalancePay = false;

    $scope.submit = function () {
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
        if ($scope.user.hasPayPassword) {
            $scope.isBalancePay = true;
            $scope.payPassword = "";
        } else {
            bootbox.alert("还未设置支付密码", function () {
                $state.go("personalChangePayPassword");
            });
        }
    };

    $scope.withdraw = function () {
        if ($scope.payPassword == null || $scope.payPassword == "") {
            bootbox.alert("请输入支付密码");
            return;
        }
        if (!$scope.payPassword.match(yhyKey.passwordReg)) {
            bootbox.alert("支付密码格式错误");
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
        $http.post(yhyUrl.saveBalanceOrder, {
            payPassword: $scope.payPassword,
            json: JSON.stringify(jsonObj)
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    $http.post(yhyUrl.doTransfers, {
                        orderId: data.order.id
                    }).success(function (data) {
                        if (data.success) {
                            bootbox.alert("提现成功", function () {
                                history.go(-1);
                            });
                        } else {
                            $scope.isBalancePay = false;
                            bootbox.alert(data.errMsg);
                        }
                    });
                } else {
                    bootbox.alert(data.errMsg);
                }
            }
        }).error(function (data) {
            bootbox.alert(data.errMsg);
        });
    };

    $scope.cancel = function () {
        $scope.isBalancePay = false;
    }
});
personalCenter.controller("personalRecordCtrl", function ($scope, $http, Check) {
    $http.post(yhyUrl.balanceLog, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            angular.forEach(data.balanceLogList, function (balanceLog) {
                if (balanceLog.orderType == "sailboat" || balanceLog.orderType == "yacht" || balanceLog.orderType == "huanguyou") {
                    balanceLog.orderType = "sailling";
                }
                switch (balanceLog.orderType) {
                    case "hotel":
                        balanceLog.showClass = "icon-certer_hotel hotel";
                        break;
                    case "ferry":
                        balanceLog.showClass = "icon-certer_cruise ferry";
                        break;
                    case "shenzhou":
                        balanceLog.showClass = "icon-certer_car shenzhou";
                        break;
                    case "cruiseship":
                        balanceLog.showClass = "icon-certer_ship cruiseship";
                        break;
                    case "sailling":
                        balanceLog.showClass = "icon-certer_sailing sailling";
                        break;
                    case "ticket":
                        balanceLog.showClass = "icon-certer_attrction ticket";
                        break;
                    case "recharge":
                        balanceLog.showClass = "icon-certer_chong recharge";
                        break;
                    case "withdraw":
                        balanceLog.showClass = "icon-certer_wallet withdraw";
                        break;
                    case "plan":
                        balanceLog.showClass = "icon-certer_trip plan";
                        break;
                }
            });
            $scope.balanceLogList = data.balanceLogList;
        }
    });
});

personalCenter.controller("personalTouristListCtrl", function ($scope, $http, Check, $rootScope) {
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

    function loadTouristList() {
        $rootScope.loading = true;
        $http.post(yhyUrl.touristList, {}).success(function (data) {
            $rootScope.loading = false;
            if (Check.loginCheck(data)) {
                if (data.success) {
                    angular.forEach(data.touristList, function (tourist) {
                        tourist.check = false;
                        if (tourist.peopleType == "ADULT") {
                            tourist.showType = "成人";
                        } else {
                            tourist.showType = "儿童";
                        }
                        //tourist.telephone = tourist.telephone.substr(0, tourist.telephone.length - 4) + "****";
                    });
                    $scope.touristList = data.touristList;
                    $scope.search();
                } else {
                }
            }
        }).error(function (data) {
            $rootScope.loading = false;
            alert(data.errorMsg);
        });
    }

    loadTouristList();

    $scope.delete = function (tourist, event) {
        event.stopPropagation();
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: "确认",
                    className: "btn-confirm"
                },
                cancel: {
                    label: "取消",
                    className: "btn-confirm"
                }
            },
            message: "确定删除该常用旅客？",
            callback: function (result) {
                if (result) {
                    $http.post(yhyUrl.deleteTourist, {
                        touristId: tourist.touristId
                    }).success(function (data) {
                        if (!Check.loginCheck(data)) {
                            return;
                        }
                        if (data.success) {
                            bootbox.alert("删除成功", function () {
                                loadTouristList();
                            });
                        }
                    }).error(function () {
                        bootbox.alert("删除失败");
                    });
                }
            }
        });
    };
});
personalCenter.controller("personalWriteTourMessCtrl", function ($scope, $http, $stateParams, Check, PeopleType, idCard) {
    $scope.touristId = $stateParams.touristId;
    if ($scope.touristId != null && $scope.touristId != "") {
        $http.post(yhyUrl.touristDetail, {
            touristId: $scope.touristId
        }).success(function (data) {
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                $scope.tourist = data.tourist;
                if ($scope.tourist.gender == "male") {
                    $scope.Sex = 1;
                } else {
                    $scope.Sex = 0;
                }
                if ($scope.tourist.idType == "IDCARD") {
                    $scope.tag = '身份证';
                } else {
                    $scope.tag = '护照';
                }
            }
        });
    } else {
        $scope.tourist = {};
        $scope.tourist.gender = "female";
        $scope.tourist.idType = "IDCARD";
        $scope.Sex = 1;
        $scope.tag = '身份证';
    }
    $scope.idcar = false;
    $scope.idcarlist = [
        {index: 1, name: '身份证'},
        {index: 2, name: '护照'}
    ];
    $scope.showidcar = function (event) {
        event.stopPropagation();
        $scope.idcar = true;
    };
    $scope.selid = function (id) {
        if (id.index == 1) {
            $scope.tourist.idType = "IDCARD";
        } else if (id.index == 2) {
            $scope.tourist.idType = "PASSPORT";
        }
        $scope.tag = id.name;
        $scope.idcar = false;
    };
    $scope.clearidbox = function () {
        $scope.idcar = false;
    };
    $scope.selSex = function (num) {
        if (num == 1) {
            $scope.tourist.gender = "male";
        } else {
            $scope.tourist.gender = "female";
        }
        $scope.Sex = num;
    };

    $scope.save = function () {
        if ($scope.tourist.name == null || $scope.tourist.name == "") {
            bootbox.alert("请填写姓名");
            return;
        }
        if (!$scope.tourist.name.match(yhyKey.nameReg)) {
            bootbox.alert("姓名格式错误");
            return;
        }
        if ($scope.tourist.idNumber == null || $scope.tourist.idNumber == "") {
            bootbox.alert("请填写证件号码");
            return;
        }
        if ($scope.tourist.idType == "IDCARD") {
            var msg = idCard.checkIdcard($scope.tourist.idNumber);
            if (msg != "验证通过!") {
                bootbox.alert(msg);
                return;
            }
        }
        if ($scope.tourist.telephone == null || $scope.tourist.telephone == "") {
            bootbox.alert("请填写手机号");
            return;
        }
        if (!$scope.tourist.telephone.match(yhyKey.telephoneReg)) {
            bootbox.alert("手机号格式错误");
            return;
        }
        if (PeopleType.isAdult($scope.tourist.idNumber)) {
            $scope.tourist.peopleType = "ADULT";
        } else {
            $scope.tourist.peopleType = "KID";
        }
        $http.post(yhyUrl.saveTourist, {
            json: JSON.stringify($scope.tourist)
        }).success(function (data) {
            if (data.success) {
                bootbox.alert("保存成功", function () {
                    history.go(-1)
                });
            } else {
                bootbox.alert(data.errMsg);
            }
        });
    }

});
personalCenter.controller("timeOutCtrl", function ($scope, Wechatpay) {
    var m_top = (window.screen.height - 233)/2 - 30;
    var m_left = (window.screen.width - 300)/2;
    var btn_left = (window.screen.availWidth - 150)/2;
    var height = window.screen.height;
    $('.waittingPage .img').css({'marginTop':m_top,'marginLeft':m_left});
    $('.backout').css({'left':btn_left});

    $scope.closeWindow = function () {
        if (Wechatpay.isWeiXin()) {
            WeixinJSBridge.invoke('closeWindow',{},function(res){
            });
        }
    };
});