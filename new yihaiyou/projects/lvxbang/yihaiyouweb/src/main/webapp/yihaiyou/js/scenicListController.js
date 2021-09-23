/**
 * Created by dy on 2016/8/3.
 */
var scenicListModule = angular.module("scenicListModule", ['infinite-scroll']);

scenicListModule.controller("scenicListCtrl", function ($scope, $http, $state, storage, $stateParams, $rootScope) {
    var params = $stateParams.params;
    var obj = JSON.parse(params);
    $scope.cityCode = "";
    $scope.cityName = "";
    $scope.cityTempName= "";
    var objCityCode = null;
    var objSort = null;
    var objSortOrder = null;
    var objLabel = null;
    var objLabelId = null;
    var keyword = null;
    var objTicketNearLng = null;
    var objTicketNearLat = null;
    var objDistance = null;
    $scope.tempTheme = [];
    if (obj) {
        objCityCode = obj.cityCode;
        if (objCityCode) {
            $scope.cityCode = objCityCode;
        } else if (obj.ticketNearLng && obj.ticketNearLat) {
            objTicketNearLng = obj.ticketNearLng;
            objTicketNearLat = obj.ticketNearLat;
            objDistance = obj.distance;
            //$scope.cityName = obj.scenicListTitle;
            $scope.cityTempName = obj.scenicListTitle;
        } else if (obj.keyword) {
            keyword = obj.keyword;
            $scope.cityTempName = obj.keyword;
        }
        objSort = obj.sort;
        if (!obj.sortOrder) {
            objSortOrder = "asc";
        } else {
            objSortOrder = obj.sortOrder;
        }
        objLabel = obj.label;
        if (objLabel != null && objLabel != 'undefined') {
            objLabel = decodeURIComponent(objLabel);
            $scope.tempTheme.push(objLabel);
        }
        if (obj.labelId != null && obj.labelId != 'undefined') {
            objLabelId = obj.labelId;
        }
    }

    $scope.goToSearch = function(){
        storage.set("preUrl", "scenicList");
        $state.go("search",{});
    };

    $scope.productAttr = "门票";

    $scope.destinationFlag = false;
    $scope.priceFlag = false;
    $scope.featuresFlag = false;
    $scope.adviseFlag = false;
    $scope.darkShow = false;

    $scope.clickFooter = function(index) {
        if (index == 1) {
            $scope.destinationFlag = true;
            $scope.darkShow = true;
            $scope.priceFlag = false;
            $scope.featuresFlag = false;
            $scope.adviseFlag = false;
        } else if (index == 2) {
            $scope.darkShow = true;
            $scope.destinationFlag = false;
            $scope.priceFlag = true;
            $scope.featuresFlag = false;
            $scope.adviseFlag = false;
        } else if (index == 3) {
            $scope.darkShow = true;
            $scope.destinationFlag = false;
            $scope.priceFlag = false;
            $scope.featuresFlag = true;
            $scope.adviseFlag = false;
        } else if (index == 4) {
            $scope.darkShow = true;
            $scope.destinationFlag = false;
            $scope.priceFlag = false;
            $scope.featuresFlag = false;
            $scope.adviseFlag = true;
        }
        if (index == 1) {
            $("div[ng-controller]").removeClass('bodystop');
        } else {
            $("div[ng-controller]").addClass('bodystop');
        }
    };

    $scope.hideFooter = function() {
        $scope.darkShow = false;
        $scope.destinationFlag = false;
        $scope.priceFlag = false;
        $scope.featuresFlag = false;
        $scope.adviseFlag = false;
        $("div[ng-controller]").removeClass('bodystop');
    }

    // 个人中心
    $scope.personal = function() {
        //$state.go("myteam", {}, {reload: true});
        location.href = yhyUrl.personal;
    };


    //列表页切换
    $scope.tabChange = function(tag) {
        if (tag == 1) {
            var params = storage.get("params");
            params['tag'] = 1;
            $state.go("lineList", {params: JSON.stringify(params)});
        } else if (tag == 2) {
            var params = storage.get("params");
            params['tag'] = 2;
            $state.go("lineList", {params: JSON.stringify(params)});
        } else if (tag == 3) {
            var params = storage.get("params");
            params['tag'] = 3;
            $state.go("lineList", {params: JSON.stringify(params)});
        } else if(tag == 4) {
            var params = storage.get("params");
            params['tag'] = 4;
            $state.go("lineList", {params: JSON.stringify(params)});
        } else if(tag == 5) {
            var params = storage.get("params");
            $state.go("scenicList", {params: JSON.stringify(params)});
        }
    };



//var cityId = "350200";
    var cityId = $scope.cityCode;
    var fatherId = 0;
    if (cityId != null) {
        var cityIdStr = cityId.toString();
        fatherId = cityIdStr.substr(0, cityIdStr.length-4) + "0000";
    }

    //目的地列表加载
    $http.post(yhyUrl.mXianlu_areaList, {'level':'1', 'fatherId': '100000'}).success(
        function(data) {
            $scope.provinceList = [];

            if (fatherId != 0) {

                angular.forEach(data.rows, function(item) {
                    var provinceObj = {};
                    provinceObj['id'] = item.id;
                    provinceObj['name'] = item.name;
                    if (fatherId == item.id) {
                        provinceObj['selectClass'] = 'secnicList_select_province_li';
                    } else {
                        provinceObj['selectClass'] = "";
                    }
                    $scope.provinceList.push(provinceObj);
                });


                $http.post(yhyUrl.mXianlu_areaList, {'level':'2', 'fatherId': fatherId}).success(
                    function(data) {
                        $scope.cityList = [];
                        angular.forEach(data.rows, function(item) {
                            var cityObj = {};
                            cityObj['id'] = item.id;
                            cityObj['name'] = item.name;
                            if (cityId == item.id) {
                                cityObj['master'] = true;
                                cityObj['cityClass'] = 'pro_green';
                                $scope.cityName = item.name;
                                $scope.cityTempName = item.name;
                            } else {
                                cityObj['master'] = false;
                            }
                            //console.log(cityObj);
                            $scope.cityList.push(cityObj);
                        });
                    }
                );
            } else {
                $scope.provinceList = data.rows;
                $http.post(yhyUrl.mXianlu_areaList, {'level':'2', 'fatherId': data.rows[0].id}).success(
                    function(data) {
                        $scope.cityList = data.rows;
                    }
                );
            }

            $scope.selectProvince = function(provinceId) {
                $(".provinceClass").removeClass("secnicList_select_province_li");
                $("#province_"+ provinceId +"").addClass("secnicList_select_province_li");
                //console.log(provinceId);
                $http.post(yhyUrl.mXianlu_areaList, {'level':'2', 'fatherId': provinceId}).success(
                    function(data) {
                        $scope.cityList = [];
                        angular.forEach(data.rows, function(item) {
                            var cityObj = {};
                            cityObj['id'] = item.id;
                            cityObj['name'] = item.name;
                            if (cityId == item.id) {
                                cityObj['master'] = true;
                                cityObj['cityClass'] = 'pro_green';
                                $scope.cityName = item.name;
                                $scope.cityTempName = item.name;
                            } else {
                                cityObj['master'] = false;
                            }
                            //console.log(cityObj);
                            $scope.cityList.push(cityObj);
                        });
                    }
                );
            }
        }
    );

    //目的地筛选
    var cityIdTempArr = [Number(cityId)];
    var cityIdTempArr = cityId;
    $scope.choseArr=[]; //定义数组用于存放前端显示
    $scope.cityNameArr=[]; //定义数组用于存放前端显示
    $scope.x=false; //默认未选中
    var oldTempCityId = cityId;
    $scope.chk= function (id, cityName) {//单选或者多选

        if (id) {
            //cityIdTempArr = id
            $scope.cityCode = id;
            $("#bingo_"+ id +"").addClass('pro_green');
            objTicketNearLng ="";
            objTicketNearLat="";
            objDistance="";
            //$scope.cityName = cityName;
            $scope.cityTempName = cityName;

            $("#bingo_"+ id +"").addClass('pro_green');
            if (oldTempCityId != id) {
                $("#bingo_"+ oldTempCityId +"").removeClass('pro_green');
            }
            oldTempCityId = id;
        }
    };


    /**
     * 特色筛选处理
     * @param index
     */
    $scope.locationFlag = false;
    $scope.themeFlag = false;
    $scope.selectFeatures = function(index) {
        if (index == 1) {
            $scope.locationFlag = true;
            $scope.themeFlag = false;
        } else {
            $scope.locationFlag = false;
            $scope.themeFlag = true;        }
    };
    $scope.selectFeatures(1);   //初始化特俗筛选


    /**
     * 选择位置
     * @type {number}
     */
    $scope.tempRegion = [];
    $scope.locationIndex = 1;
    $scope.selectLocation = function(index, region) {
        $scope.locationIndex = index;
        if (region) {
            $scope.tempRegion[0] = region;
        } else {
            $scope.tempRegion = [];
        }

    }


    /**
     * 获取景点主题
     * @type {Array}
     */
    $scope.themeList = [];
    $scope.getThemeList = function() {
        $http.post(yhyUrl.seceicThemes, {}).success(
            function(data) {
                if (data.success) {
                    $scope.themeList = data.theme;
                }
            }
        );
    };
    $scope.getThemeList();  //初始化景点主题

    /**
     * 选择主题
     * @type {number}
     */

    if (objLabelId) {
        $scope.themeIndex = objLabelId;
    } else {
        $scope.themeIndex = 0;
    }

    $scope.selectTheme = function(index, name) {
        $scope.themeIndex = index;
        if (name) {
            $scope.tempTheme[0] = name;
        } else {
            $scope.tempTheme = [];
        }

    }

    /**
     * 选择价格范围
     * @type {number}
     */
    $scope.maxPrice = -1;
    $scope.minPrice = -1;
    $scope.priceIndex = 0;
    $scope.selectPrice = function(index, minPrice, maxPrice) {
        $scope.priceIndex = index;
        $scope.maxPrice = maxPrice;
        $scope.minPrice = minPrice;
    };


    /**
     * 选择排序方式
     * @type {number}
     */
    $scope.sortIndex = 0;
    $scope.tempSort = "ranking";
    $scope.orderType = "asc";
    if (objSort) {
        $scope.tempSort = objSort;
        $scope.orderType = objSortOrder;
    }
    $scope.checkSort = function(index, tempSort, orderType) {
        $scope.sortIndex = index;
        $scope.tempSort = tempSort;
        $scope.orderType = orderType;
    };


    //清除筛选
    $scope.clearDestination = function() {

        //清除位置
        $scope.selectLocation(1);

        //清除主题
        $scope.selectTheme(0);

        //清除价格筛选
        $scope.selectPrice(0, -1, -1);

        //清除排序条件筛选
        $scope.checkSort(0, 'ranking', 'asc');
    };


    $scope.page = 1;
    $scope.nomore = false;
    $rootScope.loading = false;
    $scope.scenicList = [];
    $scope.searchScenicList = function() {
        $scope.page = 1;
        $scope.nomore = false;
        $rootScope.loading = false;
        $scope.scenicList = [];
        $scope.getScenicList();
    };

    $scope.getScenicList = function() {
        if ($rootScope.loading) return;
        if ($scope.nomore) return;
        $rootScope.loading = true;

        var priceRange = [];
        if ($scope.minPrice >= 0) {
            priceRange.push($scope.minPrice);
        }
        if ($scope.maxPrice >= 0) {
            priceRange.push($scope.maxPrice)
        }

        var tempCityids = [];
        if ($scope.cityCode) {
            tempCityids.push($scope.cityCode);
            var params = {
                cityCode:$scope.cityCode,
                keyword:null
            };
            storage.set("params", params);
        }

        if (keyword) {
            var params = {
                cityCode:null,
                keyword:keyword
            };
            storage.set("params", params);
        }



        var requestParams = {
            cityIds: tempCityids,
            name:keyword,
            region:$scope.tempRegion,
            themes:$scope.tempTheme,
            priceRange:priceRange,
            orderColumn: $scope.tempSort,
            orderType: $scope.orderType,
            lng:objTicketNearLng,
            lat:objTicketNearLat,
            distance:objDistance
        };


        $http.post(yhyUrl.mScenicList, {
            json: JSON.stringify(requestParams),
            pageNo: $scope.page,
            labelName:objLabel,
            pageSize: 10
        }).success(function (data) {
            if (data.success) {
                angular.forEach(data.scenicList, function (item) {
                    $scope.scenicList.push(item);
                });
                $scope.page++;
                $scope.nomore = data.nomore;
                $scope.hideFooter();
                $scope.cityName = $scope.cityTempName;
            }
            $rootScope.loading = false;
        }).error(function (data) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message:'系统错误'
            })
        });
    };
});

