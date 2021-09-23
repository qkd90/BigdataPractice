/**
 * Created by huangpeijie on 2016-09-21,0021.
 */
yhyapp.service("Check", function ($state) {
    return {
        hasToken: function (data) {
            if (!data.success && data.redirectUrl != null) {
                location.href = data.redirectUrl;
            }
        },
        loginCheck: function (json) {
            try {
                if (json.success != null && !json.success && json.nologin != null && json.nologin) {
                    $state.go("timeOut");
                    return false;
                }
            } catch (e) {
            }
            return true;

        }
    };
}).service('Banner', function () {
    var timer;
    var i = -1;
    var offset = 3000;
    var Banner = {
        init: function (options) {
            var Slide = new Swiper(options.container, {
                direction: 'horizontal',
                effect: options.effect,
                loop: true,
                autoplay: options.autoplay,
                autoplayDisableOnInteraction: false,
                pagination: options.pagination,
                paginationClickable: true,
                paginationElement: 'a'
            });
            return Slide;
        }
    };
    return Banner;
}).service('ImageSwiper', function () {
    var timer;
    var i = -1;
    var offset = 3000;
    var ImageSwiper = {
        init: function (options) {
            var Slide = new Swiper(options.container, {
                direction: 'horizontal',
                effect: options.effect,
                loop: true,
                autoplay: options.autoplay,
                autoplayDisableOnInteraction: false,
                pagination: options.pagination,
                onSlideChangeEnd: function (swiper) {
                    if (swiper.activeIndex > 1) {

                        var totalCont = Number($("#runIndex").attr("total-count"));
                        if (swiper.activeIndex > totalCont) {
                            $('#runIndex').html(swiper.activeIndex - totalCont);
                        } else {
                            $('#runIndex').html(swiper.activeIndex);
                        }

                    } else {
                        if ($("#runIndex").attr("total-count")) {
                            $('#runIndex').html(1);
                        } else {
                            $('#runIndex').html(0);
                        }

                    }
                },
                paginationClickable: true,
                paginationElement: 'a'
            });
            return Slide;
        }
    };
    return ImageSwiper;
}).service('TourInit', function () {
    var TourInit = {
        moreCity: function () {
            $('#more_city').click(function () {
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
})
    .service('IdCardUtil', function () {
        var IdCardUtil = {
            getAge: function (idCardNum) {
                var myDate = new Date();
                var month = myDate.getMonth() + 1;
                var day = myDate.getDate();
                var year = myDate.getFullYear();
                var birthYear = idCardNum.substring(6, 10) - 1;
                birthYear = Number(birthYear);
                var birthMonth = idCardNum.substring(10, 12);
                birthMonth = Number(birthMonth);
                var birthDay = idCardNum.substring(12, 14);
                birthDay = Number(birthDay);

                var age = year - birthYear;
                if (birthMonth < month || birthMonth == month && birthDay <= day) {
                    age++;
                }
                return age;
            },
            getPeopleType: function (idCardNum) {
                var age = IdCardUtil.getAge(idCardNum);
                var peopleType = "ADULT";
                if (age >= 0 && age < 18) {
                    peopleType = "KID";
                } else {
                    peopleType = "ADULT";
                }
                return peopleType;
            },
            getSex: function (idCardNum) {
                var sexIndex = parseInt(idCardNum.substr(16, 1));
                if (sexIndex % 2 == 1) {
                    return "male";
                } else {
                    return "female";
                }

            }
        }
        return IdCardUtil;
    })
    .service('DateUtils', function ($http, $state, $cookieStore, storage) {
        //var result = GetDateDiff("2010-02-26 16:00:00", "2011-07-02 21:48:40", "day");
        var DateUtils = {
            GetDateDiff: function (startTime, endTime, diffType) {
                //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
                startTime = startTime.replace(/\-/g, "/");
                endTime = endTime.replace(/\-/g, "/");
                //将计算间隔类性字符转换为小写
                diffType = diffType.toLowerCase();
                var sTime = new Date(startTime);      //开始时间
                var eTime = new Date(endTime);  //结束时间
                //作为除数的数字
                var divNum = 1;
                switch (diffType) {
                    case "second":
                        divNum = 1000;
                        break;
                    case "minute":
                        divNum = 1000 * 60;
                        break;
                    case "hour":
                        divNum = 1000 * 3600;
                        break;
                    case "day":
                        divNum = 1000 * 3600 * 24;
                        break;
                    default:
                        break;
                }
                return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
            },
            getCurrentDateRange: function () {
                var nowDate = new Date();
                nowDate.setHours(0);
                nowDate.setMinutes(0);
                nowDate.setSeconds(0);
                var nowTime = nowDate.getTime();
                var startDate = new Date(nowTime + 86400000);
                var leaveDate = new Date(nowTime + 86400000 * 4);
                var dateRange = [];
                dateRange.push(startDate);
                dateRange.push(leaveDate);
                return dateRange;
            }
        };
        return DateUtils;
    })
    .service('Position', function ($http) {
        var Position = {
            getInfo: function (productType, callback) {
                var info = {};
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        info[productType + 'NearLng'] = position.coords.longitude;
                        info[productType + 'NearLat'] = position.coords.latitude;
                        $http.post(yhyUrl.selectcitys, {
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
                $http.post(yhyUrl.reverseCity, {
                    address: info[productType + 'CityName'],
                    city: info[productType + 'CityName']
                }).success(function (result) {
                    info[productType + 'NearLng'] = result.lng;
                    info[productType + 'NearLat'] = result.lat;
                    callback(info);
                }).error(function () {
                    bootbox.alert('位置信息获取失败');
                    callback(info);
                });
            }
        };
        return Position;
    })
    .service('HotelDate', function () {
        var HotelDate = {
            init: function (options) {
                // 重要! 先移除之前可能存在的日历组件
                $('#calendar_hotel').remove();
                $('#startDate').remove();
                $('#endDate').remove();
                $('#needCompare').remove();
                // 限制最多预订28天
                var maxValidDate = new Date(new Date().getTime() + 28 * 86400000);
                var hotelDateRange = new pickerDateRange(options.elementId, {
                    isTodayValid: options.isTodayValid,
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
            afterSelect: function () {
                $('#J_hotel_header').show();
                $('#hotel_main').removeClass('hide');
                $('#calendarArea').addClass('hide');
            }
        };
        return HotelDate;
    })
    .filter('trustHtml', function ($sce) {
        return function (input) {
            return $sce.trustAsHtml(input);
        }
    })
    .filter('textCutLength', function () {
        return function (input, maxLength, hightLineWord, appendText) {

            input = input || '';
            appendText = appendText || '';
            var output = '';

            if (maxLength == null || maxLength < 0) {
                maxLength = 0;
            }
            if (input.length > maxLength) {
                output = input.substr(0, maxLength) + appendText;
            } else {
                output = input;
            }
            if (hightLineWord != null && hightLineWord != "" && hightLineWord != 'undefined') {
                var hightHtml = '<font style="color: #ff6000;">' + hightLineWord + '</font>';
                output = output.replace(hightLineWord, hightHtml);
            }
            return output;
        }
    }).service('Wechatpay', function ($http, $state, $cookieStore, storage, $interval, $timeout, $rootScope) {
        var Wechatpay = {
            // 判断微信浏览器方法
            checker: null,
            pays: {},
            isWeiXin: function () {
                var ua = window.navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == 'micromessenger') {
                    return true;
                } else {
                    return false;
                    //return true;//Just for test
                }
            },
            payOrder: function (cost) {
                if (this.isWeiXin()) {
                    $.post(yhyUrl.getPrepayId, {cost: cost}, function (result) {
                        if (result.success) {
                            Wechatpay.wxPay(result.prepayId, result.orderId, Wechatpay.paySuccess);
                        } else {
                            bootbox.alert("出错了:" + result.errMsg);
                        }
                    });
                } else {
                    //for test
                    bootbox.alert("请在微信浏览器下使用支付功能", function () {
                        $.post(yhyUrl.getPrepayId, {cost: cost}, function (result) {
                            if (result.success) {
                                Wechatpay.wxPay(result.prepayId, result.orderId, Wechatpay.paySuccess);
                            } else {
                                bootbox.alert("出错了:" + result.errMsg);
                            }
                        });
                    });

                }
            },
            payOrderWithBack: function (orderId, orderType, callback) {
                if (this.isWeiXin()) {
                    $.ajax({
                        url: yhyUrl.getPrepayId,
                        data: {orderId: orderId, orderType: orderType},
                        success: function (result) {
                            if (result.success) {
                                Wechatpay.wxPay(result.prepayId, result.orderId, callback);
                            } else {
                                $rootScope.$apply(function () {
                                    $rootScope.loading = false;
                                });
                                bootbox.alert("出错了:" + result.errMsg);
                            }
                        },
                        dataType: "json",
                        type: "post"
                    });
                    //$.post(yhyUrl.getPrepayId, {orderId: orderId, orderType: orderType}, function (result) {
                    //    if (result.success) {
                    //        Wechatpay.wxPay(result.prepayId, result.orderId, callback);
                    //    } else {
                    //        bootbox.alert("出错了:" + result.errMsg);
                    //        $("div.ajaxloading").hide();
                    //    }
                    //});
                } else if (plus) {
                    $.post(yhyUrl.getAppPrepayId, {orderId: orderId, orderType: orderType}, function (result) {
                        if (result.success) {
                            Wechatpay.wxPay(result.prepayId, result.orderId, callback);
                        } else {
                            $rootScope.$apply(function () {
                                $rootScope.loading = false;
                            });
                            bootbox.alert("出错了:" + result.errMsg);
                        }
                    });
                } else {
                    $rootScope.$apply(function () {
                        $rootScope.loading = false;
                    });
                    bootbox.alert("请在微信浏览器下使用支付功能");
                }
            },
            alipayNative: function (orderId, way) {
                $.post(yhyUrl.alinativePay, {orderId: orderId, way: way}, function (result) {
                    if (result.success) {
                        var orderStr = result.payStr;
                        console.info(JSON.stringify(orderStr));
                        var temp = '{"appid":"wx0411fa6a39d61297","noncestr":"oIZMFpmHXEJOnXba","package":"Sign=WXPay","partnerid":"1230636401","prepayid":"wx2016071314403317b60ba47b0916054328","timestamp":1468392033,"sign":"95B60C9945E47C0A27C9887ABF2D27EF"}';
                        //var temp = '{"appid":"wxa444b077b160ca6e","noncestr":"G56QDQ_miC685Ae_jZCb","package":"Sign=WXPay","partnerid":"1357348802","prepayid":"wx201607131339256d6ae9dab90132185995","timestamp":1468388364,"sign":"CA5DE7538B826822DDE551999F3EFC67"}';
                        plus.payment.request(pays[way], JSON.stringify(orderStr), function (result) {
                            console.info(JSON.stringify(result));
                            plus.nativeUI.alert("支付成功：感谢你的支持，我们会继续努力完善产品。", function () {
                                back();
                            });
                        }, function (e) {
                            $("div.ajaxloading").hide();
                            console.info(JSON.stringify(e));
                            plus.nativeUI.alert("[" + e.code + "]：" + e.message, null, "支付失败：" + e.code);
                        });
                    } else {
                        bootbox.alert("出错了:" + result.errorMsg);
                        $("div.ajaxloading").hide();
                    }
                });
            },
            wxPay: function (prepayId, orderId, callback) {
                var ctimestamp;
                var cnonceStr;
                var cpaysign;
                var cappId;
                var partnerId;
                var payconfigUrl = "";
                if (this.isWeiXin()) {
                    payconfigUrl = yhyUrl.getPayConfig;
                } else if (plus) {
                    payconfigUrl = yhyUrl.getAppPayConfig;
                }
                $.ajax({
                    url: payconfigUrl,
                    data: {prepayId: prepayId},
                    success: function (result) {
                        ctimestamp = result.timeStamp;
                        cnonceStr = result.nonceStr;
                        cpaysign = result.paySign;
                        cappId = result.appId;
                        partnerId = result.partnerId;
                        if (Wechatpay.isWeiXin()) {
                            try {
                                WeixinJSBridge.invoke('getBrandWCPayRequest', {
                                        appId: cappId,
                                        timeStamp: "" + ctimestamp + "",
                                        nonceStr: cnonceStr,
                                        package: 'prepay_id=' + prepayId,
                                        signType: 'MD5',
                                        paySign: cpaysign
                                    },
                                    function (res) {
                                        //$("div.ajaxloading").hide();
                                        if (res.err_msg == "get_brand_wcpay_request:ok") {
                                            callback(orderId);
                                        } else {
                                            $rootScope.$apply(function () {
                                                $rootScope.loading = false;
                                            });
                                            Wechatpay.stopCheck();
                                        }
                                    });
                            } catch (e) {
                                //for test
                                callback(orderId);
                            }
                        } else if (plus) {
                            plus.payment.request(pays['wxpay'], {
                                appid: cappId,
                                timestamp: "" + ctimestamp + "",
                                nonceStr: cnonceStr,
                                package: 'Sign=WXPay',
                                prepayId: prepayId,
                                signType: 'MD5',
                                partnerId: partnerId,
                                paySign: cpaysign,

                            }, function (result) {
                                console.info(JSON.stringify(result));
                                plus.nativeUI.alert("支付成功：感谢你的支持，我们会继续努力完善产品。", function () {
                                    back();
                                });
                            }, function (e) {
                                plus.nativeUI.alert("[" + e.code + "]：" + e.message, null, "支付失败：" + e.code);
                            });
                        }
                    },
                    type: "post",
                    dataType: "json"
                });
                //$.post(payconfigUrl, {prepayId: prepayId}, function (result) {
                //    ctimestamp = result.timeStamp;
                //    cnonceStr = result.nonceStr;
                //    cpaysign = result.paySign;
                //    cappId = result.appId;
                //    partnerId = result.partnerId;
                //    if (Wechatpay.isWeiXin()) {
                //        try {
                //            WeixinJSBridge.invoke('getBrandWCPayRequest', {
                //                    appId: cappId,
                //                    timeStamp: "" + ctimestamp + "",
                //                    nonceStr: cnonceStr,
                //                    package: 'prepay_id=' + prepayId,
                //                    signType: 'MD5',
                //                    paySign: cpaysign
                //                },
                //                function (res) {
                //                    //$("div.ajaxloading").hide();
                //                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                //                        callback(orderId);
                //                    } else {
                //                        bootbox.alert("用户取消，或支付异常");
                //                        Wechatpay.stopCheck();
                //                        $("div.ajaxloading").hide();
                //                        //alert(JSON.stringify(res));
                //                    }
                //                });
                //        } catch (e) {
                //            //for test
                //            callback(orderId);
                //        }
                //    } else if (plus) {
                //        plus.payment.request(pays['wxpay'], {
                //            appid: cappId,
                //            timestamp: "" + ctimestamp + "",
                //            nonceStr: cnonceStr,
                //            package: 'Sign=WXPay',
                //            prepayId: prepayId,
                //            signType: 'MD5',
                //            partnerId: partnerId,
                //            paySign: cpaysign,
                //
                //        }, function (result) {
                //            console.info(JSON.stringify(result));
                //            plus.nativeUI.alert("支付成功：感谢你的支持，我们会继续努力完善产品。", function () {
                //                back();
                //            });
                //        }, function (e) {
                //            plus.nativeUI.alert("[" + e.code + "]：" + e.message, null, "支付失败：" + e.code);
                //        });
                //    }
                //});
            },
            paySuccess: function (orderId) {
                $timeout(function () {
                    Wechatpay.startCheck(orderId);
                }, 200);
            },
            startCheck: function (orderId) {
                Wechatpay.checker = $interval(function () {
                    Wechatpay.checkStatus(orderId);
                }, 800);
            },
            checkStatus: function (orderId) {
                $.post("/yihaiyou/pay/checkStatus.jhtml", {'orderId': orderId}, function (result) {
                    if (result.success) {
                        if (result.errorMsg != 'WAIT' && result.errorMsg != 'UNCONFIRMED' && result.errorMsg != 'CONFIRMED') {
                            $rootScope.$apply(function () {
                                $rootScope.loading = false;
                            });
                            Wechatpay.stopCheck();
                            if (result.type == "plan") {
                                $state.go("planOrderDetail", {id: orderId});
                            } else if (result.type == "ticket") {
                                $state.go("ticketOrderDetail", {orderId: orderId});
                            } else if (result.type == "ferry") {
                                $state.go("ferryOrderDetail", {orderId: orderId});
                            } else if (result.type == "cruiseship") {
                                $state.go("cruiseShipOrderDetail", {orderId: orderId});
                            } else if (result.type == "sailboat" || result.type == "yacht" || result.type == "huanguyou") {
                                $state.go("sailling/orderDetail", {orderId: orderId});
                            } else if (result.type == "hotel") {
                                $state.go("hotelOrderDetail", {orderId: orderId});
                            } else if (result.type == "recharge") {
                                $state.go("personalWallet");
                            }
                        }

                    }
                });
            },
            stopCheck: function () {
                $interval.cancel(Wechatpay.checker);
            },
        };
        return Wechatpay;
    })
    .service('MyStorage', function ($http, $state, $cookieStore, storage) {
        var MyStorage = {
            reset: function () {
                for (var i in yhyKey) {
                    storage.remove(yhyKey[i]);
                }
            }
        };
        return MyStorage;
    })
    .service("PeopleType", function () {
        return {
            isAdult: function (idNumber) {
                var bir = parseInt(idNumber.substr(6, 8));
                var now = parseInt(new Date().format("yyyyMMdd"));
                return (now - bir) > (12 * 10000);
            }
        };
    })
    .service("NumberHandle", function () {
        return {
            roundTwoDecimal: function (number) {
                return Math.round(number * 100) / 100;
            }
        };
    })
    .service("ImageHandel", function () {
        return {
            completeImage: function (image) {
                if (image == null || image == "") {
                    return "";
                } else {
                    if (image.indexOf("http") == 0) {
                        return image;
                    } else {
                        return QINIU_BUCKET_URL + image;
                    }
                }
            }
        };
    })
    .service("ObjectHandle", function () {
        return {
            clone: function (obj) {
                // Handle the 3 simple types, and null or undefined
                if (null == obj || "object" != typeof obj) return obj;

                // Handle Date
                if (obj instanceof Date) {
                    var copy = new Date();
                    copy.setTime(obj.getTime());
                    return copy;
                }

                // Handle Array
                if (obj instanceof Array) {
                    var copy = [];
                    var len = obj.length;
                    for (var i = 0; i < len; ++i) {
                        copy[i] = this.clone(obj[i]);
                    }
                    return copy;
                }

                // Handle Object
                if (obj instanceof Object) {
                    var copy = {};
                    for (var attr in obj) {
                        if (obj.hasOwnProperty(attr)) copy[attr] = this.clone(obj[attr]);
                    }
                    return copy;
                }

                throw new Error("Unable to copy obj! Its type isn't supported.");
            }
        }
    })
    .service("idCard", function () {
        return {
            /**
             * 身份证15位编码规则：dddddd yymmdd xx p
             * dddddd：地区码
             * yymmdd: 出生年月日
             * xx: 顺序类编码，无法确定
             * p: 性别，奇数为男，偶数为女
             * <p />
             * 身份证18位编码规则：dddddd yyyymmdd xxx y
             * dddddd：地区码
             * yyyymmdd: 出生年月日
             * xxx:顺序类编码，无法确定，奇数为男，偶数为女
             * y: 校验码，该位数值可通过前17位计算获得
             * <p />
             * 18位号码加权因子为(从右到左) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]
             * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
             * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
             * i为身份证号码从右往左数的 2...18 位; Y_P为脚丫校验码所在校验码数组位置
             *
             */

            checkParseIdCard: function (val) {
                var val = this.trim(val);
                var msg = this.checkIdcard(val);
                if (msg != "验证通过!") {
                    alert(msg);
                    return;
                }

                var birthdayValue;
                var sexId;
                var sexText;
                var age;

                if (15 == val.length) { //15位身份证号码
                    birthdayValue = val.charAt(6) + val.charAt(7);
                    if (parseInt(birthdayValue) < 10) {
                        birthdayValue = '20' + birthdayValue;
                    } else {
                        birthdayValue = '19' + birthdayValue;
                    }
                    birthdayValue = birthdayValue + '-' + val.charAt(8) + val.charAt(9)
                        + '-' + val.charAt(10) + val.charAt(11);
                    if (parseInt(val.charAt(14) / 2) * 2 != val.charAt(14)) {
                        sexId = "1";
                        sexText = "男";
                    } else {
                        sexId = "2";
                        sexText = "女";
                    }
                }
                if (18 == val.length) { //18位身份证号码
                    birthdayValue = val.charAt(6) + val.charAt(7) + val.charAt(8)
                        + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11) + '-'
                        + val.charAt(12) + val.charAt(13);
                    if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16)) {
                        sexId = "1";
                        sexText = "男";
                    } else {
                        sexId = "2";
                        sexText = "女";
                    }
                }
                //年龄
                var dt1 = new Date(birthdayValue.replace("-", "/"));
                var dt2 = new Date();
                var age = dt2.getFullYear() - dt1.getFullYear();
                var m = dt2.getMonth() - dt1.getMonth();
                if (m < 0)
                    age--;
                alert(birthdayValue + sexId + sexText + age);
            },
            checkIdcard: function (idcard) {
                var Errors = new Array("验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!",
                    "身份证号码校验错误!", "身份证地区非法!");
                var area = {
                    11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林",
                    23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东",
                    41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川",
                    52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆",
                    71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
                };
                var idcard, Y, JYM;
                var S, M;
                var idcard_array = new Array();
                idcard_array = idcard.split("");
                //地区检验
                if (area[parseInt(idcard.substr(0, 2))] == null)
                    return Errors[4];
                //身份号码位数及格式检验
                switch (idcard.length) {
                    case 15:
                        if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
                            || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
                                .substr(6, 2)) + 1900) % 4 == 0)) {
                            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
                        } else {
                            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
                        }
                        //if (ereg.test(idcard))
                        return Errors[1];
                        //else
                        //	return Errors[2];
                        break;
                    case 18:
                        //18位身份号码检测
                        //出生日期的合法性检查
                        //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
                        //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
                        if (parseInt(idcard.substr(6, 4)) % 4 == 0
                            || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
                                .substr(6, 4)) % 4 == 0)) {
                            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
                        } else {
                            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
                        }
                        //if (ereg.test(idcard)) {//测试出生日期的合法性
                        //计算校验位
                        S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
                            + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
                            + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
                            + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
                            + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
                            + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
                            + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
                            + parseInt(idcard_array[7]) * 1
                            + parseInt(idcard_array[8]) * 6
                            + parseInt(idcard_array[9]) * 3;
                        Y = S % 11;
                        M = "F";
                        JYM = "10X98765432";
                        M = JYM.substr(Y, 1); //判断校验位
                        if (M == idcard_array[17] || M == "X")
                            return Errors[0]; //检测ID的校验位
                        else
                            return Errors[3];
                        //} else
                        //	return Errors[2];
                        break;
                    default:
                        return Errors[1];
                        break;
                }
            },

            /**
             * 验证18位数身份证号码中的生日是否是有效生日
             * @param idCard 18位书身份证字符串
             * @return
             */
            isValidityBrithBy18IdCard: function (idCard18) {
                var year = idCard18.substring(6, 10);
                var month = idCard18.substring(10, 12);
                var day = idCard18.substring(12, 14);
                var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
                // 这里用getFullYear()获取年份，避免千年虫问题
                if (temp_date.getFullYear() != parseFloat(year)
                    || temp_date.getMonth() != parseFloat(month) - 1
                    || temp_date.getDate() != parseFloat(day)) {
                    return false;
                } else {
                    return true;
                }
            },
            /**
             * 验证15位数身份证号码中的生日是否是有效生日
             * @param idCard15 15位书身份证字符串
             * @return
             */
            isValidityBrithBy15IdCard: function (idCard15) {
                var year = idCard15.substring(6, 8);
                var month = idCard15.substring(8, 10);
                var day = idCard15.substring(10, 12);
                var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
                // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
                if (temp_date.getYear() != parseFloat(year)
                    || temp_date.getMonth() != parseFloat(month) - 1
                    || temp_date.getDate() != parseFloat(day)) {
                    return false;
                } else {
                    return true;
                }
            },
            //去掉字符串头尾空格
            trim: function (str) {
                return str.replace(/(^\s*)|(\s*$)/g, "");
            }
        }
    })
;