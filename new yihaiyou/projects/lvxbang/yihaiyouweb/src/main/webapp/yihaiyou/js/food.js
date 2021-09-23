var foodModule = angular.module('foodModule', []);
foodModule.controller('foodCtrl', function ($scope, $http, ImageHandel, $rootScope) {
    $scope.shadowbox = false;
    $scope.nomore = false;
    $rootScope.loading = false;
    $scope.foodList = [];
    $scope.pageNo = 1;

    $scope.getFootList = function () {
        if ($scope.nomore) return;
        if ($rootScope.loading) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.foodList, {
            json: JSON.stringify({}),
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.foodList, function (food) {
                    food.cover = ImageHandel.completeImage(food.cover);
                    $scope.foodList.push(food);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++;
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };

    $scope.moreInformation = function (food) {
        $scope.shadowbox = true;
        food.showMore = true;
        //$('body').addClass('bodyStop');
    };
    $scope.clearShadow = function () {
        $scope.shadowbox = false;
        angular.forEach($scope.foodList, function (food) {
            food.showMore = false;
        });
        //$('body').removeClass('bodyStop');
    }
});


foodModule.filter("writingLimit", function ($sce) {
    return function (input) {
        var str = input.trim();
        return $sce.trustAsHtml(str.substr(0, 22) + "...<br/>" + str.substring(23, str.length));
    }
});


	
