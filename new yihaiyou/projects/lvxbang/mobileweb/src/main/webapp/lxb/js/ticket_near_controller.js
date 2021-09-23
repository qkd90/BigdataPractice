/**
 * Created by zzl on 2016/8/4.
 */
var mTicketNearModule = angular.module('mTicketNearModule', ['infinite-scroll']);
mTicketNearModule.controller('mTicketNearCtrl', function ($scope, $http, storage) {
    $scope.IMG_DOMAIN = LXB_KEY.IMG_DOMAIN;

    $scope.ticketCityCode = storage.get('ticketCityCode');
    $scope.ticketCityName = storage.get('ticketCityName');
    $scope.ticketNearLng = storage.get('ticketNearLng');
    $scope.ticketNearLat = storage.get('ticketNearLat');

    // 获取数据
     $http.post(LXB_URL.mTicketNearData, {}).success(function (result) {
         if (result.success) {
             $scope.result = result;
             $scope.loading = false;
         } else {
             alert(result.msg);
         }
     }).error(function(result) {
         // ...
     });

    $scope.nearScenicList = function() {

    };

    $scope.nearLineList = function() {

    };

});