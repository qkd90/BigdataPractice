/**
 * Created by dy on 2016/7/19.
 */
var lineListModule = angular.module('lineListModule', ['infinite-scroll']);

lineListModule.controller('lineListController', function ($scope, $state, $http, $location, storage, $stateParams) {

    //doRequestLineList("350200", "0", "", "satisfaction");




    $scope.destination = "";
    //目的地初始化
    var params = $stateParams.params;

    //var obj = params.parseJSON();
    var obj = JSON.parse(params);

    var cityId = obj.cityCode;
    var keyword = obj.keyword;
    var tag = obj.tag;
    if (keyword != null) {
        $scope.destination = keyword;
        $scope.tabKeyword = keyword;
    }
    if (cityId) {
        $scope.cityCode = cityId;
        $scope.tabKeyword = null;
    }

    var fatherId = 0;
    if (cityId != null) {
        var cityIdStr = cityId.toString();
        fatherId = cityIdStr.substr(0, cityIdStr.length-4) + "0000";
    }
    $scope.tabName = "精品定制";
    //var cityId = "350200";
    $scope.jingpinShow = true;
    $scope.zizhuShow = false;
    $scope.zijiaShow = false;
    $scope.gentuanShow = false;

    if (tag != null) {
        $(".swiper-slide").children().removeClass("top_list_bg");
        if (tag == 1) {
            $scope.tabName = "精品定制";
            $scope.jingpinShow = true;
            $scope.zizhuShow = false;
            $scope.zijiaShow = false;
            $scope.gentuanShow = false;
            $("#swiper-slide_1").children("span").addClass("top_list_bg");
        } else if (tag == 2) {
            $scope.tabName = "自助游";
            $scope.jingpinShow = false;
            $scope.zizhuShow = true;
            $scope.zijiaShow = false;
            $scope.gentuanShow = false;
            $("#swiper-slide_2").children("span").addClass("top_list_bg");
        } else if (tag == 3) {
            $scope.tabName = "自驾游";
            $scope.jingpinShow = false;
            $scope.zizhuShow = false;
            $scope.zijiaShow = true;
            $scope.gentuanShow = false;
            $("#swiper-slide_3").children("span").addClass("top_list_bg");
        } else {
            $scope.tabName = "跟团游";
            $scope.jingpinShow = false;
            $scope.zizhuShow = false;
            $scope.zijiaShow = false;
            $scope.gentuanShow = true;
            $("#swiper-slide_4").children("span").addClass("top_list_bg");
        }


        //$(".top_list li").eq(tag - 1).addClass("top_list_bg").siblings().removeClass("top_list_bg");
        //$(".produc_contain").eq(tag - 1).show().siblings(".produc_contain").hide();
    }

    $scope.goToSearch = function(){
        storage.set("preUrl", "lineList");
        $state.go("search",{});
    };

    //列表页切换
    $scope.tabChange = function(tag) {
        if (tag == 1) {
            $scope.tabName = "精品定制";
            $scope.listLine(1);
            $scope.jingpinShow = true;
            $scope.zizhuShow = false;
            $scope.zijiaShow = false;
            $scope.gentuanShow = false;
            $("#jingpinLi").addClass("top_list_bg");
            $("#zizhuLi").removeClass("top_list_bg");
            $("#zijiaLi").removeClass("top_list_bg");
            $("#gentuanLi").removeClass("top_list_bg");
        } else if (tag == 2) {
            $scope.tabName = "自助游";
            $scope.listLine(2);
            $scope.jingpinShow = false;
            $scope.zizhuShow = true;
            $scope.zijiaShow = false;
            $scope.gentuanShow = false;
            $("#jingpinLi").removeClass("top_list_bg");
            $("#zizhuLi").addClass("top_list_bg");
            $("#zijiaLi").removeClass("top_list_bg");
            $("#gentuanLi").removeClass("top_list_bg");
        } else if (tag == 3) {
            $scope.tabName = "自驾游";
            $scope.listLine(3);
            $scope.jingpinShow = false;
            $scope.zizhuShow = false;
            $scope.zijiaShow = true;
            $scope.gentuanShow = false;
            $("#jingpinLi").removeClass("top_list_bg");
            $("#zizhuLi").removeClass("top_list_bg");
            $("#zijiaLi").addClass("top_list_bg");
            $("#gentuanLi").removeClass("top_list_bg");
        } else if(tag == 4) {
            $scope.tabName = "跟团游";
            $scope.listLine(4);
            $scope.jingpinShow = false;
            $scope.zizhuShow = false;
            $scope.zijiaShow = false;
            $scope.gentuanShow = true;
            $("#jingpinLi").removeClass("top_list_bg");
            $("#zizhuLi").removeClass("top_list_bg");
            $("#zijiaLi").removeClass("top_list_bg");
            $("#gentuanLi").addClass("top_list_bg");
        } else if(tag == 5) {
            var params = storage.get("params");
            $state.go("scenicList", {params: JSON.stringify(params)});
           //console.log(obj.cityCode);
           // console.log($scope.destination);
        }
    };


    //目的地列表加载
    $http.post(LXB_URL.mXianlu_areaList, {'level':'1', 'fatherId': '100000'}).success(
        function(data) {
            $scope.provinceList = [];
            //$scope.provinceList = data.rows;
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
                        var tempCityId = 0;
                        angular.forEach(data.rows, function(item) {
                            var cityObj = {};
                            cityObj['id'] = item.id;
                            cityObj['name'] = item.name;
                            if (cityId == item.id) {
                                cityObj['master'] = true;
                                cityObj['cityClass'] = 'pro_green';
                                $scope.destination = item.name;
                                $scope.selCityId = item.id;
                                //tempCityId = item.id;
                                //$scope.greenClass = "pro_green";
                            } else {
                                cityObj['master'] = false;
                                cityObj['cityClass'] = '';
                            }
                            //console.log(cityObj);
                            $scope.cityList.push(cityObj);
                            //$("#bingo_"+ tempCityId +"").addClass('pro_green');
                        });

                    }
                );
            } else {
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
                        $scope.cityList = data.rows;
                    }
                );
            }
        }
    );


    //if (cityId != null) {
    //    $("#bingo_"+ cityId +"").addClass('pro_green');
    //}


    //**************************点击底栏筛选按钮*******************
    $scope.destinationFlag = false;
    $scope.daysFlag = false;
    $scope.featuresFlag = false;
    $scope.adviseFlag = false;
    $scope.darkDivShow = false;
    $scope.clickFooter = function(index) {
        if (index == 1) {
            $scope.destinationFlag = true;
            $scope.darkDivShow = true;
            $scope.daysFlag = false;
            $scope.featuresFlag = false;
            $scope.adviseFlag = false;
        } else if (index == 2) {
            $scope.darkDivShow = true;
            $scope.destinationFlag = false;
            $scope.daysFlag = true;
            $scope.featuresFlag = false;
            $scope.adviseFlag = false;
        } else if (index == 3) {
            $scope.darkDivShow = true;
            $scope.destinationFlag = false;
            $scope.daysFlag = false;
            $scope.featuresFlag = true;
            $scope.adviseFlag = false;
        } else if (index == 4) {
            $scope.darkDivShow = true;
            $scope.destinationFlag = false;
            $scope.daysFlag = false;
            $scope.featuresFlag = false;
            $scope.adviseFlag = true;
        }
    };
    $scope.hideFooter = function() {
        $scope.darkDivShow = false;
        $scope.destinationFlag = false;
        $scope.daysFlag = false;
        $scope.featuresFlag = false;
        $scope.adviseFlag = false;
    }



//#####################底栏条件筛选################
    var cityIdTemp = null;
    var oldCityIdTemp = null;
    if (cityId != null) {
        cityIdTemp = cityId;
        oldCityIdTemp = cityId;
    }
    var cityTempName = ""
    $scope.chk= function (id, name) {//单选或者多选
        oldCityIdTemp = cityIdTemp;
        $scope.cityCode = cityIdTemp;
        cityTempName = name;
        if (id) {//选中
            $("#bingo_"+ id +"").addClass('pro_green');

            if (cityIdTemp != id) {
                $("#bingo_"+ oldCityIdTemp +"").removeClass('pro_green');
            }
            cityIdTemp = id;
        }

    };


    $scope.isDay0=true;
    $scope.isDay1=false;
    $scope.isDay2=false;
    $scope.isDay3=false;
    $scope.isDay4=false;
    $scope.isDay5=false;
    $scope.isDay6=false;
    $scope.isDay7=false;
    $scope.isDay8=false;
    $scope.isDay9=false;
    $scope.isDay10=false;


    //时间天数筛选
    var choseDayTempArr=[0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]; //定义数组用于存放前端显示
    $("#day0").addClass('pro_green');
    $scope.checkDay= function (day, index, isChecked) {//单选或者多选
        if (isChecked) {
            if (index != 0 && $scope.isDay0) {
                $scope.isDay0 = false;
                choseDayTempArr[0] = -1;
                $("#day0").removeClass('pro_green');
            } else if (index == 0) {
                $scope.isDay0=true;
                $scope.isDay1=false;
                $scope.isDay2=false;
                $scope.isDay3=false;
                $scope.isDay4=false;
                $scope.isDay5=false;
                $scope.isDay6=false;
                $scope.isDay7=false;
                $scope.isDay8=false;
                $scope.isDay9=false;
                $scope.isDay10=false;
                choseDayTempArr=[0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1];
                $("#day" + index + "").addClass('pro_green');
                for (var i=0; i<choseDayTempArr.length; i++) {
                    if (i > 0) {
                        $("#day" + i + "").removeClass('pro_green');
                    }
                }
            }
            choseDayTempArr[index] = day;
            $("#day" + index + "").addClass('pro_green');
            //console.log($("#day" + index + ""));
        } else {
            choseDayTempArr[index] = -1;
            $("#day" + index + "").removeClass('pro_green');
        }
        //console.log(choseDayTempArr);
    };


    $scope.cheFeature="纯玩无购物";
    $("#feature0").addClass('pro_green');
    //线路特色筛选
    var choseFeature=""; //定义数组用于存放前端显示
    $scope.checkFeature= function (index, features) {//单选或者多选
        if (features) {//选中
            choseFeature = features;
            for (var i=0; i<3; i++) {
                if (index == i) {
                    $("#feature"+ i +"").addClass('pro_green');
                } else {
                    $("#feature"+ i +"").removeClass('pro_green');
                }
            }
        }
    };

    $scope.sort=0;
    $("#sort0").addClass('pro_green');
    //线路特色筛选
    var choseSort=""; //定义数组用于存放前端显示
    $scope.checkSort= function (index, sort) {//单选或者多选
        if (sort) {//选中
            choseSort = sort;
            for (var i=0; i<5; i++) {
                if (index == i) {
                    $("#sort"+ i +"").addClass('pro_green');
                } else {
                    $("#sort"+ i +"").removeClass('pro_green');
                }
            }
        }
    };

    //清除目的地筛选
    $scope.clearDestination = function() {

        //清空目的地
        $("#bingo_"+ cityId +"").addClass('pro_green');
        $scope.selCityId = cityId;

        //清空时间天数
        $scope.isDay0=true;
        $scope.isDay1=false;
        $scope.isDay2=false;
        $scope.isDay3=false;
        $scope.isDay4=false;
        $scope.isDay5=false;
        $scope.isDay6=false;
        $scope.isDay7=false;
        $scope.isDay8=false;
        $scope.isDay9=false;
        $scope.isDay10=false;
        choseDayTempArr=[0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1];
        $("#day0").addClass('pro_green');
        for (var i=0; i<choseDayTempArr.length; i++) {
            if (i > 0) {
                $("#day" + i + "").removeClass('pro_green');
            }
        }

        //线路特色筛选
        $scope.cheFeature="纯玩无购物";
        choseFeature="";
        for (var i=0; i<3; i++) {
            if (0 == i) {
                $("#feature"+ i +"").addClass('pro_green');
            } else {
                $("#feature"+ i +"").removeClass('pro_green');
            }
        }

        //线路特色筛选
        $scope.sort=0;
        choseSort="";
        for (var i=0; i<5; i++) {
            if (0 == i) {
                $("#sort"+ i +"").addClass('pro_green');
            } else {
                $("#sort"+ i +"").removeClass('pro_green');
            }
        }

    }

    $scope.productAttr = "";
    $scope.cityIds = cityId;
    $scope.days = "0";
    $scope.features = "";
    $scope.sort = "satisfaction";
    $scope.busy = false;

    $scope.page1 = 1;
    $scope.loading1 = false;
    $scope.nomore1 = false;
    $scope.lineList1 = [];


    $scope.page2 = 1;
    $scope.loading2 = false;
    $scope.nomore2 = false;
    $scope.lineList2 = [];

    $scope.page3 = 1;
    $scope.loading3 = false;
    $scope.nomore3 = false;
    $scope.lineList3 = [];

    $scope.page4 = 1;
    $scope.loading4 = false;
    $scope.nomore4 = false;
    $scope.lineList4 = [];

    $scope.listLine = function(tab) {
        var cityIds = "";
        if (cityIdTemp) {
            cityIds = cityIdTemp;
            $scope.cityCode = cityIds;
            var params = {
                cityCode:cityIds,
                keyword:null
            };
            storage.set("params", params);
            keyword = "";
        } else {
            keyword = $scope.destination;
            var params = {
                cityCode:null,
                keyword:keyword
            };
            storage.set("params", params);
        }
        if (cityTempName) {
            $scope.destination = cityTempName;
            keyword = $scope.destination;
            var params = {
                cityCode:null,
                keyword:cityTempName
            };
            storage.set("params", params);
        }

        //storage.set("tab_cityCode", );
        //$scope.destination = cityIdTemp;

        var tempDays = [];
        for (var i=0; i<choseDayTempArr.length; i++) {
            if (i != 0) {
                if (choseDayTempArr[i] != -1) {
                    tempDays.push(choseDayTempArr[i]);
                }
            }
        }

        $scope.busy = false;
        $scope.page = 1;

        if (tab == 1) {
            $scope.productAttr = "精品定制";
        } else if (tab  == 2) {
            $scope.productAttr = "自助游";
        } else if (tab  == 3) {
            $scope.productAttr = "自驾游";
        } else {
            $scope.productAttr = "跟团游";
        }

        if ($scope['loading'+tab]) return;
        if ($scope['nomore'+tab]) return;
        $scope['loading'+tab] = true;
        $http.post(LXB_URL.mXianlu_list, {
            'productAttr': $scope.productAttr,
            'cityIds': cityIds,
            'days': tempDays.join(","),
            'features': choseFeature,
            'sort': choseSort,
            'name':keyword,
            page: $scope['page'+tab],
            rows: 10
        }).success(function (data) {
            if (data.success) {
                angular.forEach(data.lineList, function (item) {
                    $scope['lineList'+tab].push(item);
                });
                $scope['page'+tab]++;
                $scope['nomore'+tab] = data.nomore;
            } else {
                $scope['nomore'+tab] = data.nomore;
            }
            $scope['loading'+tab] = false;
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


    $scope.searchLineList = function() {

        $scope.page1 = 1;
        $scope.loading1 = false;
        $scope.nomore1 = false;
        $scope.lineList1.splice(0, $scope.lineList1.length);


        $scope.page2 = 1;
        $scope.loading2 = false;
        $scope.nomore2 = false;
        $scope.lineList2.splice(0, $scope.lineList2.length);

        $scope.page3 = 1;
        $scope.loading3 = false;
        $scope.nomore3 = false;
        $scope.lineList3.splice(0, $scope.lineList3.length);

        $scope.page4 = 1;
        $scope.loading4 = false;
        $scope.nomore4 = false;
        $scope.lineList4.splice(0, $scope.lineList4.length);


        $scope.listLine(1);
        $scope.listLine(2);
        $scope.listLine(3);
        $scope.listLine(4);
        $scope.hideFooter();

    };

    $scope.personal = function () {
        location.href = GetUrl.personal;
    };



    //$('body').delegate(".pro_desDay_check", "click", function(){
    //    if ($(this).attr('checked')){
    //        $('.bingo1').addClass('pro_green');
    //    }
    //});


});


/*

*/
/**
 * 精品定制
 *//*

lineListModule.controller('line_custommade', function ($scope, $http, $location) {
    $http.post(LXB_URL.mXianlu_list, {'lineSearchRequest.productAttr':'精品定制'}).success(
        function(data) {
            $scope.custommadeList = data;
        }
    );
});

*/
/**
 * 自助游数据
 *//*

lineListModule.controller('line_ziyou', function ($scope, $http, $location) {
    $http.post(LXB_URL.mXianlu_list, {'lineSearchRequest.productAttr':'自助游'}).success(
        function(data) {
            $scope.ziyouList = data;
        }
    );
});

*/
/*

lineListModule.controller('line_zijia', function ($scope, $http, $location) {
    $http.post(LXB_URL.mXianlu_list, {'lineSearchRequest.productAttr':'自驾游'}).success(
        function(data) {
            $scope.zijiaList = data;
        }
    );
});

*/
/**
 * 跟团游数据
 */
 /*

lineListModule.controller('line_gentuan', function ($scope, $http, $location) {
    $http.post(LXB_URL.mXianlu_list, {'lineSearchRequest.productAttr':'跟团游'}).success(
        function(data) {
            $scope.gentuanList = data;
        }
    );
});
*/

/**
 * 目的地
 */
/*lineListModule.controller('line_province_destination', function ($scope, $http, $location) {

});*/




