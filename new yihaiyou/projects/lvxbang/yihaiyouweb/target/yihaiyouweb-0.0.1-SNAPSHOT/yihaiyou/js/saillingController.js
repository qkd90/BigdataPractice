/**
 * Created by dy on 2016/10/7.
 */
var saillingModule = angular.module("saillingModule", ['infinite-scroll']);

saillingModule.filter("fmtDate", function() {
    return function (input, pattern) {
        var out = pattern || '';
        var yyyy = input.substring(0, 4);
        var mm = input.substring(5, 7);
        var dd = input.substring(8, 10);

        if (out.length > 0) {
            out = out.replace("yyyy", yyyy);
            out = out.replace("mm", mm);
            out = out.replace("dd", dd);
        } else {
            out = input;
        }
        return out;
    }
});

saillingModule.directive("sailScrollTabs", function() {
    return {
        restrict:'A',
        scope: {
            listLength: "="
        },
        link:function(scope, element){
            var tr_distanceTop = element.offset().top-45;
            var tr_distanceBottom = element.offset().top;
            var tr_window = angular.element(window);
            tr_window.scroll(function() {
                var tr_scrlloHeight = tr_window.scrollTop();
                var listHeight = 106*Number(scope.listLength) + 234 - 45;
                //console.log(listHeight + "," + tr_scrlloHeight+ "," + tr_distanceTop);
                if (tr_scrlloHeight > tr_distanceTop) {
                    element.addClass("guid_fixed");
                    var labelLiArray = element.find("div").find("ul").find("li");
                    if (tr_scrlloHeight > listHeight) {
                        labelLiArray.removeClass("active_green");
                        var secondLi = angular.element(labelLiArray[1]);
                        secondLi.addClass("active_green");
                    } else {
                        labelLiArray.removeClass("active_green");
                        var firstLi = angular.element(labelLiArray[0]);
                        firstLi.addClass("active_green");
                    }
                }else{
                    element.removeClass("guid_fixed");
                }
            })
        }
    }
}).directive("initHeight", function() {
    return {
        restrict:'A',
        scope: {
            listLength: "="
        },
        link:function(scope, element){
            var tr_distanceTop = angular.element(element).height();
            var tr_distanceBottom = element.offset().top;
            var tr_window = angular.element(window);
            console.log(tr_distanceTop);
            console.log(tr_window.height());
        }
    }
});

saillingModule.controller("saillingIndexCtrl", function($scope, $http, $state) {


    $scope.sailboatList = [];
    //帆船列表数据获取
    $http.post(yhyUrl.sailboatList,
        {
            'ticketSearchRequest.ticketTypes[0]': 'sailboat',
            'ticketSearchRequest.orderColumn': 'showOrder',
            'ticketSearchRequest.orderType': 'asc',
            page:1,
            pageSize:4
        }
    ).success(
        function(data){
            if (data.success) {
                angular.forEach(data.resultList,
                    function(item) {
                        var productImage = item.productImg;
                        if (productImage.substring(0, productImage.indexOf("/") - 2) != 'http') {
                            item['productImg'] = QINIU_BUCKET_URL + productImage + '?imageView2/1/w/173/h/90/q/75';
                        }
                        $scope.sailboatList.push(item);
                    }
                );
            }
        }
    ).error(function(error) {

        }
    );

    $scope.yachtList = [];
    //游艇列表数据获取
    $http.post(yhyUrl.yachtList,
        {
            'ticketSearchRequest.ticketTypes[0]': 'yacht',
            'ticketSearchRequest.orderColumn': 'showOrder',
            'ticketSearchRequest.orderType': 'asc',
            page:1,
            pageSize:4
        }
    ).success(
        function(data){
            if (data.success) {
                angular.forEach(data.resultList,
                    function(item) {
                        var productImage = item.productImg;
                        if (productImage.substring(0, productImage.indexOf("/") - 2) != 'http') {
                            item['productImg'] = QINIU_BUCKET_URL + productImage + '?imageView2/1/w/173/h/90/q/75';
                        }
                        $scope.yachtList.push(item);
                    }
                );
            }
        }
    ).error(
        function(error) {

        }
    );

    $scope.goSailboatList = function() {
        var paramsObj = {
            label:'sailboat'
        };
        var paramsStr = JSON.stringify(paramsObj);
        $state.go("sailboat/list", {params: encodeURIComponent(paramsStr)});
    };

    $scope.goYachtList = function() {
        var paramsObj = {
            label:'yacht'
        };
        var paramsStr = JSON.stringify(paramsObj);
        $state.go("yacht/list", {params: encodeURIComponent(paramsStr)});
    }

    $scope.goIndex = function() {
        $state.go("index");
    }

    $scope.goSailboatYachtDetail = function(id) {
        $state.go("sailling/detail", {sailId: id});
    }
});

saillingModule.controller('sailboatYachListCtrl', function ($scope, $stateParams, $http, $state, $rootScope, storage) {
    $scope.typelist = [
        {index: 1, name: '全部', labelName: ['yacht', 'sailboat', 'huanguyou']},
        {index: 2, name: '游艇', labelName: ['yacht']},
        {index: 3, name: '帆船', labelName: ['sailboat']},
        {index: 4, name: '鹭岛游', labelName: ['huanguyou']}
    ];

    $scope.selrangeone = $stateParams.index;
    if (isNaN($scope.selrangeone) || $scope.selrangeone < 1 || $scope.selrangeone > 4) {
        $scope.selrangeone = 1;
    }
    angular.forEach($scope.typelist, function (type) {
        if ($scope.selrangeone == type.index) {
            $scope.label= type.labelName;
        }
    });
    $scope.num = 1;
    $scope.chose = -1;
    $scope.sailSelNum = 1;
    $scope.shadowbox = false;
    $scope.listNum = -1;
    $scope.trangle = 1;
    $scope.titleNum = -1;


    $scope.sailSel = '价格区间';
    $scope.sailSort = '推荐排序';
    $scope.down1 = '&#xe642;';
    $scope.down2 = '&#xe642;';
    $scope.down3 = '&#xe642;';

    $scope.tempSupplierName = '登艇地点';
    $scope.supplierName = "";

    $scope.supplierList =[
        {index:0, name: '全部'},
        {index:1, name: '香山游艇码头'},
        {index:2, name: '五缘湾码头'}
    ];

    $scope.priceRange = [];
    $scope.Tprice = [
        {index:0, name: '不限'},
        {index:1, name:'0~100', priceRange:[0, 100]},
        {index:2, name:'100~1000', priceRange:[100, 1000]},
        {index:3, name:'1000~10000', priceRange:[1000, 10000]}
    ];

    $scope.orderColumn = "showOrder";
    $scope.orderType = "asc";
    $scope.Sorts = [
        {index:1, name:'推荐排序', orderColumn: 'showOrder', orderType:'asc'},
        {index:2, name:'评分最高', orderColumn: 'productScore', orderType:'desc'},
        {index:3, name:'预定人数最多', orderColumn: 'orderNum', orderType:'desc'},
        {index:4, name:'价格从低到高', orderColumn: 'disCountPrice', orderType:'asc'},
        {index:5, name:'价格从高到低', orderColumn: 'disCountPrice', orderType:'desc'}
    ];


    $scope.searchList = function() {
        $scope.commodities = [];
        $scope.page = 1;
        $scope.nomore = false;
        $rootScope.loading = false;
        $scope.getSailboatYachtList();
    };

    //获取列表数据
    $scope.commodities = [];
    $scope.page = 1;
    $scope.nomore = false;
    $rootScope.loading = false;
    $scope.getSailboatYachtList = function() {
        if ($rootScope.loading) return;
        if ($scope.nomore) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.sailboatList,
            {
                'ticketSearchRequest.ticketTypes': $scope.label,
                'ticketSearchRequest.supplierName': $scope.supplierName,
                'ticketSearchRequest.priceRange': $scope.priceRange,
                'ticketSearchRequest.orderColumn': $scope.orderColumn,
                'ticketSearchRequest.orderType': $scope.orderType,
                'page':$scope.page,
                'pageSize':10
            }
        ).success(
            function(data) {
                if (data.success) {
                    angular.forEach(data.resultList,
                        function(item) {
                            var productImage = item.productImg;
                            if (productImage.substring(0, productImage.indexOf("/") - 2) != 'http') {
                                item['productImg'] = QINIU_BUCKET_URL + productImage + '?imageView2/1/w/173/h/90/q/75';
                            }
                            item.xing = 'xing_' + item.productScore;
                            if (item.productScore == null) {
                                item.xing = "xing_0";
                            }

                            $scope.commodities.push(item);
                        }
                    );
                    $scope.page++;
                }
                $scope.nomore = data.nomore;
                $rootScope.loading = false;
            }
        ).error(
            function(error) {

            }
        );

    };


    $scope.showselectList = function(num){
        $scope.shadowbox = true;
        $("div[ng-controller]").addClass('bodyStopScroll');
        $scope.listNum = num;
        $scope.titleNum = num;
        if(num == 1){
            $scope.down1 = '&#xe641;';
            $scope.down2 = '&#xe642;';
            $scope.down3 = '&#xe641;';
        }else if(num == 2){
            $scope.down1 = '&#xe642;';
            $scope.down2 = '&#xe641;';
            $scope.down3 = '&#xe642;';
        }else{
            $scope.down1 = '&#xe642;';
            $scope.down2 = '&#xe642;';
            $scope.down3 = '&#xe641;';
        }
    };


    $scope.showstyle =function(num){
        $scope.sailSelNum = num;
    };


    $scope.hideshadow = function(){
        $scope.shadowbox = false;
        $("div[ng-controller]").removeClass('bodyStopScroll');
        $scope.listNum = -1;
        $scope.titleNum = -1;
        $scope.down1 = '&#xe642;';
        $scope.down2 = '&#xe642;';
        $scope.down3 = '&#xe642;';
    };


    $scope.sortIndex = 0;
    $scope.replaceSort = function(sorts, index){
        $scope.sailSort = sorts;
        $scope.sortIndex = index;
        $scope.orderColumn = $scope.Sorts[index - 1].orderColumn;
        $scope.orderType = $scope.Sorts[index - 1].orderType;
        $scope.searchList();
        $scope.hideshadow();
    };

    $scope.tpriceIndex = 0;
    $scope.replacePrice = function(price, index){

        $scope.tpriceIndex = index;
        if (index != 0) {
            $scope.priceRange = $scope.Tprice[index].priceRange;
            $scope.sailSel = price;
        } else {
            $scope.priceRange = [];
            $scope.sailSel = '价格区间';
        }
        $scope.searchList();
        $scope.hideshadow();
    };

    $scope.supplierIndex = 0;
    $scope.replaceSupplier = function(supplierName, index) {

        if (index == 0) {
            $scope.tempSupplierName = "登艇地点";
            $scope.supplierName = "";
        } else {
            $scope.tempSupplierName = supplierName;
            $scope.supplierName = supplierName;
        }
        $scope.supplierIndex = index;
        $scope.searchList();
        $scope.hideshadow();
    };

    $scope.goSailboatYachtIndex = function() {
        $state.go("index");
    };
    $scope.goSailboatYachtDetail = function(id) {
        $state.go("sailling/detail", {sailId: id});
    };

    $scope.selranges = function(index){
        $state.go("sailboat/list", {index: index});
    }
});

saillingModule.controller("sailboatYachtDetailCtrl", function ($scope, $http, $stateParams, $location, $anchorScroll, $state, $rootScope, Check) {

    $scope.star = 'star_0';
    $scope.dianpingstar = 'dianpingstar0';

    var ticketId = $stateParams.sailId;
    // 查询景点概要信息
    $http.post(yhyUrl.ticketInfo, {
        ticketId: ticketId, favorite: 'QUERY_TRUE'
    }).success(function (data) {
        if (data.error) {
            $scope.error = true;
        } else {
            $scope.scenicId = data.scenicId;
            $scope.ticketId = data.ticketId;
            $scope.scenicName = data.scenicName;
            $scope.ticketName = data.ticketName;
            $scope.privilege = data.privilege;
            $scope.enterDesc = data.enterDesc;
            $scope.initImageCount = data.imageCount;
            $scope.telephone = data.telephone;
            if (data.productImg) {
                $scope.productImg = QINIU_BUCKET_URL + "/" + data.productImg;
            }
            $scope.openTime = data.openTime;
            $scope.tips = data.tips;
            $scope.proInfo = data.proInfo;
            $scope.address = data.address;
            $scope.productScore = Math.round(data.productScore / 20);
            $scope.favorite = data.favorite;
            $scope.ticketType = data.ticketType;
            $scope.star = 'star_' + $scope.productScore;
            $scope.dianpingstar = 'dianpingstar' + $scope.productScore;
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

    $scope.doFavorite = function () {
        $http.post(yhyUrl.favorite, {
            favoriteId: $scope.ticketId,
            favoriteType: 'sailboat'
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.success) {
                    $scope.favorite = data.favorite;
                }
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
    };


    $scope.priceTypeIdList = [];
    $scope.ticketTypeList = [];
    var loadTicketFlag = false; // 是否已加载门票列表，只加载一次
    var loadDetailFlag = false; // 是否已加载详情，只加载一次
    $scope.nomore = false;
    $rootScope.loading = false;
    $scope.noTicket = true;
    // 获取门票列表
    $scope.listTicketType = function () {
        if (loadTicketFlag) {
            return;
        }
        loadTicketFlag = true;
        $rootScope.loading = true;
        $http.post(yhyUrl.ticketTypeList, {
            ticketId: ticketId
        }).success(function (data) {
            console.info(data);
            $rootScope.loading = false;
            if (data.error) {
                $scope.error = true;
            } else {
                if (data.ticketTypeList && data.ticketTypeList.length > 0) {
                    $scope.noTicket = false;
                    angular.forEach(data.ticketTypeList, function(item) {
                        $scope.priceTypeIdList.push(item.id);
                        $scope.ticketTypeList.push(item);
                    });

                    //$scope.ticketTypeList = data.ticketTypeList;
                } else {
                    $scope.noTicket = true;
                }
            }
        }).error(function (data) {
            $rootScope.loading = false;
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '确认'
                    }
                },
                message: '系统错误'
            })
        });
    };

    $scope.commentList = [];
    $scope.pageNo = 1;
    $scope.getCommentList = function() {
        if ($rootScope.loading) return;
        if ($scope.nomore) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.productComment, {
            productType: "sailboat",
            targetId: ticketId,
            pageNo: $scope.pageNo,
            pageSize: 5
        }).success(function (data) {
            $rootScope.loading = false;
            if (data.success) {
                angular.forEach(data.commentList, function(item) {
                    if (item.score != null) {
                        item.star = item.score / 20;
                    } else {
                        item.star = 0;
                    }
                    $scope.commentList.push(item);
                });
                $scope.nomore = data.nomore;
                $scope.pageNo++;
            }
        }).error(function () {
            $rootScope.loading = false;
        });
    };



    $scope.ticketDescFlag = false;
    $scope.showTicketPriceDesc = function(ticketPriceId) {
    // 查找对应的票型记录
        for (var i = 0; i < $scope.ticketTypeList.length; i++) {
            if (ticketPriceId == $scope.ticketTypeList[i].id) {
                $scope.showTicketPrice = $scope.ticketTypeList[i];
                $scope.ticketDescFlag = true;
                break;
            }
        }
    }
    // 票型说明 - 关闭
    $scope.closeTicketDesc = function () {
        $scope.ticketDescFlag = false;
        $scope.showTicketPrice = null;
    };

    $scope.goFeature = function() {
        if ($scope.ticketId == null || $scope.ticketId == "undifine" || $scope.ticketId == "") {
            return ;
        }
        $state.go("sailling/feature", {ticketId:$scope.ticketId});
    };

    $scope.goOrder = function (ticketPriceId, event) {
        event.stopPropagation();
        if (ticketPriceId == null || ticketPriceId == "undifine" || ticketPriceId == "") {
            return ;
        }
        $state.go("sailling/date", {ticketPriceId:ticketPriceId});
    };


    $scope.index = 1;
    $scope.tabIndex = 1;

    $scope.changeTab = function(tabIndex, $event) {
        $scope.tabIndex = tabIndex;
        //var scrollTargetObj = $location.hash("ticketTab_"+ tabIndex +"");
        //$scope.index += 1;

        //$anchorScroll.yOffset = 90;
        //$anchorScroll();
        //if (scrollTargetObj.length == 1) {
        //    var top = scrollTargetObj.offset().top + 50;
        //    if (top > 0) {
        //        $('html,body').animate({scrollTop: top}, 1000);
        //    }

        //}
    }
    $scope.goBackIndex = function() {
        window.history.go(-$scope.index);
    }


    $scope.sel_time_show = false;
    $scope.openpichuangkou  = function() {
        $scope.sel_time_show = true;
        var tempData = {
            proId:ticketId,
            typePriceId: null
        }
        $state.go("sailling_album", {params: JSON.stringify(tempData)});
    }
    //$scope.jjj =2;
    //$scope.opendiv =function(num){
    //    $scope.jjj =num;
    //}


    //点击显示全部门票及收起
    $scope.jjj =4;
    $scope.bo=true;
    $scope.opendiv =function(num,$event){
        if($scope.bo)
        {
            $scope.jjj =num;
            $scope.roateIndex=0;
            $scope.bo=false;
        }else{
            $scope.jjj =4;
            $scope.roateIndex=1;
            $scope.bo=true;}

    }




});

saillingModule.controller("sailboatYachtTicketDetailCtrl", function ($scope, $http, $stateParams, $state) {
    $scope.priceId = $stateParams.priceId;
    $scope.proId = null;
    if ($scope.priceId == null || $scope.priceId <= 0) {
        history.go(-1);
        return;
    }
    $scope.ticketPrice = {};
    $http.post(yhyUrl.ticketPriceDetail, {
        priceId: $scope.priceId
    }).success(function (data) {
        console.info(data);
        if (data.success) {
            $scope.ticketPrice = data.ticketPrice;
            $scope.proId = $scope.ticketPrice.ticketId;
            if (!data.ticketPrice.commentCount) {
                $scope.ticketPrice.commentCount = "暂无";
            }
            if (data.ticketPrice.score/20 <= 5 && data.ticketPrice.score/20 > 4) {
                $scope.ticketPrice.score = "5分";
                $scope.ticketPrice['star'] = 'xingji-star_5';
            } else if (data.ticketPrice.score/20 <= 4 && data.ticketPrice.score/20 > 3) {
                $scope.ticketPrice.score = "4分";
                $scope.ticketPrice['star'] = 'xingji-star_4';
            } else if (data.ticketPrice.score/20 <= 3 && data.ticketPrice.score/20 > 2) {
                $scope.ticketPrice.score = "3分";
                $scope.ticketPrice['star'] = 'xingji-star_3';
            } else if (data.ticketPrice.score/20 <= 2 && data.ticketPrice.score/20 > 1) {
                $scope.ticketPrice.score = "2分";
                $scope.ticketPrice['star'] = 'xingji-star_2';
            } else if (data.ticketPrice.score/20 <= 1 && data.ticketPrice.score/20 > 0) {
                $scope.ticketPrice.score = "1分";
                $scope.ticketPrice['star'] = 'xingji-star_1';
            } else {
                $scope.ticketPrice.score = "0分";
                $scope.ticketPrice['star'] = 'xingji-star_0';
            }

        }
    });

    $scope.openpichuangkou  = function() {
        var tempData = {
            proId:$scope.proId,
            typePriceId: $scope.priceId
        }
        $state.go("sailling_album", {params: JSON.stringify(tempData)});
    }

    $scope.goSailComment = function() {
        $state.go("sailling/saillingCommend", {ticketPriceId: $scope.priceId});
    }
});


saillingModule.controller("sailboatYachtFeatureCtrl", function ($scope, $http, $stateParams, $state, $rootScope) {
    var scenicId = $stateParams.ticketId;

    $http.post(yhyUrl.ticketProductDesc, {
        ticketId: scenicId
    }).success(function (data) {
        if (data.success) {
            $scope.proInfo = data.ticketExplain.proInfo;
        }
    }).error(function (data) {
        $rootScope.loading = false;
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message: '系统错误'
        })
    });
    $scope.goBack = function() {
        history.go(-1);
    }
});

saillingModule.controller("sailboatDateCtrl", function($scope, $http, $state, $stateParams, storage) {
    $scope.ticketPriceId = $stateParams.ticketPriceId;
    $("div.ajaxloading").show();
    $http.post(yhyUrl.ticketDatePrice, {
        ticketPriceId: $scope.ticketPriceId
    }).success(function (data) {
        if (data.success) {
            var calendarData = [];
            angular.forEach(data.ticketDatepriceList, function (datePrice) {
                calendarData.push({
                    id: datePrice.huiDate.substring(0, 10),
                    ticketDatePriceId: datePrice.id,
                    price: (datePrice.priPrice),
                    date: datePrice.huiDate.substring(0, 10),
                    title: '¥' + (datePrice.priPrice)
                });
            });
            $scope.calendarData = calendarData;
        }
        $("div.ajaxloading").hide();
    });

    $scope.eventClick = function (calEvent) {
        $scope.ticketDatePriceId = calEvent.ticketDatePriceId;
        $scope.price = calEvent.price;
        $scope.date = calEvent.date;
    };

    $scope.dayClick = function (date) {
        var calEvent = $("#calendar").fullCalendar('clientEvents', date)[0];
        if (calEvent != null) {
            $scope.ticketDatePriceId = calEvent.ticketDatePriceId;
            $scope.price = calEvent.price;
            $scope.date = calEvent.date;
        } else {
            $(".fc-highlight-skeleton").remove();
            $("#calendar").fullCalendar('select', $.fullCalendar.moment($scope.date));
        }
    };

    $scope.goNext = function () {
        if ($scope.ticketDatePriceId != null && $scope.ticketDatePriceId > 0 && $scope.date != null && $scope.date.length > 0) {
            //var orderInfo = {
            //    ticketDatePriceId: $scope.ticketDatePriceId,
            //    date: $scope.date
            //}
            //storage.set("orderSailInfo", orderInfo);
            storage.set(yhyKey.ticketDatePriceId, $scope.ticketDatePriceId);
            storage.remove(yhyKey.selectedTourist);
            $state.go("sailling/order");
        } else {
            bootbox.alert("请选择日期");
        }
    }
});
saillingModule.controller("sailling_album", function($scope, $http, $stateParams, ImageSwiper) {


    $scope.myActiveSlide = 0;
    $scope.initImageCount = 0;
    $scope.initIndex = 0;
    var paramsStr = $stateParams.params;
    var paramsObj = JSON.parse(paramsStr);

    var ticketId = paramsObj.proId;
    var ticketPriceId = paramsObj.typePriceId;
    $scope.imageList = [];
    $http.post(yhyUrl.getImageList, {
        proId:ticketId,
        typePriceId: ticketPriceId
    }).success(function (data) {
        if (data.success) {
            $scope.imageList = data.proImageList;
            $scope.initImageCount = data.proImageList.length;
            $scope.initIndex = 1;
        }
    });

    // 广告数据加载状态
    $scope.finish = {
        over: false
    };
    $scope.$watch("finish.over", function () {
        // 幻灯广告
        ImageSwiper.init({
            container: '#image-top-slider-container',
            effect: 'fade',
            //autoplay: 3000,
            pagination: '#image-top-slide-page',
            onSlideChangeEnd: function(swiper) {
                if (swiper.activeIndex > 1) {
                    $scope.slideHasChanged(swiper.activeIndex);
                    //$scope.initIndex = swiper.activeIndex - 1;
                }
            }
        });
    });

    $scope.slideHasChanged = function(index) {
        $scope.initIndex = index - 1;
    }
    $scope.onAlbum = function (event) {
        event.stopPropagation();
    };
    $scope.outAlbum = function(){
        history.go(-1);
    }
});

saillingModule.controller('sailling_commend',function($scope, $http, $stateParams, $rootScope){

    $scope.ticketPriceId = $stateParams.ticketPriceId;

    $scope.fiveStar = 0;
    $scope.fourStar = 0;
    $scope.threeStar = 0;
    $scope.twoStar = 0;
    $scope.oneStar = 0;
    $scope.zeoStar = 0;
    $scope.totalScore = 0;
    $scope.avgScore = 0;
    $scope.resultScore = 0;
    $scope.totalCount = 0;
    $scope.initStar = [];
    $scope.pstar = 'pstar0';
    $http.post(yhyUrl.sailyachtCommentInfo, {
        'comment.priceId': $scope.ticketPriceId,
        'comment.type': 'sailboat'
    }).success(function (data) {
        if (data.success) {
            $scope.totalCount = data.totalCount;
            $scope.fiveStar = data.fiveStar;
            var five = {starnum:'5', width: $scope.fiveStar/$scope.totalCount*100+'%', star:$scope.fiveStar};
            $scope.initStar.push(five);
            $scope.fourStar = data.fourStar;
            var four = {starnum:'4', width: $scope.fourStar/$scope.totalCount*100+'%', star:$scope.fourStar};
            $scope.initStar.push(four);
            $scope.threeStar = data.threeStar;
            var three = {starnum:'3', width: $scope.threeStar/$scope.totalCount*100+'%', star:$scope.threeStar};
            $scope.initStar.push(three);
            $scope.twoStar = data.twoStar;
            var two = {starnum:'2', width: $scope.twoStar/$scope.totalCount*100+'%', star:$scope.twoStar};
            $scope.initStar.push(two);
            $scope.oneStar = data.oneStar;
            var one = {starnum:'1', width: $scope.oneStar/$scope.totalCount*100+'%', star:$scope.oneStar};
            $scope.initStar.push(one);
            $scope.zeoStar = data.zeoStar;
            $scope.avgScore = data.avgScore;
            $scope.resultScore = data.resultScore;

            if ($scope.resultScore <= 5 && $scope.resultScore > 4) {
                $scope.pstar = 'pstar5';
            } else if ($scope.resultScore <= 4 && $scope.resultScore > 3) {
                $scope.pstar = 'pstar4';
            } else if ($scope.resultScore <= 3 && $scope.resultScore > 2) {
                $scope.pstar = 'pstar3';
            } else if ($scope.resultScore <= 2 && $scope.resultScore > 1) {
                $scope.pstar = 'pstar2';
            } else if ($scope.resultScore <= 1 && $scope.resultScore > 0) {
                $scope.pstar = 'pstar1';
            } else {
                $scope.pstar = 'pstar0';
            }

        }
    });

    $scope.commentList = [];
    $scope.initPage = 1;
    $scope.initPageSize = 10;
    $scope.nomore = false;
    $scope.totalCount = 0;
    $scope.ticketTypeName = '';
    $scope.ticketName = '';
    $rootScope.loading = false;

    $scope.getCommentList = function () {
        if ($rootScope.loading) return;
        if ($scope.nomore) return;
        $rootScope.loading = true;
        $http.post(yhyUrl.sailyachtCommentList, {
            'comment.priceId': $scope.ticketPriceId,
            'comment.type': 'sailboat',
            page:$scope.initPage,
            pageSize:$scope.initPageSize
        }).success(function (data) {
            if (data.success) {

                angular.forEach(data.commentList, function(item) {
                    var sorceTotal = 0;
                    angular.forEach(item.commentScores, function(scoreItem) {
                        sorceTotal += scoreItem.score;
                    });
                    if (sorceTotal > 0) {
                        var avgScore = sorceTotal/item.commentScores.length;
                        if (avgScore/20 <= 5 && avgScore/20 > 4) {
                            item['star'] = 'star5';
                        } else if (avgScore/20 <= 4 && avgScore/20 > 3) {
                            item['star'] = 'star4';
                        } else if (avgScore/20 <= 3 && avgScore/20 > 2) {
                            item['star'] = 'star3';
                        } else if (avgScore/20 <= 2 && avgScore/20 > 1) {
                            item['star'] = 'star2';
                        } else if (avgScore/20 <= 1 && avgScore/20 > 0) {
                            item['star'] = 'star1';
                        } else if (avgScore/20 <= 0) {
                            item['star'] = 'star0';
                        } else {
                            item['star'] = 'star0';
                        }
                    } else {
                        item['star'] = 'star0';
                    }
                    $scope.commentList.push(item);
                });
                //$scope.commentList = data.commentList;
                $scope.ticketTypeName = data.ticketTypeName;
                $scope.ticketName = data.ticketName;
                $scope.totalCount = data.totalCount;
                $scope.initPage++;
            }
            $scope.nomore = data.nomore;
            $rootScope.loading = false;
        });

    };

    /*$scope.btnum = function(num){
        $scope.linum = num;
    };*/
})