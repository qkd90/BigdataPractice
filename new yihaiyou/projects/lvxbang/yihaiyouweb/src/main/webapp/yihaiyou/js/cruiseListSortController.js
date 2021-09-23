/**
 * Created by Administrator on 2017/7/26.
 */
var cruiseListSortModule = angular.module("cruiseListSortModule",[]);

cruiseListSortModule.controller("CruiseListController",function($scope){
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
    $scope.routerList = [
        {id:0,value:'不限航线',status:'active'},
        {id:1,value:'东南亚风情',status:'default'},
        {id:2,value:'日韩之旅',status:'default'},
        {id:3,value:'国内航线',status:'default'}
    ];
    /*品牌数据*/
    $scope.brandList = [
        {id:0,value:'不限品牌',status:'active'},
        {id:1,value:'歌诗达',status:'default'},
        {id:2,value:'维京',status:'default'},
    ];
    /*航线和品牌的参数值*/
    $scope.routerVal = 0;
    $scope.brandVal = 0;
    /*弹窗显示/隐藏事件*/
    $scope.switchPop = function(){
        if($scope.popStatus){
            $scope.popStatus = false;
        }else{
            $scope.popStatus = true;
        }
    }
    /*tap切换事件*/
    $scope.switchTab = function(tab){
        $scope.routerStatus = false;
        $scope.brandStatus = false;
        $scope.departTimeStatus = false;
        switch(tab) {
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
    $scope.chooseRouter = function(id){
        $scope.routerVal = id;
        angular.forEach($scope.routerList,function(item){
            item.status = "default";
        });
        angular.forEach($scope.routerList,function(item){
            if(item.id == id){
                item.status = "active";
            }
        });
    }
    /*选择品牌事件*/
    $scope.chooseBrand = function(id){
        $scope.brandVal = id;
        angular.forEach($scope.brandList,function(item){
            item.status = "default";
        });
        angular.forEach($scope.brandList,function(item){
            if(item.id == id){
                item.status = 'active';
            }
        });
    }

});