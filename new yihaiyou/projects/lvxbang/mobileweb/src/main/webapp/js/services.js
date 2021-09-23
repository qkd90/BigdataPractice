routerApp
    .factory('bdMapSev', function ($http) {
        var bdMap = function () {
            this.marker = '';
            this.map = new BMap.Map("map", {enableMapClick: false});
            this.markerColor = ['green', 'indigo', 'blue', 'purple', 'pink', 'red', 'orange'];
            this.lineColor = ['#00bf6b', '#12cfd6', '#57b3ff', '#b184f1', '#fd5cbd', '#ff6471', '#fd9d5d'];
            this.markers = {};
            this.lines = {};
            this.zoomLevel = 15;
            this.zoomLevelSmall = 12;
            this.init();
        };
        bdMap.prototype = {
            init: function () {
                this.map.centerAndZoom(new BMap.Point(0, 0), this.zoomLevel);
                var local = new BMap.LocalSearch(this.map, {
                    renderOptions: {map: this.map}
                });
                this.map.clearOverlays();
                this.map.addControl(new BMap.NavigationControl());// 添加平移缩放控件
                this.map.enableScrollWheelZoom();//启用滚轮放大缩小
                //this.dragMarker(118.07491, 24.482206);
            },
            dragMarker: function (pointX, pointY) {
                var new_point = new BMap.Point(pointX, pointY);
                this.map.panTo(new_point);
                // 拖拽坐标
                marker = new BMap.Marker(new_point);  // 创建标注
                this.map.addOverlay(marker);              // 将标注添加到地图中
                var label = new BMap.Label("拖拽坐标确定位置", {offset: new BMap.Size(20, -10)});
                marker.setLabel(label);
                this.map.addOverlay(marker);
                marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                marker.enableDragging();    //可拖拽
                marker.addEventListener("dragend", function (e) {//将结果进行拼接并显示到对应的容器内
                    pointX = e.point.lng;
                    pointY = e.point.lat;
                    $('.quicklist-r .linktop .message-num').attr('data-point', pointX + ',' + pointY);
                });
            },
            dragEvent: function () {
                this.map.addEventListener("click", function (e) {
                    pointX = e.point.lng;
                    pointY = e.point.lat;
                    $('.quicklist-r .linktop .message-num').attr('data-point', pointX + ',' + pointY);
                    this.map.removeOverlay(marker);
                    dragMarker(pointX, pointY);
                });
                local.search($scope.start);
            },
            addAScenic: function (scenic, day, index) {
                if (!day) {
                    scenic.color = [this.markerColor[0]];
                }
                scenic.color = this.markerColor[day % 7];
                var Dday = day + 1;
                var label = '<div class="posiR Pop_ups_posiR">' +
                    '<div class="location" data-id="' + scenic.id + '">' +
                    '<label class="fl location_l ' + scenic.color + '">' +
                    '<b class="ff_yh  disB title-day">' + index + '</b>' +
                    '<b class="ff_yh  disB title-whole">D' + Dday + '</b>' +
                    '</label>' +
                    '</div></div>';
                var marker = new BMapLib.RichMarker(label, new BMap.Point(scenic.lng, scenic.lat),
                    {
                        "anchor": new BMap.Size(-15, -35)
                    }
                );
                label = $(marker.getContent());
                label.find(".location").click(function () {
                    label.find('.Pop_ups').show();
                });
                label.find(".Pop_ups .closex").click(function () {
                    label.find('.Pop_ups').hide();
                });
                if (!this.markers[day]) {
                    this.markers[day] = [];
                }
                this.markers[day].push(marker);
                this.map.addOverlay(marker);
            },
            drawLines: function () {
                var $thiz = this;
                $.each(this.markers, function (day) {
                    $thiz.drawALine(day);
                });
            },
            drawALine: function (day) {
                var points = [];
                $.each(this.markers[day], function (index, data) {
                    points.push(data.getPosition());
                });
                var line = new BMap.Polyline(points, {
                    strokeColor: this.lineColor[day],
                    strokeWeight: 2,
                    strokeOpacity: 1
                });
                if (!this.lines[day]) {
                    this.lines[day] = [];
                }
                this.lines[day].push(line);
                this.map.addOverlay(line);
            },
            showAllLine: function () {
                for (var day in this.lines) {
                    $.each(this.lines[day], function (index, line) {
                        this.map.addOverlay(line);
                    });
                }
                var lng = 0;
                var lat = 0;
                var maxLng = 0;
                var minLng = 150;
                var maxLat = 0;
                var minLat = 90;
                for (var dayIndex in this.markers) {
                    $.each(this.markers[dayIndex], function (index, data) {
                        var point = data.getPosition();
                        if (maxLng < point.lng) {
                            maxLng = point.lng;
                        }
                        if (minLng > point.lng) {
                            minLng = point.lng;
                        }
                        if (maxLat < point.lat) {
                            maxLat = point.lat;
                        }
                        if (minLat > point.lat) {
                            minLat = point.lat;
                        }
                        lng += point.lng;
                        lat += point.lat;
                    });
                }
                var centerPoint = new BMap.Point((maxLng + minLng) / 2, (maxLat + minLat) / 2);
                var bigZoom = parseInt(Math.max((maxLng - minLng) / 0.34, (maxLat - minLat) / (0.17)));
                if (bigZoom > 2) {
                    bigZoom = 1 + parseInt(Math.sqrt(bigZoom));
                }
                this.zoomLevelSmall = 14 - bigZoom;
                this.map.centerAndZoom(centerPoint, this.zoomLevelSmall);
            },
            showDay: function (day) {
                var thiz = this;
                for (var i in thiz.lines) {
                    if (i == day) {
                        $.each(thiz.lines[i], function (index, line) {
                            thiz.map.addOverlay(line);
                        });
                        continue;
                    }
                    $.each(thiz.lines[i], function (index, line) {
                        thiz.map.removeOverlay(line);
                    });
                }
                var lng = 0;
                var lat = 0;
                var maxLng = 0;
                var minLng = 150;
                var maxLat = 0;
                var minLat = 90;
                $.each(thiz.markers[day], function (index, data) {
                    var point = data.getPosition();
                    if (maxLng < point.lng) {
                        maxLng = point.lng;
                    }
                    if (minLng > point.lng) {
                        minLng = point.lng;
                    }
                    if (maxLat < point.lat) {
                        maxLat = point.lat;
                    }
                    if (minLat > point.lat) {
                        minLat = point.lat;
                    }
                    lng += point.lng;
                    lat += point.lat;
                });
                var centerPoint = new BMap.Point((maxLng + minLng) / 2, (maxLat + minLat) / 2);
                var bigZoom = parseInt(Math.max((maxLng - minLng) / 0.085, (maxLat - minLat) / (0.1)));
                if (bigZoom > 2) {
                    bigZoom = 1 + parseInt(Math.sqrt(bigZoom));
                }
                thiz.zoomLevel = 16 - bigZoom;
                thiz.map.centerAndZoom(centerPoint, thiz.zoomLevel);
            },
            locationTo: function (lng, lat) {
                var centerPoint = new BMap.Point(lng, lat);
                this.map.centerAndZoom(centerPoint, this.zoomLevel);
            },
            transformation: function () {

            }
        };
        return bdMap;
    })
    .service('user', function ($http) {
        var user = {
            isLogin: function () {
                $scope.userid = storage.get(keys.userid) == null ? '' : storage.get(keys.userid);
            }
        };
        return user;
    })
    .service('Check', function ($http, $state) {
        var Check = {
            loginCheck: function (json) {
                try {
                    if (json.success != null && !json.success && json.nologin != null && json.nologin) {
                        bootbox.alert('请先去登录', function () {
                            location.href = GetUrl.loginPage;
                        });
                        return false;
                    }
                } catch (e) {
                }
                return true;

            },
            loginCheckAndBack: function (json) {
                try {
                    if (json.success != null && !json.success && json.nologin != null && json.nologin) {
                        bootbox.alert('请先去登录', function () {
                            //location.href = '/#/login?url=' + document.location.href;
                            //$state.go('login', {url: document.location.href});
                            location.href = GetUrl.loginPage;
                        });
                        return false;
                    }
                } catch (e) {
                }
                return true;

            }
        };
        return Check;
    })
    .service('MyStorage', function ($http, $state, $cookieStore, storage) {
        var MyStorage = {
            reset: function () {
                storage.remove(keys.xianluSearch);
                storage.remove(keys.selectCitys);
                storage.remove(keys.fromCity);
                storage.remove(keys.addScenics);
                storage.remove(keys.scenic);
                storage.remove(keys.selectTime);
                storage.remove(keys.startDay);
                storage.remove(keys.optimizeScenics);
                storage.remove(keys.jiaotong);
                storage.remove(keys.addNodes);
                storage.remove(keys.jiudian);
                storage.remove(keys.removeNodes);
                storage.remove(keys.train);
                storage.remove(keys.plan);
                storage.remove(keys.coupons);
                storage.remove(keys.usecoupon);
                storage.remove(keys.contact);
                storage.remove(keys.invoice);
                storage.remove(keys.selectedtourist);
                storage.remove(keys.productcost);
                storage.remove(keys.creditCard);
                storage.remove(keys.youhui);
                storage.remove(keys.order);
                storage.remove(keys.fromQuote);
                storage.remove(keys.cityMap);
                storage.remove(keys.hotPlans);
                storage.remove(keys.place);
                storage.remove(keys.yxContent);
                storage.remove(keys.yxPhoto);
                storage.remove(keys.hotel);
            }
        };
        return MyStorage;
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
                var startDate = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate() + 1);
                var leaveDate = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate() + 4);
                var dateRange = [];
                dateRange.push(startDate);
                dateRange.push(leaveDate);
                return dateRange;
            }
        };
        return DateUtils;
    })
    .service('Wechatpay', function ($http, $state, $cookieStore, storage, $interval, $timeout) {
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
                    $.post(GetUrl.getPrepayId, {cost: cost}, function (result) {
                        if (result.success) {
                            Wechatpay.wxPay(result.prepayId, result.orderId, Wechatpay.paySuccess);
                        } else {
                            bootbox.alert("出错了:" + result.errMsg);
                        }
                    });
                } else {
                    //for test
                    bootbox.alert("请在微信浏览器下使用支付功能", function () {
                        $.post(GetUrl.getPrepayId, {cost: cost}, function (result) {
                            if (result.success) {
                                Wechatpay.wxPay(result.prepayId, result.orderId, Wechatpay.paySuccess);
                            } else {
                                bootbox.alert("出错了:" + result.errMsg);
                            }
                        });
                    });

                }
            },
            payOrderWithBack: function (orderId, callback) {
                if (this.isWeiXin()) {
                    $.post(GetUrl.getPrepayId, {orderId: orderId}, function (result) {
                        if (result.success) {
                            Wechatpay.wxPay(result.prepayId, result.orderId, callback);
                        } else {
                            bootbox.alert("出错了:" + result.errMsg);
                            $("div.ajaxloading").hide();
                        }
                    });
                } else if (plus) {
                    $.post(GetUrl.getAppPrepayId, {orderId: orderId}, function (result) {
                        if (result.success) {
                            Wechatpay.wxPay(result.prepayId, result.orderId, callback);
                        } else {
                            bootbox.alert("出错了:" + result.errMsg);
                            $("div.ajaxloading").hide();
                        }
                    });
                } else {
                    bootbox.alert("请在微信浏览器下使用支付功能");
                    $("div.ajaxloading").hide();
                }
            },
            alipayNative : function(orderId,way){
                $.post(GetUrl.alinativePay, {orderId: orderId,way:way}, function (result) {
                    if (result.success) {
                        var orderStr = result.payStr;
                        console.info(JSON.stringify(orderStr));
                        var temp = '{"appid":"wx0411fa6a39d61297","noncestr":"oIZMFpmHXEJOnXba","package":"Sign=WXPay","partnerid":"1230636401","prepayid":"wx2016071314403317b60ba47b0916054328","timestamp":1468392033,"sign":"95B60C9945E47C0A27C9887ABF2D27EF"}';
                        //var temp = '{"appid":"wxa444b077b160ca6e","noncestr":"G56QDQ_miC685Ae_jZCb","package":"Sign=WXPay","partnerid":"1357348802","prepayid":"wx201607131339256d6ae9dab90132185995","timestamp":1468388364,"sign":"CA5DE7538B826822DDE551999F3EFC67"}';
                        plus.payment.request(pays[way], JSON.stringify(orderStr), function (result) {
                            console.info(JSON.stringify(result));
                            plus.nativeUI.alert("支付成功：感谢你的支持，我们会继续努力完善产品。", function () {
                                back();
                            }, "旅行帮");
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
                    payconfigUrl = GetUrl.getPayConfig;
                } else if (plus) {
                    payconfigUrl = GetUrl.getAppPayConfig;
                }
                $.post(payconfigUrl, {prepayId: prepayId}, function (result) {
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
                                        bootbox.alert("用户取消，或支付异常");
                                        Wechatpay.stopCheck();
                                        $("div.ajaxloading").hide();
                                        //alert(JSON.stringify(res));
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
                            }, "旅行帮");
                        }, function (e) {
                            plus.nativeUI.alert("[" + e.code + "]：" + e.message, null, "支付失败：" + e.code);
                        });
                    }
                });
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
                $.post("/mobile/pay/checkStatus.jhtml", {'orderId': orderId}, function (result) {
                    if (result.success) {
                        if (result.errorMsg != 'WAIT' && result.errorMsg != 'UNCONFIRMED' && result.errorMsg != 'CONFIRMED') {
                            $("div.ajaxloading").hide();
                            Wechatpay.stopCheck();
                            if (result.type == "line") {
                                location.href = GetUrl.lineOrderDetail + orderId;
                            } else if (result.type == "ticket") {
                                location.href = GetUrl.ticketOrderDetail + orderId;
                            } else if (result.type == "hotel") {
                                location.href = GetUrl.hotelOrderDetail + orderId;
                            } else {
                                $state.go("my-order-info", {id: orderId});
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
    .service('Share', function ($http, $state, $cookieStore, storage, $interval, $timeout) {
        var Share = {
            shares: window.shares,
            Intent: null,
            File: null,
            Uri: null,
            main: null,
            // H5 plus事件处理
            plusReady: function () {
                updateSerivces();
                if (plus.os.name == "Android") {
                    main = plus.android.runtimeMainActivity();
                    Intent = plus.android.importClass("android.content.Intent");
                    File = plus.android.importClass("java.io.File");
                    Uri = plus.android.importClass("android.net.Uri");
                }
            },
            /**
             * 更新分享服务
             */
            updateSerivces: function () {
                plus.share.getServices(function (s) {
                    shares = {};
                    for (var i in s) {
                        var t = s[i];
                        shares[t.id] = t;
                    }
                }, function (e) {
                    outSet("获取分享服务列表失败：" + e.message);
                });
            }
        };
        return Share;
    })
    .service('WechatShare', function ($http, $state, $location) {  // 微信分享
        var url = $location.absUrl();
        if (url.indexOf("#") > 0) {   // 微信签名不包含#及其后面部分
            url = url.substring(0, url.indexOf("#"));
        }
        url = encodeURI(encodeURI(url));
        var WechatShare = {
            // 初始微信分享所需配置
            initShareCfg: function (cfg) {  // cfg = {title:'标题',desc:'描述',link:'链接',imgUrl: '图片链接',success:fn, cancel:fn}
                $http.post(LXB_URL.getShareConfig, {
                    url: url
                }).success(function (data) {
                    if (data.success) {
                        // 通过config接口注入权限验证配置
                        wx.config({
//                          debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: data.appId, // 必填，公众号的唯一标识
                            timestamp: data.timeStamp, // 必填，生成签名的时间戳
                            nonceStr: data.nonceStr, // 必填，生成签名的随机串
                            signature: data.signature,// 必填，签名，见附录1
                            jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });
                        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                        wx.ready(function() {
                            // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
                            wx.onMenuShareTimeline({
                                title: cfg.title, // 分享标题
                                link: cfg.link + '/' + data.userId, // 分享链接
                                imgUrl: cfg.imgUrl,
                                success: function () {
                                    // 用户确认分享后执行的回调函数
                                    if (cfg.success) {
                                        cfg.success.apply(this, ['Timeline']);
                                    }
                                },
                                cancel: function () {
                                    // 用户取消分享后执行的回调函数
                                }
                            });
                            // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
                            wx.onMenuShareAppMessage({
                                title: cfg.title, // 分享标题
                                desc: cfg.desc, // 分享描述
                                link: cfg.link + '/' + data.userId, // 分享链接
                                imgUrl: cfg.imgUrl,
                                type: '', // 分享类型,music、video或link，不填默认为link
                                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                                success: function () {
                                    // 用户确认分享后执行的回调函数
                                    if (cfg.success) {
                                        cfg.success.apply(this, ['AppMessage']);
                                    }
                                },
                                cancel: function () {
                                    // 用户取消分享后执行的回调函数
                                }
                            });
                        });
                        // 通过error接口处理失败验证
                        wx.error(function(res){
                            // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
                            alert(res.toString);
                        });
                    } else {
                        console.log('获取微信分享配置出错');
                    }
                }).error(function (data) {
                    console.log('获取微信分享配置出错');
                });
            }

        };
        return WechatShare;
    })
    .filter('trustHtml', function ($sce) {
        return function (input) {
            return $sce.trustAsHtml(input);
        }
    })
    .filter("cutByLength", function() {
        return function (input, maxLength) {
            input = input || '';
            var output = '';
            if (maxLength == null || maxLength < 0 ) {
                maxLength = 0;
            }
            if (input.length > maxLength) {
                output = input.substr(0, maxLength) + "...";
            } else {
                output = input;
            }
            return output;
        }
    })
;
