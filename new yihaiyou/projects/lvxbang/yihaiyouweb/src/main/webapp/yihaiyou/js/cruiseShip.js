/**
 * Created by Think on 2017/8/1.
 */
var cruiseShip = angular.module('cruiseShip', ['infinite-scroll']);
cruiseShip.controller("orderKnowCtrl", function ($scope, $http, $state, $stateParams) {

    var str = $stateParams.dateId;
    $scope.dateId = str.split("-")[0];
    $scope.mark = str.split("-")[1];
    $http.post(yhyUrl.shipDetail, {dateId: $scope.dateId}).success(function (data) {
        if (data.success) {
            $scope.orderKnow = data.ship.extend.orderKnow;
            $scope.payWay = data.ship.extend.payWay;
            $scope.ship = data.ship;
        }
    });
    $scope.transf = function (id){
        if(id == 1){
            $scope.mark = 1;
        }else{
            $scope.mark = 2;
        }
    }
});

cruiseShip.controller("routeDetails", function ($scope, $http, $state, $stateParams) {
    $scope.dateId = $stateParams.dateId;

    $http.post(yhyUrl.shipDetail, {dateId: $scope.dateId}).success(function (data) {
        if (data.success) {
            $scope.orderKnow = data.ship.extend.orderKnow;
            $scope.payWay = data.ship.extend.payWay;
            $scope.day = data.ship.date.date;
            $scope.planDays = data.ship.planDays;

            var beginDay = new Date($scope.day);
            for (var i = 0; i < $scope.planDays.length; i++) {
                beginDay.setDate(beginDay.getDate() + 1);
                $scope.planDays[i].beginYear = beginDay.format("yyyy");
                $scope.planDays[i].beginMonth = beginDay.format("MM");
                $scope.planDays[i].beginDay = beginDay.format("dd");
            }
        }
    });
});

cruiseShip.controller("cruiseShipList", function ($scope, $http, $state, $stateParams) {
    $scope.pageNo = 1;
    $scope.pageSize = 5;
    $http.post(yhyUrl.shipList, {pageNo: $scope.pageNo, pageSize: $scope.pageSize}).success(function (data) {
        if (data.success) {
            $scope.shipList = data.shipList;
        }
    });

    /*导航状态切换*/
    $scope.navStatus1 = true;
    $scope.navStatus2 = false;
    $scope.switchNav = function(condition){
        switch (condition){
            case 1:
                $scope.navStatus1 = true;
                $scope.navStatus2 = false;
                break;
            case 2:
                $scope.navStatus1 = false;
                $scope.navStatus2 = true;
        }
    }

});

cruiseShip.controller("shipList", function ($scope, $http, $state, storage,  $stateParams, $rootScope) {

    /*弹窗状态初始化*/
    $scope.popStatus = false;
    /*弹窗左边tap状态*/
    $scope.routerStatus = true;
    $scope.brandStatus = false;
    $scope.departTimeStatus = false;
    /*弹窗右边状态*/
    $scope.routercontent = true;
    $scope.brandContent = false;
    $scope.departTimeContent = false;

    /*弹窗选择筛选条件*/
    /*航线数据*/
    //$scope.routerList = [
    //    {id:0,value:'不限航线',status:'active'},
    //    {id:1,value:'东南亚风情',status:'default'},
    //    {id:2,value:'日韩之旅',status:'default'},
    //    {id:3,value:'国内航线',status:'default'}
    //];
    /*品牌数据*/
    //$scope.brandList = [
    //    {id:0,value:'不限品牌',status:'active'},
    //    {id:1,value:'歌诗达',status:'default'},
    //    {id:2,value:'维京',status:'default'},
    //];
    /*航线和品牌的参数值*/
    $scope.routerVal = 0;
    $scope.brandVal = 0;
    /*弹窗显示/隐藏事件*/
    $scope.switchPop = function () {
        if ($scope.popStatus) {
            $scope.popStatus = false;
        } else {
            $scope.popStatus = true;
        }
    }

    /*确定 弹窗显示/隐藏事件*/
    $scope.switchPopY = function () {
        if ($scope.popStatus) {
            $scope.popStatus = false;
        } else {
            $scope.popStatus = true;
        }
        $scope.showList1 = $scope.routeId;
        $scope.showList2 = $scope.brandId;
        $scope.init();
        $scope.showList();


    }
    /*取消 弹窗显示/隐藏事件*/
    $scope.switchPopN = function () {
        if ($scope.popStatus) {
            $scope.popStatus = false;
        } else {
            $scope.popStatus = true;
        }
        $scope.routeId = $scope.showList1;
        $scope.brandId = $scope.showList2;

    }
    /*tap切换事件*/
    $scope.switchTab = function (tab) {
        $scope.routerStatus = false;
        $scope.brandStatus = false;
        $scope.departTimeStatus = false;
        switch (tab) {
            case 1:
                $scope.routerStatus = true;
                $scope.brandStatus = false;
                $scope.departTimeStatus = false;
                $scope.routercontent = true;
                $scope.brandContent = false;
                $scope.departTimeContent = false;
                break;
            case 2:
                $scope.routerStatus = false;
                $scope.brandStatus = true;
                $scope.departTimeStatus = false;
                $scope.routercontent = false;
                $scope.brandContent = true;
                $scope.departTimeContent = false;
                break;
            case 3:
                $scope.routerStatus = false;
                $scope.brandStatus = false;
                $scope.departTimeStatus = true;
                $scope.routercontent = false;
                $scope.brandContent = false;
                $scope.departTimeContent = true;
                break;
        }
    }
    /*选择航线事件*/
    $scope.chooseRouter = function (id) {
        $scope.routerVal = id;
        angular.forEach($scope.routerList, function (item) {
            item.status = "default";
        });
        angular.forEach($scope.routerList, function (item) {
            if (item.id == id) {
                item.status = "active";
            }
        });
    }

    //选择航线（2）
    $scope.showlist1 = function (num) {
        $scope.routeId = num;
        //    $scope.init();
        //   $scope.showList();
    };

    $scope.init = function () {
        $scope.pageNo = 1;
        $scope.ships = [];
        $scope.nomore = false;
    };

    /*选择品牌事件*/
    $scope.chooseBrand = function (id) {
        $scope.brandVal = id;
        angular.forEach($scope.brandList, function (item) {
            item.status = "default";
        });
        angular.forEach($scope.brandList, function (item) {
            if (item.id == id) {
                item.status = 'active';
            }
        });
    }

    //选择品牌
    $scope.showlist2 = function (num) {
        // $scope.showList2 = num;
        $scope.brandId = num;
        //$scope.init();
        //$scope.showList();
    };


    $scope.pageNo = 1;
    $scope.pageSize = 5;
    $scope.cruiseList = [];
    $rootScope.loading = false;
    $scope.sortName = null;
    $scope.sortOrder = null;

    $scope.brands = [];
    $scope.routes = [];
    $scope.hotBrands = [];
    $scope.hotRoutes = [];

    //查询
    $scope.shipList = function () {
        if ($rootScope.loading) {
            return;
        }
        if ($scope.nomore) {
            return;
        }
        $rootScope.loading = true;
        $http.post(yhyUrl.shipList, {
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize,
            sortName: $scope.sortName,
            sortOrder: $scope.sortOrder,
            brandId: $scope.showList2,
            routeId: $scope.showList1,
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.shipList, function (ship) {
                    $scope.cruiseList.push(ship);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++;
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    }
    //排序
    $scope.sortArr = [
        {type:'minSalePrice',classStatus:'sort-up'},
        {type:'date',classStatus:'sort-up'}
    ];
    $scope.showList = function (num, sortName, sortOrder) {

        angular.forEach($scope.sortArr,function(item){
            if(item.type == sortName ){
                if(item.classStatus == 'sort-up'){
                    item.classStatus = 'sort-down';
                }else{
                    item.classStatus = 'sort-up';
                }
            }
        });


        if ($scope.sortName == sortName) {
            if ($scope.sortOrder == "asc") {
                $scope.sortOrder = "desc";
            } else if ($scope.sortOrder == "desc") {
                $scope.sortOrder = "asc";
            }
        } else {
            $scope.sortName = sortName;
            $scope.sortOrder = sortOrder;
        }
        $scope.pageNo = 1;
        $scope.nomore = false;
        $scope.cruiseList = [];
        $scope.listNum = num;
        $rootScope.loading = true;
        $http.post(yhyUrl.shipList,
            {
                pageNo: $scope.pageNo,
                pageSize: $scope.pageSize,
                sortName: $scope.sortName,
                sortOrder: $scope.sortOrder,
                brandId: $scope.showList2,
                routeId: $scope.showList1,
                startTime: $scope.startTime
            }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.shipList, function (ship) {
                    $scope.cruiseList.push(ship);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++;
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    //品牌列表
    $http.post(yhyUrl.shipBrand, {}).success(function (data) {
        if (data.success) {
            $scope.brands = data.brands;
            $scope.hotBrands = data.brands;
            if ($scope.brand != null && $scope.brand > 0) {
                angular.forEach($scope.brands, function (brand) {
                    if (brand.id == $scope.request.brand) {
                        $scope.showList2 = brand.id;
                    }
                });
            }
        }
    });

    //航线列表
    $http.post(yhyUrl.shipRoute, {}).success(function (data) {
        if (data.success) {
            $scope.routes = data.routes;
            $scope.hotRoutes = data.routes;
            if ($scope.route != null && $scope.route > 0) {
                angular.forEach($scope.routes, function (route) {
                    if (route.id == $scope.request.route) {
                        $scope.showList1 = route.id;

                    }
                });
            }
        }
    });
    var date = new Date();
    var limit = date.format("yyyy-MM-dd");
    date.setDate(date.getDate() + 365);
    limit += "," + date.format("yyyy-MM-dd");
    $("#time").attr("data-lcalendar", limit);
    var calendardatetime = new lCalendar();
    calendardatetime.init({
        'trigger': '#time',
        'type': 'date'
    });
});


//邮轮详情
cruiseShip.controller("cruiseDetails", function ($scope, $http, storage, $state, $stateParams,$rootScope) {

    /*图片的全局地址*/
    $scope.IMG_DOMAIN = QINIU_BUCKET_URL;

    /*数据*/
    $scope.data = {
        /*舱房类型数据*/
        cabinClassify: [
            $scope.roomTypes
        ],
        /*bannerImgList: [
            {id: 1, imgUrl: '../image/banner01.png'},
            {id: 2, imgUrl: '../image/banner02.png'},
            {id: 3, imgUrl: '../image/banner03.png'}
        ]*/

        //cabinClassify:[
        //    {id:1,cabinListStatus:true,cabinName:"内舱房",roomList:[{roomId:1,roomName:"随机内舱房",roomPopStatus:false},{roomId:2,roomName:"随机内地铺",roomPopStatus:false}]},
        //    {id:2,cabinListStatus:false,cabinName:"海景房",roomList:[{roomId:1,roomName:"随机海景房",roomPopStatus:false},{roomId:2,roomName:"海景地铺",roomPopStatus:false}]},
        //    {id:3,cabinListStatus:false,cabinName:"高级舱房",roomList:[{roomId:1,roomName:"随机高级舱房",roomPopStatus:false},{roomId:2,roomName:"高档地铺",roomPopStatus:false}]}
        //],
        //bannerImgList:[
        //    {id:1,imgUrl:'../image/banner01.png'},
        //    {id:2,imgUrl:'../image/banner02.png'},
        //    {id:3,imgUrl:'../image/banner03.png'}
        //]
    };

    $scope.dateId = $stateParams.dateId;
    $http.post(yhyUrl.shipDetail, {dateId: $scope.dateId}).success(function (data) {
        if (data.success) {
            $scope.ship = data.ship;
            $scope.roomTypes = data.ship.roomTypes;
            $scope.day = data.ship.date.date;
            $scope.planDays = data.ship.planDays;

            var orderyoulun = {
                shipId: $scope.ship.id,
                dateId: $scope.ship.date.id
            };
            storage.set(yhyKey.orderyoulun, orderyoulun);

            var beginDay = new Date($scope.day);
            for (var bt = 0; bt < $scope.planDays.length; bt++) {
                beginDay.setDate(beginDay.getDate() + 1);
                $scope.planDays[bt].beginDate = beginDay.format("MM-dd");
            }
            for (var i = 0; i < $scope.roomTypes.length; i++) {
                $scope.roomTypes[i].cabinListStatus = false;
                $scope.roomTypes[i].id = "1";
                for (var j = 0; j < $scope.roomTypes[i].roomList.length; j++) {
                    $scope.roomTypes[i].roomList[j].roomPopStatus = false;
                    $scope.roomTypes[i].roomList[j].selectNum = 0;
                    $scope.roomTypes[i].roomList[j].totalPrice = 0;
                }
            }
        }
    });

    /*方法*/

    $scope.totalPeopleNum = 0;
    $scope.method = {
        /*舱房列表  展开*/
        listSwitch: function (id) {
            angular.forEach($scope.roomTypes, function (item, i) {
                if (i == id) {
                    if (item.cabinListStatus) {
                        item.cabinListStatus = false;
                    } else {
                        item.cabinListStatus = true;
                    }
                }
            });
        },
        /*查看舱房详情  弹窗*/
        popSwitch: function (id) {
            angular.forEach($scope.roomTypes, function (item) {
                angular.forEach(item.roomList, function (single) {

                    if (single.roomId == id) {
                        if (single.roomPopStatus) {
                            single.roomPopStatus = false;
                        } else {
                            single.roomPopStatus = true;
                        }
                    }
                });
            });
        },
        //验证
        startOrder:function(){

            //取选中房型列表
            var cruiseRoomList = [];
            angular.forEach($scope.roomTypes, function (item) {
                angular.forEach(item.roomList, function (single) {
                    if(single.selectNum > 0){
                        cruiseRoomList.push(single);
                    }
                });
            });
            storage.set(yhyKey.roomList, cruiseRoomList);

            if ($scope.totalPeopleNum == 0) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '请选择人数'
                });
                return;
            }else{
                $state.go("editOrder", {dateId: $scope.ship.date.id});
            }


        },

        //触发加
        addNum: function (id) {
            var addNum = 0;
            angular.forEach($scope.roomTypes, function (item) {
                angular.forEach(item.roomList, function (single) {
                    if (single.roomId == id) {
                        if(single.forceFlag){
                            addNum = single.peopleNum;
                        }else{
                            addNum = 1;
                        }
                         single.selectNum += parseInt(addNum);
                        single.totalPrice = single.selectNum * single.price;
                    }

                });
            });
            $scope.totalPeopleNum = 0;
            $scope.totalCost = 0;
            for (var i = 0; i < $scope.roomTypes.length; i++) {
                for (var j = 0; j < $scope.roomTypes[i].roomList.length; j++) {
                    $scope.totalPeopleNum += $scope.roomTypes[i].roomList[j].selectNum;
                    $scope.totalCost += $scope.roomTypes[i].roomList[j].totalPrice;
                }
            }
            storage.set(yhyKey.orderPrice, $scope.totalCost);
            storage.set(yhyKey.orderPeopleNum, $scope.totalPeopleNum);
        },

        //触发减
        minsNum: function (id) {
            var minNum = 0;
            angular.forEach($scope.roomTypes, function (item) {
                angular.forEach(item.roomList, function (single) {
                    if (single.roomId == id) {
                        if(single.selectNum > 0){
                            if(single.forceFlag){
                                minNum = single.peopleNum;
                            }else{
                                minNum = 1;
                            }
                            single.selectNum = single.selectNum - parseInt(minNum);
                            single.totalPrice = single.selectNum * single.price;
                        }

                    }
                });
            });
            if($scope.totalPeopleNum > 0){
                $scope.totalPeopleNum = 0;
                $scope.totalCost = 0;
                for (var i = 0; i < $scope.roomTypes.length; i++) {
                    for (var j = 0; j < $scope.roomTypes[i].roomList.length; j++) {
                        $scope.totalPeopleNum += $scope.roomTypes[i].roomList[j].selectNum;
                        $scope.totalCost += $scope.roomTypes[i].roomList[j].totalPrice;
                    }
                }
                storage.set(yhyKey.orderPrice, $scope.totalCost);
                storage.set(yhyKey.orderPeopleNum, $scope.orderPeopleNum);
            }

        }

    };



});



//轮播指令
cruiseShip.directive('swiper', function ($timeout) {
    return {
        restrict: "EA",
        scope: {
            data: "=",
            url:'='
        },
        template: '<div class="swiper-container" id="newCruiseBanner">' +
        '<ul class="swiper-wrapper">' +
        '<li class="swiper-slide" ng-repeat="item in data">' +
        '<a class="img40" href="javascript:;" rel="external nofollow" ><img ng-src="{{url}}{{item}}" alt="" /></a>' +
        '</li>' +
        '</ul>' +
        '<div class="swiper-pagination"></div>' +
        '</div>',
        link: function (scope, element, attrs) {
            $timeout(function () {
                var swiper = new Swiper('#newCruiseBanner', { //轮播图绑定样式名
                    autoplay: 2500,
                    loop: true,
                    pagination : '.swiper-pagination',
                    paginationClickable: true,
                    longSwipesRatio: 0.3,
                    touchRatio:1,
                    autoplayDisableOnInteraction : false,
                    observer:true,//修改swiper自己或子元素时，自动初始化swiper
                    observeParents:true,//修改swiper的父元素时，自动初始化swiper
                });
            }, 100);
        }
    };
});





//订单填写
cruiseShip.controller("editOrder", function ($scope, $http, $state, storage, $stateParams,$rootScope, Check) {

    if($scope.contact == 0){
        $scope.contact = {};
    }



    $scope.selectedtourist = storage.get(yhyKey.selectedTourist);
    $scope.contact = storage.get(yhyKey.userInfo);
    $scope.email = storage.get(yhyKey.userEmail)

    $scope.price = storage.get(yhyKey.orderPrice);
    $scope.peopleNum = storage.get(yhyKey.orderPeopleNum);

    $scope.saveInfo = function(){
        storage.set(yhyKey.userInfo, $scope.contact);
        storage.set(yhyKey.userEmail, $scope.email);

    }
    /*发票弹窗状态*/
    $scope.billPopStatus = false;
    $scope.billClickFn = function(){
        if($scope.billPopStatus){
            $scope.billPopStatus = false;
        }else{
            $scope.billPopStatus = true;
        }
    }
    /*---发票弹窗交互  ending*/

    $scope.dateId = $stateParams.dateId;

    $http.post(yhyUrl.shipDetail, {dateId: $scope.dateId}).success(function (data) {
        if (data.success) {
            $scope.ship = data.ship
            $scope.roomList = data.ship.roomTypes.roomList
            $scope.startDay = data.ship.date.date;
          //  $scope.endDay = $scope.ship.playDay
            $scope.planDays = data.ship.planDays;

            var beginDay = new Date($scope.startDay);
            $scope.beginMonth = beginDay.format("MM");
            $scope.beginDay = beginDay.format("dd");

            var endDay = new Date();
            endDay.setDate(beginDay.getDate() + $scope.ship.playDay);
            $scope.endMonth = endDay.format("MM");
            $scope.endDay = endDay.format("dd");

        }
    });
    $scope.roomType = $stateParams.roomType;
    $scope.orderyoulun = storage.get(yhyKey.orderyoulun) == null ? {} : storage.get(yhyKey.orderyoulun);
    $scope.roomsList = [];
    $scope.selectRooms = [];
   // $scope.contact = {};
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
        if ($scope.selectedtourist.length == 0) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写游客信息'
            });
            return;
        }
        if ($scope.selectedtourist.length != $scope.peopleNum ) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '游客人数填写有误'
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
                $scope.cruiseRoomList = storage.get(yhyKey.roomList);
                    angular.forEach($scope.cruiseRoomList, function (room) {
                        if ($scope.peopleNum > 0) {
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
                jsonObj.details = details;
                $http.post(yhyUrl.saveOrder, {
                    json: JSON.stringify(jsonObj)
                }).success(function (data) {
                    $rootScope.loading = false;
                    if (data.success) {
                        storage.remove(yhyKey.selectedTourist);
                        $state.go("orderPay", {orderId: data.order.id});
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

