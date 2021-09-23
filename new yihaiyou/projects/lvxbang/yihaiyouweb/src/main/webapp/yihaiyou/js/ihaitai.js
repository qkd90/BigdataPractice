var ihaitaiModule = angular.module("ihaitaiModule", []);
ihaitaiModule.controller("ihaitaiCtrl", function($scope) {
    // 返回
    $scope.back = function () {
        history.go(-1);
    };
});

