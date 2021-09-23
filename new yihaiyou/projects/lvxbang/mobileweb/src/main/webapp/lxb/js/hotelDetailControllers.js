var hotelDetailModule = angular.module('hotelDetailModule', ['infinite-scroll']);
// 筛选条件滚动固定
hotelDetailModule.directive("scrollFixed", function() {
    return {
        restrict : "A",
        link: function (scope, element, attrs) {
            $(window).scroll(function() {
                var scrollY = this.scrollY;
                //console.log(scrollY);
                if (scrollY > 200 && !element.hasClass('fixed')) {
                    element.addClass('fixed');
                } else if (scrollY <= 200 && element.hasClass('fixed')) {
                    element.removeClass('fixed');
                }
            });
        }
    };
});
// 房型筛选条件按钮
hotelDetailModule.directive("roomFilterBtn", function() {
    return {
        restrict : "A",
        link: function (scope, element, attrs) {
            element.click(function (event) {
                scope.$apply(function () {
                    scope[attrs.id] = !scope[attrs.id];
                    if (scope.bntBedType || scope.bntBreakfast || scope.bntPayWay || scope.bntPrice) {
                        scope.hasRoomFilter = true;       // 是否有附加条件
                    } else {
                        scope.hasRoomFilter = false;
                    }
                });
            });
        }
    };
});
// 房型筛选条件切换
hotelDetailModule.directive("roomFilterTab", function() {
    return {
        restrict : "A",
        link: function (scope, element, attrs) {
            element.click(function (event) {
                scope.$apply(function () {
                    scope.tabname = attrs.tabname;
                });
            });
        }
    };
});
// 房型筛选条件选择
hotelDetailModule.directive("roomFilterSelect", function() {
    return {
        restrict : "A",
        link: function (scope, element, attrs) {
            element.click(function (event) {
                // 检查是单选还是多选项
                var multiFlag = element.parent().parent().hasClass('multi');
                var tabname = element.parent().parent().attr('id');
                var selCode = attrs.code;
                // 数据处理
                scope.$apply(function () {
                    scope.handleSeled(tabname, selCode, multiFlag, element);
                });
                // 样式处理
                scope.handleSeledCls(tabname, selCode, multiFlag, element);
            });
        }
    };
});
// 房型筛选条件选择 - 日期范围
hotelDetailModule.directive("roomDateSelect", function() {
    return {
        restrict : "A",
        link: function (thisScope, element, attrs, ctrl) {
            element.click(function (event) {
                var disabledFlag = element.hasClass('disabled');
                if (disabledFlag) {
                    return;
                }
                var scope = thisScope.$parent.$parent;  // 父级scope
                var date = attrs.date;
                // 数据处理
                scope.$apply(function () {
                    if (scope.tipInDate && !scope.tipOutDate) {   // 如果入住日期不为空且离店日期为空
                        if (scope.tipInDate < date) {   // 选中的日期在入住日期之后，选中日期设置为离店日期
                            scope.tipOutDate = date;
                            // 关闭页面
                            scope.back();
                        } else {    // 否则入住日期更新为新的选中日期
                            // 清除选中样式
                            scope.tipInDate = date;
                            scope.tipOutDate = null;
                        }
                    } else {
                        // 清除选中样式
                        scope.tipInDate = date;
                        scope.tipOutDate = null;
                    }
                });
            });
        }
    };
});
// 酒店详情
hotelDetailModule.controller('hotelDetailCtrl', function ($scope, $http, $location, $stateParams, $state, storage, Check) {
    $scope.showAlbumFlag = false;   // 是否显示相册
    $scope.hasRoomFilter = false;       // 是否有附加条件
    //$scope.bntBookChoose = false;       // 可订
    //$scope.bntConfirmChoose = false;   // 立即确认
    //$scope.bntHourRoom = false;         // 钟点房
    //$scope.bntCancelChoose = false;     // 免费取消
    //$scope.bntAddBed = false;           // 可加床
    $scope.dateCount = 2;                   // 入住晚数
    var curDate = new Date();
    var inDate = new Date(curDate.getTime() + 1000*60*60*24);   // 当前天+1
    var outDate = new Date(inDate.getTime() + 1000*60*60*24*$scope.dateCount);

    // 日期格式化
    $scope.formatDate2String = function(date) {
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    };
    $scope.tipInDate = $scope.formatDate2String(inDate);            // 入住日期
    $scope.tipOutDate = $scope.formatDate2String(outDate);           // 离店日期
    $scope.tipInDateDispaly = '';            // 入住日期显示
    $scope.tipOutDateDispaly = '';           // 离店日期显示

    // 查询酒店概要信息
    $scope.score = 0;
    var hotelId = $stateParams.hotelId;
    $http.post(LXB_URL.hotelInfo, {
        id : hotelId
    }).success(function (data) {
        if (!data.success) {
            $scope.error = true;
        } else {
            $scope.hotelId = data.hotelId;
            $scope.hotelName = data.hotelName;
            $scope.score = data.score;
            $scope.star = data.star;
            $scope.starDesc = data.starDesc;
            $scope.address = data.address;
            $scope.hotelCover = data.hotelCover;
            $scope.images = data.images;
            $scope.imageCount = data.images.length;
            $scope.commentCount = data.commentCount;
            $scope.shortDesc = data.shortDesc;
            $scope.description = data.description;
            $scope.favorite = data.favorite;

            // 初始化图片预览
            setTimeout(function () {
                $('#hotelImgView').owlCarousel({
                    singleItem: true,
                    //autoHeight: true,
                    pagination: false
                });
            }, 1000);
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message:'系统错误'
        });
    });

    // 初始化选中条件
    $scope.initFilter = function() {
        var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
        if(!roomFilter) {
            roomFilter = {};
        };
        $scope.bntBedType = roomFilter.bntBedType;       // 床型
        $scope.bntBreakfast = roomFilter.bntBreakfast;     // 早餐
        //$scope.bntNetwork = roomFilter.bntNetwork;       // 宽带
        $scope.bntPayWay = roomFilter.bntPayWay;        // 支付方式
        $scope.bntPrice = roomFilter.bntPrice;         // 价格范围
        //$scope.bntBookChoose = roomFilter.bntBookChoose;       // 可订
        //$scope.bntConfirmChoose = roomFilter.bntConfirmChoose;   // 立即确认
        //$scope.bntHourRoom = roomFilter.bntHourRoom;         // 钟点房
        //$scope.bntCancelChoose = roomFilter.bntCancelChoose;     // 免费取消
        //$scope.bntAddBed = roomFilter.bntAddBed;           // 可加床
        if (roomFilter.bntBedType || roomFilter.bntBreakfast || roomFilter.bntPayWay || roomFilter.bntPrice) {
            $scope.hasRoomFilter = true;       // 是否有附加条件
        }
        // 入住日期
        if (roomFilter.tipInDate && roomFilter.tipInDate > $scope.tipInDate) {
            $scope.tipInDate = roomFilter.tipInDate;
        } else {
            roomFilter.tipInDate = $scope.tipInDate;
        }
        // 离店日期
        if (roomFilter.tipOutDate && roomFilter.tipOutDate > $scope.tipInDate) {
            $scope.tipOutDate = roomFilter.tipOutDate;
        } else {
            roomFilter.tipOutDate = $scope.tipOutDate;
        }
        $scope.tipInDateDispaly = $scope.tipInDate.substr(5);            // 入住日期显示
        $scope.tipOutDateDispaly = $scope.tipOutDate.substr(5);           // 离店日期显示
        var inDateArray = $scope.tipInDate.split('-');
        var outDateArray = $scope.tipOutDate.split('-');
        var inDate = new Date(parseInt(inDateArray[0]), parseInt(inDateArray[1])-1, parseInt(inDateArray[2]));
        var outDate = new Date(parseInt(outDateArray[0]), parseInt(outDateArray[1])-1, parseInt(outDateArray[2]));
        $scope.dateCount = (outDate.getTime() - inDate.getTime())/(24 * 60 * 60 * 1000);                  // 入住晚数
        storage.set(LXB_KEY.hotelDetailRoomFilter, roomFilter);
    };
    $scope.initFilter();
    // 获取酒店房型列表
    $scope.nomore = false;
    var loadFlag = false; // 是否已加载，只加载一次
    $scope.listHotelPrice = function () {
        if (loadFlag) {
            return;
        }
        loadFlag = true;
        // 处理参数
        var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
        if(!roomFilter) {
            roomFilter = {};
        };
        var tipInDate = roomFilter.tipInDate;// 入住日期
        var tipOutDate = roomFilter.tipOutDate;// 离店日期
        var bntBedType = roomFilter.bntBedType;       // 床型
        if (!bntBedType) {
            bntBedType = null;
        }
        var bntBreakfast = roomFilter.bntBreakfast;     // 早餐
        if (!bntBreakfast) {
            bntBreakfast = null;
        }
        var bntPayWay = roomFilter.bntPayWay;        // 支付方式
        if (!bntPayWay) {
            bntPayWay = null;
        }
        var bntPrice = roomFilter.bntPrice;         // 价格范围
        var priceLow = null;
        var priceHigh = null;
        if (!bntPrice) {
            bntPrice = null;
        } else {
            priceLow = roomFilter.priceLow;
            priceHigh = roomFilter.priceHigh;
        }

        $scope.loading = true;
        $http.post(LXB_URL.listHotelPrice, {
            hotelId : hotelId,
            tipInDate : tipInDate,
            tipOutDate : tipOutDate,
            bntBedType : bntBedType,
            bntBreakfast : bntBreakfast,
            bntPayWay : bntPayWay,
            bntPrice : bntPrice,
            priceLow : priceLow,
            priceHigh : priceHigh
        }).success(function (data) {
            $scope.loading = false;
            if (!data.success) {
                $scope.error = true;
            } else {
                if (data.hotelPriceList && data.hotelPriceList.length > 0) {
                    $scope.noHotelPrice = false;
                    $scope.hotelPriceList = data.hotelPriceList;
                } else {
                    $scope.noHotelPrice = true;
                }
                $scope.lineList = data.lineList;
            }
        }).error(function (data) {
            $scope.loading = false;
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
    // 首页
    $scope.home = function() {
        $state.go("index", {}, {reload: true});
    };
    // 查询
    $scope.search = function() {
        $state.go("search", {}, {reload: true});
    };
    // 个人中心
    $scope.personal = function() {
        //$state.go("myteam", {}, {reload: true});
        location.href = GetUrl.personal;
    };
    // 返回
    $scope.back = function() {
        history.go(-1);
    };
    //收藏
    $scope.doFavorite = function () {
        $http.post(LXB_URL.favorite, {
            favoriteId: hotelId,
            favoriteType: 'hotel'
        }).success(function (data) {
            if (Check.loginCheck(data)) {
                if (data.error) {
                    $scope.error = true;
                    console.log(data.errorMsg);
                } else {
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
    // 日期筛选米面板
    $scope.filterDatePanel = function() {
        $state.go("hotelDateFilter", {}, {reload: true});
    };
    // 条件筛选面板
    $scope.filterPanel = function() {
        // 设置条件
        //var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
        //roomFilter.bntBookChoose = $scope.bntBookChoose;      // 可订
        //roomFilter.bntConfirmChoose = $scope.bntConfirmChoose;   // 立即确认
        //roomFilter.bntHourRoom = $scope.bntHourRoom;         // 钟点房
        //roomFilter.bntCancelChoose = $scope.bntCancelChoose;     // 免费取消
        //roomFilter.bntAddBed = $scope.bntAddBed;           // 可加床
        //storage.set(LXB_KEY.hotelDetailRoomFilter, roomFilter);
        $state.go("hotelDetailRoomFilter", {}, {reload: true});
    };
    // 打开相册
    $scope.openAlbum = function() {
        $scope.showAlbumFlag = true;
    };
    // 关闭相册
    $scope.closeAlbum = function() {
        $scope.showAlbumFlag = false;
    };
    // 图片预览
    $scope.viewAlbumImg = function(index) {
        var owl = $('#hotelImgView').data('owlCarousel');
        owl.jumpTo(index);
        $('.swiper-container').css("display","block");
    };
    // 取消图片预览
    $scope.cancelAlbumImg = function() {
        $('.swiper-container').css("display","none");
    };
    // 酒店评论页面
    $scope.goHotelComments = function() {
        $state.go("hotelComments", {hotelId : hotelId}, {reload: true});
    };
    // 下单
    $scope.doBook = function(hotelPriceId) {
        var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
        var tipInDate = roomFilter.tipInDate;// 入住日期
        var tipOutDate = roomFilter.tipOutDate;// 离店日期
        var params = JSON.stringify({hotelPriceId:hotelPriceId,startDate:tipInDate,endDate:tipOutDate});
        $state.go("orderHotel", {params:params}, {reload: true});
    };
});
// 酒店详情 - 筛选条件
hotelDetailModule.controller('hotelDetailFilterCtrl', function ($scope, $http, $location, $state, storage) {
    // 默认条件状态
    $scope.tabname = 'bntBedType';  // 选中的tab

    // 返回
    $scope.back = function() {
        history.go(-1);
    };
    // 初始化选中条件
    $scope.initFilter = function() {
        var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
        if(!roomFilter) {
            roomFilter = {};
        };
        $scope.bntBedType = roomFilter.bntBedType;       // 床型
        $scope.bntBreakfast = roomFilter.bntBreakfast;     // 早餐
        //$scope.bntNetwork = roomFilter.bntNetwork;       // 宽带
        $scope.bntPayWay = roomFilter.bntPayWay;        // 支付方式
        $scope.bntPrice = roomFilter.bntPrice;         // 价格范围
        $scope.priceLow = roomFilter.priceLow;
        $scope.priceHigh = roomFilter.priceHigh;
        //$scope.bntBookChoose = roomFilter.bntBookChoose;       // 可订
        //$scope.bntConfirmChoose = roomFilter.bntConfirmChoose;   // 立即确认
        //$scope.bntHourRoom = roomFilter.bntHourRoom;         // 钟点房
        //$scope.bntCancelChoose = roomFilter.bntCancelChoose;     // 免费取消
        //$scope.bntAddBed = roomFilter.bntAddBed;           // 可加床
        // 初始化样式
        $scope.initFilterCls('bntBedType', $scope.bntBedType);
        $scope.initFilterCls('bntBreakfast', $scope.bntBreakfast);
        //$scope.initFilterCls('bntNetwork', $scope.bntNetwork);
        $scope.initFilterCls('bntPayWay', $scope.bntPayWay);
        $scope.initFilterCls('bntPrice', $scope.bntPrice);
    };
    // 初始化样式
    $scope.initFilterCls = function(tabname, selCodes) {
        var el = $('#' + tabname);
        var multiFlag = el.hasClass('multi');
        var liChildren = el.children().children();
        liChildren.removeClass('active');
        if (multiFlag) {    // 多选
            liChildren.children('label').removeClass('icon-checkbox');
            liChildren.children('label').addClass('icon-checkbox_bg');
        } else {
            liChildren.children('label').addClass('hide');
        }
        if (!selCodes) {  // 选中“不限”条件
            liChildren.first().addClass('active');
            if (multiFlag) {    // 多选
                liChildren.first().children('label').addClass('icon-checkbox');
                liChildren.first().children('label').removeClass('icon-checkbox_bg');
            } else {
                liChildren.first().children('label').removeClass('hide');
            }
        } else {
            for (var i = 0; i < liChildren.length; i++) {
                var code = $(liChildren[i]).attr('data-code');
                if (multiFlag) {    // 多选
                    for (var j = 0; j < selCodes.length; j++) {
                        if (selCodes[j] == code) {
                            $(liChildren[i]).addClass('active');
                            $(liChildren[i]).children('label').addClass('icon-checkbox');
                            $(liChildren[i]).children('label').removeClass('icon-checkbox_bg');
                        }
                    }
                } else {
                    if (selCodes == code) {
                        $(liChildren[i]).addClass('active');
                        $(liChildren[i]).children('label').removeClass('hide');
                        break;
                    }
                }
            }
        }
    };
    $scope.initFilter();
    // 恢复默认
    $scope.resetFilter = function() {
        $scope.bntBedType = null;       // 床型
        $scope.bntBreakfast = null;     // 早餐
        //$scope.bntNetwork = null;       // 宽带
        $scope.bntPayWay = null;        // 支付方式
        $scope.bntPrice = null;         // 价格范围
        //$scope.bntBookChoose = false;       // 可订
        //$scope.bntConfirmChoose = false;   // 立即确认
        //$scope.bntHourRoom = false;         // 钟点房
        //$scope.bntCancelChoose = false;     // 免费取消
        //$scope.bntAddBed = false;           // 可加床
        $scope.setFilterToStorage();
        // 初始化样式
        $scope.initFilterCls('bntBedType', $scope.bntBedType);
        $scope.initFilterCls('bntBreakfast', $scope.bntBreakfast);
        //$scope.initFilterCls('bntNetwork', $scope.bntNetwork);
        $scope.initFilterCls('bntPayWay', $scope.bntPayWay);
        $scope.initFilterCls('bntPrice', $scope.bntPrice);
    };
    // 确认
    $scope.cfmFilter = function() {
        $scope.setFilterToStorage();
        $scope.back();
    };
    // 记录查询条件
    $scope.setFilterToStorage = function() {
        var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
        if(!roomFilter) {
            roomFilter = {};
        };
        roomFilter.bntBedType = $scope.bntBedType;       // 床型
        roomFilter.bntBreakfast = $scope.bntBreakfast;     // 早餐
        //roomFilter.bntNetwork : $scope.bntNetwork,       // 宽带
        roomFilter.bntPayWay = $scope.bntPayWay;        // 支付方式
        roomFilter.bntPrice = $scope.bntPrice;         // 价格范围
        roomFilter.priceLow = $scope.priceLow;
        roomFilter.priceHigh = $scope.priceHigh;
        //roomFilter.bntBookChoose : $scope.bntBookChoose,      // 可订
        //roomFilter.bntConfirmChoose : $scope.bntConfirmChoose,   // 立即确认
        //roomFilter.bntHourRoom : $scope.bntHourRoom,         // 钟点房
        //roomFilter.bntCancelChoose : $scope.bntCancelChoose,     // 免费取消
            //roomFilter.bntAddBed : $scope.bntAddBed           // 可加床
        storage.set(LXB_KEY.hotelDetailRoomFilter, roomFilter);
    };

    // 处理选中值，参数：tabname-选中的标签名称, selCode-选中的代码值, multiFlag-是否多选
    $scope.handleSeled = function(tabname, selCode, multiFlag, element) {
        if (!selCode) {  // “不限”条件
            $scope[tabname] = null;
            return;
        }
        if (multiFlag) {    // 多选
            // 判断是否存在，如果已经存在则删除，不存在则添加
            if ($scope[tabname]) {
                var codeArray = $scope[tabname];
                var newCodeArray = [];
                for (var i = 0; i < codeArray.length; i++) {
                    if (selCode != codeArray[i]) {
                        newCodeArray.push(codeArray[i]);
                    }
                }
                if (codeArray.length == newCodeArray.length) {   // 不存在则添加
                    newCodeArray.push(selCode);
                }
                if (newCodeArray.length === 0) {
                    newCodeArray = null;
                }
                $scope[tabname] = newCodeArray;
            } else {
                $scope[tabname] = [];
                $scope[tabname].push(selCode);
            }
        } else {
            $scope[tabname] = selCode;
            // 如果是价格存储起始价格段
            if ('bntPrice' == tabname) {
                $scope.priceLow = element.attr('data-low');
                $scope.priceHigh = element.attr('data-high');
            }
        }
    };
    // 处理选中样式，参数：tabname-选中的标签名称, selCode-选中的代码值, multiFlag-是否多选
    $scope.handleSeledCls = function(tabname, selCode, multiFlag, element) {
        if (!selCode) {  // 选中“不限”条件
            element.siblings().removeClass('active');
            if (multiFlag) {    // 多选
                element.siblings().children('label').removeClass('icon-checkbox');
                element.siblings().children('label').addClass('icon-checkbox_bg');
            } else {
                element.siblings().children('label').addClass('hide');
            }
            element.addClass('active');
            if (multiFlag) {    // 多选
                element.children('label').addClass('icon-checkbox');
                element.children('label').removeClass('icon-checkbox_bg');
            } else {
                element.children('label').removeClass('hide');
            }
            return;
        } else {    // 清除“不限”条件选中
            element.siblings().first().removeClass('active');

            if (multiFlag) {    // 多选
                element.siblings().first().children('label').removeClass('icon-checkbox');
                element.siblings().first().children('label').addClass('icon-checkbox_bg');
            } else {
                element.siblings().first().children('label').addClass('hide');
            }
        }
        if (multiFlag) {    // 多选
            // 判断选中是否只有一只，只有一个取消选中时，默认选中“不限”
            var selElCls = element.hasClass('active');
            var siblingsSelElCls = element.siblings().hasClass('active');
            if (!siblingsSelElCls) {
                if (selElCls) { // 取消最后一个选中，并默认选中“不限”条件
                    element.siblings().first().addClass('active');
                    element.siblings().first().children('label').addClass('icon-checkbox');
                    element.siblings().first().children('label').removeClass('icon-checkbox_bg');
                }
            }
            element.toggleClass('active');
            element.children('label').toggleClass('icon-checkbox');
            element.children('label').toggleClass('icon-checkbox_bg');
        } else {
            element.siblings().removeClass('active');
            //element.siblings().children('label').removeClass('icon-checkbox');
            element.siblings().children('label').addClass('hide');
            element.addClass('active');
            //element.children('label').addClass('icon-checkbox');
            element.children('label').removeClass('hide');
        }
    };
});

// 酒店详情 - 日期筛选条件
hotelDetailModule.controller('hotelDateFilterCtrl', function ($scope, $http, $location, $state, storage) {
    // 初始页面日期
    $scope.months = null;
    var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
    if(!roomFilter) {
        roomFilter = {};
    };
    $scope.tipInDate = roomFilter.tipInDate;    // 入住日期
    $scope.tipOutDate = roomFilter.tipOutDate;  // 离店日期
    $http.post(LXB_URL.hotelDateCalendar, {
    }).success(function (data) {
        if (data.error) {
            $scope.error = true;
        } else {
            $scope.months = data.months;
            $scope.today = data.today;
        }
    }).error(function (data) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确认'
                }
            },
            message:'系统错误'
        });
    });

    // 返回
    $scope.back = function() {
        var roomFilter = storage.get(LXB_KEY.hotelDetailRoomFilter);
        if(!roomFilter) {
            roomFilter = {};
        };
        roomFilter.tipInDate = $scope.tipInDate;    // 入住日期
        roomFilter.tipOutDate = $scope.tipOutDate;  // 离店日期
        storage.set(LXB_KEY.hotelDetailRoomFilter, roomFilter);
        history.go(-1);
    };
});

// 酒店详情 - 评论列表
hotelDetailModule.controller('hotelCommentsCtrl', function ($scope, $http, $location, $state, $stateParams) {
    $scope.pageNo = 1;
    $scope.commentList = [];
    $scope.loading = false;
    $scope.nomore = false;

    var hotelId = $stateParams.hotelId;
    // 获取评论列表
    $scope.listComment = function () {
        if ($scope.nomore) return;
        $scope.loading = true;
        $http.post(LXB_URL.hotelCommentList, {
            hotelId : hotelId,
            pageNo: $scope.pageNo,
            pageSize: 10
        }).success(function (data) {
            $scope.loading = false;
            if (data.error) {
                $scope.error = true;
            } else {
                $scope.commentCount = data.commentCount;
                angular.forEach(data.commentList, function (item) {
                    if (!item.nickName) {
                        item.nickName = '匿名驴友';
                    }
                    $scope.commentList.push(item);
                });
                $scope.pageNo++;
                $scope.nomore = data.nomore;
            }
        }).error(function (data) {
            $scope.loading = false;
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

    // 返回
    $scope.back = function() {
        history.go(-1);
    };
});
