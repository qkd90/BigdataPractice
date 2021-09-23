/**
 * Created by zzl on 2016/7/25.
 */

var mCustomTourIndexModule = angular.module('mCustomTourIndexModule', ['infinite-scroll']);
mCustomTourIndexModule.controller('mCunstomTourIndexCtrl', function($scope, $http, Banner, TourInit) {
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
    $scope.preCityCode = "";
    // 列表线路分类
    $scope.lineType = ['around', 'china', 'foreign'];

    // 广告数据加载状态
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        // 幻灯广告
        Banner.init({
            container: '#cti-top-slider-container',
            effect: 'fade',
            autoplay: 3000,
            pagination: '#cti-top-slide-page'
        });
    });

    $scope.init = function() {
        // 更多城市
        TourInit.moreCity();
    };

    // 获取定制精品首页数据
    $http.post(LXB_URL.mCustomTourIndexData, {}).success(function(result) {
        if (result.success) {
            $scope.result = result;
            // 初始化banner
            $scope.init();
            // 列表加载状态
            // 初始化线路列表城市代码
            $scope.currentCityCode = result.zhoubianAreas[0].cityCode;
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
        $('.sub-tabs ul.sub-list').css('display', 'none');
        $('.sub-tabs ul.sub-list').eq($(this).index()).css('display', 'block');
        $('.gentuan_line').css('display', 'none');
        $('.gentuan_line').eq($(this).index()).css('display', 'block');
        var $li = $('.sub-tabs ul.sub-list').eq($(this).index()).children('li');
        // 默认点击第一个城市
        if ($scope.areaLineListFirstLoad[$(this).index()]) {
            $li.children('.sub-list-name').eq(0).click();
            $scope.areaLineListFirstLoad[$(this).index()] = false;
        }
    });

    // 分类栏目*目的地*点击
    $scope.goAreaLineList = function($this, $event) {
        var $li = angular.element($event.target).parent('li');
        var li_index = $li.parent('ul').index();
        // 标记列表加载状态
        if ($scope.areaLineListFirstLoad[li_index]) {
            $scope.areaLineListFirstLoad[li_index] = false;
        }
        // 已选择, 返回
        if ($scope.preCityCode == $this.area.cityCode) {
            return;
        }
        $li.siblings().children('div').removeClass('super_bg').addClass('each_bg');
        angular.element($event.target).removeClass('each_bg').addClass('super_bg');
        // 重置该目的地的分页页码
        $scope.areaLineListPageSizeNos[li_index] = 1;
        // 重置该目的地的数据状态
        $scope.hasMore[li_index] = true;
        // 当前列表序号
        $scope.areaLineIndex = li_index;
        // 当前目的地代码
        $scope.currentCityCode = $this.area.cityCode;
        // 加载数据
        $scope.getAreaLineListData();
    };

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
            'lineSearchRequest.productAttr': '精品定制',
            'lineSearchRequest.lineType': $scope.lineType[$scope.areaLineIndex],
            'lineSearchRequest.cityId': $scope.currentCityCode
        }).success(function(result) {
            // 点击新目的地后清空列表
            if ($scope.preCityCode != $scope.currentCityCode) {
                $scope.areaLineLists[$scope.areaLineIndex] = [];
                // 保存当前城市代码
                $scope.preCityCode = $scope.currentCityCode;
            }
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