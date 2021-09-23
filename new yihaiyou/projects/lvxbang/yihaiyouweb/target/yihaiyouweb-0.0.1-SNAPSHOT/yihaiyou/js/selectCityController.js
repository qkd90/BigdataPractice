/**
 * Created by dy on 2016/7/25.
 */

//选择城市
var xingcityModule = angular.module("xingcityModule", ['angularLocalStorage']);
xingcityModule.controller('xingcityCtrl', ['$scope', '$http', 'storage', '$stateParams', '$location', '$anchorScroll', '$state', function ($scope, $http, storage, $stateParams, $location, $anchorScroll, $state) {
    $scope.selectCitys = storage.get(yhyKey.selectCitys) == null ? [] : storage.get(yhyKey.selectCitys);
    var preUrl = storage.get("preUrl");
    $scope.fromQuote = $stateParams.fromQuote == "true" ? true : false;
    var cityMap = storage.get(yhyKey.cityMap);
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            $http.post(GetUrl.selectcitys, {
                longitude: position.coords.longitude,
                latitude: position.coords.latitude
            }).success(function (data) {
                if (data.success) {
                    $scope.city = data.cityList[0];
                    $(".now").removeClass("hidden");
                }
            }).error(function (data) {
                alert(data.errorMsg);
            });
        });
    }



    $scope.hotCity = [];
    $scope.fromCity = storage.get(yhyKey.fromCity) == null ? {
        id: 310100,
        name: '上海'
    } : storage.get(yhyKey.fromCity);

    if (cityMap == undefined || (new Date().getTime - cityMap.createDate) > cityMap.time) {
        $http({
            method: 'GET',
            url: yhyUrl.mHotArea
        }).success(function (data) {
            cityMap = data.sortMap;
            cityMap.time = 60 * 24 * 60 * 60 * 1000;
            cityMap.createDate = new Date().getTime();
            storage.set(yhyKey.cityMap, cityMap);
            completeCity();
        }).error(function (data) {
            alert(data.errorMsg)
        });
    } else {
        completeCity();
    }


    $scope.selectCityCode = function(obj) {

        var cityCode = 350200;
        if (obj.id) {
            cityCode = obj.id;
        }
        var params = {
            keyword: null,
            cityCode: cityCode
        };
        // 需要跳转回去
        var reurl = storage.get('reurl');
        if (!preUrl) {
            preUrl = "lineList";
        }

        if(reurl != null && reurl != "") {
            storage.set(storage.get('cityCodeKey'), obj.id);
            storage.set(storage.get('cityNameKey'), obj.name);
            storage.remove('reurl'); // 移除本次跳转
            window.location.href = reurl;
        } else {
            storage.set(yhyKey.fromCity, obj);
            $state.go(preUrl, {params: JSON.stringify(params)});
        }
    }

    $scope.setStorage = function(id, name) {
        console.log("id:" + id + ", name:" + name);
    };



    function completeCity() {
        $scope.hotCity = cityMap.hot;
        $scope.A = cityMap.A;
        $scope.B = cityMap.B;
        $scope.C = cityMap.C;
        $scope.D = cityMap.D;
        $scope.E = cityMap.E;
        $scope.F = cityMap.F;
        $scope.G = cityMap.G;
        $scope.H = cityMap.H;
        $scope.I = cityMap.I;
        $scope.J = cityMap.J;
        $scope.K = cityMap.K;
        $scope.L = cityMap.L;
        $scope.M = cityMap.M;
        $scope.N = cityMap.N;
        $scope.O = cityMap.O;
        $scope.P = cityMap.P;
        $scope.Q = cityMap.Q;
        $scope.R = cityMap.R;
        $scope.S = cityMap.S;
        $scope.T = cityMap.T;
        $scope.U = cityMap.U;
        $scope.V = cityMap.V;
        $scope.W = cityMap.W;
        $scope.X = cityMap.X;
        $scope.Y = cityMap.Y;
        $scope.Z = cityMap.Z;
    };

    $scope.index = 1;
    $scope.gotoBottom = function (label){
        var target = $location.hash(label);
        $anchorScroll();

        //var target = $(location.hash);
        if(target.length==1){
            var top = target.offset().top-44;
            if(top > 0){
                $('html,body').animate({scrollTop:top}, 1000);
            }
        }

        $scope.index += 1;
        //console.log($scope.index);
    };

    $scope.goback = function() {
        window.history.go(-$scope.index);
        //$state.go("search");
    }

    $scope.onSearchCity = function() {
        $("#xingcitysearchDiv").show();
        //$("#xingcityDiv").hide();
    };

    $scope.selectedCitys = storage.get(yhyKey.selectCitys) == null ? [] : storage.get(yhyKey.selectCitys);
    $scope.me = false;
    $scope.pageNo = 1;
    $scope.pageSize = 12;
    $scope.nomore = false;
    $scope.searchkey = '';
    $scope.citys = [];
    $scope.searchReShow= false;
    $scope.onSelectCitys = function () {

        if ($scope.searchkey.length > 0) {
            $http.post(yhyUrl.selectcitys, {
                    keyword: $scope.searchkey,
                    pageNo: $scope.pageNo,
                    pageSize: $scope.pageSize
                }
            ).success(function (data) {
                    for (var i = 0; i < data.cityList.length; i++) {
                        var city = data.cityList[i];
                        city.html = city.name.replace($scope.searchkey, '<b>' + $scope.searchkey + '</b>');
                        var eq = false;
                        for (var j = 0; j < $scope.selectedCitys.length; j++) {
                            if (city.id == $scope.selectedCitys[j].id) {
                                eq = true;
                                break;
                            }
                        }
                        city.check = eq;
                    }
                    $scope.citys = data.cityList;
                    $scope.searchReShow= true;
                }).error(function (data) {
                    $scope.citys = [];
                    $scope.searchReShow= false;
                    alert(data.errorMsg);
                });
        } else {
            $scope.citys = [];
            $scope.searchReShow= false;
        }
    };

    $scope.selected = function (obj) {

        $scope.fromCity = obj.city;
        storage.set(yhyKey.fromCity, $scope.fromCity);
        $state.go('xing-cfd');
    };

    $scope.selectCity = function(city) {
        //$scope.keyword = selectKeyword;
        var preUrl = storage.get("preUrl");
        if (!preUrl) {
            preUrl = "lineList";
        }

        var params = {
            keyword: null,
            cityCode: city.id
        };
        // 需要跳转回去
        var reurl = storage.get('reurl');
        if(reurl != null && reurl != "") {
            storage.set(storage.get('cityCodeKey'), city.id);
            storage.set(storage.get('cityNameKey'), city.name);
            storage.remove('reurl'); // 移除本次跳转
            window.location.href = reurl;
        } else {
            $state.go(preUrl, {params: JSON.stringify(params)});
        }
    };

    $scope.cancelSearchCity = function() {
        $("#xingcitysearchDiv").hide();
        $("#xingcityDiv").show();
    };


}]);

//
////城市搜索
//var xingcitysearchModule = angular.module("xingcitysearchModule", ['angularLocalStorage']);
//xingcitysearchModule.controller('xingcitysearchCtrl', ['$scope', '$http', 'storage', '$state', function ($scope, $http, storage, $state) {
//
//
//    $("#xingcitysearch").focus();
//    $scope.selectedCitys = storage.get(yhyKey.selectCitys) == null ? [] : storage.get(yhyKey.selectCitys);
//    $scope.me = false;
//    $scope.pageNo = 1;
//    $scope.pageSize = 12;
//    $scope.nomore = false;
//    $scope.searchkey = '';
//    $scope.citys = [];
//    $scope.selectCitys = function () {
//        if ($scope.searchkey.length > 0) {
//            $http.post(yhyUrl.selectcitys, {
//                    keyword: $scope.searchkey,
//                    pageNo: $scope.pageNo,
//                    pageSize: $scope.pageSize
//                }
//            ).success(function (data) {
//                    for (var i = 0; i < data.cityList.length; i++) {
//                        var city = data.cityList[i];
//                        city.html = city.name.replace($scope.searchkey, '<b>' + $scope.searchkey + '</b>');
//                        var eq = false;
//                        for (var j = 0; j < $scope.selectedCitys.length; j++) {
//                            if (city.id == $scope.selectedCitys[j].id) {
//                                eq = true;
//                                break;
//                            }
//                        }
//                        city.check = eq;
//                    }
//                    $scope.citys = data.cityList;
//                }).error(function (data) {
//                    alert(data.errorMsg);
//                });
//        }
//    };
//
//    $scope.selected = function (obj) {
//
//        $scope.fromCity = obj.city;
//        storage.set(yhyKey.fromCity, $scope.fromCity);
//        $state.go('xing-cfd');
//    };
//
//    $scope.selectCity = function(city) {
//        //$scope.keyword = selectKeyword;
//        var preUrl = storage.get("preUrl");
//        if (!preUrl) {
//            preUrl = "lineList";
//        }
//
//        var params = {
//            keyword: null,
//            cityCode: city.id
//        };
//        // 需要跳转回去
//        var reurl = storage.get('reurl');
//        if(reurl != null && reurl != "") {
//            storage.set(storage.get('cityCodeKey'), city.id);
//            storage.set(storage.get('cityNameKey'), city.name);
//            storage.remove('reurl'); // 移除本次跳转
//            window.location.href = reurl;
//        } else {
//            $state.go(preUrl, {params: JSON.stringify(params)});
//        }
//    };
//
//    $scope.cancelSearchCity = function() {
//        $("#xingcitysearchDiv").hide();
//        $("#xingcityDiv").show();
//    };
//
//    //$scope.keyup = function (e) {
//    //    var keycode = window.event ? e.keyCode : e.which;
//    //    if (keycode == 13) {
//    //        selectCitys();
//    //    }
//    //};
//
//
//}]);