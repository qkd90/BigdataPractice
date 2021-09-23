/**
 * Created by dy on 2016/9/19.
 */
var sceneryDetailModule = angular.module("sceneryDetailModule", ['infinite-scroll']);

sceneryDetailModule.directive("sceneryDetailTab", function () {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.click(function (event) {
                scope.$apply(function () {
                    scope.tabIndex = attrs.tabindex;
                    if (attrs.tabindex == 2) {
                        //scope.searchScenicDesc();
                    }
                });
            });
        }
    };
});

sceneryDetailModule.controller("sceneryDetailController", function($scope, $http, $location, $stateParams, $state) {
    $scope.tabIndex = 1;
    $scope.lat = 0;
    $scope.lng = 0;
    $scope.showTicketPrice = null;
    $scope.noTicket = true; // 是否有门票标志
    var loadTicketFlag = false; // 是否已加载门票列表，只加载一次
    var loadDetailFlag = false; // 是否已加载详情，只加载一次
    $scope.nomore = false;
    $scope.ticketDescFlag = false; // 票型说明窗口是否显示标志
    $scope.priceResponseList = [];
    $scope.showMap = false;     //百度地图显示
    var sceneryId = $stateParams.sceneryId;

    if (sceneryId) {
        sceneryId = 5000236;
    }
    // 查询景点概要信息
    $http.post(LXB_URL.sceneryDetail, {
        'ticket.id' : sceneryId
    }).success(function (data) {
        if (data.error) {
            $scope.error = true;
        } else {

            $scope.sceneryId = data.scenery.id;
            $scope.name = data.scenery.name;
            if (data.scenery.cover) {
                $scope.cover = LXB_KEY.IMG_DOMAIN + "/" + data.scenery.cover;
            }
            $scope.openTime = data.scenery.openTime;
            $scope.address = data.scenery.address;
            $scope.proInfo = data.scenery.proInfo;
            $scope.introduction = data.scenery.introduction;
            $scope.enterDesc = data.scenery.enterDesc;
            $scope.rule = data.scenery.rule;
            $scope.privilege = data.scenery.privilege;
            $scope.lat = data.scenery.lat;
            $scope.lng = data.scenery.lng;
            //$scope.productRemark = data.productRemark;
            //$scope.satisfaction = data.satisfaction;
            //$scope.favorite = data.favorite;
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message:'系统错误'
        });
    });

    $scope.goTrafficGuide = function() {
        if ($scope.lat == 0 || $scope.lng == 0) {
            return ;
        }
        $state.go("sceneryDetailMap", {sceneryId: $scope.sceneryId}, {reload: true})

    };


    $scope.listTicket = function() {

        $http.post(LXB_URL.sceneryPriceType, {
            'ticket.id' : sceneryId
        }).success(function (data) {
            if (data.error) {
                $scope.error = true;
            } else {
                $scope.noTicket = false;
                $scope.typeList = data.result;
                $scope.priceResponseList = data.priceResponseList;
            }
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message:'系统错误'
            });
        });

    };

    // 票型说明 - 显示
    $scope.showTicketDesc = function(ticketPriceId) {
        // 查找对应的票型记录
        for (var i = 0; i < $scope.priceResponseList.length; i++) {
            if (ticketPriceId == $scope.priceResponseList[i].id) {
                $scope.showTicketPrice = $scope.priceResponseList[i];
                break;
            }
        }
        // 查询票型详情
        if (!$scope.showTicketPrice.addinfoDetailList) {    // 判断是否已经加载过
            $scope.loading = true;
            $http.post(LXB_URL.ticketAddinfoDetail, {
                ticketPriceId : ticketPriceId
            }).success(function (data) {
                $scope.loading = false;
                if (data.error) {
                    $scope.error = true;
                } else {
                    $scope.showTicketPrice.addinfoDetailList = data.addinfoDetailList;
                    $scope.ticketDescFlag = true;
                }
            }).error(function (data) {
                $scope.loading = false;
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message:'系统错误'
                })
            });
        } else {
            $scope.ticketDescFlag = true;
        }
    };

    // 票型说明 - 关闭
    $scope.closeTicketDesc = function() {
        $scope.ticketDescFlag = false;
        $scope.showTicketPrice = null;
    };
    // 查询景点详情
    $scope.searchScenicDesc = function () {
        if (loadDetailFlag) {
            return;
        }
        loadDetailFlag = true;
        $scope.loading = true;
        $http.post(LXB_URL.scenicDesc, {
            scenicId: sceneryId
        }).success(function (data) {
            $scope.loading = false;
            if (data.error) {
                $scope.error = true;
            } else {
                $scope.descripton = data.descripton;
            }
        }).error(function (data) {
            $scope.loading = false;
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


    // 首页
    $scope.home = function() {
        $state.go("index", {}, {reload: true});
    };
    // 查询
    $scope.search = function() {
        $state.go("search", {}, {reload: true});
    };
    // 个人中心
    $scope.personal = function() {
        //$state.go("myteam", {}, {reload: true});
        location.href = GetUrl.personal;
    };
    // 返回
    $scope.back = function() {
        history.go(-1);
    };

});

sceneryDetailModule.controller("sceneryDetailMapController", function($scope, $http, $location, $stateParams, $state) {
    var sceneryId = $stateParams.sceneryId;
    if (sceneryId) {
        sceneryId = 5000236; //测试游艇帆船
    }
    // 查询景点概要信息
    $http.post(LXB_URL.sceneryDetail, {
        'ticket.id' : sceneryId
    }).success(function (data) {
        if (data.error) {
            $scope.error = true;
        } else {
            $scope.sceneryId = data.scenery.id;
            $scope.name = data.scenery.name;
            $scope.lat = data.scenery.lat;
            $scope.lng = data.scenery.lng;
            $scope.trafficInfo = data.scenery.trafficInfo;
            var bmap = new BMap.Map("baiduMap");    //创建地图实例
            var point = new BMap.Point($scope.lng, $scope.lat);  // 创建点坐标
            bmap.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别

            var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25));
            var marker = new BMap.Marker(point, {icon:myIcon});
            bmap.addOverlay(marker);    //增加点
            bmap.addControl(new BMap.NavigationControl());
            var opts = {
                //width : '100',     // 信息窗口宽度
                //height: ''     // 信息窗口高度
                //title : "Hello"  // 信息窗口标题
            }
            var infoWindow = new BMap.InfoWindow($scope.name, opts);  // 创建信息窗口对象
            bmap.openInfoWindow(infoWindow, bmap.getCenter());      // 打开信息窗口
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message:'系统错误'
        });
    });

    $scope.backDetail = function() {
        history.go(-1);
    };
});