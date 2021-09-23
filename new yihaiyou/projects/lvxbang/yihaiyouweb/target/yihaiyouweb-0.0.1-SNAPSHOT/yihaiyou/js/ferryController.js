var ferryModule = angular.module('ferryModule', ['angularLocalStorage']);

ferryModule.directive("selectVisitor", function (storage) {
    return {
        restrict: "A",
        scope: {
            vId: '=',
            vName: '=',
            vIdNum: '=',
            vPeopleType: '='
            //sumVisitorCounts: "&"
        },
        link: function (scope, element) {
            var visitorList = storage.get("visitorList");
            for (var i = 0; i < visitorList.length; i++) {
                if (visitorList[i].touristId == scope.vId) {
                    element.addClass("visitormesx");
                }
            }
            element.bind('click', function () {
                visitorList = storage.get("visitorList");
                var vCounts = visitorList.length;
                vCounts = Number(vCounts);
                if (element.hasClass("visitormesx")) {
                    element.removeClass("visitormesx");
                    for (var i = 0; i < visitorList.length; i++) {
                        if (visitorList[i].touristId == scope.vId) {
                            visitorList.splice(i, 1);
                        }
                    }
                    storage.set("visitorList", visitorList);
                } else {
                    if (vCounts < 5) {
                        element.addClass("visitormesx");
                        var obj = {
                            name: scope.vName,
                            touristId: scope.vId,
                            idNumber: scope.vIdNum,
                            peopleType: scope.vPeopleType
                        }
                        visitorList.push(obj);
                        storage.set("visitorList", visitorList);
                    } else {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: '微信单次限购5张船票'
                        })
                    }

                }

            });
        }
    }
});

ferryModule.controller("ferryListCtrl", function ($scope, $state, storage, $http, $rootScope) {
    $scope.ferryListParams = storage.get(yhyKey.ferryListParams);
    if ($.isEmptyObject($scope.ferryListParams) || $.isEmptyObject($scope.ferryListParams.flightLine) || $scope.ferryListParams.date == null || $scope.ferryListParams.date == "") {
        $state.go("ferrySearch");
        return;
    }

    var minDate = new Date(new Date().format("yyyy-MM-dd"));
    var maxDate = new Date(new Date().format("yyyy-MM-dd"));
    maxDate.setDate(maxDate.getDate() + 14);

    var limit = minDate.format("yyyy-MM-dd") + "," + maxDate.format("yyyy-MM-dd");
    $("#time").attr("data-lcalendar", limit);
    var calendardatetime = new lCalendar();
    calendardatetime.init({
        'trigger': '#time',
        'type': 'date'
    });

    $scope.selectedTouristList = storage.get(yhyKey.ferrySelectedTourist);
    if ($scope.selectedTouristList == null) {
        $scope.selectedTouristList = [];
    }

    $scope.getFlightList = function () {
        $rootScope.loading = true;
        $http.post(yhyUrl.ferryGetDailyFlight, {
            flightLineId: $scope.ferryListParams.flightLine.number,
            date: $scope.ferryListParams.date
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.flightList, function (ferry) {
                    var time = ferry.departTime.split(" ");
                    ferry.startTime = time[time.length - 1];
                });
                $scope.flightList = data.flightList;
            } else {
                bootbox.alert(data.errMsg, function () {
                    $state.go("ferrySearch");
                });
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    $scope.getFlightList();

    $scope.changeDate = function (i) {
        var date = new Date($scope.ferryListParams.date);
        date.setDate(date.getDate() + i);
        if (date < minDate || date > maxDate) {
            return;
        }
        $scope.ferryListParams.date = date.format("yyyy-MM-dd");
        storage.set(yhyKey.ferryListParams, $scope.ferryListParams);
        $scope.getFlightList();
    };

    $scope.goVisitorOrOrder = function (ferry) {
        var count = parseInt(ferry.freeCount);
        if (count > 0) {
            storage.set(yhyKey.ferrySelectedTicket, ferry);
            storage.set(yhyKey.preUrl, "ferryOrder");
            $state.go("ferryVisitor");
        } else {
            bootbox.alert("所选航班余票不足");
        }
    };
});

ferryModule.controller("ferryDateCtrl", function ($scope, $state, storage) {
    $scope.ferryListParams = storage.get(yhyKey.ferryListParams);
    if ($.isEmptyObject($scope.ferryListParams) || $scope.ferryListParams.date == null || $scope.ferryListParams.date == "") {
        $state.go("ferrySearch");
        return;
    }

    $scope.eventClick = function (calEvent) {
        $scope.ferryListParams.date = calEvent.date;
        storage.set(yhyKey.ferryListParams, $scope.ferryListParams);
        history.go(-1);
    };

    $scope.dayClick = function (date) {
        var now = new Date(new Date().format("yyyy-MM-dd"));
        var select = new Date(date);
        if (select.getTime() < now.getTime() || (select.getTime() - now.getTime()) > 5 * 24 * 60 * 60 * 1000) {
            $(".fc-highlight-skeleton").remove();
            $("#calendar").fullCalendar('select', $.fullCalendar.moment($scope.ferryListParams.date));
        } else {
            $scope.ferryListParams.date = date;
            storage.set(yhyKey.ferryListParams, $scope.ferryListParams);
            history.go(-1);
        }
    };

});
ferryModule.controller('ferryOrderCtrl', function ($scope, storage, $state, NumberHandle, $http, Check, $rootScope, PeopleType) {
    var ferryListParams = storage.get(yhyKey.ferryListParams);
    if ($.isEmptyObject(ferryListParams)) {
        $state.go("ferrySearch");
        return;
    }
    storage.remove(yhyKey.ferryOrder);
    $scope.flightLine = ferryListParams.flightLine;
    console.info($scope.flightLine);
    $scope.touristList = storage.get(yhyKey.ferrySelectedTourist);
    if ($scope.touristList == null || $scope.touristList.length == 0) {
        storage.set(yhyKey.preUrl, "ferryOrder");
        $state.go("ferryVisitor");
        return;
    }
    $scope.ticket = storage.get(yhyKey.ferrySelectedTicket);
    if ($.isEmptyObject($scope.ticket)) {
        $state.go("ferryList");
        return;
    }
    angular.forEach($scope.touristList, function (tourist) {
        var ticNum;
        if (tourist.peopleType == "KID") {
            ticNum = "E";
        } else {
            ticNum = "Q";
        }
        angular.forEach($scope.ticket.ticketLst.Ticket, function (ticket) {
            if (ticket.number.indexOf(ticNum) > -1) {
                tourist.ticket = ticket;
            }
        });
    });
    console.info($scope.touristList);

    function changePrice() {
        var totalPrice = 0;
        angular.forEach($scope.touristList, function (tourist) {
            var tic = tourist.ticket;
            if ($.isEmptyObject(tic)) {
                return true;
            }
            totalPrice += Number(tic.price);
        });
        $scope.totalPrice = NumberHandle.roundTwoDecimal(totalPrice);
    }

    changePrice();

    $scope.isAgree = false;
    $scope.agree = function () {
        $scope.isAgree = !$scope.isAgree;
    };

    $scope.getbox1 = function (tourist) {
        tourist.hasChild = !tourist.hasChild;
    };

    $scope.getbox2 = function (ticket, index) {
        ticket.selected = index;
        changePrice();
    };

    $scope.submitOrder = function () {
        if (!$scope.isAgree) {
            bootbox.alert("请同意条款");
            return;
        }
        if ($scope.totalPrice <= 0) {
            bootbox.alert("请选择票型");
            return;
        }
        var ticketList = [];
        angular.forEach($scope.touristList, function (tourist) {
            var tic = tourist.ticket;
            if ($.isEmptyObject(tic)) {
                return true;
            }
            var ticket = {
                ticketId: tic.id,
                ticketName: tic.name,
                price: tic.price,
                number: tic.number,
                name: tourist.name,
                idnumber: tourist.idNumber,
                mobile: tourist.telephone
            };
            if (tourist.idType == "IDCARD") {
                ticket.idType = "ID_CARD";
            } else {
                ticket.idType = "OTHER";
            }
            ticketList.push(ticket);
        });
        if (ticketList.length == 0) {
            bootbox.alert("请选择票型");
            return;
        }
        var jsonObj = {
            dailyFlightGid: $scope.ticket.dailyFlightGid,
            flightNumber: $scope.ticket.number,
            flightLineName: $scope.flightLine.namea + "-" + $scope.flightLine.nameb,
            departTime: $scope.ticket.departTime,
            amount: $scope.totalPrice,
            seat: ticketList.length,
            ferryOrderItemList: ticketList
        };
        $rootScope.loading = true;
        $http.post(yhyUrl.ferrySaveOrder, {
            json: JSON.stringify(jsonObj)
        }).success(function (data) {
            $rootScope.loading = false;
            if (!Check.loginCheck(data)) {
                return;
            }
            if (data.success) {
                $state.go("ferry_firmorder", {orderId: data.orderId});
            } else {
                if (!data.noMember && !data.noReal) {
                    bootbox.alert(data.errMsg);
                    return;
                }
                storage.set(yhyKey.ferryOrder, jsonObj);
                storage.set(yhyKey.preUrl, "ferryOrder");
                if (data.noMember) {
                    $state.go("ferryRealname");
                }
                if (data.noReal) {
                    $state.go("ferryDoRealname");
                }
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };
    $scope.ticketType = ['往返全价票50', '往返残军半价票18', '往返儿童半价票18'];
    $scope.ticketListbox = false;
    $scope.ferryshadow = false;
    $scope.oneTicket = '往返全价票50';
    $scope.ticketbox = function (tourist) {
        $scope.changingTourist = tourist;
        $scope.ticketListbox = true;
        $scope.ferryshadow = true;
        $('body').css({'overflow': 'hidden'})
    };
    $scope.shutferrybox = function () {
        $scope.changingTourist = null;
        $scope.ticketListbox = false;
        $scope.ferryshadow = false;
        $('body').css({'overflow': 'auto'})
    };
    $scope.select_p = function (tic) {
        $scope.changingTourist.ticket = tic;
        $scope.ticketListbox = false;
        $scope.ferryshadow = false;
        changePrice();
        $('body').css({'overflow': 'auto'})
    };

});
//航线查询
ferryModule.controller('ferrySearch', function ($scope, $state, storage) {
    $scope.ferryListParams = storage.get(yhyKey.ferryListParams);
    if ($.isEmptyObject($scope.ferryListParams)) {
        $scope.ferryListParams = {};
    }

    if ($scope.ferryListParams.date == null || $scope.ferryListParams.date == "" || new Date($scope.ferryListParams.date).getTime() < new Date(new Date().format("yyyy-MM-dd")).getTime()) {
        $scope.ferryListParams.date = new Date().format("yyyy-MM-dd");
    }

    $scope.passId = function (id) {
        $scope.ljId = id;
    };

    /*驴记数据列表*/
    $scope.lvji = {
        data: [
            {
                id: 1, name: "鼓浪屿景区", price: 16, popStatus: false, selectStatus: false
            },
            {
                id: 2, name: "湖里山炮台", price: 2, popStatus: false, selectStatus: false
            },
            {
                id: 3, name: "嘉庚公园景区", price: 2, popStatus: false, selectStatus: false
            }
        ],
        method: {
            initStatus: function () {
                $scope.lvji.data[0].selectStatus = true;
            },
            popSwitch: function (id) {
                angular.forEach($scope.lvji.data, function (item) {
                    if (item.id == id) {
                        if (item.popStatus) {
                            item.popStatus = false;
                            $("body,html").css({
                                "height": 'auto',
                                "overflow": "auto"
                            });
                        } else {
                            item.popStatus = true;
                            $("body,html").css({
                                "height": '100%',
                                "overflow": "hidden"
                            });
                        }
                    }
                });
            },
            selectSwitch: function (id) {
                angular.forEach($scope.lvji.data, function (item) {
                    item.selectStatus = false;
                });
                angular.forEach($scope.lvji.data, function (item) {
                    if (item.id == id) {
                        $scope.ferryListParams.flightLine = item;
                        item.selectStatus = true;
                    }
                });
            }
        }

    };

    /*初始化选中状态*/
    $scope.lvji.method.initStatus();


    $scope.ferryLineList = [
        {
            index: 0,
            img: '1',
            namea: "邮轮中心厦鼓码头",
            nameb: "三丘田码头(热门)",
            departPort: '邮轮中心厦鼓码头',
            arrivePort: '三丘田码头',
            number: "9F3A7072E74142D7A41D999318818922"
        },
        {
            index: 1,
            img: '2',
            namea: "邮轮中心厦鼓码头",
            nameb: "内厝澳码头(普通)",
            departPort: '邮轮中心厦鼓码头',
            arrivePort: '内厝奥码头',
            number: "4A3BA22E8D6A4CD3B54DCD52A7E1E258"
        },
        {
            index: 2,
            img: '3',
            namea: "邮轮中心厦鼓码头",
            nameb: "内厝澳码头(豪华)",
            departPort: '邮轮中心厦鼓码头',
            arrivePort: '内厝奥码头',
            number: "167B6542C83A4F94871B54DADCE049E9",
        },
        {
            index: 3,
            img: '4',
            namea: "海沧嵩屿码头",
            nameb: "内厝澳码头(海沧)",
            departPort: '嵩鼓码头（嵩屿）',
            arrivePort: '内厝澳码头',
            number: "8ADC5A482FE04AE1A9B8613179005090"
        },
        {
            index: 4,
            img: '5',
            namea: "厦门轮渡码头",
            nameb: "三丘田码头(夜航)",
            departPort: '厦门轮渡码头',
            arrivePort: '三丘田码头',
            number: "2BB589AD46C04BCE88B74AEBC387F1D3"
        }
    ];
    $scope.ferryListParams.flightLine = null;
    if ($.isEmptyObject($scope.ferryListParams.flightLine)) {
        $scope.ferryListParams.flightLine = $scope.lvji.data[0];
    }

    $scope.selectProtLine = function (flightLine) {

        angular.forEach($scope.lvji.data, function (item) {
            item.selectStatus = false;
        });

        $scope.ferryListParams.flightLine = flightLine;
    };

    $scope.submit = function () {
        if ($scope.ferryListParams.flightLine.id) {
            storage.set(yhyKey.lvjiList, $scope.ferryListParams.flightLine);
            $state.go("lvjiOrder", {num: 1});
        } else {
            storage.set(yhyKey.ferryListParams, $scope.ferryListParams);
            $state.go("ferryList");
        }
    };
    $scope.submit2 = function (id) {
        switch (id) {
            case 1:
                storage.set(yhyKey.lvjiList, $scope.lvji.data[0]);
                $state.go("lvjiOrder", {num: 2});
                break;

            case 2:
                storage.set(yhyKey.lvjiList, $scope.lvji.data[1]);
                $state.go("lvjiOrder", {num: 2});
                break;

            case 3:
                storage.set(yhyKey.lvjiList, $scope.lvji.data[2]);
                $state.go("lvjiOrder", {num: 2});
                break;

            default:
                storage.set(yhyKey.lvjiList, $scope.lvji.data[0]);
                $state.go("lvjiOrder", {num: 2});
        }
    };


    var wheight = window.screen.availHeight;
    $('.shadow_map').css({'height': wheight});
    $('.maxshow img').css({'bottom': '0'});
    $scope.amap = -1;
    $scope.dark = false;
    $scope.enlarge = function (num) {
        $scope.amap = num;
        $scope.dark = true;
        $('.GLYmap').hide();
        $('.youlunsearch .choseline').hide();
        //scroll(0, 0);
    };

    $scope.lessen = function () {
        $scope.amap = 0;
        $scope.dark = false;
        $('.GLYmap').show();
        $('.youlunsearch .choseline').show();
    }
});

//选择乘客
ferryModule.controller('ferryVisitorCtrl', function ($scope, $http, $state, storage, Check) {
    $scope.selectedTouristList = storage.get(yhyKey.ferrySelectedTourist);
    if ($scope.selectedTouristList == null) {
        $scope.selectedTouristList = [];
    }

    $scope.preUrl = storage.get(yhyKey.preUrl);
    storage.remove(yhyKey.preUrl);
    if ($scope.preUrl == null || $scope.preUrl == "") {
        $scope.preUrl = "ferrySearch";
    }

    $scope.showSync = false;

    $http.post(yhyUrl.personalInfo).success(function (data) {
        if (data.success) {
            $scope.showSync = data.user.isReal;
        }
    });

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

    //获取常用联系人列表
    $scope.getTouristList = function () {
        $http.post(yhyUrl.checkReal).success(function (result) {
            if (!Check.loginCheck(result)) {
                return;
            }
            if (result.success) {
                $http.post(yhyUrl.touristList).success(
                    function (data) {
                        if (Check.loginCheck(data)) {
                            if (data.success) {
                                var touristList = [];
                                angular.forEach(data.touristList, function (tourist) {
                                    if (tourist.peopleType == "ADULT") {
                                        tourist.showType = "成人";
                                    } else {
                                        tourist.showType = "儿童";
                                    }
                                    tourist.selected = false;
                                    angular.forEach($scope.selectedTouristList, function (select) {
                                        if (select.touristId == tourist.touristId) {
                                            tourist.selected = true;
                                        }
                                    });
                                    touristList.push(tourist);
                                });
                                $scope.touristList = touristList;
                                $scope.search();
                            }
                        }
                    }
                );
            } else {
                if (!result.noMember && !result.noReal) {
                    bootbox.alert(result.errMsg);
                    return;
                }
                storage.set(yhyKey.preUrl, "ferryVisitor");
                if (result.noMember) {
                    $state.go("ferryRealname");
                }
                if (result.noReal) {
                    $state.go("ferryDoRealname");
                }
            }
        });
    };
    $scope.getTouristList();

    $scope.selectTourist = function (tourist) {
        var index = -1;
        if (tourist.selected) {
            angular.forEach($scope.selectedTouristList, function (select, i) {
                if (select.touristId == tourist.touristId) {
                    index = i;
                    return false;
                }
            });
            if (index > -1) {
                $scope.selectedTouristList.splice(index, 1);
            }
        } else {
            angular.forEach($scope.selectedTouristList, function (select, i) {
                if (select.touristId == tourist.touristId) {
                    index = i;
                    return false;
                }
            });
            if (index < 0) {
                if ($scope.selectedTouristList.length >= 5) {
                    bootbox.alert("最多只能选择5个旅客");
                    return;
                }
                $scope.selectedTouristList.push(tourist);
            }
        }
        tourist.selected = !tourist.selected;
    };

    $scope.subVisitor = function () {
        if ($scope.selectedTouristList.length == 0) {
            bootbox.alert("请选择旅客");
            return;
        }
        storage.set(yhyKey.ferrySelectedTourist, $scope.selectedTouristList);
        $state.go("ferryOrder");
    };

    $scope.syncTourist = function () {
        $http.post(yhyUrl.syncTourist).success(function (data) {
            if (data.success) {
                bootbox.alert("同步完成", $scope.getTouristList);
            }
        });
    }
});

//新增乘客
ferryModule.controller('ferryVisitorEditCtrl', function ($scope, $http, $state, IdCardUtil, $rootScope) {

    $scope.ferryVeditorDrak = false;
    $scope.select_type_show = false;


    $scope.name = '';
    $scope.gender = 'male';
    $scope.idNumber = '';
    $scope.peopleType = 'ADULT';
    $scope.telephone = '';
    $scope.touristId = '';
    $scope.email = '';
    $scope.idType = 'IDCARD';
    $scope.idTypeName = '身份证';

    $scope.isIdCard = true;
    $scope.isJunIdCard = false;


    $scope.initIdType = [
        {type: 'IDCARD', name: '身份证'},
        {type: 'STUDENTCARD', name: '学生证'},
        {type: 'PASSPORT', name: '护照'},
        {type: 'JUNIDCARD', name: '军官证'}
    ];


    $scope.validIdNumberReg = yhyKey.idCardReg;
    $scope.telephoneReg = yhyKey.telephone;

    $scope.onClickType = function () {
        $scope.ferryVeditorDrak = true;
        $scope.select_type_show = true;
    }
    $scope.selectIdType = function (type, name) {
        $scope.idType = type;
        $scope.idTypeName = name;
        $scope.idNumber = '';
        if (type == 'IDCARD') {
            $scope.isIdCard = true;
            $scope.validIdNumberReg = yhyKey.idCardReg;
            $scope.isJunIdCard = false;
        } else if (type == 'JUNIDCARD') {
            $scope.isIdCard = false;
            $scope.validIdNumberReg = yhyKey.junNumberReg;
            $scope.isJunIdCard = true;
        } else {
            $scope.isIdCard = true;
            $scope.validIdNumberReg = yhyKey.idCardReg;
            $scope.isJunIdCard = false;
        }
        $scope.hideDark();
    }

    $scope.hideDark = function () {
        $scope.ferryVeditorDrak = false;
        $scope.select_type_show = false;
    }

    $scope.checkGender = function () {
        if ($scope.idType == 'IDCARD') {
            if ($scope.visitorForm.idNumber.$valid) {
                $scope.gender = IdCardUtil.getSex($scope.idNumber);
            }
        }
    }


    $scope.saveTourist = function () {


        if (!$scope.visitorForm.name.$valid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请正确输入姓名'
            })
            return;
        }
        if (!$scope.visitorForm.idNumber.$valid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请输入正确的证件号码'
            })
            return;
        }
        if (!$scope.visitorForm.telephone.$valid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请输入正确的手机号码'
            })
            return;
        }
        if (!$scope.visitorForm.email.$valid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请输入正确的电子邮件'
            })
            return;
        }


        $scope.peopleType = IdCardUtil.getPeopleType($scope.idNumber);

        var tempParams = {
            gender: $scope.gender,
            idNumber: $scope.idNumber,
            idType: $scope.idType,
            name: $scope.name,
            peopleType: $scope.peopleType,
            telephone: $scope.telephone,
            email: $scope.email
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.saveTourist,
            {
                json: JSON.stringify(tempParams)
            }
        ).success(
            function (data) {
                $rootScope.loading = false;
                if (data.success) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: '添加成功'
                    })
                    $state.go("ferryVisitor");
                }
            }
        ).error(
            function () {
                $rootScope.loading = false;
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '系统错误'
                })
            }
        );
    }
});

ferryModule.controller("ferryRealnameCtrl", function ($scope, $http, $state, storage, $rootScope, Check, idCard) {
    storage.remove(yhyKey.ferryMember);
    $scope.member = {
        idTypeName: "ID_CARD"
    };

    $rootScope.loading = true;
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        $rootScope.loading = false;
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.member.trueName = data.user.userName;
            $scope.member.idnumber = data.user.idNumber;
            $scope.member.mobile = data.user.telephone;
            $scope.member.bankNo = data.user.bankNo;
            $scope.member.email = data.user.email;
            //$scope.submitReal(false);
        }
    }).error(function () {
        $rootScope.loading = false;
    });

    $scope.submitReal = function (isAlert) {
        if ($scope.member.trueName == null || $scope.member.trueName == "") {
            if (isAlert) {
                bootbox.alert("请输入姓名");
            }
            return;
        }
        if (!$scope.member.trueName.match(yhyKey.nameReg) || $scope.member.trueName.length > 10) {
            if (isAlert) {
                bootbox.alert("姓名格式错误");
            }
            return;
        }
        if ($scope.member.idnumber == null || $scope.member.idnumber == "") {
            if (isAlert) {
                bootbox.alert("请输入身份证号");
            }
            return;
        }
        var msg = idCard.checkIdcard($scope.member.idnumber);
        if (msg != "验证通过!") {
            if (isAlert) {
                bootbox.alert(msg);
            }
            return;
        }
        if ($scope.member.mobile == null || $scope.member.mobile == "") {
            if (isAlert) {
                bootbox.alert("请输入手机号");
            }
            return;
        }
        if (!$scope.member.mobile.match(yhyKey.telephoneReg)) {
            if (isAlert) {
                bootbox.alert("手机号格式错误");
            }
            return;
        }
        if ($scope.member.email == null || $scope.member.email == "") {
            if (isAlert) {
                bootbox.alert("请输入邮箱");
            }
            return;
        }
        if (!$scope.member.email.match(yhyKey.emailReg)) {
            if (isAlert) {
                bootbox.alert("邮箱格式错误");
            }
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.ferryQueryReal, {
            json: JSON.stringify($scope.member)
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                $scope.member.name = data.name;
                $scope.member.isReal = data.isReal;
                storage.set(yhyKey.ferryMember, $scope.member);
                if (data.isReal) {
                    $state.go("ferryLogin");
                } else {
                    location.href = data.url;
                }
            } else {
                bootbox.alert(data.errMsg);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };
});

ferryModule.controller("ferryDoRealnameCtrl", function ($scope, $http, $state, storage, $rootScope, Check, idCard) {
    storage.remove(yhyKey.ferryMember);
    $scope.member = {
        idTypeName: "ID_CARD"
    };

    $rootScope.loading = true;
    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        $rootScope.loading = false;
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.member.trueName = data.user.userName;
            $scope.member.idnumber = data.user.idNumber;
            $scope.member.mobile = data.user.telephone;
            $scope.member.bankNo = data.user.bankNo;
            $scope.submitReal(false);
        }
    }).error(function () {
        $rootScope.loading = false;
    });

    $scope.submitReal = function (isAlert) {
        if ($scope.member.trueName == null || $scope.member.trueName == "") {
            if (isAlert) {
                bootbox.alert("请输入姓名");
            }
            return;
        }
        if (!$scope.member.trueName.match(yhyKey.nameReg) || $scope.member.trueName.length > 10) {
            if (isAlert) {
                bootbox.alert("姓名格式错误");
            }
            return;
        }
        if ($scope.member.idnumber == null || $scope.member.idnumber == "") {
            if (isAlert) {
                bootbox.alert("请输入身份证号");
            }
            return;
        }
        var msg = idCard.checkIdcard($scope.member.idnumber);
        if (msg != "验证通过!") {
            if (isAlert) {
                bootbox.alert(msg);
            }
            return;
        }
        if ($scope.member.mobile == null || $scope.member.mobile == "") {
            if (isAlert) {
                bootbox.alert("请输入手机号");
            }
            return;
        }
        if (!$scope.member.mobile.match(yhyKey.telephoneReg)) {
            if (isAlert) {
                bootbox.alert("手机号格式错误");
            }
            return;
        }
        if ($scope.member.bankNo == null || $scope.member.bankNo == "") {
            if (isAlert) {
                bootbox.alert("请输入银行卡号");
            }
            return;
        }
        if (!$scope.member.bankNo.match(yhyKey.bankNoReg)) {
            if (isAlert) {
                bootbox.alert("银行卡号格式错误");
            }
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.ferryDoRealname, {
            json: JSON.stringify($scope.member)
        }).success(function (data) {
            if (data.success && data.isReal) {
                //if (!$.isEmptyObject(storage.get(yhyKey.ferryOrder))) {
                //    $http.post(yhyUrl.ferrySaveOrder, {
                //        json: JSON.stringify(storage.get(yhyKey.ferryOrder))
                //    }).success(function (data) {
                //        $rootScope.loading = false;
                //        if (!Check.loginCheck(data)) {
                //            return;
                //        }
                //        if (data.success) {
                //            storage.remove(yhyKey.ferryOrder);
                //            $state.go("ferry_firmorder", {orderId: data.orderId});
                //        } else {
                //            bootbox.alert(data.errMsg);
                //        }
                //    }).error(function () {
                //        $rootScope.loading = false;
                //    });
                //} else {
                $rootScope.loading = false;
                var preUrl = storage.get(yhyKey.preUrl);
                storage.remove(yhyKey.preUrl);
                $state.go(preUrl);
                //}
            } else {
                $rootScope.loading = false;
                bootbox.alert(data.errMsg);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };
});

ferryModule.controller("ferryLoginCtrl", function ($scope, $http, storage, $state, $rootScope, Check) {
    $scope.member = storage.get(yhyKey.ferryMember);

    $http.post(yhyUrl.checkLogin, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
    });

    if ($.isEmptyObject($scope.member)) {
        history.back();
    }

    $scope.login = function () {
        if ($scope.password == null) {
            bootbox.alert("请输入密码");
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.ferryLogin, {
            password: $scope.password
        }).success(function (data) {
            if (data.success) {
                //if (!$.isEmptyObject(storage.get(yhyKey.ferryOrder))) {
                //    $http.post(yhyUrl.ferrySaveOrder, {
                //        json: JSON.stringify(storage.get(yhyKey.ferryOrder))
                //    }).success(function (data) {
                //        $rootScope.loading = false;
                //        if (!Check.loginCheck(data)) {
                //            return;
                //        }
                //        if (data.success) {
                //            storage.remove(yhyKey.ferryOrder);
                //            $state.go("ferry_firmorder", {orderId: data.orderId});
                //        } else {
                //            bootbox.alert(data.errMsg);
                //        }
                //    }).error(function () {
                //        $rootScope.loading = false;
                //    });
                //} else {
                $rootScope.loading = false;
                var preUrl = storage.get(yhyKey.preUrl);
                storage.remove(yhyKey.preUrl);
                $state.go(preUrl);
                //}
            } else {
                $rootScope.loading = false;
                bootbox.alert(data.errMsg);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };
});

ferryModule.controller("ferryRegisterCtrl", function ($scope, $http, storage, $state, $rootScope, Check) {
    $scope.member = storage.get(yhyKey.ferryMember);
    if ($.isEmptyObject($scope.member)) {
        history.back();
    }
    $scope.rePassword = "";

    $scope.register = function () {
        if ($scope.member.password == null) {
            bootbox.alert("请输入密码");
            return;
        }
        if (!$scope.member.password.match(yhyKey.passwordReg)) {
            bootbox.alert("密码格式错误");
            return;
        }
        if ($scope.member.password != $scope.rePassword) {
            bootbox.alert("两次输入的密码不一样");
            return;
        }
        if ($scope.member.bankNo == null || $scope.member.bankNo == "") {
            bootbox.alert("请输入银行卡号");
            return;
        }
        if (!$scope.member.bankNo.match(yhyKey.bankNoReg)) {
            bootbox.alert("银行卡号格式错误");
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.ferryRegister, {
            json: JSON.stringify($scope.member)
        }).success(function (data) {
            if (data.success && data.isReal) {
                //if (!$.isEmptyObject(storage.get(yhyKey.ferryOrder))) {
                //    $http.post(yhyUrl.ferrySaveOrder, {
                //        json: JSON.stringify(storage.get(yhyKey.ferryOrder))
                //    }).success(function (data) {
                //        $rootScope.loading = false;
                //        if (!Check.loginCheck(data)) {
                //            return;
                //        }
                //        if (data.success) {
                //            storage.remove(yhyKey.ferryOrder);
                //            $state.go("ferry_firmorder", {orderId: data.orderId});
                //        } else {
                //            bootbox.alert(data.errMsg);
                //        }
                //    }).error(function () {
                //        $rootScope.loading = false;
                //    });
                //} else {
                $rootScope.loading = false;
                var preUrl = storage.get(yhyKey.preUrl);
                storage.remove(yhyKey.preUrl);
                $state.go(preUrl);
                //}
            } else if (data.success && !data.isReal) {
                $rootScope.loading = false;
                $state.go("ferryDoRealname");
            } else {
                $rootScope.loading = false;
                bootbox.alert(data.errMsg);
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    }
});

ferryModule.controller("ferryRegisterBackCtrl", function ($scope, storage, $state) {
    var preUrl = storage.get(yhyKey.preUrl);
    if (preUrl == null || preUrl == "") {
        $state.go("personalInfo");
    } else {
        $state.go(preUrl);
    }
});

ferryModule.controller("ferryOrderDetailCtrl", function ($scope, $http, $stateParams, Check, $state, storage, $rootScope) {
    $scope.orderId = $stateParams.orderId;
    if ($scope.orderId == null || $scope.orderId == "") {
        history.back();
        return;
    }

    $rootScope.loading = true;
    $http.post(yhyUrl.ferryOrderDetail, {
        orderId: $scope.orderId
    }).success(function (data) {
        $rootScope.loading = false;
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.order = data.order;
        }
    }).error(function () {
        $rootScope.loading = false;
    });

    $scope.back = function () {
        var url = storage.get(yhyKey.preUrl);
        storage.remove(yhyKey.preUrl);
        if ("personalOrder" == url) {
            history.back();
        } else {
            $state.go("personalOrder", {index: 0});
        }
    };

    $scope.cancel = function () {
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
                    $http.post(yhyUrl.ferryCancelDetail, {
                        orderId: $scope.orderId
                    }).success(function (data) {
                        if (data.success) {
                            bootbox.alert("订单取消成功");
                            $rootScope.loading = true;
                            $http.post(yhyUrl.ferryOrderDetail, {
                                orderId: $scope.orderId
                            }).success(function (data) {
                                $rootScope.loading = false;
                                if (!Check.loginCheck(data)) {
                                    return;
                                }
                                if (data.success) {
                                    $scope.order = data.order;
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

    $scope.returnOrder = function () {
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
                    $http.post(yhyUrl.ferryRefundOrder, {
                        orderId: $scope.orderId
                    }).success(function (data) {
                        $rootScope.loading = false;
                        if (!Check.loginCheck(data)) {
                            return;
                        }
                        if (data.success) {
                            $scope.order = data.order;
                        } else {
                            bootbox.alert(data.errorMsg);
                        }
                    }).error(function () {
                        $rootScope.loading = false;
                    });
                }
            }
        });
    };
});

ferryModule.controller('ferryfirmOrderCtrl', function ($scope, $stateParams, $http, Check, $state, Wechatpay, $rootScope, $interval, storage) {
    $scope.orderId = $stateParams.orderId;
    $scope.selectedType = 1;
    $scope.get = function (num) {
        $scope.selectedType = num;
    };

    $http.post(yhyUrl.personalInfo, {}).success(function (data) {
        if (!Check.loginCheck(data)) {
            return;
        }
        if (data.success) {
            $scope.balance = data.user.balance;
            $scope.hasPayPassword = data.user.hasPayPassword;
            $http.post(yhyUrl.ferryOrderDetail, {
                orderId: $scope.orderId
            }).success(function (data) {
                if (!Check.loginCheck(data)) {
                    return;
                }
                if (data.success) {
                    $scope.order = data.order;
                    if (data.order.orderStatus != "WAIT") {
                        bootbox.alert("订单不处于待支付状态", function () {
                            $scope.orderDetail();
                        });
                    }
                    if ($scope.order.price > $scope.balance) {
                        $scope.selectedType = 2;
                    }
                    var seconds = data.order.waitTime;
                    $scope.timer = $interval(function () {
                        if (seconds <= 0) {
                            $interval.cancel($scope.timer);
                            $scope.orderDetail();
                        }
                        $scope.minute = Math.floor(seconds / 60);
                        $scope.second = Math.floor(seconds % 60);
                        seconds--;
                    }, 1000);
                }
            });
        }
    });

    $scope.back = function () {
        $interval.cancel($scope.timer);
        history.back();
    };

    $scope.pay = function () {
        if ($scope.selectedType == 1) {
            if ($scope.hasPayPassword) {
                $scope.isBalancePay = true;
                $scope.payPassword = "";
            } else {
                bootbox.alert("还未设置支付密码", function () {
                    $state.go("personalChangePayPassword");
                });
            }
        } else if ($scope.selectedType == 2) {
            Wechatpay.payOrderWithBack($scope.order.id, $scope.order.type, Wechatpay.paySuccess);
        }
    };

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
            orderId: $scope.order.id,
            orderType: $scope.order.type
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                bootbox.alert("支付成功", $scope.orderDetail);
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

    $scope.closePWinput = function () {
        $scope.isBalancePay = false;
    };

    $scope.orderDetail = function () {
        storage.remove(yhyKey.preUrl);
        $state.go("ferryOrderDetail", {orderId: $scope.orderId});
    };

    $scope.cancel = function () {
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
                    $http.post(yhyUrl.ferryCancelDetail, {
                        orderId: $scope.order.id
                    }).success(function (data) {
                        if (data.success) {
                            bootbox.alert("订单取消成功", $scope.orderDetail);
                        } else {
                            bootbox.alert(data.errMsg);
                        }
                    });
                }
            }
        });
    };
});