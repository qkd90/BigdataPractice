/**
 * Created by dy on 2016/10/9.
 */
var guideModule = angular.module("guideModule", ['infinite-scroll']);

guideModule.controller("guideIndexCtrl", function($scope, $rootScope, $http, $state, Banner) {

    $scope.showTabList = true;
    $scope.tabIndex = 1;
    $scope.changeTabList = function(index) {
        $scope.tabIndex = index;
        if (index == 2) {
            $scope.showTabList = false;
        } else {
            $scope.showTabList = true;
        }
    }

    //获取列表数据
    $scope.recommendPlanList = [];
    $scope.rePage = 1;
    $scope.reNomore = false;
    $rootScope.loading = false;
    $scope.getRecommendPlanList = function() {
        if ($rootScope.loading) return;
        if ($scope.reNomore) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.recommendPlanList,
            {
                'recommendPlanSearchRequest.cityIds[0]': 350200,
                'recommendPlanSearchRequest.orderColumn': 'viewNum',
                'recommendPlanSearchRequest.orderType': 'desc',
                'page':$scope.rePage,
                'pageSize':10
            }
        ).success(
            function(data) {
                if (data.success) {
                    angular.forEach(data.rePlanList,
                        function(item) {
                            $scope.recommendPlanList.push(item);
                        }
                    );

                    $scope.rePage++;
                }
                $scope.reNomore = data.nomore;
                $rootScope.loading = false;
            }
        ).error(
            function(error) {

            }
        );
    }


    $scope.topBannerAds = [];

    $http.post(yhyUrl.getRecplanTopBannerAds,
        {}
    ).success(
        function(data) {
            if (data.success) {
                $scope.topBannerAds = data.ads;
            }

        }
    ).error(
        function(error) {

        }
    );

    // 广告数据加载状态
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        // 幻灯广告
        Banner.init({
            container: '#recplan-top-slider-container',
            effect: 'fade',
            autoplay: 3000,
            pagination: '#recplan-top-slide-page'
        });
    });

    $scope.newestPlanList = [];
    $scope.newPage = 1;
    $scope.newNomore = false;
    $scope.newLoading = false;
    $scope.getNewestPlanList = function() {
        if ($scope.newLoading) return;
        if ($scope.newNomore) return;
        $scope.newLoading = true;
        $http.post(yhyUrl.recommendPlanList,
            {
                'recommendPlanSearchRequest.cityIds[0]': 350200,
                'recommendPlanSearchRequest.orderColumn': 'createTime',
                'recommendPlanSearchRequest.orderType': 'desc',
                'page':$scope.newPage,
                'pageSize':10
            }
        ).success(
            function(data) {
                if (data.success) {
                    angular.forEach(data.rePlanList,
                        function(item) {
                            $scope.newestPlanList.push(item);
                        }
                    );
                    $scope.newPage++;
                }
                $scope.newNomore = data.nomore;
                $scope.newLoading = false;
            }
        ).error(
            function(error) {

            }
        );
    }
    $scope.goRecommendPlanDetail = function(id) {
        $state.go("guide/detail", {recommendPlanId:id});
    }


    $(window).scroll(function() {
        var guideHeight = $('body').scrollTop();
        $scope.guideHeight = guideHeight;
    })
    $scope.gotosky = function(){
       $('body').animate({'scrollTop': 0},1000);
    }


});
//guideModule.directive('guidescroll',function(){
//    return{
//        restrict:'A',
//        link:function(scope){
//            $('body').scroll = function(){
//                var guideHeight = $('body').scrollTop();
//                scope.guideHeight = guideHeight;
//                alert(guideHeight)
//            }
//        }
//    };
//})

guideModule.controller("guideDetailCtrl", function($scope, $http, $stateParams, $state, Check, ImageHandel) {

    var windowObj = angular.element(window);
    $scope.bgWidth = windowObj.width() + 'px';

    $scope.recommendPlanId = $stateParams.recommendPlanId;
    if ($scope.recommendPlanId == null || $scope.recommendPlanId == "" || $scope.recommendPlanId == "undefined") {
        history.go(-1);
    }

    $scope.recommendPlan = null;
    $scope.recommendPlanDays = [];
    $http.post(yhyUrl.recommendPlanDetail,
        {
            'recommendPlanId':$scope.recommendPlanId,
            favorite: 'QUERY_TRUE'
        }
    ).success(
        function(data) {
            if (data.success) {
                data.recommendPlan.coverPath = ImageHandel.completeImage(data.recommendPlan.coverPath);
                $scope.recommendPlan = data.recommendPlan;
                angular.forEach(data.recommendPlanDays, function (planDay) {
                    angular.forEach(planDay.recommendPlanTrips, function (planTrip) {
                        angular.forEach(planTrip.recommendPlanPhotos, function (planPhoto) {
                            planPhoto.photoLarge = ImageHandel.completeImage(planPhoto.photoLarge);
                        });
                    });
                });
                $scope.recommendPlanDays = data.recommendPlanDays;
                $scope.favorite = data.favorite;

                $http.post(yhyUrl.addViewNum,
                    {
                        'recommendPlanId':$scope.recommendPlanId
                    }
                ).success(
                    function(result) {
                        if (result.success) {
                            $scope.recommendPlan['viewNum'] = result.viewNum;
                        }
                    }
                ).error(
                    function(error) {
                    }
                );


            }
        }
    ).error(
        function(error) {
        }
    );


    // 收藏
    $scope.doFavorite = function () {
        $http.post(yhyUrl.favorite, {
            favoriteId: $scope.recommendPlanId,
            favoriteType: 'recplan'
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

    $scope.goGuideComment = function() {
        $state.go("guide/comment", {recommendPlanId:$scope.recommendPlanId});
    };


    $(window).scroll(function() {
        var guideHeight = $('body').scrollTop();
        $scope.$apply(function () {
            $scope.guideHeight = guideHeight;
            //console.info($scope.guideHeight);
        });

    });
    $scope.gotosky = function(){
        $('body').animate({'scrollTop': 0},1000);
    };
});

guideModule.controller("guideCommentCtrl", function ($scope, $http, $stateParams, $state, Check, $rootScope) {
    $scope.recommendPlanId = $stateParams.recommendPlanId;
    if ($scope.recommendPlanId == null || $scope.recommendPlanId == "" || $scope.recommendPlanId == "undefined") {
        history.go(-1);
    }

    $scope.content = null;


    $scope.reloadCommentList = function() {
        $scope.commentList = [];
        $scope.noComment = false;
        $rootScope.loading = false;
        $scope.getCommentList();
    }

    $scope.commentList = [];
    $scope.noComment = false;
    $rootScope.loading = false;
    $scope.getCommentList = function() {
        if ($rootScope.loading) return;
        if ($scope.noComment) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.getRecommendPlanCommentList,
            {
                'recommendPlanId':$scope.recommendPlanId
            }
        ).success(
            function(data) {
                if (data.success) {
                    $scope.userName = data.userName;
                    $scope.commentList = data.commentList;
                    if ($scope.commentList != null && $scope.commentList.length > 0) {
                        $scope.noComment = true;
                    }
                } else {
                    $scope.noComment = false;
                }
                $rootScope.loading = false;
            }
        ).error(
            function(error) {
            }
        );
    }

    $scope.saveComment = function() {

        if ($scope.content == null || $scope.content == "") {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请填写评论内容'
            });
            return;
        }


        $http.post(yhyUrl.saveRePlanComment,
            {
                'comment.targetId':$scope.recommendPlanId,
                'comment.type':'recplan',
                'comment.content':$scope.content
            }
        ).success(
            function(data) {
                if (Check.loginCheck(data)) {
                    if (data.success) {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: '评论成功'
                        });
                        $scope.reloadCommentList();
                        $scope.content = '';
                    } else {
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '确认'
                                }
                            },
                            message: '评论失败'
                        });
                    }
                }
            }
        ).error(
            function(error) {
            }
        );
    }

});
