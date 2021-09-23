/**
 * Created by Administrator on 2017/7/31.
 */
var cruiseEditorOrderModule = angular.module("cruiseEditorOrderModule",[]);
cruiseEditorOrderModule.controller('CruiseEditorOrderController',function($scope){
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
});