var myTeamModule = angular.module('myTeamModule', ['infinite-scroll']);
// 我的团队不同层级点击
myTeamModule.directive("levelClick", function() {
    return {
        restrict : "A",
        link: function (scope, element, attrs) {
            element.click(function (event) {
                scope.$apply(function () {
                    scope.level = attrs.level;
                });
            });
        }
    };
});
// 我的团队某层级人员团队点击
myTeamModule.directive("levelDetailClick", function() {
    return {
        restrict : "A",
        link: function (scope, element, attrs) {
            element.click(function (event) {
                element.parent().parent().parent().next().toggleClass('display-none');
                element.toggleClass('his-team-bg');
                element.toggleClass('his-team-bg-minus');
            });
        }
    };
});
// 我的团队
myTeamModule.controller('myTeamCtrl', function ($scope, $http, $location, WechatShare) {
    // 一级会员
    $scope.pageNo1 = 1;
    $scope.loading1 = false;
    $scope.nomore1 = false;
    $scope.teamList1 = [];
    // 二级会员
    $scope.pageNo2 = 1;
    $scope.loading2 = false;
    $scope.nomore2 = false;
    $scope.teamList2 = [];
    // 三级会员
    $scope.pageNo3 = 1;
    $scope.loading3 = false;
    $scope.nomore3 = false;
    $scope.teamList3 = [];

    $scope.level = 1;
    $scope.firstCount = 0;
    $scope.secondCount = 0;
    $scope.thirdCount = 0;
    // 初始当前人员各级会员数
    $http.get(LXB_URL.countTeam
    ).success(function (data) {
        if (data.success) {
            $scope.firstCount = data.firstCount;
            $scope.secondCount = data.secondCount;
            $scope.thirdCount = data.thirdCount;
        } else {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message:data.errMsg
            });
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
    // 获取会员列表
    $scope.listTeam = function (level) {
        if ($scope['loading'+level]) return;
        if ($scope['nomore'+level]) return;
        $scope['loading'+level] = true;
        $http.post(LXB_URL.listTeam, {
            level: level,
            pageNo: $scope['pageNo'+level],
            pageSize: 10
        }).success(function (data) {
            //if (Check.loginCheck(data)) {
                if (data.success) {
                    angular.forEach(data.teamList, function (item) {
                        $scope['teamList'+level].push(item);
                    });
                    $scope['pageNo'+level]++;
                    $scope['nomore'+level] = data.nomore;
                }
            //}
            $scope['loading'+level] = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message:'系统错误'
            })
        });
    };
    // 微信分享
    var shareLink = apihost + "/mobile/index/lxbIndex.jhtml?route=/distributeshare";
    var imgUrl = apihost + "/lxb/images/logo_lv.png";
    var cfg = {title:'加入旅行帮',desc:'关注就可以成为分销员了',link:shareLink,imgUrl:imgUrl,
        success : function(shareType) {
            $http.post(LXB_URL.doDisShareSuccess, {
                shareType: shareType
            });
        }};
    WechatShare.initShareCfg(cfg);
});
// 分销分享页面
myTeamModule.controller('distributeShareCtrl', function ($scope, $http, $location, $stateParams, $state) {
    $scope.subscribe = true;
    $scope.error = false;
    $scope.canBeInvited = false;
    $scope.notCanBeInvited = false;
    var pid = $stateParams.pid;
    $http.post(LXB_URL.doDistributeShare, {
        pid : pid
    }).success(function (data) {
        if (data.error) {
            $scope.error = true;
        } else {
            if (data.subscribe) {   // 已关注
                $scope.canBeInvited = data.canBeInvited;
                $scope.notCanBeInvited = !data.canBeInvited;
            } else {
                $scope.subscribe = data.subscribe;
                $scope.qrcode = data.qrcode;
            }
        }
    }).error(function (data) {
        $scope.error = true;
    });
    $scope.goMyTeam = function() {
        $state.go("myteam", {}, { reload: true });
    };
});