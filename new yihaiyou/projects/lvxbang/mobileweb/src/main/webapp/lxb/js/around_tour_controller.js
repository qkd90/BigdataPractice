/**
 * Created by zzl on 2016/7/25.
 */

var mAroundTourIndexModule = angular.module('mAroundTourIndexModule', ['infinite-scroll']);
mAroundTourIndexModule.controller('mAroundTourIndexCtrl', function ($scope, $http, Banner, TourInit) {
    $scope.IMG_DOMAIN = LXB_KEY.IMG_DOMAIN;

    // 初始列表数据
    $scope.areaLineLists = [[],[],[]];
    // 初始分页信息
    $scope.areaLineListPageSize = 3;
    $scope.areaLineListPageSizeNos = [1, 1, 1];
    // 默认列表序号
    $scope.areaLineIndex = 0;
    // 初始状态
    $scope.hasMore = [true, true, true];
    // 加载数据中
    $scope.loading = true;
    // 列表点击标记
    $scope.areaLineListFirstLoad = [true, true, true];
    // 列表线路属性
    $scope.productAttr = ['精品定制', '自助自驾', '跟团游'];


    // 广告数据加载状态
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        // 幻灯广告
        Banner.init({
            container: '#ati-top-slider-container',
            effect: 'fade',
            autoplay: 3000,
            pagination: '#ati-top-slide-page'
        });
    });

    $scope.init = function() {
        // 更多城市
        TourInit.moreCity();
    };

    // 获取周边游首页数据
    $http.post(LXB_URL.mAroundTourIndexData, {}).success(function(result) {
        if (result.success) {
            $scope.result = result;
            // 初始化banner
            $scope.init();
            // 列表加载状态
            //// 初始化线路列表城市代码
            //$scope.currentCityCode = result.zhoubianAreas[0].cityCode;
            $scope.loading = false;
        } else {
            alert(result.msg);
        }
    }).error(function(result) {
        //
    });


    // 分类栏目点击
    $('#tr_list li').click(function () {
        $('#tr_list li').removeClass('active_green');
        $(this).addClass('active_green');
        $('.gentuan_line').css('display', 'none');
        $('.gentuan_line').eq($(this).index()).css('display', 'block');
        $scope.areaLineIndex = $(this).index();
        // 默认加载数据
        if ($scope.areaLineListFirstLoad[$(this).index()]) {
            $scope.areaLineListFirstLoad[$(this).index()] = false;
            $scope.getAreaLineListData();
        }
    });

    // 目的地线路列表分页加载处
    $scope.getAreaLineListData = function () {
        if($scope.loading) return;
        if(!$scope.hasMore[$scope.areaLineIndex]) return;
        $scope.loading = true;
        // 标记列表首次加载状态
        $scope.areaLineListFirstLoad[$scope.areaLineIndex] = false;
        $http.post(LXB_URL.mIndexAreaLineData, {
            areaLineListPageSize: $scope.areaLineListPageSize,
            areaLineListPageSizeNo: $scope.areaLineListPageSizeNos[$scope.areaLineIndex],
            'lineSearchRequest.lineType': 'around',
            'lineSearchRequest.productAttr': $scope.productAttr[$scope.areaLineIndex]
        }).success(function(result) {
            // 更新数据
            angular.forEach(result.lineList, function(line) {
                $scope.areaLineLists[$scope.areaLineIndex].push(line);
            });
            $scope.hasMore[$scope.areaLineIndex] = result.hasMore;
            if ($scope.hasMore[$scope.areaLineIndex]) {
                $scope.areaLineListPageSizeNos[$scope.areaLineIndex] += 1;
            }
            $scope.loading = false;
        }).error(function(result) {
            //
        });
    };



});