/**
 * Created by dy on 2017/1/6.
 */
/**
 * Created by dy on 2016/10/9.
 */
var feedBackModule = angular.module("feedBackModule", []);

feedBackModule.controller("feedBackCtrl", function($scope, $rootScope, $http, $state) {

    $scope.content = "";
    $scope.contact = "";

    $scope.submitFeedBack = function() {

        if ($scope.content.length <= 0) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请完善意见内容"
            });
            return;
        }
        if ($scope.contact.length <= 0 || !$scope.contact.match(yhyKey.telephoneReg)) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "请完善联系电话"
            });
            return;
        }

        $http.post(yhyUrl.submitFeedBack,
            {
                content: $scope.content,
                contact: $scope.contact
            }
        ).success(
            function(data) {
                if (data.success) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: "提交成功"
                    });
                    $scope.content = "";
                    $scope.contact = "";
                } else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: "提交失败"
                    });
                }
            }
        ).error(
            function(error) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: "提交错误"
                });
            }
        );
    }
});