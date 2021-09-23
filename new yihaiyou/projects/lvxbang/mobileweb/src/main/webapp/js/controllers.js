/**
 * 这里是印象列表模块
 */
var yxlistModule = angular.module("yxlistModule", ['infinite-scroll']);
yxlistModule.controller('yxlistCtrl', function ($scope, $http) {
    $scope.pageNo = 1;
    $scope.pageSize = 10;
    $scope.loading = false;
    $scope.nomore = false;
    $scope.yxlist = [];

    $scope.impressionList = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(GetUrl.yxlist, {
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            if (data.success) {
                angular.forEach(data.impressionList, function (impr) {
                    $scope.yxlist.push(impr);
                });
                $scope.nomore = data.nomore;
                if (!$scope.nomore) {
                    $scope.pageNo += 1;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            alert(data.msg)
        });
    }
});
/**
 * 这里是印象详情模块
 */
var yxdetailModule = angular.module("yxdetailModule", []);
yxdetailModule.controller('yxdetailCtrl', function ($scope, $http, $stateParams) {
    var imprId = $stateParams.id;
    $http.post(GetUrl.yxdetail, {
        imprId: imprId
    }).success(function (data) {
        if (data.success) {
            $scope.yxdetail = data.impression;
        }
    }).error(function (data) {
        alert(data.msg)
    });
});
/**
 * 写印象模块
 * @type {angular.Module|*}
 */
var xieganxiangModule = angular.module("xieganxiangModule", []);
xieganxiangModule.controller("xieganxiangCtrl", function ($scope, $http, storage, $state, Check) {
    $scope.place = storage.get(keys.place) == undefined ? {} : storage.get(keys.place);
    $scope.content = storage.get(keys.yxContent);
    $scope.gallery = storage.get(keys.yxPhoto) == undefined ? [] : storage.get(keys.yxPhoto);

    $scope.addPhoto = function () {
        $("#add-photo").click();
    };

    $("#add-photo").change(function () {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function () {
            var view = {};
            var i;
            $scope.$apply(function () {
                view = {
                    isView: true,
                    imgUrl: reader.result,
                    percent: 0
                };
                i = $scope.gallery.length;
                $scope.gallery.push(view);
            });
            var fd = new FormData();
            fd.append("img", file);
            fd.append("section", "impression");
            var request = new XMLHttpRequest();
            request.open("POST", GetUrl.uploadPhoto, true);
            request.onload = function () {
                var data = JSON.parse(request.response);
                if (data.success) {
                    $scope.$apply(function () {
                        var photo = data.photo;
                        $scope.gallery.splice(i, 1, photo);
                    });
                }
            };
            request.upload.onprogress = function (e) {
                if (e.lengthComputable) {
                    //更新页面显示效果
                    $scope.$apply(function () {
                        var percent = parseInt(100 * e.loaded / e.total);
                        view.percent = percent;
                    });
                }
            };
            $("#percent").removeClass("hidden");
            request.send(fd);
        };
        reader.readAsDataURL(file);
    });

    function uploading() {
        var uploading = false;
        angular.forEach($scope.gallery, function (gal) {
            if (gal.isView) {
                uploading = true;
                return false;
            }
        });
        return uploading;
    }

    $scope.lookPhoto = function () {
        if (uploading()) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "图片正在上传"
            });
            return;
        }
        storage.set(keys.yxContent, $scope.content);
        storage.set(keys.yxPhoto, $scope.gallery);
        $state.go("xieganxiang-pic");
    };

    $scope.selectPlace = function (type) {
        if (uploading()) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "图片正在上传"
            });
            return;
        }
        storage.set(keys.yxContent, $scope.content);
        storage.set(keys.yxPhoto, $scope.gallery);
        $state.go("search-fujin", {type: type});
    };

    $scope.submit = function () {
        if ($scope.gallery.length < 1) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "至少上传1张图片"
            });
            return;
        }
        if (uploading()) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "图片正在上传"
            });
            return;
        }
        if ($scope.place == undefined || $scope.place.name == undefined || $scope.place.name == "") {
            $("#jinggao").modal("show");
            return;
        }
        var json = {
            placeName: $scope.place.name,
            targetId: $scope.place.targetId,
            placeType: $scope.place.type,
            content: $scope.content,
            cover: "",
            type: 1
        };
        if ($scope.gallery.length > 0) {
            json.cover = $scope.gallery[0].imgUrl;
            $scope.gallery.splice(0, 1);
            angular.forEach($scope.gallery, function (photo) {
                delete photo.$$hashKey;
            });
            json.gallery = $scope.gallery;
        }
        $http.post(GetUrl.yxsave, {
            json: JSON.stringify(json)
        }).success(function (data) {
            if (Check.loginCheck((data))) {
                if (data.success) {
                    storage.remove(keys.place);
                    storage.remove(keys.yxContent);
                    storage.remove(keys.yxPhoto);
                    $state.go("yxdetail", {id: data.id});
                }
            }
        }).error(function (data) {
            alert(data.msg)
        });
    }
});
/**
 * 写印象图片浏览
 * @type {angular.Module|*}
 */
var xieganxiangpicModule = angular.module("xieganxiangpicModule", []);
xieganxiangpicModule.controller("xieganxiangpicCtrl", function ($scope, storage) {
    $scope.gallery = storage.get(keys.yxPhoto) == undefined ? [] : storage.get(keys.yxPhoto);
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        if ($scope.finish.over) {
            $('#gxpiclist').owlCarousel({
                singleItem: true,
                autoHeight: true,
                pagination: false,
                addClassActive: true,
                afterMove: function () {
                    $scope.$apply(function () {
                        var active = $(".owl-item.active");
                        $scope.index = active.index();
                    });
                }
            });
        }
    });

    $scope.delete = function () {
        $scope.gallery.splice($scope.index, 1);
        storage.set(keys.yxPhoto, $scope.gallery);
        $(".modal.in").modal("hide");
        location.reload();
    }
});
/**
 * 附近地点
 * @type {angular.Module|*}
 */
var searchfujinModule = angular.module("searchfujinModule", ['infinite-scroll']);
searchfujinModule.controller("searchfujinCtrl", function ($scope, $http, $state, storage, $stateParams) {
    $scope.selectType = $stateParams.type == undefined || "" ? "scenic" : $stateParams.type;
    if ($scope.selectType == "scenic") {
        $("#scenic-tab").addClass("active");
        $("#jingdian").addClass("active");
    }
    if ($scope.selectType == "hotel") {
        $("#hotel-tab").addClass("active");
        $("#jiudian").addClass("active");
    }
    if ($scope.selectType == "food") {
        $("#food-tab").addClass("active");
        $("#meishi").addClass("active");
    }
    $scope.pageSize = 10;
    $scope.loading = false;
    init();

    function init() {
        $scope.scenicPageNo = 1;
        $scope.hotelPageNo = 1;
        $scope.foodPageNo = 1;
        $scope.scenics = [];
        $scope.hotels = [];
        $scope.foods = [];
        $scope.scenicNomore = false;
        $scope.hotelNomore = false;
        $scope.foodNomore = false;
    }

    $scope.changePlaceType = function (type) {
        if ($scope.loading) return;
        init();
        $scope.selectType = type;
        if (type == "scenic") {
            $scope.scenicList();
        }
        if (type == "hotel") {
            $scope.hotelList();
        }
        if (type == "food") {
            $scope.foodList();
        }
    };

    $scope.scenicList = function () {
        if ($scope.loading) return;
        if ("scenic" != $scope.selectType) return;
        if ($scope.scenicNomore) return;
        $scope.loading = true;
        var json = {
            type: "scenic",
            longitude: 118.18134038910694,
            latitude: 24.49198529225134
            //longitude: $scope.longitude,
            //latitude: $scope.latitude
        };
        $http.post(GetUrl.placeList, {
            json: JSON.stringify(json),
            pageNo: $scope.scenicPageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            if (data.success) {
                angular.forEach(data.placeList, function (place) {
                    $scope.scenics.push(place);
                });
                $scope.scenicNomore = data.nomore;
                if (!$scope.scenicNomore) {
                    $scope.scenicPageNo += 1;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            alert(data.msg)
        });
    };

    $scope.hotelList = function () {
        if ($scope.loading) return;
        if ("hotel" != $scope.selectType) return;
        if ($scope.hotelNomore) return;
        $scope.loading = true;
        var json = {
            type: "hotel",
            longitude: 118.18134038910694,
            latitude: 24.49198529225134
            //longitude: $scope.longitude,
            //latitude: $scope.latitude
        };
        $http.post(GetUrl.placeList, {
            json: JSON.stringify(json),
            pageNo: $scope.hotelPageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            if (data.success) {
                angular.forEach(data.placeList, function (place) {
                    $scope.hotels.push(place);
                });
                $scope.hotelNomore = data.nomore;
                if (!$scope.hotelNomore) {
                    $scope.hotelPageNo += 1;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            alert(data.msg)
        });
    };

    $scope.foodList = function () {
        if ($scope.loading) return;
        if ("food" != $scope.selectType) return;
        if ($scope.foodNomore) return;
        $scope.loading = true;
        var json = {
            type: "food",
            longitude: 118.18134038910694,
            latitude: 24.49198529225134
            //longitude: $scope.longitude,
            //latitude: $scope.latitude
        };
        $http.post(GetUrl.placeList, {
            json: JSON.stringify(json),
            pageNo: $scope.foodPageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            if (data.success) {
                angular.forEach(data.placeList, function (place) {
                    $scope.foods.push(place);
                });
                $scope.foodNomore = data.nomore;
                if (!$scope.foodNomore) {
                    $scope.foodPageNo += 1;
                }
            }
            $scope.loading = false;
        }).error(function (data) {
            alert(data.msg)
        });
    };

    $scope.selectPlace = function (obj) {
        storage.set(keys.place, obj.place);
        $state.go("xieganxiang");
    }
});
/**
 * 搜索地点
 * @type {angular.Module|*}
 */
var searchdifangModule = angular.module("searchdifangModule", ['infinite-scroll']);
searchdifangModule.controller("searchdifangCtrl", function ($scope, $http, storage, $state, $timeout) {
    $scope.pageSize = 10;
    $scope.loading = false;
    init();

    function init() {
        $scope.pageNo = 1;
        $scope.nomore = false;
        $scope.places = [];
    }

    $scope.keyChange = function () {
        if ($scope.key == undefined || $scope.key == "") return;
        init();
        $scope.placeList();
    };

    $scope.placeList = function () {
        if ($scope.key == undefined || $scope.key == "") return;
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        var json = {
            keyword: $scope.key,
            type: "all"
        };
        $http.post(GetUrl.placeList, {
            json: JSON.stringify(json),
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            if (data.success) {
                angular.forEach(data.placeList, function (place) {
                    place.html = place.name.replace($scope.key, "<b>" + $scope.key + "</b>");
                    if (place.type == "scenic") {
                        place.class = "ico1";
                    }
                    if (place.type == "hotel") {
                        place.class = "ico2";
                    }
                    if (place.type == "food") {
                        place.class = "ico3";
                    }
                    $scope.places.push(place);
                });
                $scope.nomore = data.nomore;
            }
            $scope.loading = false;
        }).error(function (data) {
            alert(data.msg)
        });
    };

    $scope.selectPlace = function (obj) {
        storage.set(keys.place, obj.place);
        $state.go("xieganxiang");
    };

    $scope.addPlace = function (type) {
        var place = {
            name: $scope.key,
            targetId: 0,
            type: type
        };
        storage.set(keys.place, place);
        $(".modal.in").modal("hide");
        $timeout(function () {
            $state.go("xieganxiang");
        }, 400);
    }
});
/**
 * 这里是游记列表模块
 */
var YoujiModule = angular.module("YoujiModule", []);
YoujiModule.controller('YoujiListCtrl', function ($scope, $http) {
    var req = {
        method: 'GET',
        url: GetUrl.yjlist
    };
    $http(req).success(function (data) {
        $scope.yjlists = data.data;
    }).error(function (data) {
        alert(data.msg)
    });
});
/**
 * 这里是线路列表模块
 */
var xianluModule = angular.module("xianluModule", ['infinite-scroll']);
xianluModule.controller('xianluCtrl', function ($scope, $http, $stateParams, storage) {
    var request = storage.get(keys.xianluSearch) == undefined ? {
        orderColumn: "viewNum",
        orderType: "desc"
    } : storage.get(keys.xianluSearch);
    var first = $stateParams.first == "" ? true : $stateParams.first;
    delete request.startCityHtml;
    delete request.endCityHtml;
    delete request.orderHtml;
    $scope.pageNo = 1;
    $scope.pageSize = 8;
    $scope.nomore = false;
    $scope.loading = false;
    $scope.xianlus = [];

    $scope.fetchXianLus = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;

        if (first == true) {
            var hotPlans = storage.get(keys.hotPlans);
            if (hotPlans != undefined && (new Date().getTime - hotPlans.createDate) > hotPlans.time) {
                delete hotPlans.time;
                delete hotPlans.createDate;
                $scope.xianlus = hotPlans;
                $scope.loading = false;
                first = false;
            } else {
                $http.post(GetUrl.hotPlans).success(function (data) {
                    if (data.success) {
                        if (data.planList.length == 0) {
                            searchList();
                            return;
                        }
                        $scope.xianlus = data.planList;
                        hotPlans = data.planList;
                        hotPlans.time = 3 * 24 * 60 * 60 * 1000;
                        hotPlans.createDate = new Date().getTime();
                        storage.set(keys.hotPlans, hotPlans);
                    }
                    $scope.loading = false;
                }).error(function (data) {
                    $scope.loading = false;
                    alert(data.errorMsg);
                });
                first = false;
            }
            request = {
                orderColumn: "viewNum",
                orderType: "desc"
            };
        } else {
            searchList();
        }
    };

    function searchList() {
        $http.post(GetUrl.planlist, {
            json: JSON.stringify(request),
            pageNo: $scope.pageNo,
            pageSize: $scope.pageSize
        }).success(function (data) {
            if (data.success) {
                $.each(data.planList, function (i, plan) {
                    $scope.xianlus.push(plan);
                });
                $scope.pageNo++;
                $scope.nomore = data.nomore;
            }
            $scope.loading = false;
        }).error(function (data) {
            alert(data.errorMsg);
        });
    }
});

/**
 * 线路列表筛选条件
 * @type {angular.Module|*}
 */
var shaixuanModule = angular.module("shaixuanModule", []);
shaixuanModule.controller("shaixuanCtrl", function ($scope, $state, $http, $timeout, storage) {
    var json = storage.get(keys.xianluSearch) == undefined ? {} : storage.get(keys.xianluSearch);
    var cityMap = storage.get(keys.cityMap);
    if (json.monthRange != undefined && json.monthRange.length > 0) {
        $(".sx2 span").text(json.monthRange[0] + "-" + json.monthRange[1] + "月");
    }
    if (json.dayRange != undefined && json.dayRange.length > 0) {
        if (json.dayRange[1] > 10) {
            $(".sx3 span").text("10天以上");
        } else {
            $(".sx3 span").text(json.dayRange[0] + "-" + json.dayRange[1] + "天");
        }
    }
    if (json.costRange != undefined && json.costRange.length > 0) {
        if (json.costRange[1] > 7000) {
            $(".sx4 span").text("7000元以上");
        } else {
            $(".sx4 span").text(json.costRange[0] + "-" + json.costRange[1]);
        }
    }
    if (json.cityIds == undefined || json.cityIds.length < 1) {
        json.cityIds = [];
    } else {
        $(".sx1").html(json.endCityHtml);
    }
    if (json.fromCityIds == undefined || json.fromCityIds.length < 1) {
        json.fromCityIds = [];
    } else {
        $(".sx5").html(json.startCityHtml);
    }
    if (json.orderColumn == undefined || json.orderType == undefined) {
        json.orderColumn = undefined;
        json.orderType = undefined;
    } else {
        $(".sx6 span").text(json.orderHtml);
    }
    if (cityMap == undefined || (new Date().getTime - cityMap.createDate) > cityMap.time) {
        $http.post(GetUrl.hotCity).success(function (data) {
            cityMap = data.sortMap;
            cityMap.time = 60 * 24 * 60 * 60 * 1000;
            cityMap.createDate = new Date().getTime();
            storage.set(keys.cityMap, cityMap);
            completeCity();
        }).error(function (data) {
            alert(data.errorMsg)
        });
    } else {
        completeCity();
    }

    function completeCity() {
        $scope.hotCity = cityMap.hot;
        $scope.citys_ABCD = cityMap.A.concat(cityMap.B).concat(cityMap.C).concat(cityMap.D);
        $scope.citys_EFGH = cityMap.E.concat(cityMap.F).concat(cityMap.G).concat(cityMap.H);
        $scope.citys_IJKL = cityMap.I.concat(cityMap.J).concat(cityMap.K).concat(cityMap.L);
        $scope.citys_MNOP = cityMap.M.concat(cityMap.N).concat(cityMap.O).concat(cityMap.P);
        $scope.citys_QRST = cityMap.Q.concat(cityMap.R).concat(cityMap.S).concat(cityMap.T);
        $scope.citys_UVWX = cityMap.U.concat(cityMap.V).concat(cityMap.W).concat(cityMap.X);
        $scope.citys_YZ = cityMap.Y.concat(cityMap.Z);
    }

    $scope.changeEndCity = function (cityId, cityName) {
        if (json.cityIds.indexOf(cityId) < 0) {
            json.cityIds.push(cityId);
            $(".sx1").append("<span class='cityId" + cityId + "'>" + cityName + "；</span>")
        } else {
            json.cityIds.splice(json.cityIds.indexOf(cityId), 1);
            $(".sx1 .cityId" + cityId).remove();
        }
        json.endCityHtml = $(".sx1").html();
        $('#sx1').modal('hide');
    };

    $scope.changeMonth = function (min, max, text) {
        if (min == undefined || max == undefined) {
            json.monthRange = [];
        } else {
            json.monthRange = [min, max];
        }
        $(".sx2 span").text(text);
        $('#sx2').modal('hide');
    };

    $scope.changeDay = function (min, max, text) {
        if (min == undefined || max == undefined) {
            json.dayRange = [];
        } else {
            json.dayRange = [min, max];
        }
        $(".sx3 span").text(text);
        $('#sx3').modal('hide');
    };

    $scope.changeCost = function (min, max, text) {
        if (min == undefined || max == undefined) {
            json.costRange = [];
        } else {
            json.costRange = [min, max];
        }
        $(".sx4 span").text(text);
        $('#sx4').modal('hide');
    };

    $scope.changeStartCity = function (cityId, cityName) {
        if (json.fromCityIds.indexOf(cityId) < 0) {
            json.fromCityIds.push(cityId);
            $(".sx5").append("<span class='cityId" + cityId + "'>" + cityName + "；</span>")
        } else {
            json.fromCityIds.splice(json.fromCityIds.indexOf(cityId), 1);
            $(".sx5 .cityId" + cityId).remove();
        }
        json.startCityHtml = $(".sx5").html();
        $('#sx5').modal('hide');
    };

    $scope.changeOrder = function (orderColumn, orderType, text) {
        json.orderColumn = orderColumn;
        json.orderType = orderType;
        $(".sx6 span").text(text);
        json.orderHtml = text;
        $('#sx6').modal('hide');
    };

    $scope.goBack = function () {
        if ($(".sxmodal.in").length > 0) {
            $(".sxmodal.in").modal("hide");
            $timeout(function () {
                history.go(-1);
            }, 400);
        } else {
            history.go(-1);
        }
    };

    $scope.searchList = function () {
        storage.set(keys.xianluSearch, json);
        if ($(".sxmodal.in").length > 0) {
            $(".sxmodal.in").modal("hide");
            $timeout(function () {
                $state.go("xianlu", {first: false});
            }, 400);
        } else {
            $state.go("xianlu", {first: false});
        }
    }
});

/**
 * 这里是游记详情模块
 */
var YjdetailModule = angular.module("YjdetailModule", []);
YjdetailModule.controller('YjdetailCtrl', function ($scope, $http) {
    var req = {
        method: 'GET',
        url: GetUrl.yjdetail
    };
    $http(req).success(function (data) {
        $scope.yjdetails = data.data;
    }).error(function (data) {
        alert(data.msg)
    });
});


/**
 * 选择城市模块
 */
var SelectCityModule = angular.module("SelectCityModule", ['angularLocalStorage']);
SelectCityModule.controller('SelectCityCtrl', ['$scope', '$http', 'storage', '$state', function ($scope, $http, storage, $state) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    var cityMap = storage.get(keys.cityMap);
    $scope.me = false;
    $scope.pageNo = 1;
    $scope.pageSize = 12;
    $scope.nomore = false;
    $scope.citys = [];

    $scope.removeCity = function (obj) {
        var city = obj.city;
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            if (city.id == $scope.selectCitys[i].id) {
                $scope.selectCitys.splice(i, 1);
                var item = $("li[citid='" + obj.city.id + "']");
                if (item.hasClass('active')) {
                    item.removeClass('active');
                }
                break;
            }
        }
        storage.set(keys.selectCitys, $scope.selectCitys);
    };

    //selectCitys();

    //$('.dzlistall').unbind().dropload({
    //    domDown: {
    //        domClass: 'dropload-down',
    //        domRefresh: '<div class="dropload-refresh">↑上拉加载更多</div>',
    //        domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
    //        domNoData: '<div class="dropload-noData">暂无数据</div>'
    //    },
    //    loadDownFn: function (me) {
    //        $scope.me = me;
    //        selectCitys();
    //    }
    //});
    function checkCity(data) {
        for (var i = 0; i < data.length; i++) {
            var city = data[i];
            var eq = false;
            for (var j = 0; j < $scope.selectCitys.length; j++) {
                if (city.id == $scope.selectCitys[j].id) {
                    eq = true;
                    break;
                }
            }
            city.check = eq;
        }
    }

    if (cityMap == undefined || (new Date().getTime - cityMap.createDate) > cityMap.time) {
        $http({
            method: 'GET',
            url: GetUrl.hotCity
        }).success(function (data) {
            cityMap = data.sortMap;
            cityMap.time = 60 * 24 * 60 * 60 * 1000;
            cityMap.createDate = new Date().getTime();
            storage.set(keys.cityMap, cityMap);
            completeCity();
        }).error(function (data) {
            alert(data.errorMsg)
        });
    } else {
        completeCity();
    }

    function completeCity() {
        $scope.hotCity = cityMap.hot;
        $scope.citys = cityMap.hot;
        checkCity($scope.hotCity);
        $scope.citys_ABCD = cityMap.A.concat(cityMap.B).concat(cityMap.C).concat(cityMap.D);
        checkCity($scope.citys_ABCD);
        $scope.citys_EFGH = cityMap.E.concat(cityMap.F).concat(cityMap.G).concat(cityMap.H);
        checkCity($scope.citys_EFGH);
        $scope.citys_IJKL = cityMap.I.concat(cityMap.J).concat(cityMap.K).concat(cityMap.L);
        checkCity($scope.citys_IJKL);
        $scope.citys_MNOP = cityMap.M.concat(cityMap.N).concat(cityMap.O).concat(cityMap.P);
        checkCity($scope.citys_MNOP);
        $scope.citys_QRST = cityMap.Q.concat(cityMap.R).concat(cityMap.S).concat(cityMap.T);
        checkCity($scope.citys_QRST);
        $scope.citys_UVWX = cityMap.U.concat(cityMap.V).concat(cityMap.W).concat(cityMap.X);
        checkCity($scope.citys_UVWX);
        $scope.citys_YZ = cityMap.Y.concat(cityMap.Z);
        checkCity($scope.citys_YZ);
    }

    $scope.next = function () {
        $scope.ajaxloading = true;
        if ($scope.selectCitys.length == 0) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请先选择城市'
            });
        } else {
            //location.href = '/#/xing-cfd';
            $state.go('xing-cfd');
        }
    }

}]);

var searchmddModule = angular.module("searchmddModule", []);
searchmddModule.controller("searchmddCtrl", function ($scope, storage, $http, $state) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.pageNo = 1;
    $scope.pageSize = 20;
    $scope.cityList = function () {
        if ($scope.key == undefined || $scope.key.length < 1) {
            $scope.citys = [];
            return;
        }
        $http.post(GetUrl.selectcitys, {
                keyword: $scope.key,
                pageNo: $scope.pageNo,
                pageSize: $scope.pageSize
            }
        ).success(function (data) {
            angular.forEach(data.cityList, function (city) {
                city.html = city.name.replace($scope.key, "<b>" + $scope.key + "</b>");
            });
            $scope.citys = data.cityList;
        }).error(function (data) {
            alert(data.errorMsg);
        });
    };

    $scope.selectCity = function (obj) {
        var city = obj.city;
        var eq = false;
        angular.forEach($scope.selectCitys, function (selectCity) {
            if (city.id == selectCity.id) {
                eq = true;
                return false;
            }
        });
        if (eq) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "已选择该城市！"
            });

        } else {
            $scope.selectCitys.push(city);
            storage.set(keys.selectCitys, $scope.selectCitys);
            $state.go("xing");
        }
    }
});

//先把出发地
var xingcfdModule = angular.module("xingcfdModule", ['angularLocalStorage']);
xingcfdModule.controller('xingcfdCtrl', ['$scope', '$http', 'storage', '$state', '$stateParams', function ($scope, $http, storage, $state, $stateParams) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.fromQuote = $stateParams.fromQuote == "true" ? true : false;

    $scope.fromCity = storage.get(keys.fromCity) == null ? {
        id: 310100,
        name: '上海'
    } : storage.get(keys.fromCity);
    storage.set(keys.fromCity, $scope.fromCity, {path: '/'});

    var date = new Date();
    var currYear = (date).getFullYear();
    var opt = {};
    opt.date = {preset: 'date'};
    //opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
    opt.datetime = {preset: 'datetime'};
    opt.time = {preset: 'time'};
    opt.default = {
        theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式
        mode: 'scroller', //日期选择模式
        lang: 'zh',
        setText: '完成',
        defaultValue: date,
        startYear: currYear, //开始年份
        endYear: currYear + 1, //结束年份
        minDate: date
    };

    $scope.startDay = (date).Format("yyyy-MM-dd");
    $("#appDate").val($scope.startDay).scroller('destroy').scroller($.extend(opt['date'], opt['default']));
    var optDateTime = $.extend(opt['datetime'], opt['default']);
    var optTime = $.extend(opt['time'], opt['default']);
    $("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
    $("#appTime").mobiscroll(optTime).time(optTime);
    function saveSelectCity() {
        $(".num").each(function () {
            var id = $(this).attr('id');
            for (var i = 0; i < $scope.selectCitys.length; i++) {
                var city = $scope.selectCitys[i];
                if (parseFloat(city.id) == parseFloat(id)) {
                    city.day = $(this).val();
                }
            }
        });
        storage.set(keys.selectCitys, $scope.selectCitys, {path: '/'});
        storage.set(keys.startDay, $scope.startDay, {path: '/'});
    }

    $scope.gotoCity = function () {
        saveSelectCity();
        $state.go('xing-city', {fromQuote: $scope.fromQuote});
    };
    $scope.cankao = function () {
        saveSelectCity();
        var request = {
            cityIds: [],
            endCityHtml: "目的地"
        };
        angular.forEach($scope.selectCitys, function (city) {
            request.cityIds.push(city.id);
            request.endCityHtml += "<span class='cityId" + city.id + "'>" + city.name + "；</span>";
        });
        storage.set(keys.xianluSearch, request);
        $state.go('xianlu', {first: false});
    };
    function searchTraffic(){
        //预访问交通
        var plans = [];
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            var city = $scope.selectCitys[i];
            plans.push({
                cityId: city.id,
                day: city.day
            });
        }
        $http.post(GetUrl.recommend, {
                json: JSON.stringify({
                    startDate: $scope.startDay,
                    startCityId: $scope.fromCity.id,
                    plan: plans
                })
            }
        ).success(function (data) {
        });
    }
    $scope.yuanchuang = function () {
        saveSelectCity();
        //searchTraffic();推荐交通查询
        $state.go('xing-jingdian');
    };
    $scope.nextjiaotong = function () {
        saveSelectCity();
        $state.go('xing-jiudian');
    }

}]);
//选择城市
var xingcityModule = angular.module("xingcityModule", ['angularLocalStorage']);
xingcityModule.controller('xingcityCtrl', ['$scope', '$http', 'storage', '$stateParams', function ($scope, $http, storage, $stateParams) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.fromQuote = $stateParams.fromQuote == "true" ? true : false;
    var cityMap = storage.get(keys.cityMap);
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
    $scope.fromCity = storage.get(keys.fromCity) == null ? {
        id: 310100,
        name: '上海'
    } : storage.get(keys.fromCity);

    if (cityMap == undefined || (new Date().getTime - cityMap.createDate) > cityMap.time) {
        $http({
            method: 'GET',
            url: GetUrl.hotCity
        }).success(function (data) {
            cityMap = data.sortMap;
            cityMap.time = 60 * 24 * 60 * 60 * 1000;
            cityMap.createDate = new Date().getTime();
            storage.set(keys.cityMap, cityMap);
            completeCity();
        }).error(function (data) {
            alert(data.errorMsg)
        });
    } else {
        completeCity();
    }

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
    }

}]);
//城市搜索
var xingcitysearchModule = angular.module("xingcitysearchModule", ['angularLocalStorage']);
xingcitysearchModule.controller('xingcitysearchCtrl', ['$scope', '$http', 'storage', '$state', function ($scope, $http, storage, $state) {
    $scope.selectedCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.me = false;
    $scope.pageNo = 1;
    $scope.pageSize = 12;
    $scope.nomore = false;
    $scope.searchkey = '';
    $scope.citys = [];
    $scope.selectCitys = function () {
        if ($scope.searchkey.length > 0) {
            $http.post(GetUrl.selectcitys, {
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
            }).error(function (data) {
                alert(data.errorMsg);
            });
        }
    };

    $scope.selected = function (obj) {

        $scope.fromCity = obj.city;
        storage.set(keys.fromCity, $scope.fromCity);
        $state.go('xing-cfd');
    };

    //$scope.keyup = function (e) {
    //    var keycode = window.event ? e.keyCode : e.which;
    //    if (keycode == 13) {
    //        selectCitys();
    //    }
    //};


}]);
//选择景点
var xingjingdianModule = angular.module("xingjingdianModule", ['angularLocalStorage', 'infinite-scroll']);
xingjingdianModule.controller('xingjingdianCtrl', ['$scope', '$http', 'storage', 'storage', '$state', function ($scope, $http, $cookieStore, storage, $state) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.selectTime = storage.get(keys.selectTime) == null ? "2" : storage.get(keys.selectTime);
    $scope.theme = "";
    $scope.showDark = false;
    $scope.showDark2 = false;

    $scope.changeTime = function (val) {
        storage.set(keys.selectTime, val);
    };

    $("li[selecttime]").removeClass('active');
    $("li[selecttime='" + $scope.selectTime + "']").addClass('active');

    $scope.pageNo = 1;
    $scope.nomore = false;
    $scope.loading = false;
    $http.post(GetUrl.theme, {}
    ).success(function (data) {
        $scope.themes = data.theme;
    }).error(function (data) {
        alert(data.errorMsg)
    });

    $scope.selectedCityId = $scope.selectCitys[0].id;
    $scope.theme = $scope.theme;
    $scope.orderColumn = 'ranking';
    $scope.orderType = 'asc';
    $scope.scenics = [];
    $scope.changeScenic = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        //$scope.loading = true;
        var json = {
            name: $scope.key == undefined ? "" : $scope.key,
            cityIds: [$scope.selectedCityId],
            theme: $scope.theme,
            orderColumn: $scope.orderColumn,
            orderType: $scope.orderType
        };

        $http.post(GetUrl.sceniclist, {
                json: JSON.stringify(json),
                pageNo: $scope.pageNo,
                pageSize: 10
            }
        ).success(function (data) {
            if (data.success) {
                for (var i = 0; i < data.scenicList.length; i++) {
                    var find = false;
                    for (var j = 0; j < $scope.addScenics.length; j++) {
                        if (parseFloat($scope.addScenics[j].id) == parseFloat(data.scenicList[i].id)) {
                            find = true;
                            break;
                        }
                    }
                    if (find) {
                        data.scenicList[i].added = true;
                    } else {
                        data.scenicList[i].added = false;
                    }
                    $scope.scenics.push(data.scenicList[i]);
                }
                if (data.scenicList.length == 10) {
                    $scope.pageNo++;
                } else {
                    $scope.nomore = true;
                }
            } else {

            }
            $scope.loading = false;
        }).error(function (data) {
            alert(data.errorMsg);
        });

    };

    //changeScenic();

    $scope.changeCity = function (obj) {
        $scope.pageNo = 1;
        $scope.nomore = false;
        $scope.showDark = false;
        $('body').css({'overflow':'auto'});
        $('html').css({'overflow':'auto'});
        if (parseFloat(obj.city.id) != parseFloat($scope.selectedCityId)) {
            $scope.scenics = [];
            $scope.selectedCityId = obj.city.id;
            $scope.changeScenic();
        }
    };

    $scope.changeTheme = function (obj) {
        $scope.pageNo = 1;
        $scope.nomore = false;
        $scope.showDark = false;
        $('body').css({'overflow':'auto'});
        $('html').css({'overflow':'auto'});
        if (obj != null && obj.theme != null && obj.theme != $scope.theme) {
            $scope.scenics = [];
            $scope.theme = obj.theme;
            $scope.changeScenic();
        } else {
            $scope.theme = '';
            $scope.changeScenic();
        }
    };

    $scope.sort = function (orderColumn, orderType) {
        $scope.pageNo = 1;
        $scope.nomore = false;
        if (orderColumn != $scope.orderColumn || orderType != $scope.orderType) {
            $scope.scenics = [];
            $scope.orderColumn = orderColumn;
            $scope.orderType = orderType;
            $scope.changeScenic();
        }
    };

    $('.son_ul').hide(); //初始ul隐藏
    $('.select_box span').hover(function () { //鼠标移动函数
            $(this).parent().find('ul.son_ul').slideDown();  //找到ul.son_ul显示
            $(this).parent().find('li').hover(function () {
                $(this).addClass('hover');
            }, function () {
                $(this).removeClass('hover');
            });
            //li的hover效果
            $(this).parent().hover(function () {
                },
                function () {
                    $(this).parent().find("ul.son_ul").slideUp();
                }
            );
        $scope.$apply(function () {
            $scope.showDark = true;
        })

        }, function () {
        }
    );

    $scope.hideDark = function () {
        $scope.showDark = false;
        $scope.showaddscenic = true;
        var showS = $scope.showDark?'hidden':'scroll';
        $('body').css({'overflow':showS});
        $('html').css({'overflow':showS});
    }
    $scope.hideDark2 = function () {
        $scope.showDark2 = false;
        $scope.showaddscenic = true;
        var showS = $scope.showDark2?'hidden':'scroll';
        $('body').css({'overflow':showS});
        $('html').css({'overflow':showS});
    }


    //2016.9.9
    $('#ul1').click(function(){
        $('.sliderbur').animate({'left':0},80);
        $('body').css({'overflow':'hidden'});
        $('html').css({'overflow':'hidden'});
    })
    $('#ul2').click(function(){
        $('.sliderbur').animate({'left':'33%'},80);
        $('body').css({'overflow':'hidden'});
        $('html').css({'overflow':'hidden'});
    })
    $('#ul3').click(function(){
        $('.sliderbur').animate({'left':'66%'},80);
        $('body').css({'overflow':'hidden'});
        $('html').css({'overflow':'hidden'});
    })


    //function createshade() {
    //    var _parent = $('.xing_jingdian');
    //    var shadeHeight = window.screen.height;
    //    var shade = $('<div class="darkshadow"></div>');
    //    shade.appendTo(_parent);
    //    shade.css({'height': shadeHeight});
    //}


    $scope.addScenic = function (flag, obj) {
        var scenic = obj.scenic;
        if (flag) {
            $scope.addScenics.push(scenic);
            scenic.added = true;
        } else {
            for (var i = 0; i < $scope.addScenics.length; i++) {
                if ($scope.addScenics[i].id == scenic.id) {
                    $scope.addScenics.splice(i, 1);
                }
            }
            for (var i = 0; i < $scope.scenics.length; i++) {
                if ($scope.scenics[i].id == scenic.id) {
                    $scope.scenics[i].added = false;
                }
            }
            scenic.added = false;
        }
        storage.set(keys.addScenics, $scope.addScenics);
    };

    $scope.removeScenic = function (flag, obj) {
        var scenic = obj.scenic;
        for (var i = 0; i < $scope.addScenics.length; i++) {
            if ($scope.addScenics[i].id == scenic.id) {
                $scope.addScenics.splice(i, 1);
            }
        }
        for (var i = 0; i < $scope.scenics.length; i++) {
            if (parseFloat($scope.scenics[i].id) == parseFloat(scenic.id)) {
                $scope.scenics[i].added = false;
                break;
            }
        }
        storage.set(keys.addScenics, $scope.addScenics);
    };

    $scope.optimizePlan = function () {
        var addScenics = [];
        var findAll = true;
        var notAddScenicCity = null;
        for (var j = 0; j < $scope.selectCitys.length; j++) {
            var city = $scope.selectCitys[j];
            var find = false;
            for (var i = 0; i < $scope.addScenics.length; i++) {
                if ($scope.addScenics[i].cityId.toString().substring(0, 4) == city.id.toString().substring(0, 4)) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                notAddScenicCity = city;
                findAll = false;
                break;
            }
        }
        //alert('ddd' + findAll);
        if (findAll) {
            //alert('length' + $scope.addScenics.length);
            for (var i = 0; i < $scope.addScenics.length; i++) {
                var scenic = $scope.addScenics[i];
                var find = false;
                //alert(scenic.toString());
                for (var j = 0; j < $scope.selectCitys.length; j++) {
                    var city = $scope.selectCitys[j];
                    if (scenic.cityId.toString().substring(0, 4) == city.id.toString().substring(0, 4)) {
                        find = true;
                        break;
                    }
                }
                if (find) {
                    addScenics.push(scenic);
                } else {
                    //alert(scenic.name);
                }
            }
            storage.set(keys.addScenics, addScenics);
            //alert('go');
            $state.go("xing-gailan", {fromQuote: false});
        } else {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: notAddScenicCity.name + ' 这个城市未选择景点哦！'
            });
        }
    };

    $scope.toSearch = function () {
        var cityIds = [];
        angular.forEach($scope.selectCitys, function (city) {
            cityIds.push(city.id);
        });
        $state.go("xing-jingdian-search", {cityIds: JSON.stringify(cityIds)});
    }
    $scope.showaddscenic = true;
    $scope.showAddSce = function(){
        var shadeHeight2 = $('body').height();
        $('.darkshadow2').css({'height':shadeHeight2});
            $scope.showaddscenic =  !$scope.showaddscenic;
            $scope.showDark2 = !$scope.showaddscenic;
          var showS = $scope.showDark2?'hidden':'scroll';
           $('body').css({'overflow':showS})
    }
}]);

//名称搜索景点
var xingjingdiansearchModule = angular.module("xingjingdiansearchModule", []);
xingjingdiansearchModule.controller("xingjingdiansearchCtrl", function ($scope, $http, $stateParams) {
    $scope.cityIds = JSON.parse($stateParams.cityIds);
    $scope.scenicList = function () {
        if ($scope.key == undefined || $scope.key.length < 1) {
            return;
        }
        var json = {
            name: $scope.key,
            cityIds: $scope.cityIds
        };

        $http.post(GetUrl.sceniclist, {
                json: JSON.stringify(json),
                pageNo: 1,
                pageSize: 10
            }
        ).success(function (data) {
            angular.forEach(data.scenicList, function (scenic) {
                scenic.html = scenic.name.replace($scope.key, "<b>" + $scope.key + "</b>");
            });
            $scope.scenics = data.scenicList;
        }).error(function (data) {
            alert(data.errorMsg);
        });

    };
});

//景点详情
var xingjingdianinfoModule = angular.module("xingjingdianinfoModule", ['angularLocalStorage']);
xingjingdianinfoModule.controller('xingjingdianinfoCtrl', ['$scope', '$http', '$cookieStore', '$stateParams', 'storage', function ($scope, $http, $cookieStore, $stateParams, storage) {
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.added = false;
    for (var i = 0; i < $scope.addScenics.length; i++) {
        if ($scope.addScenics[i].id == $stateParams.id) {
            $scope.added = true;
        }
    }
    $http.post(GetUrl.scenicdetail, {
            id: $stateParams.id
        }
    ).success(function (data) {
        if (data.success) {
            $scope.scenic = data.scenic;
            storage.set(keys.scenic, $scope.scenic);
            $('#viewMore').click(function () {
                $('#moreinfo').css({'height': 'auto', 'overflow': 'none'});
                $('#viewMore').toggle();
                $('#viewMoreclose').removeClass("hidden");
            });
            $('#viewMoreclose').click(function () {
                $('#moreinfo').css({'height': '95px', 'overflow': 'hidden'});
                $('#viewMore').show();
                $('#viewMoreclose').addClass("hidden");
            });
        } else {

        }
    }).error(function (data) {
        alert(data.errorMsg);
    });
    $scope.addToPlan = function () {
        var scenic = $scope.scenic;
        scenic.added = true;
        $scope.addScenics.push(scenic);
        storage.set(keys.addScenics, $scope.addScenics);
        $scope.added = true;
    };
    $scope.removeFromPlan = function () {
        var scenic = $scope.scenic;
        scenic.added = false;
        for (var i = 0; i < $scope.addScenics.length; i++) {
            if ($scope.addScenics[i].id == scenic.id) {
                $scope.addScenics.splice(i, 1);
            }
        }
        storage.set(keys.addScenics, $scope.addScenics);
        $scope.added = false;
    };
}]);

//景点地图
var xingjingdianmapModule = angular.module("xingjingdianmapModule", []);
xingjingdianmapModule.controller("xingjingdianmapCtrl", function ($scope, storage) {
    $scope.scenic = storage.get(keys.scenic);
    // 百度地图API功能
    var sContent =
        "<h4 style='margin:0 0 5px 0;padding:0.2em 0'>" + $scope.scenic.name + "</h4>" +
        "<img style='float:right;margin:4px' id='imgDemo' src='" + $scope.scenic.cover + "' width='139' height='104' title='" + $scope.scenic.name + "'/>" +
        "<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>" + $scope.scenic.introduction + "</p>" +
        "</div>";
    var map = new BMap.Map("allmap", {enableMapClick: false});
    var point = new BMap.Point($scope.scenic.longitude, $scope.scenic.latitude);
    var marker = new BMap.Marker(point);
    var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
    map.centerAndZoom(point, 19);
    map.addControl(new BMap.NavigationControl());// 添加平移缩放控件
    map.enableScrollWheelZoom();//启用滚轮放大缩小
    map.addOverlay(marker);
    marker.addEventListener("click", function () {
        this.openInfoWindow(infoWindow);
        //图片加载完毕重绘infowindow
        document.getElementById('imgDemo').onload = function () {
            infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
        }
    });
});

//行程概览
var xinggailanModule = angular.module("xinggailanModule", ['angularLocalStorage']);
xinggailanModule.controller('xinggailanCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', 'storage', '$state', '$stateParams', '$timeout', function ($rootScope, $scope, $http, $cookieStore, storage, $state, $stateParams, $timeout) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.selectTime = storage.get(keys.selectTime) == null ? "2" : storage.get(keys.selectTime);
    $scope.startDay = storage.get(keys.startDay);
    var days = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    var addNodes = storage.get(keys.addNodes) == null ? [] : storage.get(keys.addNodes);
    var removeNodes = storage.get(keys.removeNodes) == null ? [] : storage.get(keys.removeNodes);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.fromQuote = $stateParams.fromQuote == "true" ? true : false;

    $scope.daychange = function (obj, cityid) {
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            var city = $scope.selectCitys[i];
            if (city.id == obj.city.city.id) {
                city.day = $("#city" + cityid).val();
            }
        }
        storage.set(keys.selectCitys, $scope.selectCitys);
        optimize();
        //searchTraffic();推荐交通查询
    };
    function searchTraffic(){
        //预访问交通
        var plans = [];
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            var city = $scope.selectCitys[i];
            plans.push({
                cityId: city.id,
                day: city.day
            });
        }
        $http.post(GetUrl.recommend, {
                json: JSON.stringify({
                    startDate: $scope.startDay,
                    startCityId: $scope.fromCity.id,
                    plan: plans
                })
            }
        ).success(function (data) {
        });
    }

    //从行程规划那边过来，才重新计算
    function optimize() {
        var cityDays = {};
        var totalDay = 0;
        $scope.loaded = false;
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            var obj = {};
            var name = $scope.selectCitys[i].id + "";
            if (name.length > 4) {
                name = name.substr(0, 4);
            }
            totalDay += $scope.selectCitys[i].day;
            cityDays[name] = $scope.selectCitys[i].day;
        }
        var scenicList = [];
        for (var i = 0; i < $scope.addScenics.length; i++) {
            scenicList.push({
                type: '1',
                id: $scope.addScenics[i].id
            });
        }
        $http.post(GetUrl.optimize, {
                json: JSON.stringify({
                    day: totalDay,
                    hour: $scope.selectTime,
                    type: 2,
                    cityDays: cityDays,
                    scenicList: scenicList
                })
            }
        ).success(function (data) {
            $scope.loaded = true;
            if (data.success) {
                $scope.addNodes = data.result.addNodes;
                $scope.removeNodes = data.result.removeNode;
                var last = null;
                var city = null;
                var citys = [];
                for (var i = 0; i < data.result.data.length; i++) {
                    var item = data.result.data[i];
                    if (last != null && last.city.id != item.city.id) {
                        item.fromCity = last.city;
                        city = {};
                        city.city = item.city;
                        var trips = {dayindex: i, trips: []};
                        trips.trips = item.tripList;
                        city.days = [];
                        city.days.push(trips);
                        city.day = city.days.length;
                        citys.push(city);
                    } else if (last == null) {
                        item.fromCity = $scope.fromCity;
                        city = {};
                        city.city = item.city;
                        var trips = {dayindex: i, trips: []};
                        trips.trips = item.tripList;
                        city.days = [];
                        city.days.push(trips);
                        city.day = city.days.length;
                        citys.push(city);
                    } else {
                        var trips = {dayindex: i, trips: []};
                        trips.trips = item.tripList;
                        city.days.push(trips);
                        city.day = city.days.length;
                    }
                    last = item;
                }
                $scope.citys = citys;
                $scope.addNodes = data.result.addNodes;
                $scope.removeNodes = data.result.removedNode;
                $scope.optimizeScenics = data.result.data;
                storage.set(keys.optimizeScenics, $scope.optimizeScenics);
                storage.set(keys.addNodes, $scope.addNodes);
                storage.set(keys.removeNodes, $scope.removeNodes);
                //console.info($scope.citys);
            }
        }).error(function (data) {
            alert(data.errorMsg);
            $scope.loaded = true;
        });
    }

    if ($scope.fromQuote || ($rootScope.previousState.name.length > 0 && $rootScope.previousState.name != 'xing-jingdian')) {
        $scope.loaded = true;
        $scope.addNodes = addNodes;
        $scope.removeNodes = removeNodes;
        $scope.optimizeScenics = days;
        var data = days;
        var city = null;
        var citys = [];
        var last = null;
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            if (last != null && last.city.id != item.city.id) {
                item.fromCity = last.city;
                city = {};
                city.city = item.city;
                var trips = {dayindex: i, trips: []};
                trips.trips = item.tripList;
                city.days = [];
                city.days.push(trips);
                city.day = city.days.length;
                citys.push(city);
            } else if (last == null) {
                item.fromCity = $scope.fromCity;
                city = {};
                city.city = item.city;
                var trips = {dayindex: i, trips: []};
                trips.trips = item.tripList;
                city.days = [];
                city.days.push(trips);
                city.day = city.days.length;
                citys.push(city);
            } else {
                var trips = {dayindex: i, trips: []};
                trips.trips = item.tripList;
                city.days.push(trips);
                city.day = city.days.length;
            }
            last = item;
        }
        $scope.addScenics = [];
        angular.forEach(data, function (day) {
            angular.forEach(day.tripList, function (trip) {
                $scope.addScenics.push(trip);
            });
        });
        $scope.citys = citys;
        if ($scope.fromQuote) {
            $scope.optimizeScenics = data;
            storage.set(keys.optimizeScenics, $scope.optimizeScenics);
            storage.set(keys.addScenics, $scope.addScenics);
            storage.remove(keys.addNodes);
            storage.remove(keys.removeNodes);
        }
    } else if ($rootScope.previousState.name == '' || $rootScope.previousState.name == 'xing-jingdian') {
        $scope.loaded = false;
        optimize();
    }
    function saveAll() {
        storage.set(keys.removeNodes, $scope.removeNodes);
        storage.set(keys.addNodes, $scope.addNodes);
        storage.set(keys.optimizeScenics, $scope.optimizeScenics);
    }

    $scope.deleteScenic = function (obj) {
        var scenic = obj.scenic;
        var day = 0;
        for (var i = 0; i < $scope.optimizeScenics.length; i++) {
            var day = $scope.optimizeScenics[i];
            var ifDel = false;
            for (var j = 0; j < day.tripList.length; j++) {
                var item = day.tripList[j];
                if (item.id == scenic.id) {
                    day.tripList.splice(j, 1);
                    break;
                }
            }
        }
        var fromAddNode = false;
        for (var j = 0; j < $scope.addNodes.length; j++) {
            var item = $scope.addNodes[j];
            if (item.id == scenic.id) {
                $scope.addNodes.splice(j, 1);
                fromAddNode = true;
                break;
            }
        }
        $scope.removeNodes.push(scenic);
        saveAll();
    };

    $scope.addScenic = function (obj) {
        var scenic = obj.scenic;
        var dayIndex = parseInt($("#xianludays").data('owlCarousel').currentItem);
        for (var i = 0; i < $scope.optimizeScenics.length; i++) {
            var day = $scope.optimizeScenics[i];
            if (i == dayIndex) {
                day.tripList.push(scenic);
            }
        }
        //var fromAddNode = false;
        //for (var j = 0; j < $scope.addNodes.length; j++) {
        //    var item = $scope.addNodes[j];
        //    if (item.id == scenic.id) {
        //        $scope.addNodes.splice(j, 1);
        //        fromAddNode = true;
        //        break;
        //    }
        //}
        for (var j = 0; j < $scope.removeNodes.length; j++) {
            var item = $scope.removeNodes[j];
            if (item.id == scenic.id) {
                $scope.removeNodes.splice(j, 1);
                fromAddNode = true;
                break;
            }
        }
        saveAll();
    };

    $scope.toScenicList = function () {
        $(".modal.in").modal("hide");
        $timeout(function () {
            $state.go("xing-jingdian");
        }, 400);
    }
}]);
//行程编辑
var xingxlbianjiModule = angular.module("xingxlbianjiModule", ['angularLocalStorage']);
xingxlbianjiModule.controller('xingxlbianjiCtrl', ['$rootScope', '$scope', 'storage', '$http', '$cookieStore', '$state', '$stateParams', '$timeout', function ($rootScope, $scope, storage, $http, $cookieStore, $state, $stateParams, $timeout) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.selectTime = storage.get(keys.selectTime) == null ? "2" : storage.get(keys.selectTime);
    $scope.startDay = storage.get(keys.startDay);
    $scope.optimizeScenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.addNodes = storage.get(keys.addNodes) == null ? [] : storage.get(keys.addNodes);
    $scope.removeNodes = storage.get(keys.removeNodes) == null ? [] : storage.get(keys.removeNodes);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.day = $stateParams.day;

    $scope.goBack = function () {
        $(".modal.in").modal("hide");
        $timeout(function () {
            history.go(-1);
        }, 400);
    };

    function saveAll() {
        storage.set(keys.removeNodes, $scope.removeNodes);
        storage.set(keys.addNodes, $scope.addNodes);
        storage.set(keys.optimizeScenics, $scope.optimizeScenics);
    }

    $scope.deleteScenic = function (obj) {
        var scenic = obj.scenic;
        var day = 0;
        for (var i = 0; i < $scope.optimizeScenics.length; i++) {
            var day = $scope.optimizeScenics[i];
            var ifDel = false;
            for (var j = 0; j < day.tripList.length; j++) {
                var item = day.tripList[j];
                if (item.id == scenic.id) {
                    day.tripList.splice(j, 1);
                    break;
                }
            }
        }
        var fromAddNode = false;
        for (var j = 0; j < $scope.addNodes.length; j++) {
            var item = $scope.addNodes[j];
            if (item.id == scenic.id) {
                $scope.addNodes.splice(j, 1);
                fromAddNode = true;
                break;
            }
        }
        $scope.removeNodes.push(scenic);
        saveAll();
    };

    $scope.addScenic = function (obj) {
        var scenic = obj.scenic;
        var dayIndex = parseInt($("#xianludays").data('owlCarousel').currentItem);
        if (scenic.cityId != $scope.optimizeScenics[dayIndex].city.id) {
            alert("此景点不在当前城市中！");
            return;
        }
        for (var i = 0; i < $scope.optimizeScenics.length; i++) {
            var day = $scope.optimizeScenics[i];
            if (i == dayIndex) {
                day.tripList.push(scenic);
            }
        }
        //var fromAddNode = false;
        //for (var j = 0; j < $scope.addNodes.length; j++) {
        //    var item = $scope.addNodes[j];
        //    if (item.id == scenic.id) {
        //        $scope.addNodes.splice(j, 1);
        //        fromAddNode = true;
        //        break;
        //    }
        //}
        for (var j = 0; j < $scope.removeNodes.length; j++) {
            var item = $scope.removeNodes[j];
            if (item.id == scenic.id) {
                $scope.removeNodes.splice(j, 1);
                fromAddNode = true;
                break;
            }
        }
        saveAll();
    };

    $scope.toMap = function () {
        var dayIndex = parseInt($("#xianludays").data('owlCarousel').currentItem);
        $state.go("xing-xianlu-map", {day: dayIndex});
    };

    $scope.toScenicList = function () {
        $(".modal.in").modal("hide");
        $timeout(function () {
            $state.go("xing-jingdian");
        }, 400);
    }
}]);
//行程地图
var xingxianlumapModule = angular.module("xingxianlumapModule", ['angularLocalStorage']);
xingxianlumapModule.controller('xingxianlumapCtrl', ['$scope', '$http', '$cookieStore', 'bdMapSev', 'storage', '$stateParams', function ($scope, $http, $cookieStore, bdMapSev, storage, $stateParams) {
    $scope.scenics = storage.get(keys.optimizeScenics);
    var num = $scope.scenics.length
    var Twidht = (num +1) * 94;
    //alert(Twidht)
    $('.xlalldays').css({'width':Twidht})
    $scope.day = $stateParams.day == undefined ? -1 : parseInt($stateParams.day);
    $scope.finish = {
        over: false
    };

    $scope.map = new bdMapSev("map");

    angular.forEach($scope.scenics, function (day, i) {
        angular.forEach(day.tripList, function (scenic, j) {
            scenic.score = scenic.score / 20;
            $scope.map.addAScenic(scenic, i, j + 1);
            if (i == 0 && j == 0) {
                $scope.selectScenic = scenic;
                $scope.map.locationTo(scenic.lng, scenic.lat);
            }
        });
    });

    $scope.map.drawLines();
    $scope.map.showAllLine();

    $scope.$watch("finish.over", function () {
        if ($scope.finish.over) {
            //$(".xlalldays a").eq($scope.day + 1).click();
            if ($scope.day == -1) {
                $scope.showAll();
            } else {
                $scope.showDay($scope.day);
            }
        }
    });

    $("#map .Pop_ups_posiR .location").click(function () {
        var id = $(this).data("id");
        angular.forEach($scope.scenics, function (day) {
            angular.forEach(day.tripList, function (scenic) {
                if (scenic.id == id) {
                    $scope.$apply(function () {
                        $scope.selectScenic = scenic;
                        $(".mapinfo").removeClass("hidden");
                    });
                }
            });
        });
    });

    $scope.showDay = function (day) {
        $scope.selectScenic = $scope.scenics[day].tripList[0];
        $(".title-day").show();
        $(".title-whole").hide();
        $(".mapinfo").addClass("hidden");
        $scope.map.showDay(day);
    };

    $scope.showAll = function () {
        $scope.selectScenic = $scope.scenics[0].tripList[0];
        $(".title-day").hide();
        $(".title-whole").show();
        $(".mapinfo").addClass("hidden");
        $scope.map.showAllLine();
    }
}]);
//推荐交通
var xingjiaotongModule = angular.module("xingjiaotongModule", ['angularLocalStorage']);
xingjiaotongModule.controller('xingjiaotongCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', 'storage', '$state', function ($rootScope, $scope, $http, $cookieStore, storage, $state) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.scenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.jiaotong = storage.get(keys.jiaotong) == null ? [] : storage.get(keys.jiaotong);
    $scope.train = storage.get(keys.train) == null ? {} : storage.get(keys.train);
    $scope.trafficList = [];
    $scope.loaded = false;
    var plans = [];
    for (var i = 0; i < $scope.selectCitys.length; i++) {
        var city = $scope.selectCitys[i];
        plans.push({
            cityId: city.id,
            day: city.day
        });
    }
    if ($rootScope.previousState.name == '' || $rootScope.previousState.name == 'xing-gailan' || $rootScope.previousState.name == 'xing-cfd') {
        $http.post(GetUrl.recommend, {
                json: JSON.stringify({
                    startDate: $scope.startDay,
                    startCityId: $scope.fromCity.id,
                    plan: plans
                })
            }
        ).success(function (data) {
            if (data.success) {
                var citys = [];
                for (var i = 0; i < data.trafficList.length; i++) {
                    var city = data.trafficList[i];
                    $.each(city.traffics, function (i, obj) {
                        if (obj.trafficHash != "") {
                            obj.selected = true;
                            obj.selectPrice = obj.prices[0];
                        } else {
                            obj.selected = false;
                        }
                    });
                    if (city.traffics[0].selected && city.traffics[1].selected) {
                        if (city.traffics[0].selectPrice.price > city.traffics[1].selectPrice.price) {
                            city.traffics[0].selected = false;
                        } else {
                            city.traffics[1].selected = false;
                        }
                    }
                    $.each(city.returnTraffics, function (i, obj) {
                        if (obj.trafficHash != "") {
                            obj.selected = true;
                            obj.selectPrice = obj.prices[0];
                        } else {
                            obj.selected = false;
                        }
                    });
                    if (city.returnTraffics.length == 2 && city.returnTraffics[0].selected && city.returnTraffics[1].selected) {
                        if (city.returnTraffics[0].selectPrice.price > city.returnTraffics[1].selectPrice.price) {
                            city.returnTraffics[0].selected = false;
                        } else {
                            city.returnTraffics[1].selected = false;
                        }
                    }
                    if (city.traffics.length > 0 || city.returnTraffics.length > 0) {
                        citys.push(city);
                    }
                }
                $scope.trafficList = citys;
                $scope.loaded = true;
                storage.set(keys.jiaotong, $scope.trafficList);
            } else {

            }
        }).error(function (data) {
            alert(data.errorMsg);
        });
    } else {
        $scope.trafficList = $scope.jiaotong;
        $scope.loaded = true;
    }

    $scope.jiaotonglist = function (obj, isReturn) {
        var traffics = obj.traffics;
        var city = obj.$parent.city;
        if (isReturn) {
            city = {
                fromCityName: city.toCityName,
                fromCityId: city.toCityId,
                toCityName: $scope.fromCity.name,
                toCityId: $scope.fromCity.id,
                trafficType: traffics.trafficType,
                startDate: traffics.leaveDate
            }
        }
        //console.info(traffics);
        var params = {
            traffics: JSON.stringify({
                fromCityName: city.fromCityName,
                fromCityId: city.fromCityId,
                toCityName: city.toCityName,
                toCityId: city.toCityId,
                trafficType: traffics.trafficType,
                startDate: city.startDate
            })
        };
        if (traffics.trafficType == 'TRAIN') {
            $state.go('xing-jiaotong-huoche', params);
        } else if (traffics.trafficType == 'AIRPLANE') {
            $state.go('xing-jiaotong-jipiao', params);
        }
    };
    $scope.recommendJiudian = function () {
        for (var i = 0; i < $scope.trafficList.length; i++) {
            for (var j = 0; j < $scope.trafficList[i].traffics.length; j++) {
                var item = $scope.trafficList[i].traffics[j];
                var check = $("input[name='" + item.trafficHash + item.trafficType + "']");
                if (check.parent().hasClass('checked')) {
                    item.selected = true;
                } else {
                    item.selected = false;
                }
            }
            for (var j = 0; j < $scope.trafficList[i].returnTraffics.length; j++) {
                var item = $scope.trafficList[i].returnTraffics[j];
                var check = $("input[name='" + item.trafficHash + item.trafficType + "']");
                if (check.parent().hasClass('checked')) {
                    item.selected = true;
                } else {
                    item.selected = false;
                }
            }
        }
        storage.set(keys.jiaotong, $scope.trafficList);
        //location.href = '/#/xing-jiudian';
        $state.go('xing-jiudian');
    };

    $scope.changeTraffic = function () {
        var scope = angular.element(event.currentTarget).scope();
        var obj = scope.$parent;
        var traffics = obj.$parent.city.traffics;
        if (String(true) == attrs.isreturn) {
            traffics = obj.$parent.city.returnTraffics;
        }
        var traffic = obj.traffics;
        $.each(traffics, function (i, item) {
            if (item.trafficHash == traffic.trafficHash) {
                obj.selected = true;
            } else {
                obj.selected = false;
            }
        });
    };

    $scope.detail = function (obj, city) {
        $state.go('xing-jiaotong-info', {
            traffics: JSON.stringify({
                traffics: city,
                detail: obj.traffics
            })
        });
    };
    $scope.returndetail = function (obj, city) {
        $state.go('xing-jiaotong-info', {
            traffics: JSON.stringify({
                traffics: {
                    fromCityName: city.toCityName,
                    fromCityId: city.toCityId,
                    toCityName: $scope.fromCity.name,
                    toCityId: $scope.fromCity.id,
                    trafficType: obj.traffics.trafficType,
                    startDate: obj.traffics.leaveDate
                },
                detail: obj.traffics
            })
        });
    }

}]);
//火车列表
var xingjiaotonghuocheModule = angular.module("xingjiaotonghuocheModule", ['angularLocalStorage']);
xingjiaotonghuocheModule.controller('xingjiaotonghuocheCtrl', ['$scope', '$http', '$cookieStore', 'storage', '$state', '$stateParams', function ($scope, $http, $cookieStore, storage, $state, $stateParams) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.jiaotong = storage.get(keys.jiaotong) == null ? [] : storage.get(keys.jiaotong);
    $scope.scenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.traffics = JSON.parse($stateParams.traffics);
    $scope.loaded = false;
    $http.post(GetUrl.trafficlist, {
            startDate: $scope.traffics.startDate,
            fromCityId: $scope.traffics.fromCityId,
            toCityId: $scope.traffics.toCityId,
            trafficType: $scope.traffics.trafficType
        }
    ).success(function (data) {
        $scope.loaded = true;
        if (data.success) {
            $scope.trafficList = data.trafficList;
        } else {

        }
    }).error(function (data) {
        alert(data.errorMsg);
    });
    $scope.huochedetail = function (obj) {
        $state.go('xing-jiaotong-info', {
            traffics: JSON.stringify({
                traffics: $scope.traffics,
                detail: obj.traffics
            })
        });
    };
    $scope.replace = function (obj) {
        for (var i = 0; i < $scope.jiaotong.length; i++) {
            var city = $scope.jiaotong[i];
            for (var j = 0; j < city.traffics.length; j++) {
                var item = city.traffics[j];
                if (city.fromCityId == $scope.traffics.fromCityId && city.toCityId == $scope.traffics.toCityId && city.startDate == $scope.traffics.startDate && item.trafficType == $scope.traffics.trafficType) {
                    city.traffics[j] = obj.traffics;
                    city.traffics[j].selected = item.selected;
                    city.traffics[j].selectPrice = city.traffics[j].prices[0];
                    break;
                }
            }
            for (var j = 0; j < city.returnTraffics.length; j++) {
                var item = city.returnTraffics[j];
                if (city.toCityId == $scope.traffics.fromCityId && $scope.fromCity.id == $scope.traffics.toCityId && item.leaveDate == $scope.traffics.startDate && item.trafficType == $scope.traffics.trafficType) {
                    city.returnTraffics[j] = obj.traffics;
                    city.returnTraffics[j].selected = item.selected;
                    city.returnTraffics[j].selectPrice = city.returnTraffics[j].prices[0];
                    break;
                }
            }
        }
        storage.set(keys.jiaotong, $scope.jiaotong);
        $state.go('xing-jiaotong');
    }

}]);
//火机机票详情
var xingjiaotonginfoModule = angular.module("xingjiaotonginfoModule", ['angularLocalStorage']);
xingjiaotonginfoModule.controller('xingjiaotonginfoCtrl', ['$scope', '$http', '$cookieStore', 'storage', '$state', '$stateParams', function ($scope, $http, $cookieStore, storage, $state, $stateParams) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.scenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.jiaotong = storage.get(keys.jiaotong) == null ? [] : storage.get(keys.jiaotong);
    var trains = storage.get(keys.train) == null ? [] : storage.get(keys.train);
    var traffics = JSON.parse($stateParams.traffics);
    $scope.traffics = traffics.detail;
    $scope.city = traffics.traffics;
    $scope.replace = function (obj) {
        for (var i = 0; i < $scope.jiaotong.length; i++) {
            var city = $scope.jiaotong[i];
            for (var j = 0; j < city.traffics.length; j++) {
                var item = city.traffics[j];
                if (city.fromCityId == $scope.city.fromCityId && city.toCityId == $scope.city.toCityId && city.startDate == $scope.city.startDate && item.trafficType == $scope.traffics.trafficType) {
                    city.traffics[j] = obj.traffics;
                    city.traffics[j].selected = item.selected;
                    city.traffics[j].selectPrice = obj.price;
                    break;
                }
            }
            for (var j = 0; j < city.returnTraffics.length; j++) {
                var item = city.returnTraffics[j];
                if (city.toCityId == $scope.city.fromCityId && $scope.fromCity.id == $scope.city.toCityId && item.leaveDate == $scope.city.startDate && item.trafficType == $scope.traffics.trafficType) {
                    city.returnTraffics[j] = obj.traffics;
                    city.returnTraffics[j].selected = item.selected;
                    city.returnTraffics[j].selectPrice = obj.price;
                    break;
                }
            }
        }
        storage.set(keys.jiaotong, $scope.jiaotong);
        $state.go('xing-jiaotong');
    }
}]);
//机票列表
var xingjiaotongjipiaoModule = angular.module("xingjiaotongjipiaoModule", ['angularLocalStorage']);
xingjiaotongjipiaoModule.controller('xingjiaotongjipiaoCtrl', ['$scope', '$http', '$cookieStore', 'storage', '$state', '$stateParams', function ($scope, $http, $cookieStore, storage, $state, $stateParams) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.jiaotong = storage.get(keys.jiaotong) == null ? [] : storage.get(keys.jiaotong);
    $scope.scenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.traffics = JSON.parse($stateParams.traffics);
    $http.post(GetUrl.trafficlist, {
            startDate: $scope.traffics.startDate,
            fromCityId: $scope.traffics.fromCityId,
            toCityId: $scope.traffics.toCityId,
            trafficType: $scope.traffics.trafficType
        }
    ).success(function (data) {
        if (data.success) {
            $scope.trafficList = data.trafficList;
        } else {

        }
    }).error(function (data) {
        alert(data.errorMsg);
    });
    $scope.huochedetail = function (obj) {
        $state.go('xing-jiaotong-info', {
            traffics: JSON.stringify({
                traffics: $scope.traffics,
                detail: obj.traffics
            })
        });
    };
    $scope.replace = function (obj) {
        for (var i = 0; i < $scope.jiaotong.length; i++) {
            var city = $scope.jiaotong[i];
            for (var j = 0; j < city.traffics.length; j++) {
                var item = city.traffics[j];
                if (city.fromCityId == $scope.traffics.fromCityId && city.toCityId == $scope.traffics.toCityId && city.startDate == $scope.traffics.startDate && item.trafficType == $scope.traffics.trafficType) {
                    city.traffics[j] = obj.traffics;
                    city.traffics[j].selected = item.selected;
                    city.traffics[j].selectPrice = city.traffics[j].prices[0];
                    break;
                }
            }
            for (var j = 0; j < city.returnTraffics.length; j++) {
                var item = city.returnTraffics[j];
                if (city.toCityId == $scope.traffics.fromCityId && $scope.fromCity.id == $scope.traffics.toCityId && item.leaveDate == $scope.traffics.startDate && item.trafficType == $scope.traffics.trafficType) {
                    city.returnTraffics[j] = obj.traffics;
                    city.returnTraffics[j].selected = item.selected;
                    city.returnTraffics[j].selectPrice = city.returnTraffics[j].prices[0];
                    break;
                }
            }
        }
        storage.set(keys.jiaotong, $scope.jiaotong);
        $state.go('xing-jiaotong');
    }
}]);
//推荐酒店
var xingjiudianModule = angular.module("xingjiudianModule", ['angularLocalStorage']);
xingjiudianModule.controller('xingjiudianCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$state', 'Check', 'MyStorage', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $state, Check, MyStorage) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.scenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.jiaotong = storage.get(keys.jiaotong) == null ? [] : storage.get(keys.jiaotong);
    var jiudian = storage.get(keys.jiudian) == null ? [] : storage.get(keys.jiudian);
    $scope.user = storage.get(keys.user);
    $scope.hotels = [];
    //$scope.ajaxloading =  true;
    if ($scope.addScenics.length == 0) {
        $state.go('my');
        return;
    }
    function getCity(id) {
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            if ($scope.selectCitys[i].id == id) {
                return $scope.selectCitys[i];
            }
        }
        return 0;
    }

    var plans = [];
    var city = null;
    var lastFromCity = null;
    for (var i = 0; i < $scope.scenics.length; i++) {
        var item = $scope.scenics[i];
        if (city == null || city.cityId != item.city.id) {
            city = {};
            city.day = getCity(item.city.id).day;
            city.cityId = item.city.id;
            city.tripList = [];
            plans.push(city);
        }
        var result = [];
        for (var j = 0; j < item.tripList.length; j++) {
            result.push({
                id: item.tripList[j].id,
                ranking: item.tripList[j].ranking
            });
        }
        city.tripList = city.tripList.concat(result);
    }
    if ($rootScope.previousState.name == '' || $rootScope.previousState.name == 'xing-gailan' || $rootScope.previousState.name == 'xing-cfd') {
        $("div.ajaxloading").show();
        $http.post(GetUrl.hotelRecommend, {
                json: JSON.stringify({
                    startDate: $scope.startDay,
                    startCityId: $scope.fromCity.id,
                    plan: plans
                })
            }
        ).success(function (data) {
            if (data.success) {
                for (var i = 0; i < data.hotelList.length; i++) {
                    var currentCity = null;
                    for (var j = 0; j <= $scope.selectCitys.length; j++) {
                        if (data.hotelList[i].cityId == $scope.selectCitys[j].id) {
                            data.hotelList[i].city = $scope.selectCitys[j];
                            data.hotelList[i].checked = true;
                            break;
                        }
                    }
                }
                $scope.hotels = data.hotelList;
                if (data.hotelList.length == 0) {
                    $scope.nohotels = true;
                } else {
                    $scope.nohotels = false;
                }
                storage.set(keys.jiudian, $scope.hotels)
            } else {

            }
            $("div.ajaxloading").hide();
        }).error(function (data) {
            alert(data.errorMsg);
            $("div.ajaxloading").hide();
        });
    } else {
        $scope.hotels = jiudian;
        if ($scope.hotels.length == 0) {
            $scope.nohotels = true;
        }
    }
    var totalDay = 0;

    function getTitle() {
        var title = "";
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            var city = $scope.selectCitys[i];
            if (i == $scope.selectCitys.length - 1) {
                title += city.name;
            } else {
                title += city.name + "、";
            }
            totalDay += parseInt(city.day);
        }
        title += totalDay + '日游';
        return title;
    }

    function getDay() {


        //for (var i = 0; i < $scope.scenics.length; i++) {
        //    var item = $scope.scenics[i];
        //    if (item.fromCity != null) {
        //        city = {};
        //        city.day = getCity(item.city.id).day;
        //        city.cityId = item.city.id;
        //        city.tripList = [];
        //        plans.push(city);
        //    }
        //    var result = [];
        //    for (var j = 0; j < item.tripList.length; j++) {
        //        result.push({
        //            id: item.tripList[j].id,
        //            ranking: item.tripList[j].ranking
        //        });
        //    }
        //    city.tripList = city.tripList.concat(result);
        //}


        var days = [];
        for (var i = 0; i < $scope.scenics.length; i++) {
            var item = $scope.scenics[i];
            var trips = [];
            for (var j = 0; j < item.tripList.length; j++) {
                trips.push({
                    id: 0,
                    scenicId: item.tripList[j].id,
                    type: 1
                });
            }
            days.push({
                cityId: item.city.id,
                trips: trips.reverse()
            });
        }
        return days;
    }

    function getTrafficAndHotels() {
        var trafficAndHotels = [];
        for (var i = 0; i < $scope.selectCitys.length; i++) {
            var city = $scope.selectCitys[i];
            var trafficAndHotel = {
                cityId: city.id
            };
            $.each($scope.hotels, function (i, hotel) {
                if (hotel.checked && hotel.cityId == city.id) {
                    trafficAndHotel.hotel = {
                        hotelId: hotel.id,
                        priceId: hotel.priceId,
                        startDate: hotel.startDate,
                        endDate: hotel.endDate
                    }
                }
            });
            //去除交通下单
            //for (var j = 0; j < $scope.jiaotong.length; j++) {
            //    var jiaotong = $scope.jiaotong[j];
            //    if (city.id == jiaotong.toCityId) {
            //        for (var tl = 0; tl < jiaotong.traffics.length; tl++) {
            //            var trafficItem = jiaotong.traffics[tl];
            //            if (trafficItem.selected) {
            //                trafficAndHotel.traffic = {
            //                    key: jiaotong.fromCityId + "##" + jiaotong.toCityId + "##" + trafficItem.trafficType + "##" + jiaotong.startDate.replace(/\-/g, ''),
            //                    trafficHash: trafficItem.trafficHash,
            //                    priceHash: trafficItem.selectPrice.priceHash
            //                };
            //                break;
            //            }
            //        }
            //        for (var tl = 0; tl < jiaotong.returnTraffics.length; tl++) {
            //            var trafficItem = jiaotong.returnTraffics[tl];
            //            if (trafficItem.selected) {
            //                trafficAndHotel.returnTraffic = {
            //                    key: jiaotong.toCityId + "##" + $scope.fromCity.id + "##" + trafficItem.trafficType + "##" + trafficItem.leaveDate.replace(/\-/g, ''),
            //                    trafficHash: trafficItem.trafficHash,
            //                    priceHash: trafficItem.selectPrice.priceHash
            //                };
            //                break;
            //            }
            //        }
            //    }
            //}
            trafficAndHotels.push(trafficAndHotel);

        }
        //for (var i = 0; i < $scope.hotels.length; i++) {
        //    var hotel = $scope.hotels[i];
        //    var traffic = {};
        //    var returnTraffic = {};
        //    for (var j = 0; j < $scope.jiaotong.length; j++) {
        //        var jiaotong = $scope.jiaotong[j];
        //        if (hotel.city.id == jiaotong.toCityId) {
        //            for (var tl = 0; tl < jiaotong.traffics.length; tl++) {
        //                var trafficItem = jiaotong.traffics[tl];
        //                if (trafficItem.selected) {
        //                    traffic = {
        //                        key: jiaotong.fromCityId + "##" + jiaotong.toCityId + "##" + trafficItem.trafficType + "##" + jiaotong.startDate.replace(/\-/g, ''),
        //                        trafficHash: trafficItem.trafficHash,
        //                        priceHash: trafficItem.selectPrice.priceHash
        //                    };
        //                    break;
        //                }
        //            }
        //            for (var tl = 0; tl < jiaotong.returnTraffics.length; tl++) {
        //                var trafficItem = jiaotong.returnTraffics[tl];
        //                if (trafficItem.selected) {
        //                    returnTraffic = {
        //                        key: jiaotong.toCityId + "##" + $scope.fromCity.id + "##" + trafficItem.trafficType + "##" + trafficItem.leaveDate.replace(/\-/g, ''),
        //                        trafficHash: trafficItem.trafficHash,
        //                        priceHash: trafficItem.selectPrice.priceHash
        //                    };
        //                    break;
        //                }
        //            }
        //        }
        //    }
        //    trafficAndHotels.push({
        //        cityId: hotel.city.id,
        //        hotel: hotel.checked ? {
        //            hotelId: hotel.id,
        //            priceId: hotel.priceId,
        //            startDate: hotel.startDate,
        //            endDate: hotel.endDate
        //        } : {},
        //        traffic: traffic,
        //        returnTraffic: returnTraffic
        //    });
        //}
        return trafficAndHotels;
    }

    function getTraffic() {
        var traffics = [];
        for (var j = 0; j < $scope.jiaotong.length; j++) {
            var jiaotong = $scope.jiaotong[j];
            if (city.id == jiaotong.toCityId) {
                for (var tl = 0; tl < jiaotong.traffics.length; tl++) {
                    var trafficItem = jiaotong.traffics[tl];
                    if (trafficItem.selected) {
                        traffics.push({
                            key: jiaotong.fromCityId + "##" + jiaotong.toCityId + "##" + trafficItem.trafficType + "##" + jiaotong.startDate.replace(/\-/g, ''),
                            trafficHash: trafficItem.trafficHash,
                            priceHash: trafficItem.selectPrice.priceHash
                        });
                        break;
                    }
                }
                for (var tl = 0; tl < jiaotong.returnTraffics.length; tl++) {
                    var trafficItem = jiaotong.returnTraffics[tl];
                    if (trafficItem.selected) {
                        traffics.push({
                            key: jiaotong.toCityId + "##" + $scope.fromCity.id + "##" + trafficItem.trafficType + "##" + trafficItem.leaveDate.replace(/\-/g, ''),
                            trafficHash: trafficItem.trafficHash,
                            priceHash: trafficItem.selectPrice.priceHash
                        });
                        break;
                    }
                }
            }
        }
        return traffics;
    }

    function getHotel() {
        var hotels = [];
        $.each($scope.hotels, function (i, hotel) {
            if (hotel.checked) {
                hotels.push({
                    name: hotel.name,
                    payType: hotel.payType,
                    hotelId: hotel.id,
                    priceId: hotel.priceId,
                    startDate: hotel.startDate,
                    leaveDate: hotel.endDate
                });
            }
        });
        return hotels;
    }

    function getPlan() {
        var plan = [];
        for (var i = 0; i < $scope.scenics.length; i++) {
            var item = $scope.scenics[i];
            var scenicIds = [];
            for (var j = 0; j < item.tripList.length; j++) {
                scenicIds.push(item.tripList[j].id);
            }
            plan.push({
                cityId: item.city.id,
                cityName: item.city.name,
                day: item.day,
                scenicIds: scenicIds
            });
        }
        return plan;
    }

    $scope.finish = function () {
        $("div.ajaxloading").show();
        //$state.go('xing-xianlu-info', {id: -1});
        $http.post(GetUrl.save, {
                json: JSON.stringify({
                    id: 0,
                    name: getTitle(),
                    startCityId: $scope.fromCity.id,
                    startDate: $scope.startDay,
                    //userId: $scope.user.id,
                    days: getDay(),
                    trafficAndHotel: getTrafficAndHotels().reverse()
                })
            }
        ).success(function (data) {
            if (data.success != null && !data.success && data.nologin != null && data.nologin) {
                bootbox.alert('请先去登录', function () {
                    //location.href = '/#/login?url=' + document.location.href;
                    $state.go('login');
                });
            } else {
                if (data.success) {
                    //bootbox.alert('线路保存成功');
                    //MyStorage.reset();
                    $state.go('xing-xianlu-info', {id: data.id});
                    storage.set(keys.plan, {
                        planId: data.id,
                        name: getTitle(),
                        day: totalDay,
                        playDate: $scope.startDay,
                    });
                } else {

                }
            }
            $("div.ajaxloading").hide();
        }).error(function (data) {
            $("div.ajaxloading").hide();
            alert(data.errorMsg);
        });
    };
    $scope.buy = function () {
        $("div.ajaxloading").show();
        $http.post(GetUrl.planOrder, {
                json: JSON.stringify({
                    name: getTitle(),
                    playDate: $scope.startDay,
                    traffic: getTraffic(),
                    hotel: getHotel(),
                    plan: getPlan()
                })
            }
        ).success(function (data) {
            if (data.success != null && !data.success && data.nologin != null && data.nologin) {
                bootbox.alert('请先去登录', function () {
                    //location.href = '/#/login?url=' + document.location.href;
                    $state.go('login');
                });
            } else {
                if (data.success) {
                    storage.set(keys.order, data.order);
                    $state.go('xing-xianlu-order-info', {planIdorderId: JSON.stringify({})});
                } else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: data.errorMsg + "<br/>请点击返回重新查询交通"
                    });
                }
            }
            $("div.ajaxloading").hide();
        }).error(function (data) {
            $("div.ajaxloading").hide();
            alert(data.errorMsg);
        });
    };
    $scope.jiudianDetail = function (ele) {
        var hotel = ele.hotel;
        $state.go('xing-jiudian-info', {
            hotel: JSON.stringify({
                id: hotel.id,
                startDate: hotel.startDate,
                endDate: hotel.endDate
            })
        });
    };
    $scope.search = function (obj) {
        $state.go('xing-jiudian-search', {
            hotel: JSON.stringify({
                startDate: obj.hotel.startDate,
                endDate: obj.hotel.endDate,
                cityId: obj.hotel.cityId
            })
        });
        //$state.go('my');
    }
}]);

//酒店列表
var jiudiansearchModule = angular.module("jiudiansearchModule", ['angularLocalStorage', 'infinite-scroll']);
jiudiansearchModule.controller('jiudiansearchCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, $state) {

    $scope.jiudian = storage.get(keys.jiudian) == null ? [] : storage.get(keys.jiudian);
    $scope.replaceHotel = JSON.parse($stateParams.hotel);
    $scope.pageNo = 1;
    $scope.pageSize = 6;
    $scope.star = '';
    $scope.priceRange = [];
    $scope.orderColumn = 'ranking';
    $scope.orderType = 'asc';
    $scope.key = '';
    $scope.hotels = [];
    $scope.loading = false;
    $scope.nomore = false;
    $scope.showDark1 = false;


    $scope.listHotel = function () {
        if ($scope.loading) return;
        if ($scope.nomore) return;
        $scope.loading = true;
        var key = $("#key").val();
        var citys = [];
        citys.push($scope.replaceHotel.cityId);
        $http.post(GetUrl.hotelList, {
                json: JSON.stringify({
                    name: $scope.key,
                    cityIds: citys,
                    star: $scope.star,
                    priceRange: $scope.priceRange,
                    orderColumn: $scope.orderColumn,
                    orderType: $scope.orderType,
                    startDate: $scope.replaceHotel.startDate,
                    endDate: $scope.replaceHotel.endDate
                }),
                pageNo: $scope.pageNo,
                pageSize: $scope.pageSize
            }
        ).success(function (data) {
            if (data.success) {
                $.each(data.hotelList, function (i, item) {
                    $scope.hotels.push(item);
                });
                if (!data.nomore) {
                    $scope.pageNo++;
                }
                $scope.nomore = data.nomore;
            } else {
            }
            $scope.showDark1 = false;
            $scope.loading = false;
            $('body').css({'overflow':"auto"});
            $('html').css({'overflow':"auto"});
        }).error(function (data) {
            alert(data.errorMsg);
            $scope.showDark1 = false;
            $scope.loading = false;
        });
    };

    $scope.jiudianDetail = function (ele) {
        var hotel = ele.hotel;
        $state.go('xing-jiudian-info', {
            hotel: JSON.stringify({
                id: hotel.id,
                startDate: $scope.replaceHotel.startDate,
                endDate: $scope.replaceHotel.endDate
            })
        });
    };
    function reset() {
        $scope.pageNo = 1;


        $scope.hotels = [];
        $scope.loading = false;
        $scope.nomore = false;
    }

    $scope.changeOrder = function (orderColumn, orderType) {
        reset();
        $scope.orderColumn = orderColumn;
        $scope.orderType = orderType;
        $scope.listHotel();
    };
    $scope.changeRange = function (down, up) {
        reset();
        $scope.priceRange = [down, up];
        $scope.listHotel();
    };
    $scope.changeStar = function (star) {
        reset();
        $scope.star = star;
        $scope.listHotel();
    };

    $scope.replace = function (obj) {
        for (var i = 0; i < $scope.jiudian.length; i++) {
            var hotel = $scope.jiudian[i];
            if (hotel.cityId == obj.hotel.cityId) {
                $scope.jiudian[i] = obj.hotel;
                $scope.jiudian[i].checked = hotel.checked;
                $scope.jiudian[i].city = hotel.city;
                $scope.jiudian[i].startDate = hotel.startDate;
                $scope.jiudian[i].endDate = hotel.endDate;
                //$scope.jiudian[i].price = obj.price.price;
                //$scope.jiudian[i].priceId = obj.price.priceId;
                //$scope.jiudian[i].priceName = obj.price.priceName;
                //$scope.jiudian[i].payType = '';
                break;
            }
        }
        storage.set(keys.jiudian, $scope.jiudian);
        $state.go('xing-jiudian');
    };

    $scope.toSearch = function () {
        var hotel = {
            cityIds: [$scope.replaceHotel.cityId],
            startDate: $scope.replaceHotel.startDate,
            endDate: $scope.replaceHotel.endDate
        };
        $state.go("xing-jiudian-key-search", {hotel: JSON.stringify(hotel)});
    }
    //2016.9.10
    $scope.clickUl = function(index) {
        $scope.showDark1 = true;
        if (index == 11) {
            $('.sliderbur1').animate({'left':0},80);
        } else if (index == 12) {
            $('.sliderbur1').animate({'left':'33%'},80);
        } else {
            $('.sliderbur1').animate({'left':'66%'},80);
        }
        var Sheight = $(window).height();
        var showS = $scope.showDark1?'hidden':'auto';
        var showH = $scope.showDark1?Sheight:'auto';
        $('body').css({'overflow':showS,'height':showH});
        $('html').css({'overflow':showS,'height':showH});
    };

    $scope.hideDark = function () {

        $scope.showDark1 = false;
        var Sheight = $(window).height();
        var showS = $scope.showDark1?'hidden':'auto';
        var showH = $scope.showDark1?Sheight:'auto';
        $('body').css({'overflow':showS,'height':showH});
        $('html').css({'overflow':showS,'height':showH});
    }
    //$('#ul_11').click(function(){
    //    $('.sliderbur1').animate({'left':0},80);
    //
    //    $('body').css({'overflow':'hidden'})
    //})
    //$('#ul_12').click(function(){
    //    $('.sliderbur1').animate({'left':'33%'},80);
    //    $scope.showDark1 = true;
    //    $('body').css({'overflow':'hidden'})
    //})
    //$('#ul_13').click(function(){
    //    $('.sliderbur1').animate({'left':'66%'},80);
    //    $scope.showDark1 = true;
    //    $('body').css({'overflow':'hidden'})
    //})

}]);

//名称搜索酒店
var xingjiudiankeysearchModule = angular.module("xingjiudiankeysearchModule", []);
xingjiudiankeysearchModule.controller("xingjiudiankeysearchCtrl", function ($scope, $http, $state, $stateParams) {
    $scope.hotel = JSON.parse($stateParams.hotel);
    $scope.hotelList = function () {
        if ($scope.key == undefined || $scope.key.length < 1) {
            return;
        }
        var json = {
            name: $scope.key,
            cityIds: $scope.hotel.cityIds,
            startDate: $scope.hotel.startDate,
            endDate: $scope.hotel.endDate
        };
        $http.post(GetUrl.hotelList, {
                json: JSON.stringify(json),
                pageNo: 1,
                pageSize: 10
            }
        ).success(function (data) {
            angular.forEach(data.hotelList, function (hotel) {
                hotel.html = hotel.name.replace($scope.key, "<b>" + $scope.key + "</b>");
            });
            $scope.hotels = data.hotelList;
        }).error(function (data) {
            alert(data.errorMsg);
        });
    };

    $scope.toDetail = function (obj) {
        var hotel = {
            id: obj.hotel.id,
            startDate: $scope.hotel.startDate,
            endDate: $scope.hotel.endDate
        };
        $state.go("xing-jiudian-info", {hotel: JSON.stringify(hotel)});
    }
});

//酒店详情
var xingjiudianinfoModule = angular.module("xingjiudianinfoModule", ['angularLocalStorage']);
xingjiudianinfoModule.controller('xingjiudianinfoCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, $state) {
    $scope.selectCitys = storage.get(keys.selectCitys) == null ? [] : storage.get(keys.selectCitys);
    $scope.addScenics = storage.get(keys.addScenics) == null ? [] : storage.get(keys.addScenics);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.fromCity = storage.get(keys.fromCity);
    $scope.scenics = storage.get(keys.optimizeScenics) == null ? [] : storage.get(keys.optimizeScenics);
    $scope.jiaotong = storage.get(keys.jiaotong) == null ? [] : storage.get(keys.jiaotong);
    $scope.param = JSON.parse($stateParams.hotel);
    $scope.jiudian = storage.get(keys.jiudian) == null ? [] : storage.get(keys.jiudian);
    $scope.user = storage.get(keys.user);

    $http.post(GetUrl.hotelDetail, {
            id: $scope.param.id,
            startDate: $scope.param.startDate,
            endDate: $scope.param.endDate
        }
    ).success(function (data) {
        if (data.success) {
            $scope.hotel = data.hotel;
            $scope.hotel.startDate = $scope.param.startDate;
            $scope.hotel.endDate = $scope.param.endDate;
            storage.set(keys.hotel, $scope.hotel);
            var date = new Date();
            var currYear = (date).getFullYear();
            var opt = {};
            opt.date = {preset: 'date'};
            //opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
            opt.datetime = {preset: 'datetime'};
            opt.time = {preset: 'time'};
            opt.default = {
                theme: 'android-ics light', //皮肤样式
                display: 'modal', //显示方式
                mode: 'scroller', //日期选择模式
                lang: 'zh',
                defaultValue: date,
                startYear: currYear, //开始年份
                endYear: currYear + 1, //结束年份
                minDate: new Date($scope.hotel.startDate.replace(/-/g, "/")),
                maxDate: new Date($scope.hotel.endDate.replace(/-/g, "/"))
            };

            var optDateTime = $.extend(opt['datetime'], opt['default']);
            var optTime = $.extend(opt['time'], opt['default']);
            $("#appDate").val($scope.hotel.startDate).scroller('destroy').scroller($.extend(opt['date'], opt['default']));
            $("#appDateTime").val($scope.hotel.endDate).scroller('destroy').scroller($.extend(opt['date'], opt['default']));
            //$("#appDateTime").mobiscroll(optTime).time(optTime);
            $("#appTime").mobiscroll(optTime).time(optTime);

            setTimeout(function () {
                var listener = function () {
                    $http.post(GetUrl.hotelDetail, {
                            id: $scope.param.id,
                            startDate: $scope.hotel.startDate,
                            endDate: $scope.hotel.endDate
                        }
                    ).success(function (data) {
                        if (data.success) {
                            $scope.hotel.prices = data.hotel.prices;
                        }
                    });
                    //alert($scope.hotel.startDate + 'hotel was modifyed' + $scope.hotel.endDate);
                };
                $scope.$watch('hotel.startDate', listener, true);
                $scope.$watch('hotel.endDate', listener, true);
            }, 500);

        } else {

        }
    }).error(function (data) {
        alert(data.errorMsg);
    });

    $scope.replace = function (obj) {
        for (var i = 0; i < $scope.jiudian.length; i++) {
            var hotel = $scope.jiudian[i];
            if (hotel.cityId == $scope.hotel.cityId) {
                $scope.jiudian[i] = $scope.hotel;
                $scope.jiudian[i].checked = hotel.checked;
                $scope.jiudian[i].city = hotel.city;
                $scope.jiudian[i].price = obj.price.price;
                $scope.jiudian[i].priceId = obj.price.priceId;
                $scope.jiudian[i].priceName = obj.price.priceName;
                $scope.jiudian[i].payType = '';
                break;
            }
        }
        storage.set(keys.jiudian, $scope.jiudian);
        $state.go('xing-jiudian');
    }

}]);
//酒店地图
var xingjiudianmapModule = angular.module("xingjiudianmapModule", []);
xingjiudianmapModule.controller("xingjiudianmapCtrl", function ($scope, storage) {
    $scope.hotel = storage.get(keys.hotel);
    // 百度地图API功能
    var sContent =
        "<h4 style='margin:0 0 5px 0;padding:0.2em 0'>" + $scope.hotel.name + "</h4>" +
        "<img style='float:right;margin:4px' id='imgDemo' src='" + $scope.hotel.cover + "' width='139' height='104' title='" + $scope.hotel.name + "'/>" +
        "<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>" + $scope.hotel.introduction + "</p>" +
        "</div>";
    var map = new BMap.Map("allmap", {enableMapClick: false});
    var point = new BMap.Point($scope.hotel.longitude, $scope.hotel.latitude);
    var marker = new BMap.Marker(point);
    var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
    map.centerAndZoom(point, 19);
    map.addControl(new BMap.NavigationControl());// 添加平移缩放控件
    map.enableScrollWheelZoom();//启用滚轮放大缩小
    map.addOverlay(marker);
    marker.addEventListener("click", function () {
        this.openInfoWindow(infoWindow);
        //图片加载完毕重绘infowindow
        document.getElementById('imgDemo').onload = function () {
            infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
        }
    });
});
//保存线路的订单
var xingxianluorderinfoModule = angular.module("xingxianluorderinfoModule", ['angularLocalStorage']);
xingxianluorderinfoModule.controller('xingxianluorderinfoCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check) {

    $scope.planIdorderId = JSON.parse($stateParams.planIdorderId);
    $scope.order = storage.get(keys.order) == null ? [] : storage.get(keys.order);
    //$scope.user = storage.get(keys.user);
    //
    $scope.loaded = $scope.order != [];
    if ($scope.order != []) {
        var total = 0;
        $.each($scope.order.hotels, function (i, item) {
            $scope.order.hotelscheck = true;
            item.check = true;
            //total += item.price
        });
        $.each($scope.order.planes, function (i, item) {
            $scope.order.planescheck = true;
            item.check = true;
            total += item.price + item.additionalFuelTax + item.airportBuildFee;
        });
        $.each($scope.order.trains, function (i, item) {
            $scope.order.trainscheck = true;
            item.check = true;
            total += item.price
        });
        $.each($scope.order.days, function (i, item) {
            item.check = true;
            var tickets = [];
            $.each(item.tickets, function (i, ticket) {
                if (ticket.status == 'normal') {
                    ticket.check = true;
                    total += ticket.price;
                    tickets.push(ticket);
                }
            });
            item.tickets = tickets;
        });
        storage.set(keys.order, $scope.order);
        $scope.total = total;
        storage.set(keys.productcost, total);
    }
    if ($scope.planIdorderId.planId) {
        $http.post(GetUrl.order, {
                planId: $scope.planIdorderId.planId
            }
        ).success(function (data) {
            $scope.loaded = true;
            if (Check.loginCheckAndBack(data)) {
                if (data.success) {
                    var total = 0;
                    var order = data.order;
                    $.each(order.hotels, function (i, item) {
                        order.hotelscheck = true;
                        item.check = true;
                        //total += item.price
                    });
                    $.each(order.planes, function (i, item) {
                        order.planescheck = true;
                        item.check = true;
                        total += item.price + item.additionalFuelTax + item.airportBuildFee;
                    });
                    $.each(order.trains, function (i, item) {
                        order.trainscheck = true;
                        item.check = true;
                        total += item.price
                    });
                    $.each(order.days, function (i, item) {
                        item.check = true;
                        var tickets = [];
                        $.each(item.tickets, function (i, ticket) {
                            if (ticket.status == 'normal') {
                                ticket.check = true;
                                total += ticket.price;
                                tickets.push(ticket);
                            }
                        });
                        item.tickets = tickets;
                    });
                    $scope.order = data.order;
                    storage.set(keys.order, data.order);
                    $scope.total = total;
                    storage.set(keys.productcost, total);

                } else {
                }
            }

        }).error(function (data) {
            $scope.loaded = true;
            alert(data.errorMsg);
        });
    }
    if ($scope.planIdorderId.orderId) {
        $http.post(GetUrl.myorderinfo, {
                orderId: $scope.planIdorderId.orderId
            }
        ).success(function (data) {
            $scope.loaded = true;
            if (Check.loginCheckAndBack(data)) {
                if (data.success) {
                    var total = 0;
                    var order = data.order;
                    $.each(order.hotels, function (i, item) {
                        order.hotelscheck = true;
                        item.check = true;
                        //total += item.price
                    });
                    $.each(order.planes, function (i, item) {
                        order.planescheck = true;
                        item.check = true;
                        total += item.price
                    });
                    $.each(order.trains, function (i, item) {
                        order.trainscheck = true;
                        item.check = true;
                        total += item.price
                    });
                    $.each(order.days, function (i, item) {
                        item.check = true;
                        var tickets = [];
                        $.each(item.tickets, function (i, ticket) {
                            if (ticket.status == 'normal') {
                                ticket.check = true;
                                total += ticket.price;
                                tickets.push(ticket);
                            }
                        });
                        item.tickets = tickets;
                    });
                    $scope.order = data.order;
                    storage.set(keys.order, data.order);
                    $scope.total = total;
                    storage.set(keys.productcost, total);

                } else {
                }
            }

        }).error(function (data) {
            $scope.loaded = true;
            alert(data.errorMsg);
        });
    }

}]);
//填写旅客信息
var xingaddlvkeModule = angular.module("xingaddlvkeModule", ['angularLocalStorage']);
xingaddlvkeModule.controller('xingaddlvkeCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', 'DateUtils', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check, DateUtils, $state) {

    //$scope.planId = $stateParams.planId;
    $scope.order = storage.get(keys.order) == null ? [] : storage.get(keys.order);
    $scope.productcost = storage.get(keys.productcost) == null ? 0 : storage.get(keys.productcost);
    $scope.youhui = storage.get(keys.youhui) == null ? {price: 0} : storage.get(keys.youhui);
    $scope.selectedtourist = storage.get(keys.selectedtourist) == null ? [] : storage.get(keys.selectedtourist);
    //$scope.plan = storage.get(keys.plan) == null ? {} : storage.get(keys.plan);
    $scope.usecoupon = storage.get(keys.usecoupon) == null ? {faceValue: 0} : storage.get(keys.usecoupon);
    $scope.contact = storage.get(keys.contact) == null ? {} : storage.get(keys.contact);
    $scope.invoice = storage.get(keys.invoice) == null ? {} : storage.get(keys.invoice);
    $scope.creditCard = storage.get(keys.creditCard) == null ? {} : storage.get(keys.creditCard);
    //$scope.user = storage.get(keys.user);

    $http.post(GetUrl.touristList, {}
    ).success(function (data) {
        $scope.loaded = true;
        if (Check.loginCheckAndBack(data)) {
            if (data.success) {
                var select = [];
                for (var i = 0; i < $scope.selectedtourist.length; i++) {
                    var selected = $scope.selectedtourist[i];
                    for (var j = 0; j < data.touristList.length; j++) {
                        var user = data.touristList[j];
                        if (user.touristId == selected.touristId) {
                            select.push(selected);
                        }
                    }
                }
                storage.set(keys.selectedtourist, select);
                $scope.selectedtourist = select;
            } else {
            }
        }

    });
    //
    $scope.loaded = false;
    $scope.withDanBao = false;
    $.each($scope.order.hotels, function (i, item) {
        if (item.check && item.payType == '担保') {
            $scope.withDanBao = true;

        }
    });
    if ($scope.withDanBao) {
        setTimeout(function () {
            var date = new Date();
            var currYear = (date).getFullYear();
            var appDate = $("#appDate");
            if ($scope.creditCard.expirationYear && $scope.creditCard.expirationMonth) {
                appDate.val($scope.creditCard.expirationYear + '年' + $scope.creditCard.expirationMonth + '月');
            }
            appDate.mobiscroll().date({
                theme: "android-ics light",
                lang: "zh",
                cancelText: null,
                mode: 'scroller',
                startYear: currYear, //开始年份
                endYear: currYear + 10, //结束年份
                minDate: date,
                dateFormat: 'yy年mm月', //返回结果格式化为年月格式
                // wheels:[], 设置此属性可以只显示年月，此处演示，就用下面的onBeforeShow方法,另外也可以用treelist去实现
                onBeforeShow: function (inst) {
                    //inst.settings.wheels[0].length > 2 ? inst.settings.wheels[0].pop() : null;
                }, //弹掉“日”滚轮
                headerText: function (valueText) { //自定义弹出框头部格式
                    //array = valueText.split('/');
                    //return array[0] + "年" + array[1] + "月";
                    return valueText;
                }
            });

        }, 500);
        //$scope.settings = {
        //    theme: 'mobiscroll',         // Specify theme like: theme: 'ios' or omit setting to use default
        //    lang: 'zh',                      // Specify language like: lang: 'pl' or omit setting to use default
        //    display: 'bottom',               // Specify display mode like: display: 'bottom' or omit setting to use default
        //    min: date,  // More info about min: https://docs.mobiscroll.com/3-0-0_beta/angular/datetime#!opt-min
        //};

    }
    $http.post(GetUrl.couponList, {}
    ).success(function (data) {
        $scope.loaded = true;
        if (Check.loginCheckAndBack(data)) {
            if (data.success) {
                var total = 0;
                var coupons = [];
                for (var i = 0; i < data.unusedCouponList.length; i++) {
                    var item = data.unusedCouponList[i];
                    if (item.limitProductTypes.indexOf('线路') > -1) {
                        coupons.push(item);
                    }
                }
                if (coupons.length == 0) {
                    $scope.usecoupon = {faceValue: 0};
                }
                $scope.coupons = coupons;
                storage.set(keys.coupons, coupons);
                $scope.total = total;

            } else {
            }
        }

    }).error(function (data) {
        $scope.loaded = true;
        alert(data.errorMsg);
    });


    $scope.orderOk = function () {
        if ($scope.selectedtourist.length == 0) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '请选添加旅客信息'
            });
            return;
        }
        if ($scope.orderForm.name.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '姓名信息不正确'
            });
            return;
        }
        if ($scope.orderForm.telephone.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '手机信息不正确'
            });
            return;
        }
        storage.set(keys.contact, $scope.contact);
        var invoice = $("div.icheckbox_square-green.checked").size() > 0;
        if (invoice) {
            if ($scope.orderForm.invoicename.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '凭证收件人信息不正确'
                });
                return;
            }
            if ($scope.orderForm.invoicetelephone.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '凭证手机不正确'
                });
                return;
            }
            if ($scope.orderForm.invoiceaddress.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '凭证地址不正确'
                });
                return;
            }
            if ($scope.orderForm.invoicetitle.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '凭证抬头不正确'
                });
                return;
            }
            storage.set(keys.invoice, $scope.invoice);
        }
        if ($scope.withDanBao) {
            if ($scope.orderForm.cardNum.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '信用卡卡号不正确'
                });
                return;
            }
            if ($scope.orderForm.holderName.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '挂卡人信息不正确'
                });
                return;
            }
            if ($.trim($("#appDate").val()).length == 0) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '有效日期不正确'
                });
                return;
            } else {
                var validDay = $.trim($("#appDate").val()).split('年');
                $scope.creditCard.expirationYear = validDay[0];
                $scope.creditCard.expirationMonth = validDay[1].replace('月', '');
            }
            if ($scope.orderForm.idNo.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '身份证信息不正确'
                });
                return;
            }
            if ($scope.orderForm.cvv.$invalid) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '校验码不正确'
                });
                return;
            }
            $http.post(GetUrl.validateCreditCart, {
                cardNum: $scope.creditCard.cardNum
            }).success(function (data) {
                if (data.success) {
                    storage.set(keys.creditCard, $scope.creditCard);
                    $state.go('xing-order-ok');
                } else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: '该信用卡暂不支持'
                    });

                }
            }).error(function (data) {

            });
        } else {
            $state.go('xing-order-ok');
        }
    }

}]);
//旅客列表
var youkechangModule = angular.module("youkechangModule", ['angularLocalStorage']);
youkechangModule.controller('youkechangCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check, $state) {

    //$scope.planId = $stateParams.planId;
    var order = storage.get(keys.order) == null ? [] : storage.get(keys.order);
    $scope.selectedtourist = storage.get(keys.selectedtourist) == null ? [] : storage.get(keys.selectedtourist);

    //$scope.user = storage.get(keys.user);
    //
    $scope.loaded = false;
    $scope.touristList = [];


    $http.post(GetUrl.touristList, {}
    ).success(function (data) {
        $scope.loaded = true;
        if (Check.loginCheckAndBack(data)) {
            if (data.success) {
                var select = [];
                for (var i = 0; i < $scope.selectedtourist.length; i++) {
                    var selected = $scope.selectedtourist[i];
                    for (var j = 0; j < data.touristList.length; j++) {
                        var user = data.touristList[j];
                        if (user.touristId == selected.touristId) {
                            select.push(selected);
                            break;
                        }
                    }
                }
                storage.set(keys.selectedtourist, select);
                $.each(data.touristList, function (i, item) {
                    for (var i = 0; i < $scope.selectedtourist.length; i++) {
                        var selected = $scope.selectedtourist[i];
                        if (item.touristId == selected.touristId) {
                            item.check = true;
                            break;
                        }
                    }
                    if (!item.check) {
                        item.check = false;
                    }
                });
                $scope.touristList = data.touristList;
                $("#selectNum").html($scope.selectedtourist.length);
                if ($scope.touristList.length == 0) {
                    $state.go('my-bianji-user', {tourist: "{}"});
                }
            } else {
            }
        }

    }).error(function (data) {
        $scope.loaded = true;
        alert(data.errorMsg);
    });

    $scope.edit = function (obj) {
        var tourist = obj.tourist;
        delete tourist.$$hashKey;
        delete tourist.check;
        $state.go('my-bianji-user', {tourist: JSON.stringify(tourist)});
    }

}]);
/**
 * 添加常用乘客列表
 */
var mybianjiuserModule = angular.module('mybianjiuserModule', ['angularLocalStorage']);
mybianjiuserModule.controller('mybianjiuserCtrl', ['$scope', '$http', '$location', 'storage', '$stateParams', function ($scope, $http, $location, storage, $stateParams) {

    $scope.json = JSON.parse($stateParams.tourist);
    $scope.json.idType = "IDCARD";
    $scope.json.gender = "male";
    $scope.json.peopleType = "ADULT";

    $scope.saveTourist = function () {
        if ($scope.userForm.name.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '用户信息不正确'
            });
            return;
        }
        if ($scope.userForm.idNumber.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '身份证信息不正确'
            });
            return;
        }
        if ($scope.userForm.telephone.$invalid) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '电话不正确'
            });
            return;
        }
        if ($scope.userForm.$valid) {
            $http.post(GetUrl.saveTourist, {
                    json: JSON.stringify($scope.json)
                }
            ).success(function (data) {
                if (data.success) {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: '保存成功'
                    });
                    history.go(-1);
                    //if (indexOf > 0) {
                    //    location.href = decodeURIComponent(url);
                    //} else {
                    //    location.href = '/';
                    //}
                } else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '确认'
                            }
                        },
                        message: '找不到该用户'
                    });
                }
            }).error(function (data) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '确认'
                        }
                    },
                    message: '系统错误'
                });
            });
        } else {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: "信息不正确"
            });
        }
    }
}]);
//可用列表
var xinghbModule = angular.module("xinghbModule", ['angularLocalStorage']);
xinghbModule.controller('xinghbCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check) {

    //$scope.planId = $stateParams.planId;
    var coupons = storage.get(keys.coupons) == null ? [] : storage.get(keys.coupons);
    var usecoupon = storage.get(keys.usecoupon) == null ? {} : storage.get(keys.usecoupon);
    if (usecoupon.id) {
        $.each(coupons, function (i, item) {
            if (usecoupon.id == item.id) {
                item.check = true;
            } else {
                item.check = false;
            }
        });
    } else {
        $.each(coupons, function (i, item) {
            item.check = false;
        });
    }
    $scope.coupons = coupons;
}]);
//订单确认
var xingorderokModule = angular.module("xingorderokModule", ['angularLocalStorage']);
xingorderokModule.controller('xingorderokCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', 'Wechatpay', 'DateUtils', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check, Wechatpay, DateUtils, $state) {

    $scope.order = storage.get(keys.order) == null ? [] : storage.get(keys.order);
    $scope.productcost = storage.get(keys.productcost) == null ? 0 : storage.get(keys.productcost);
    $scope.youhui = storage.get(keys.youhui) == null ? {price: 0} : storage.get(keys.youhui);
    $scope.selectedtourist = storage.get(keys.selectedtourist) == null ? [] : storage.get(keys.selectedtourist);
    $scope.startDay = storage.get(keys.startDay) == null ? "2" : storage.get(keys.startDay);
    $scope.plan = storage.get(keys.plan) == null ? {} : storage.get(keys.plan);
    $scope.usecoupon = storage.get(keys.usecoupon) == null ? {faceValue: 0} : storage.get(keys.usecoupon);
    $scope.contact = storage.get(keys.contact) == null ? {} : storage.get(keys.contact);
    $scope.invoice = storage.get(keys.invoice) == null ? {} : storage.get(keys.invoice);
    $scope.creditCard = storage.get(keys.creditCard) == null ? {} : storage.get(keys.creditCard);

    $scope.submit = function () {
        $("div.ajaxloading").show();
        var touristsIds = [];
        if ($scope.selectedtourist.length == 0) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '没有选择旅客'
            });
            return;
        }
        $.each($scope.selectedtourist, function (i, item) {
            touristsIds.push(item.touristId);
        });
        var jsonObj = {
            id: 0,
            name: $scope.order.name,
            day: $scope.order.day,
            playDate: $scope.startDay,
            tourists: touristsIds,
            orderType: 'plan',
            contact: $scope.contact
        };
        if ($scope.invoice.name) {
            jsonObj.invoice = $scope.invoice;
        }
        if ($scope.usecoupon.id) {
            jsonObj.couponId = $scope.usecoupon.id;
        }
        var details = [];
        $.each($scope.order.trains, function (i, item) {
            if (item.check) {
                details.push({
                    num: touristsIds.length,
                    id: item.trafficId,
                    seatType: '二等座',
                    type: 'train',
                    startTime: item.startDate,
                    endTime: item.endDate,
                    priceId: item.priceId,
                    price: item.price
                });
            }
        });
        $.each($scope.order.planes, function (i, item) {
            if (item.check) {
                details.push({
                    num: touristsIds.length,
                    id: item.trafficId,
                    seatType: '经济舱',
                    type: 'flight',
                    startTime: item.startDate,
                    endTime: item.endDate,
                    priceId: item.priceId,
                    price: item.price
                });
            }
        });
        $.each($scope.order.hotels, function (i, item) {
            if (item.check) {
                details.push({
                    num: touristsIds.length,
                    id: item.id,
                    seatType: item.priceName,
                    type: 'hotel',
                    startTime: item.startDate,
                    endTime: item.endDate,
                    priceId: item.priceId,
                    price: item.price,
                    creditCard: {
                        status: item.payType == "担保",
                        cardNum: $scope.creditCard.cardNum,
                        creditCardIdType: "IdentityCard",
                        expirationYear: $scope.creditCard.expirationYear,
                        expirationMonth: $scope.creditCard.expirationMonth,
                        holderName: $scope.creditCard.holderName,
                        idNo: $scope.creditCard.idNo,
                        cvv: $scope.creditCard.cvv
                    }
                });
            }
        });
        $.each($scope.order.days, function (i, day) {
            $.each(day.tickets, function (j, item) {
                if (item.check) {
                    details.push({
                        num: touristsIds.length,
                        id: item.ticketId,
                        seatType: item.seatType,
                        type: 'scenic',
                        startTime: item.playDate,
                        priceId: item.priceId,
                        price: item.price,
                        cityId: day.cityId,
                        day: day.day
                    });
                }
            });
        });
        jsonObj.details = details;
        $http.post(GetUrl.orderSave, {
                json: JSON.stringify(jsonObj)
            }
        ).success(function (data) {
            $scope.loaded = true;
            if (Check.loginCheckAndBack(data)) {
                if (data.success) {
                    $state.go('payment', {order: JSON.stringify(data.order)});
                } else {
                }
            }
            $("div.ajaxloading").hide();
        }).error(function (data) {
            $("div.ajaxloading").hide();
            alert(data.errorMsg);
        });
    }


}]);
//支付
var paymentModule = angular.module("paymentModule", ['angularLocalStorage']);
paymentModule.controller('paymentCtrl', ['$rootScope', '$scope', '$http', '$cookieStore', '$location', 'storage', '$stateParams', 'Check', 'Wechatpay', '$state', function ($rootScope, $scope, $http, $cookieStore, $location, storage, $stateParams, Check, Wechatpay, $state) {

    //$scope.planId = $stateParams.planId;
    $scope.order = JSON.parse($stateParams.order);
    $scope.iftaobao = !Wechatpay.isWeiXin() && typeof(plus) != "undefined";

    $("div.ajaxloading").show();
    $http.post(GetUrl.myorderinfo, {
        orderId: $scope.order.id
    }).success(function (data) {
        $("div.ajaxloading").hide();
        if (Check.loginCheck(data)) {
            if (data.success) {
                $scope.order = data.order;
            }
        }
    });

    $scope.orderDetail = function () {
        if ($scope.order.type == "line") {
            location.href = GetUrl.lineOrderDetail + $scope.order.id;
        } else if ($scope.order.type == "ticket") {
            location.href = GetUrl.ticketOrderDetail + $scope.order.id;
        } else {
            $state.go("my-order-info", {id: $scope.order.id});
        }
    };

    $scope.homePage = function () {
        location.href = GetUrl.homePage;
    };

    $scope.pay = function () {
        $("div.ajaxloading").show();
        var val = $(".iradio_square-green.checked").find('input[type="radio"]').val();
//      if (val == "1") {
//          Wechatpay.payOrderWithBack($scope.order.id, Wechatpay.paySuccess);
//      }
//      if (val == "2") {
//          
//      }
//        Wechatpay.alipayNative($scope.order.id,'alipay');
        if (typeof(auths) != "undefined") {
            Wechatpay.alipayNative($scope.order.id, 'wxpay');
        } else {
            Wechatpay.payOrderWithBack($scope.order.id, Wechatpay.paySuccess);
        }
    };
    //$("input[type='radio']").iCheck({
    //    checkboxClass: 'icheckbox_square-green',
    //    radioClass: 'iradio_square-green',
    //    increaseArea: '20%' // optional
    //});
    $("#wx").iCheck('check');
}]);

