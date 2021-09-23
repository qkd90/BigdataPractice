var scenicDetailModule = angular.module('scenicDetailModule', ['infinite-scroll']);
// tab切换
scenicDetailModule.directive("scenicDetailTab", function () {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.click(function (event) {
                scope.$apply(function () {
                    scope.tabIndex = attrs.tabindex;
                    if (attrs.tabindex == 2) {
                        scope.searchScenicDesc();
                    }
                });
            });
        }
    };
});
// 景点详情
scenicDetailModule.controller('scenicDetailCtrl', function ($scope, $http, $location, $stateParams, $state, Check, $rootScope) {
    $scope.tabIndex = 1;
    $scope.pageNo = 1;
    $scope.commentList = [];
    $scope.showTicketPrice = null;
    $scope.noTicket = true; // 是否有门票标志
    var loadTicketFlag = false; // 是否已加载门票列表，只加载一次
    var loadDetailFlag = false; // 是否已加载详情，只加载一次
    $scope.nomore = false;
    $scope.ticketDescFlag = false; // 票型说明窗口是否显示标志

    var scenicId = $stateParams.scenicId;
    // 查询景点概要信息
    $http.post(yhyUrl.scenicTicketInfo, {
        scenicId: scenicId, favorite: 'QUERY_TRUE'
    }).success(function (data) {
        if (data.error) {
            $scope.error = true;
        } else {
            $scope.scenicId = data.scenicId;
            $scope.scenicName = data.scenicName;
            $scope.scenicCover = data.scenicCover;
            $scope.openTime = data.openTime;
            $scope.address = data.address;
            $scope.productRemark = data.productRemark;
            $scope.satisfaction = data.satisfaction;
            $scope.favorite = data.favorite;
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
                } else {
                    $scope.noTicket = true;
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
    // 首页
    $scope.home = function () {
        $state.go("index", {}, {reload: true});
    };
    // 查询
    $scope.search = function () {
        $state.go("search", {}, {reload: true});
    };
    // 个人中心
    $scope.personal = function () {
        //$state.go("myteam", {}, {reload: true});
        location.href = GetUrl.personal;
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
                if (data.success) {
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
    // 下单
    $scope.doBook = function (ticketPriceId) {
        $state.go("orderTicketDate", {ticketPriceId: ticketPriceId}, {reload: true});
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
            $scope.doBook($scope.showTicketPrice.id);
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
    // 相关线路
    $scope.goRelateLine = function () {

    };
});

// 预订须知
scenicDetailModule.controller('scenicOrderKnowCtrl', function ($scope, $http, $location, $stateParams, $state, $rootScope) {
    var scenicId = $stateParams.scenicId;
    // 查询景点概要信息
    $rootScope.loading = true;
    $http.post(yhyUrl.scenicTicketInfo, {
        scenicId: scenicId
    }).success(function (data) {
        $rootScope.loading = false;
        if (data.error) {
            $scope.error = true;
        } else {
            $scope.scenicId = data.scenicId;
            $scope.scenicName = data.scenicName;
            $scope.openTime = data.openTime;
            $scope.address = data.address;
            $scope.telephone = data.telephone;
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
    // 返回
    $scope.back = function () {
        history.go(-1);
    };
    $scope.goToTop = function () {
        $('html,body').animate({"scrollTop": 0}, 80);
    }
});
//回到顶部
/*$('html,body').scroll(function () {
 if ($('html,body').scrollTop() > 300) {
 $('#scrolltoTop').addClass('toshow');
 } else {
 $('#scrolltoTop').removeClass('toshow');
 }
 $('#scrolltoTop').click(function () {
 clearInterval(timer);
 $('html,body').animate({"scrollTop": 0}, 80);
 });
 });*/
// 交通信息
scenicDetailModule.controller('scenicTrafficGuideCtrl', function ($scope, $http, $location, $stateParams, $state, $rootScope) {
    var scenicId = $stateParams.scenicId;
    // 查询景点概要信息
    $rootScope.loading = true;
    $http.post(yhyUrl.scenicTicketInfo, {
        scenicId: scenicId
    }).success(function (data) {
        $rootScope.loading = false;
        if (data.error) {
            $scope.error = true;
        } else {
            $scope.scenicId = data.scenicId;
            $scope.scenicName = data.scenicName;
            $scope.address = data.address;
            if (data.trafficGuide) {
                $scope.trafficGuide = data.trafficGuide;
            } else {
                $scope.trafficGuide = '暂无交通说明';
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
        });
    });
    // 返回
    $scope.back = function () {
        history.go(-1);
    };

});