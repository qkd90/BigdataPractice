/**
 * Created by zzl on 2016/7/19.
 */
routerApp.service('Banner', function() {
    var timer;
    var i = -1;
    var offset = 3000;
    var Banner = {
        init: function(options) {
            var Slide = new Swiper (options.container, {
                direction: 'horizontal',
                effect: options.effect,
                loop: true,
                autoplay: options.autoplay,
                autoplayDisableOnInteraction: false,
                pagination: options.pagination,
                paginationClickable :true,
                paginationElement: 'a'
            });
            return Slide;
        }
    };
    return Banner;
}).service('TourInit', function() {
    var TourInit = {
        moreCity: function() {
            $('#more_city').click(function() {
                if ($('.morechoose').eq(0).is(':hidden')) {
                    $('.morechoose').show();
                    $('#more_city').html("收起");
                    $('#more_city').removeClass('gty_down');
                    $('#more_city').addClass('gty_up');
                } else {
                    $('.morechoose').hide();
                    $('#more_city').html("更多");
                    $('#more_city').removeClass('gty_up');
                    $('#more_city').addClass('gty_down');
                }
            });
        }
    }
    return TourInit;
}).service('Position', function($http) {
    var Position = {
        getInfo: function (productType, callback) {
            var info = {};
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    info[productType + 'NearLng'] = position.coords.longitude;
                    info[productType + 'NearLat'] = position.coords.latitude;
                    $http.post(GetUrl.selectcitys, {
                        longitude: position.coords.longitude,
                        latitude: position.coords.latitude
                    }).success(function (data) {
                        if (data.success) {
                            info[productType + 'City'] = data.cityList[0];
                            info[productType + 'CityCode'] = data.cityList[0].cityCode;
                            info[productType + 'CityName'] = data.cityList[0].name;
                            callback(info);
                        }
                    }).error(function () {
                        // 城市获取失败, 默认厦门
                        info[productType + 'CityCode'] = "350200";
                        info[productType + 'CityName'] = "厦门";
                        callback(info);
                    });
                }, function () {
                    // 定位失败, 默认厦门
                    info[productType + 'CityCode'] = "350200";
                    info[productType + 'CityName'] = "厦门";
                    Position.cityReverse(productType, info, callback);
                });
                // 确认定位执行结果
                if (info[productType + 'CityCode'] == null) {
                    // 定位出错
                    info[productType + 'CityCode'] = "350200";
                    info[productType + 'CityName'] = "厦门";
                    Position.cityReverse(productType, info, callback);
                }
            } else {
                // 不支持定位, 默认厦门
                info[productType + 'CityCode'] = "350200";
                info[productType + 'CityName'] = "厦门";
                Position.cityReverse(productType, info, callback);
            }
        },
        cityReverse: function (productType, info, callback) {
            $http.post(GetUrl.reverseCity, {
                address: info[productType + 'CityName'],
                city: info[productType + 'CityName']
            }).success(function (result) {
                info[productType + 'NearLng'] =result.lng;
                info[productType + 'NearLat'] = result.lat;
                callback(info);
            }).error(function () {
                bootbox.alert('位置信息获取失败');
                callback(info);
            });
        }
    };
    return Position;
}).service('HotelDate', function() {
    var HotelDate = {
        init: function(options) {
            // 重要! 先移除之前可能存在的日历组件
            $('#calendar_hotel').remove();
            $('#startDate').remove();
            $('#endDate').remove();
            $('#needCompare').remove();
            // 限制最多预订28天
            var maxValidDate = new Date(new Date().getTime() + 28 * 86400000);
            var hotelDateRange = new pickerDateRange(options.elementId, {
                isTodayValid : options.isTodayValid,
                startDate: options.startDate,
                endDate: options.endDate,
                autoSubmit: true,
                theme: 'ta',
                calendars: 2,
                noCalendar: true,
                suffix: options.suffix,
                usePreMonthFun: false,
                useNextMonthFun: false,
                maxValidDate: maxValidDate.getTime(),
                success: options.success
            });
            //
            $('#calendar_' + options.suffix).insertAfter($('#' + options.controllerDiv));
            return hotelDateRange;
        },
        afterSelect: function() {
            $('#J_hotel_header').show();
            $('#hotel_main').removeClass('hide');
            $('#calendarArea').addClass('hide');
        }
    };
    return HotelDate;
})
;