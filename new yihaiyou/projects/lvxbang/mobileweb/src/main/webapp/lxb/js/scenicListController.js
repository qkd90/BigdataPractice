/**
 * Created by dy on 2016/8/3.
 */
var scenicListModule = angular.module("scenicListModule", ['infinite-scroll']);

scenicListModule.controller("scenicListCtrl", function($scope, $http, $state, storage, $stateParams) {
    var params = $stateParams.params;
    var obj = JSON.parse(params);
    $scope.cityCode = "";
    $scope.cityName = "";
    $scope.cityTempName= "";
    var objCityCode = null;
    var objSort = null;
    var objSortOrder = null;
    var objLabel = null;
    var keyword = null;
    var objTicketNearLng = null;
    var objTicketNearLat = null;
    var objDistance = null;
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
        if (objLabel) {
            objLabel = decodeURIComponent(objLabel);
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
    };

    $scope.hideFooter = function() {
        $scope.darkShow = false;
        $scope.destinationFlag = false;
        $scope.priceFlag = false;
        $scope.featuresFlag = false;
        $scope.adviseFlag = false;
    }

    // 个人中心
    $scope.personal = function() {
        //$state.go("myteam", {}, {reload: true});
        location.href = GetUrl.personal;
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
    $http.post(LXB_URL.mXianlu_areaList, {'level':'1', 'fatherId': '100000'}).success(
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


                $http.post(LXB_URL.mXianlu_areaList, {'level':'2', 'fatherId': fatherId}).success(
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
                $http.post(LXB_URL.mXianlu_areaList, {'level':'2', 'fatherId': data.rows[0].id}).success(
                    function(data) {
                        $scope.cityList = data.rows;
                    }
                );
            }

            $scope.selectProvince = function(provinceId) {
                $(".provinceClass").removeClass("secnicList_select_province_li");
                $("#province_"+ provinceId +"").addClass("secnicList_select_province_li");
                //console.log(provinceId);
                $http.post(LXB_URL.mXianlu_areaList, {'level':'2', 'fatherId': provinceId}).success(
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
        //$("#bingo_"+ oldTempCityId +"").removeClass('pro_green');

        //console.log(cityIdTempArr);
        //if (x == true) {//选中
        //    cityIdTempArr.push(z);
        //} else {
        //for (var i=0; i<cityIdTempArr.length; i++) {
        //    if (cityIdTempArr[i] == z) {
        //        cityIdTempArr.splice(i, 1);
        //    }
        //}

        //}
    };


    //主题筛选
    $scope.theme0 = true;
    $scope.theme1 = false;
    $scope.theme2 = false;
    $scope.theme3 = false;
    $scope.theme4 = false;
    $scope.theme5 = false;
    $scope.theme6 = false;
    $scope.theme7 = false;
    $scope.theme8 = false;
    $scope.theme9 = false;
    $scope.theme10 = false;
    $scope.theme11 = false;
    $scope.theme12 = false;

    var tempTheme = ["all", -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1];
    $("#theme0").addClass("pro_green");
    $scope.checkTheme = function(index, theme, isChecked) {
        if (isChecked) {
            if (index != 0 && $scope.theme0) {
                $scope.theme0 = false;
                tempTheme[0] = -1;
                $("#theme0").removeClass("pro_green");
            } else if (index == 0) {
                $scope.theme0 = true;
                $scope.theme1 = false;
                $scope.theme2 = false;
                $scope.theme3 = false;
                $scope.theme4 = false;
                $scope.theme5 = false;
                $scope.theme6 = false;
                $scope.theme7 = false;
                $scope.theme8 = false;
                $scope.theme9 = false;
                $scope.theme10 = false;
                $scope.theme11 = false;
                $scope.theme12 = false;
                tempTheme = ["all", -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]
                $.each(tempTheme, function(i, perValue) {
                    if (i != 0) {
                        $("#theme"+ i +"").removeClass("pro_green");
                    } else {
                        $("#theme"+ i +"").addClass("pro_green");
                    }
                });
            }
            tempTheme[index] = theme;
            $("#theme" + index + "").addClass('pro_green');
        } else {
            tempTheme[index] = -1;
            $("#theme" + index + "").removeClass('pro_green');
        }
    };


    //价格筛选
    $scope.price=0;
    var maxPrice = -1;
    var minPrice = -1;
    $("#price0").addClass("pro_green");
    $scope.checkPrice = function(index) {
        if (index == 1) {
            maxPrice = 50;
            minPrice = 0;
            $("#price1").addClass("pro_green");
        } else if (index == 2) {
            maxPrice = 100;
            minPrice = 50;
            $("#price2").addClass("pro_green");
        } else if (index == 3) {
            maxPrice = -1;
            minPrice = 100;
            $("#price3").addClass("pro_green");
        } else {
            maxPrice = -1;
            minPrice = -1;
            $("#price0").addClass("pro_green");
        }

        for (var i = 0; i < 4; i++) {
            if (i != index) {
                $("#price"+i+"").removeClass("pro_green");
            }
        }
        //console.log("maxPrice=" + maxPrice + ", minPrice=" + minPrice);
    };

    //排序条件筛选
    $scope.sort = 0;
    var tempSort = "ranking";
    var orderType = "asc";
    if (objSort) {
        tempSort = objSort;
        orderType = objSortOrder;
    }
    $("#sort0").addClass("pro_green");
    $scope.checkSort = function(index) {
        if (index == 1) {
            tempSort = "orderNum";
            orderType = "desc";
            $("#sort1").addClass("pro_green");
        } else if (index == 2) {
            tempSort = "productScore";
            orderType = "desc";
            $("#sort2").addClass("pro_green");
        } else if (index == 3) {
            tempSort = "price";
            orderType = "desc";
            $("#sort3").addClass("pro_green");
        } else if (index == 4) {
            tempSort = "price";
            orderType = "asc";
            $("#sort4").addClass("pro_green");
        } else {
            tempSort = "ranking";
            orderType = "desc";
            $("#sort0").addClass("pro_green");
        }
        for (var i = 0; i < 5; i++) {
            if (i != index) {
                $("#sort"+i+"").removeClass("pro_green");
            }
        }
        //console.log("tempSort=" + tempSort );
    };


    //清除筛选
    $scope.clearDestination = function() {
        $scope.theme0 = true;
        $scope.theme1 = false;
        $scope.theme2 = false;
        $scope.theme3 = false;
        $scope.theme4 = false;
        $scope.theme5 = false;
        $scope.theme6 = false;
        $scope.theme7 = false;
        $scope.theme8 = false;
        $scope.theme9 = false;
        $scope.theme10 = false;
        $scope.theme11 = false;
        $scope.theme12 = false;
        tempTheme = ["all", -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1];

        //清除价格筛选
        $scope.price=0;
        maxPrice = "";
        minPrice = "";

        //清除排序条件筛选
        $scope.sort = 0;
        tempSort = "ranking";
    };


    $scope.page = 1;
    $scope.nomore = false;
    $scope.loading = false;
    $scope.scenicList = [];
    $scope.searchScenicList = function() {
        $scope.page = 1;
        $scope.nomore = false;
        $scope.loading = false;
        $scope.scenicList = [];
        $scope.getScenicList();
    };

    $scope.getScenicList = function() {

        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        var themeArr = [];
        angular.forEach(tempTheme, function(item) {
            if (item != -1 && item != "all") {
                themeArr.push(item);
            }
        });

        var priceRange = [];
        if (minPrice >=0) {
            priceRange.push(minPrice);
        }
        if (maxPrice >= 0) {
            priceRange.push(maxPrice)
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
            themes:themeArr,
            priceRange:priceRange,
            orderColumn: tempSort,
            orderType: orderType,
            lng:objTicketNearLng,
            lat:objTicketNearLat,
            distance:objDistance
        }


        $http.post(LXB_URL.mScenicList, {
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
            $scope.loading = false;
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


})

