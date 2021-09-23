/**
 * Created by zzl on 2016/8/4.
 */
var mTicketNearModule = angular.module('mTicketNearModule', ['infinite-scroll']);
mTicketNearModule.controller('mTicketNearCtrl', function ($scope, $http, storage, $rootScope) {
    $scope.IMG_DOMAIN = QINIU_BUCKET_URL;
    $scope.ticketCityCode = storage.get('ticketCityCode');
    $scope.ticketCityName = storage.get('ticketCityName');
    $scope.ticketNearLng = storage.get('ticketNearLng');
    $scope.ticketNearLat = storage.get('ticketNearLat');
    // 获取数据
    $http.post(yhyUrl.mTicketNearData, {}).success(function (result) {
        if (result.success) {
            $scope.result = result;
            $rootScope.loading = false;
        } else {
            alert(result.msg);
        }
    }).error(function (result) {
        // ...
    });

    $scope.nearScenicList = function () {

    };

    $scope.nearLineList = function () {

    };

});